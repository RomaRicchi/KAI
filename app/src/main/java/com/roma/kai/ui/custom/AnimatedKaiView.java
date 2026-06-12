package com.roma.kai.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.roma.kai.R;

public class AnimatedKaiView extends AppCompatImageView {

    private Handler handler = new Handler(Looper.getMainLooper());

    private int[] frames;
    private int currentFrame = 0;

    private final Runnable animator = new Runnable() {
        @Override
        public void run() {
            if(frames == null || frames.length == 0)
                return;

            setImageResource(frames[currentFrame]);

            currentFrame++;

            if(currentFrame >= frames.length)
                currentFrame = 0;

            handler.postDelayed(this, 150);
        }
    };

    public AnimatedKaiView(@NonNull Context context) {
        super(context);
    }

    public AnimatedKaiView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedKaiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAnimation(String etapa, String estado) {
        if(etapa == null || estado == null) {
            frames = new int[] {
                    R.drawable.kai_bebe_feliz_1,
                    R.drawable.kai_bebe_feliz_2,
                    R.drawable.kai_bebe_feliz_3
            };
            return;
        }

        switch (etapa.toUpperCase()) {
            case "BEBE" :
                switch (estado.toUpperCase()) {
                    case "FELIZ" :
                        frames = new int[] {
                                R.drawable.kai_bebe_feliz_1,
                                R.drawable.kai_bebe_feliz_2,
                                R.drawable.kai_bebe_feliz_3
                        };
                        break;

                    case "CURIOSO" :
                        frames = new int[] {
                                R.drawable.kai_bebe_curioso_1,
                                R.drawable.kai_bebe_curioso_2,
                                R.drawable.kai_bebe_curioso_3,
                                R.drawable.kai_bebe_curioso_4,
                                R.drawable.kai_bebe_curioso_5,
                                R.drawable.kai_bebe_curioso_6,
                                R.drawable.kai_bebe_curioso_7
                        };
                        break;

                    case "DORMIDO" :
                        frames = new int[]{
                                R.drawable.kai_bebe_dormido_1
                        };
                        break;

                    case "ENOJADO" :
                        frames = new int[]{
                                R.drawable.kai_bebe_enojado_1
                        };
                        break;

                    default:
                        frames = new int[] {
                                R.drawable.kai_bebe_feliz_1,
                                R.drawable.kai_bebe_feliz_2,
                                R.drawable.kai_bebe_feliz_3
                        };
                }
                break;
            case "JOVEN" :
                switch (estado.toUpperCase()) {
                    case "FELIZ" :
                        frames = new int[] {
                                R.drawable.kai_bebe_feliz_1,
                                R.drawable.kai_bebe_feliz_2,
                                R.drawable.kai_bebe_feliz_3
                        };
                        break;

                    case "CURIOSO" :
                        frames = new int[] {
                                R.drawable.kai_bebe_curioso_1,
                                R.drawable.kai_bebe_curioso_2,
                                R.drawable.kai_bebe_curioso_3,
                                R.drawable.kai_bebe_curioso_4,
                                R.drawable.kai_bebe_curioso_5,
                                R.drawable.kai_bebe_curioso_6,
                                R.drawable.kai_bebe_curioso_7
                        };
                        break;

                    case "DORMIDO" :
                        frames = new int[]{
                                R.drawable.kai_bebe_dormido_1
                        };
                        break;

                    case "ENOJADO" :
                        frames = new int[]{
                                R.drawable.kai_bebe_enojado_1
                        };
                        break;

                    default:
                        frames = new int[] {
                                R.drawable.kai_bebe_feliz_1,
                                R.drawable.kai_bebe_feliz_2,
                                R.drawable.kai_bebe_feliz_3
                        };
                }
                break;

            case "ADULTO" :
                switch (estado.toUpperCase()) {
                    case "FELIZ" :
                        frames = new int[] {
                                R.drawable.kai_bebe_feliz_1,
                                R.drawable.kai_bebe_feliz_2,
                                R.drawable.kai_bebe_feliz_3
                        };
                        break;

                    case "CURIOSO" :
                        frames = new int[] {
                                R.drawable.kai_bebe_curioso_1,
                                R.drawable.kai_bebe_curioso_2,
                                R.drawable.kai_bebe_curioso_3,
                                R.drawable.kai_bebe_curioso_4,
                                R.drawable.kai_bebe_curioso_5,
                                R.drawable.kai_bebe_curioso_6,
                                R.drawable.kai_bebe_curioso_7
                        };
                        break;

                    case "DORMIDO" :
                        frames = new int[]{
                                R.drawable.kai_bebe_dormido_1
                        };
                        break;

                    case "ENOJADO" :
                        frames = new int[]{
                                R.drawable.kai_bebe_enojado_1
                        };
                        break;

                    default:
                        frames = new int[] {
                                R.drawable.kai_bebe_feliz_1,
                                R.drawable.kai_bebe_feliz_2,
                                R.drawable.kai_bebe_feliz_3
                        };
                }
                break;

            default:
                frames = new int[] {
                        R.drawable.kai_bebe_feliz_1,
                        R.drawable.kai_bebe_feliz_2,
                        R.drawable.kai_bebe_feliz_3
                };
        }
    }

    public void startAnimation() {
        stopAnimation();
        handler.post(animator);
    }

    public void stopAnimation() {
        handler.removeCallbacks(animator);
    }
}
