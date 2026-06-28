package com.roma.kai.utils;

import android.os.SystemClock;
import android.view.View;

/**
 * Listener personalizado para evitar el doble clic accidental.
 */
public abstract class OnSafeClickListener implements View.OnClickListener {
    private static final long MIN_CLICK_INTERVAL = 1000; // 1 segundo de intervalo
    private long lastClickTime = 0;

    public abstract void onSafeClick(View v);

    @Override
    public final void onClick(View v) {
        long currentTime = SystemClock.elapsedRealtime();
        if (currentTime - lastClickTime < MIN_CLICK_INTERVAL) {
            return;
        }
        lastClickTime = currentTime;
        onSafeClick(v);
    }
}
