package com.vnpay.vexemphim.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.vnpay.vexemphim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thebv on 22/12/2016.
 */

public class LoadingIOSCustom extends View {

    private final List<Integer> arrAlphaPosition = new ArrayList<>();
    private int countLine = 12;
    private int durationAnimation = 50;

    public LoadingIOSCustom(Context context) {
        super(context);
        initAnimation();
    }

    public LoadingIOSCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation();
    }

    public LoadingIOSCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingIOSCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAnimation();
    }

    public void initAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 0);
        animation.setDuration(durationAnimation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (arrAlphaPosition.size() == 12) {
                    moveObjBottomToTopOfList(arrAlphaPosition);
                    invalidate();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    public void moveObjBottomToTopOfList(List<Integer> arr) {
        int cache = arr.get(arr.size() - 1);
        for (int i = arr.size() - 1; i > 0; i--) {
            arr.set(i, arr.get(i - 1));
        }
        arr.set(0, cache);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int widthOfLine = Math.min(getWidth(), getHeight()) / countLine;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(widthOfLine);
        paint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i < countLine; i++) {
            int alpha = i;
            if (arrAlphaPosition.size() != countLine)
                arrAlphaPosition.add(i);
            else
                alpha = arrAlphaPosition.get(i);

            int color = getResources().getColor(R.color.vxp_color_default_app);

            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = (color >> 0) & 0xFF;

            paint.setColor(Color.argb(255 / countLine * alpha, r, g, b));

            PointF position1 = getPosition(new PointF(getWidth() / 2, getHeight() / 2), Math.min(getWidth() / 2 - widthOfLine / 2, getHeight() / 2 - widthOfLine / 2), 360 / 12 * i);
            PointF position2 = getPosition(new PointF(getWidth() / 2, getHeight() / 2), Math.min(getWidth() / 2.5f - widthOfLine, getHeight() / 2.5f - widthOfLine), 360 / 12 * i);

            canvas.drawLine(position1.x, position1.y, position2.x, position2.y, paint);
        }
    }

    private PointF getPosition(PointF center, float radius, float angle) {
        return new PointF((float) (center.x + radius * Math.cos(Math.toRadians(angle))), (float) (center.y + radius * Math.sin(Math.toRadians(angle))));
    }
}
