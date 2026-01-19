package com.kononovco.haircutselectionbyphoto;

import android.net.Uri;
import android.os.Bundle;
import android.content.*;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class BitmapSelector {

    private final AppCompatActivity activity;
    private final IntentResult callback;

    private ActivityResultLauncher<Intent> launcher;

    public BitmapSelector(AppCompatActivity activity, IntentResult callback) {
        this.activity = activity;
        this.callback = callback;

        register();
    }

    private void register() {
        ActivityResultContracts.StartActivityForResult result;

        result = new ActivityResultContracts.StartActivityForResult();
        launcher = activity.registerForActivityResult(result, callback::onResult);
    }

    public Bitmap getBitmapFromCamera(Intent data) {
        Bundle bundle = data.getExtras();

        if (bundle != null) {
            Bitmap bitmap = (Bitmap) bundle.get("data");

            if (bitmap != null) {
                int height = 2000;
                int width = BitmapUtils.getWidthByHeight(bitmap, height);

                return BitmapUtils.scale(bitmap, width, height);
            }
        }

        return null;
    }

    public Bitmap getBitmapFromGallery(Intent data) {
        Uri uri = data.getData();
        ContentResolver resolver = activity.getContentResolver();

        if (uri != null) {
            try {
                return MediaStore.Images.Media.getBitmap(resolver, uri);
            }

            catch (Exception ignored) {}
        }

        return null;
    }

    public void openCamera() {
        Intent intent = new Intent();

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(intent);
    }

    public void openGallery() {
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        launcher.launch(intent);
    }
}