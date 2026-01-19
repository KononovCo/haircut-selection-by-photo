package com.kononovco.haircutselectionbyphoto;

import android.view.*;
import android.widget.*;
import android.Manifest;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.*;
import androidx.annotation.NonNull;
import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, IntentResult {

    private NavigationBar menu;
    private Permission permission;

    private BitmapSelector selector;
    private BitmapRepository repository;

    private ImageView photo;
    private TextView message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        menu = new NavigationBar(this);
        permission = new Permission(this);

        selector = new BitmapSelector(this, this);
        repository = new BitmapRepository(this);

        photo = findViewById(R.id.photo);
        message = findViewById(R.id.message);

        menu.setTitle(getString(R.string.upload));
        menu.showBackButton();

        menu.showCameraButton();
        menu.showGalleryButton();
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
        int id = item.getItemId();

        if (id == R.id.camera_item) {
            String camera = Manifest.permission.CAMERA;

            if (permission.isGranted(camera)) selector.openCamera();
            else permission.request(camera);
        }

        else if (id == R.id.gallery_item) selector.openGallery();
        return menu.onNavigationBarListener(item);
    }

    @Override
    public void onClick(View v) {
        Drawable drawable = photo.getDrawable();

        if (drawable != null) {
            Redirect redirect = new Redirect(this);

            String query = ((Button) v).getText().toString();
            Bitmap photo = ((BitmapDrawable) drawable).getBitmap();

            repository.saveToTemp(photo);

            if (repository.load() != null) {
                redirect.start(DrawActivity.class, query);
            }
        }

        else {
            message.setTextColor(getColor(R.color.red));
            message.setText(getString(R.string.photo_error));
        }
    }

    @Override
    public void onResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();

            if (data != null) {
                Bitmap camera = selector.getBitmapFromCamera(data);
                Bitmap gallery = selector.getBitmapFromGallery(data);

                Bitmap photo = camera != null ? camera : gallery;
                BitmapValidator validator = new BitmapValidator();

                if (photo != null) {
                    try {
                        validator.checkFormat(photo);
                        validator.checkSize(photo);

                        if (data.getData() != null) {
                            validator.checkExtension(data.getData(), getContentResolver());
                        }

                        message.setTextColor(getColor(R.color.green));
                        message.setText(getString(R.string.success_loading_photo));

                        this.photo.setImageBitmap(photo);
                    }

                    catch (BitmapException e) {
                        message.setTextColor(getColor(R.color.red));
                        message.setText(e.getMessage(this));
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permission.isGranted(Manifest.permission.CAMERA)) {
            selector.openCamera();
        }
    }
}