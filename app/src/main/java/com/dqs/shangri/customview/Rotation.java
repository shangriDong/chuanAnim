package com.dqs.shangri.customview;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * Created by Shangri on 2016/6/7.
 */
public class Rotation {
    private Rotate3dAnimation rotation;
    private ImageView image;

    public Rotation(ImageView image) {
        this.image = image;
    }

    public void startRotation(float start, float end) {
        // 计算中心点
        final float centerX = image.getWidth() / 2.0f;
        final float centerY = image.getHeight() / 2.0f;
        //Log.d(TAG, "centerX="+centerX+", centerY="+centerY);
        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        //final Rotate3dAnimation rotation =new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        //Z轴的缩放为0
        rotation = new Rotate3dAnimation(start, end, centerX, centerY, 0f, true);
        rotation.setDuration(1);
        rotation.setFillAfter(true);
        //匀速旋转
        rotation.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotation);
    }

    public void startRotation(float start, float end, float xStart, float xEnd) {
        // 计算中心点
        final float centerX = image.getWidth() / 2.0f;
        final float centerY = image.getHeight() / 2.0f;
        //Log.d(TAG, "centerX="+centerX+", centerY="+centerY);
        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        //final Rotate3dAnimation rotation =new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        //Z轴的缩放为0
        rotation = new Rotate3dAnimation(start, end, xStart, xEnd, centerX, centerY, 0f, true);
        rotation.setDuration(1);
        rotation.setFillAfter(true);
        //匀速旋转
        rotation.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotation);
    }


    class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private final float mXFromDegrees;
        private final float mXToDegrees;
        private final float mCenterX;
        private final float mCenterY;
        private final float mDepthZ;
        private final boolean mReverse;
        private Camera mCamera;

        /**
         * Creates a new 3D rotation on the Y axis. The rotation is defined by its * start angle and its end angle. Both angles are in degrees. The rotation * is performed around a center point on the 2D space, definied by a pair * of X and Y coordinates, called centerX and centerY. When the animation * starts, a translation on the Z axis (depth) is performed. The length * of the translation can be specified, as well as whether the translation * should be reversed in time. * * @param fromDegrees the start angle of the 3D rotation * @param toDegrees the end angle of the 3D rotation * @param centerX the X center of the 3D rotation * @param centerY the Y center of the 3D rotation * @param reverse true if the translation should be reversed, false otherwise
         */
        public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mCenterX = centerX;
            mCenterY = centerY;
            mDepthZ = depthZ;
            mReverse = reverse;

            mXFromDegrees = 0;
            mXToDegrees = 0;
        }

        /**
         * Creates a new 3D rotation on the Y and Xaxis. The rotation is defined by its * start angle and its end angle. Both angles are in degrees. The rotation * is performed around a center point on the 2D space, definied by a pair * of X and Y coordinates, called centerX and centerY. When the animation * starts, a translation on the Z axis (depth) is performed. The length * of the translation can be specified, as well as whether the translation * should be reversed in time. * * @param fromDegrees the start angle of the 3D rotation * @param toDegrees the end angle of the 3D rotation * @param centerX the X center of the 3D rotation * @param centerY the Y center of the 3D rotation * @param reverse true if the translation should be reversed, false otherwise
         */
        public Rotate3dAnimation(float fromYDegrees, float toYDegrees, float fromXDegrees, float toXDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
            mFromDegrees = fromYDegrees;
            mToDegrees = toYDegrees;
            mCenterX = centerX;
            mCenterY = centerY;
            mDepthZ = depthZ;
            mReverse = reverse;

            mXFromDegrees = fromXDegrees;
            mXToDegrees = toXDegrees;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
            float xDegress = mXFromDegrees + ((mXToDegrees - mXFromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final Matrix matrix = t.getMatrix(); //保存一次camera初始状态，用于restore()
            camera.save();
            if (mReverse) {
                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
            } else {
                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
            }
            //围绕Y轴旋转degrees度
            camera.rotateY(degrees);

            //围绕X轴旋转
            camera.rotateX(xDegress);

            //行camera中取出矩阵，赋值给matrix
            camera.getMatrix(matrix);
            //camera恢复到初始状态，继续用于下次的计算
            camera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
