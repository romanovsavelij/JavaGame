package sch179.ru.openworld.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Joystick extends View {
    private final float centerX = 200f, centerY = 380f, radius = 170f;
    private float drawX = centerX, drawY = centerY, drawR = 80f;
    private float dX = 0, dY = 0;
    private boolean wasTouched = false;

    public Joystick(Context context) {
        super(context);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int c = 0;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 200, 200));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setColor(Color.rgb(80, 70, 70));
        if (wasTouched) {
            canvas.drawCircle(drawX, drawY, drawR, paint);
        }
        else {
            canvas.drawCircle(centerX, centerY, drawR, paint);
        }
        if (c == 40) {
            wasTouched = false;
            c = 0;
        }
        ++c;
        invalidate();
    }

    public boolean isClicked(float x, float y) {
        if ((centerX - x) * (centerX - x) + (centerY - y) * (centerY - y) <= radius * radius) {
            return true;
        }
        return false;
    }

    public void touched(float x, float y) {
        wasTouched = true;
        if (isClicked(x, y)) {
            drawX = x;
            drawY = y;
        }
        else {
            float len = (float) Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
            drawX = centerX + ((x - centerX) / len) * radius;
            drawY = centerY + ((y - centerY) / len) * radius;
        }
        dX = drawX - centerX;
        dY = drawY - centerY;
    }

    public float getdX() {
        return dX;
    }

    public float getdY() {
        return dY;
    }
}
