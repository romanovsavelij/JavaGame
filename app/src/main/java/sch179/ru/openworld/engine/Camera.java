package sch179.ru.openworld.engine;

import android.opengl.Matrix;
import android.util.Log;

import sch179.ru.openworld.game.GameRenderer;
import sch179.ru.openworld.utils.GameUtils;

public class Camera {
    
    private GameUtils.Vector3f positionPlayer = new GameUtils.Vector3f(0f, -100f, 0f);
    private float[] viewMatrix = new float[16];
    private float yaw = (float) Math.PI / 2, pitch = (float) -Math.PI;
    private GameRenderer gameRenderer;
    private float distantionToPlayer = 80f;

    public Camera(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    public GameUtils.Vector3f getPosition() {
        return positionPlayer;
    }

    public void TopButtonPressed(float s) {
        positionPlayer.x += s * Math.sin(yaw);
        positionPlayer.z += s * Math.cos(yaw);
    }

    public void RightButtonPressed(float s) {
        positionPlayer.x -= s * Math.sin(yaw + Math.PI / 2);
        positionPlayer.z -= s * Math.cos(yaw + Math.PI / 2);
    }

    public void LeftButtonPressed(float s) {
        positionPlayer.x += s * Math.sin(yaw + Math.PI / 2);
        positionPlayer.z += s * Math.cos(yaw + Math.PI / 2);
    }

    public void BottomButtonPressed(float s) {
        positionPlayer.x -= s * Math.sin(yaw);
        positionPlayer.z -= s * Math.cos(yaw);
    }

    public void turn(float x, float y) {
        yaw += x;
        pitch -= y;
        pitch = (float) Math.max(-Math.PI, pitch);
        pitch = (float) Math.min(-Math.PI / 2 * 4/5, pitch);
    }

    public float[] getViewMatrix() {
        Matrix.setIdentityM(viewMatrix, 0);
        GameUtils.Vector3f positionCamera = new GameUtils.Vector3f(0f, 0f, 0f);
        positionCamera.y = (float) (positionPlayer.y + distantionToPlayer * Math.sin(pitch));
        float distInXZ = (float) (distantionToPlayer * Math.cos(pitch));
        positionCamera.x = (float) (positionPlayer.x + distInXZ * Math.sin(yaw));
        positionCamera.z = (float) (positionPlayer.z + distInXZ * Math.cos(yaw));
        Matrix.rotateM(viewMatrix, 0, 180 + (float) ((pitch) * 180f / Math.PI), 1f, 0f, 0f);
        Matrix.rotateM(viewMatrix, 0, (float) (-yaw * 180f / Math.PI), 0f, 1f, 0f);
        Matrix.translateM(viewMatrix, 0, positionCamera.x, positionCamera.y, positionCamera.z);
        gameRenderer.setPlayerPosition(positionPlayer);
        return viewMatrix;
    }

}
