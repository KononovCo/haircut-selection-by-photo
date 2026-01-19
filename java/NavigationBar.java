package com.kononovco.haircutselectionbyphoto;

import android.view.*;
import androidx.appcompat.app.*;

public class NavigationBar {

    private final AppCompatActivity activity;
    private final ActionBar menu;

    private boolean langItemIsVisible;
    private boolean saveItemIsVisible;

    private boolean cameraItemIsVisible;
    private boolean galleryItemIsVisible;

    public NavigationBar(AppCompatActivity activity) {
        this.activity = activity;
        menu = activity.getSupportActionBar();
    }

    public void setTitle(String title) {
        menu.setTitle(title);
    }

    public void showBackButton() {
        menu.setDisplayHomeAsUpEnabled(true);
    }

    public void showLangButton() {
        langItemIsVisible = true;
    }

    public void showSaveButton() {
        saveItemIsVisible = true;
    }

    public void showCameraButton() {
        cameraItemIsVisible = true;
    }

    public void showGalleryButton() {
        galleryItemIsVisible = true;
    }

    public boolean onCreateNavigationBar(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.toolbar, menu);

        menu.findItem(R.id.lang_item).setVisible(langItemIsVisible);
        menu.findItem(R.id.save_item).setVisible(saveItemIsVisible);

        menu.findItem(R.id.camera_item).setVisible(cameraItemIsVisible);
        menu.findItem(R.id.gallery_item).setVisible(galleryItemIsVisible);

        return true;
    }

    public boolean onNavigationBarListener(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            activity.getOnBackPressedDispatcher().onBackPressed();
        }

        return true;
    }
}