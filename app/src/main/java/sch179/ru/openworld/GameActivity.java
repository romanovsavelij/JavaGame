package sch179.ru.openworld;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.ActivityManager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import sch179.ru.openworld.engine.Camera;
import sch179.ru.openworld.engine.Joystick;
import sch179.ru.openworld.utils.GameUtils;
import sch179.ru.openworld.game.GameRenderer;

public class GameActivity extends Activity implements View.OnTouchListener {

    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    Camera camera;
    private float xold = -1, yold = -1;
    private final float SPEED = 0.01f;                   //<----- MOVING SPEED
    Joystick joystick;

    void createSuffaceView() {

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(3);
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportsEs2) {
            glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            GameRenderer gameRenderer = new GameRenderer(); camera = gameRenderer.getCamera();
            glSurfaceView.setRenderer(gameRenderer); rendererSet = true;
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG).show(); return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameUtils.setAssetManager(this.getAssets());
        GameUtils.setContext(this);

        setContentView(R.layout.game_activity);

        createSuffaceView();

        RelativeLayout item = findViewById(R.id.rl);

        item.addView(glSurfaceView);

        item.setOnTouchListener(this);

        joystick = findViewById(R.id.joystick);
    }


    @Override
    protected void onPause() { super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }
    @Override
    protected void onResume() { super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (joystick.isClicked(x, y)) {
            joystick.touched(x, y);
            float dX = joystick.getdX(), dY = joystick.getdY();
            dX *= SPEED;
            dY *= SPEED;
            if (dX > 0) {
                camera.RightButtonPressed(dX);
            }
            else {
                camera.LeftButtonPressed(-dX);
            }
            if (dY < 0) {
                camera.TopButtonPressed(-dY);
            }
            else {
                camera.BottomButtonPressed(dY);
            }
            return true;
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (xold != -1 && yold != -1) {
                float deltaX = x - xold;
                float deltaY = y - yold;
                float SPEEDR = 0.01f;               //     <---- ROTATION SPEED
                deltaX *= SPEEDR;
                deltaY *= SPEEDR;
                camera.turn(deltaX, deltaY);
            }
        }
        xold = x;
        yold = y;
        return true;
    }

}
