package com.roma.kai.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RadarChartView extends View {

    private Paint webPaint;
    private Paint dataPaint;
    private Paint labelPaint;
    
    private float radius;
    private int centerX;
    private int centerY;
    
    private int count = 0;
    private float angle = 0;
    
    private List<Float> data = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    private float maxValue = 100f;

    public RadarChartView(Context context) {
        super(context);
        init();
    }

    public RadarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        webPaint = new Paint();
        webPaint.setColor(Color.LTGRAY);
        webPaint.setAntiAlias(true);
        webPaint.setStyle(Paint.Style.STROKE);
        webPaint.setStrokeWidth(2f);

        dataPaint = new Paint();
        dataPaint.setColor(Color.parseColor("#39A18E")); // Color KAI
        dataPaint.setAlpha(150);
        dataPaint.setAntiAlias(true);
        dataPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        labelPaint = new Paint();
        labelPaint.setColor(Color.parseColor("#1B5E50"));
        labelPaint.setTextSize(28f);
        labelPaint.setAntiAlias(true);
        labelPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2f * 0.7f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count < 3) return;
        
        drawWeb(canvas);
        drawData(canvas);
        drawLabels(canvas);
    }

    private void drawWeb(Canvas canvas) {
        Path path = new Path();
        float r = radius / 4; // 4 niveles de red
        for (int i = 1; i <= 4; i++) {
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                float curAngle = angle * j;
                float x = (float) (centerX + curR * Math.sin(curAngle));
                float y = (float) (centerY - curR * Math.cos(curAngle));
                if (j == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, webPaint);
        }
        
        // Líneas desde el centro
        for (int i = 0; i < count; i++) {
            float curAngle = angle * i;
            float x = (float) (centerX + radius * Math.sin(curAngle));
            float y = (float) (centerY - radius * Math.cos(curAngle));
            canvas.drawLine(centerX, centerY, x, y, webPaint);
        }
    }

    private void drawData(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            float percent = data.get(i) / maxValue;
            float curAngle = angle * i;
            float x = (float) (centerX + radius * Math.sin(curAngle) * percent);
            float y = (float) (centerY - radius * Math.cos(curAngle) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, dataPaint);
    }

    private void drawLabels(Canvas canvas) {
        Paint.FontMetrics fontMetrics = labelPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float curAngle = angle * i;
            float x = (float) (centerX + (radius + fontHeight) * Math.sin(curAngle));
            float y = (float) (centerY - (radius + fontHeight) * Math.cos(curAngle));
            
            canvas.drawText(labels.get(i), x, y, labelPaint);
        }
    }

    public void setData(List<String> newLabels, List<Float> newData) {
        if (newLabels.size() == newData.size() && newLabels.size() >= 3) {
            this.labels = newLabels;
            this.data = newData;
            this.count = labels.size();
            this.angle = (float) (Math.PI * 2 / count);
            
            // Ajustar maxValue si es necesario
            for (Float val : data) {
                if (val > maxValue) maxValue = val;
            }

            invalidate();
        }
    }
}
