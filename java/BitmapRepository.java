package com.kononovco.haircutselectionbyphoto;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import android.graphics.*;
import android.os.Environment;
import android.content.Context;
import android.media.MediaScannerConnection;

public class BitmapRepository {

    private final Context context;
    private final File temp;

    public BitmapRepository(Context context) {
        this.context = context;
        temp = new File(context.getCacheDir() + File.separator + "temp.png");
    }

    private File getImageCatalog() {
        String dir = Environment.DIRECTORY_PICTURES;
        String path = Environment.getExternalStoragePublicDirectory(dir).getAbsolutePath();

        path += File.separator + context.getString(R.string.company);
        return new File(path);
    }

    private void save(String path, Bitmap bitmap) {
        try {
            FileOutputStream stream = new FileOutputStream(path);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        }

        catch (IOException ignored) {}
    }

    public Bitmap load() {
        return BitmapFactory.decodeFile(temp.getAbsolutePath());
    }

    public void close() {
        temp.delete();
    }

    public void saveToTemp(Bitmap bitmap) {
        save(temp.getAbsolutePath(), bitmap);
    }

    public void saveToGallery(Bitmap bitmap) {
        final String DATE_FORMAT = "dd_MM_yyyy_HH_mm_ss";
        String date = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());

        File dir = getImageCatalog();
        String path = dir.getAbsolutePath();

        if (!dir.exists()) dir.mkdirs();
        path += File.separator + date + ".png";

        save(path, bitmap);
        MediaScannerConnection.scanFile(context, new String[] {path}, null, null);
    }
}