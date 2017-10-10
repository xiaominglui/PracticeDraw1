package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Practice10HistogramView extends View {
    Paint paint;
    TextPaint textPaint;
    Map<String, Float> data;
    int GAP_WIDTH = 20;
    int HORIZONTAL_MARGIN = 50;
    int VERTICAL_MARGIN = 30;

    int AXIS_WIDTH = 2;

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        textPaint.setColor(Color.WHITE);
        data = new HashMap<>();
        data.put("Froyo", 0.2f);
        data.put("GB", 0.6f);
        data.put("ICS", 0.6f);
        data.put("JB", 6.6f);
        data.put("KitKat", 14.5f);
        data.put("L", 27.7f);
        data.put("M", 32f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int number = data.size();

        int itemWidth = (canvasWidth - GAP_WIDTH * (number + 1) - AXIS_WIDTH - HORIZONTAL_MARGIN * 2) / number;


        // draw title
        String title = "直方图";
        int titleWidth = (int) textPaint.measureText(title);
        textPaint.setTextAlign(Paint.Align.LEFT);
        Rect r = new Rect();
        textPaint.getTextBounds(title, 0, title.length(), r);
        float titleX = (canvasWidth - titleWidth)/2;
        float titleY = canvasHeight - (r.height() + r.top) - VERTICAL_MARGIN;
        canvas.drawText(title, titleX, titleY, textPaint);

        // draw X axis and Y axis
        paint.setStrokeWidth(AXIS_WIDTH);
        paint.setColor(Color.WHITE);
        float axisXYStartX = HORIZONTAL_MARGIN;
        float axisXYStartY = canvasHeight - VERTICAL_MARGIN - titleWidth;
        float axisXEndX = axisXYStartX + GAP_WIDTH * (number + 1) + itemWidth * number;
        float axisXEndY = axisXYStartY;
        canvas.drawLine(axisXYStartX, axisXYStartY, axisXEndX, axisXEndY, paint);

        float factorItemHeight = (axisXYStartY - AXIS_WIDTH - 2 * VERTICAL_MARGIN) / 32f;


        float axisYEndX = axisXYStartX;
        float axisYEndY = axisXYStartY - factorItemHeight * 32f - GAP_WIDTH;
        canvas.drawLine(axisXYStartX, axisXYStartY, axisYEndX, axisYEndY, paint);

        // draw item bar and item title
        paint.setColor(Color.parseColor("#72b916"));

        int current = 0;

        textPaint.setTextSize(12 * getResources().getDisplayMetrics().density);
        Iterator<Map.Entry<String, Float>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Float> next = iterator.next();
            float rawHeight = next.getValue();
            float itemHeight = rawHeight * factorItemHeight;

            int left = (int) ((current + 1)*GAP_WIDTH + itemWidth * current + axisXYStartX);
            int top = (int) (axisXYStartY - itemHeight);
            int right = left + itemWidth;
            int bottom = (int) axisXYStartY;
            canvas.drawRect(new Rect(left, top, right, bottom), paint);
            current = current + 1;

            String t = next.getKey();
            int tWidth = (int) textPaint.measureText(t);
            textPaint.setTextAlign(Paint.Align.LEFT);
            Rect rect = new Rect();
            textPaint.getTextBounds(t, 0, t.length(), rect);
            float tX = left + (itemWidth/2 - rect.width()/2);
            float tY = bottom - rect.top;
            canvas.drawText(t, tX, tY, textPaint);
        }
    }
}
