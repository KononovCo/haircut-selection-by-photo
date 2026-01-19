package com.kononovco.haircutselectionbyphoto;

import android.view.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.yandex.mobile.ads.common.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, InitializationListener {

    private AppLanguage lang;
    private NavigationBar menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, this);

        lang = new AppLanguage(this);
        menu = new NavigationBar(this);

        lang.load();
        menu.showLangButton();

        menu.setTitle(getString(R.string.app));
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return this.menu.onCreateNavigationBar(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ru_item) lang.save("ru");
        else if (id == R.id.en_item) lang.save("en");

        return menu.onNavigationBarListener(item);
    }

    @Override
    public void onClick(View v) {
        Redirect redirect = new Redirect(this);
        redirect.start(UploadActivity.class);
    }

    @Override
    public void onInitializationCompleted() {
        Interstitial interstitial = new Interstitial(this);
        Banner banner = new Banner(this);

        interstitial.load();
        banner.load();
    }
}