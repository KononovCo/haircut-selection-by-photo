package com.kononovco.haircutselectionbyphoto;

import android.os.Bundle;
import android.app.Dialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MessageBox extends DialogFragment {

    private String message;
    private DialogInterface.OnClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage(message);

            dialog.setPositiveButton(R.string.positive, listener);
            dialog.setNegativeButton(R.string.negative, null);

            return dialog.create();
        }

        return super.onCreateDialog(savedInstanceState);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setListener(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }
}