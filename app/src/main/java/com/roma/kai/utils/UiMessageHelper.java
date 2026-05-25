package com.roma.kai.utils;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.roma.kai.R;

public class UiMessageHelper {

    public static void showMessage(View view, Context context, UiMessage uiMessage) {
        Snackbar snackbar = Snackbar.make(
                view,
                uiMessage.getMessage(),
                Snackbar.LENGTH_SHORT
        );

        View snackbarView = snackbar.getView();

        switch(uiMessage.getType()) {

            case SUCCESS:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.success
                        )
                );
                break;
            case ERROR:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.error
                        )
                );
                break;
            case WARNING:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.warning
                        )
                );
                break;
            case INFO:
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.info
                        )
                );
                break;
        }

        snackbar.show();
    }
}
