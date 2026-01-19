package com.kononovco.haircutselectionbyphoto;

import android.net.Uri;
import android.graphics.Bitmap;
import android.webkit.MimeTypeMap;
import android.content.ContentResolver;

public class BitmapValidator {

    public void checkFormat(Bitmap bitmap) throws BitmapException {
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            throw new BitmapException(BitmapException.FORMAT_ERROR);
        }
    }

    public void checkSize(Bitmap bitmap) throws BitmapException {
        if (bitmap.getHeight() > 3000 || bitmap.getWidth() < 50) {
            throw new BitmapException(BitmapException.SIZE_ERROR);
        }
    }

    public void checkExtension(Uri uri, ContentResolver resolver) throws BitmapException {
        String mimeType = resolver.getType(uri);
        String ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

        if (ext != null) {
            ext = ext.toLowerCase();

            if (!ext.contains("png") && !ext.contains("jpg")) {
                throw new BitmapException(BitmapException.EXTENSION_ERROR);
            }
        }
    }
}