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
    private final float centerX = 200f, centerY = 300f, radius = 170f;
    private float drawX = centerX, drawY = centerY, drawR = 80f;
    private float dX = 0, dY = 0;
    private boolean wasTouched = false;

    public Joystick(Context context) {
        super(context);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

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
        if (!isClicked(x, y)) {
            return;
        }
        dX = x - centerX;
        dY = y - centerY;
        drawX = x;
        drawY = y;
    }

    public float getdX() {
        return dX;
    }

    public float getdY() {
        return dY;
    }
}
