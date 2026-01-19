package com.kononovco.haircutselectionbyphoto;

import android.view.*;
import android.widget.*;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigationBar menu;
    private BitmapRepository repository;

    private DrawView drawView;
    private DatabaseLoader loader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);

        ViewGroup screen = findViewById(R.id.screen);
        String query = new Redirect(this).getQuery();
        Banner banner = new Banner(this);

        menu = new NavigationBar(this);
        repository = new BitmapRepository(this);

        drawView = findViewById(R.id.draw_view);
        loader = new DatabaseLoader(this, query);

        if (loader.load() && repository.load() != null) {
            menu.setTitle(query);

            menu.showBackButton();
            menu.showSaveButton();

            drawView.setBackground(repository.load());
            createContent();

            banner.add(screen);
            banner.load();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repository.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return this.menu.onCreateNavigationBar(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_item) {
            MessageBox message = new MessageBox();
            Interstitial interstitial = new Interstitial(this);

            message.setMessage(getString(R.string.confirm_loading_result));

            message.setListener((dialog, which) -> {
                repository.saveToGallery(drawView.getSaved());

                Toast.makeText(this, R.string.success_loading_result, Toast.LENGTH_SHORT).show();
                interstitial.show();
            });

            message.show(getSupportFragmentManager(), null);
        }

        return menu.onNavigationBarListener(item);
    }

    @Override
    public void onClick(View v) {
        ImageButton button = (ImageButton) v;
        Bitmap bitmap = ((BitmapDrawable) button.getDrawable()).getBitmap();

        drawView.createBitmapShape(bitmap);
    }

    private void createContent() {
        ViewGroup content = findViewById(R.id.content);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < loader.getData().length; i++) {
            View view = inflater.inflate(R.layout.hair_button, content, false);
            ImageButton button = (ImageButton) view;

            button.setImageResource(loader.getData()[i]);
            button.setOnClickListener(this);

            content.addView(button);
        }
    }
}