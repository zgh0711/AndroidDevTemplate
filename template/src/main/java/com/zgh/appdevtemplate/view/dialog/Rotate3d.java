package com.zgh.appdevtemplate.view.dialog;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 *   3D横向旋转动画
 */
public class Rotate3d extends Animation {
    private float mCenterX = 0;
    private float mCenterY = 0;



    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCenterX = width/2;
        mCenterY = height/2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        Camera camera = new Camera();
        camera.save();
        camera.rotateY(720 * interpolatedTime);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
    }
}
