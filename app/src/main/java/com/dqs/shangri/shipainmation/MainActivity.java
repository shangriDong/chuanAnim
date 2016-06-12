package com.dqs.shangri.shipainmation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dqs.shangri.customview.MatrixDraweeView;
import com.dqs.shangri.customview.Rotation;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends Activity {

    private Animation inAnim;
    private View iv_temp;
    private Handler handler = new Handler();
    private ImageView heartView;
    private ImageView arrowIV;
    private int relativeToParent;
    private MatrixDraweeView yacht_user_portrait;
    private ImageView yacht_water_view_one;
    private ImageView yacht_water_view_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.yacht_layout);
        heartView = (ImageView) findViewById(R.id.yacht_heart);
        arrowIV = (ImageView) findViewById(R.id.yacht_heart_arrow);
        iv_temp = findViewById(R.id.yacht_hull_container);
        yacht_user_portrait = (MatrixDraweeView) findViewById(R.id.yacht_user_portrait);
        yacht_water_view_one = (ImageView) findViewById(R.id.yacht_water_view_one);
        yacht_water_view_two = (ImageView) findViewById(R.id.yacht_water_view_two);

        //背景 水动画
        moveYechtWaterOne();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveYechtWaterTwo();
            }

        }, 1000);

        //轮船头像
        Rotation rotation = new Rotation(yacht_user_portrait);
        rotation.startRotation(0, 40, 0, 15);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iv_temp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_temp.setVisibility(View.GONE);

                if (yacht_water_view_one.getVisibility() == View.VISIBLE) {
                    yacht_water_view_one.setVisibility(View.GONE);
                }

                if (yacht_water_view_two.getVisibility() == View.VISIBLE) {
                    yacht_water_view_two.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeToParent = Animation.RELATIVE_TO_PARENT;
        final TranslateAnimation trans1 = new TranslateAnimation(relativeToParent, -1, relativeToParent, 0, relativeToParent, 0, relativeToParent, 0);
        TranslateAnimation trans2 = new TranslateAnimation(relativeToParent, 0, relativeToParent, 1, relativeToParent, 0, relativeToParent, 0);
        trans1.setDuration(2000);
        trans2.setDuration(2000);
        trans1.setInterpolator(new DecelerateInterpolator());
        trans2.setInterpolator(new AccelerateInterpolator());
        trans2.setStartOffset(3800);
        animationSet.addAnimation(trans1);
        animationSet.addAnimation(trans2);
        iv_temp.setAnimation(animationSet);
        animationSet.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //心 入场动画
                heartView.setBackgroundResource(R.drawable.hear_anim);
                AnimationDrawable background = (AnimationDrawable) heartView.getBackground();
                background.start();

                /*//心 变大
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(800);
                scaleAnimation.setStartOffset(500);
                scaleAnimation.setFillAfter(true);
                heartView.startAnimation(scaleAnimation);

                //心 退场动画
                AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
                alphaAnimation.setDuration(500);
                alphaAnimation.setStartOffset(1800);
                alphaAnimation.setFillAfter(true);
                //heartView.setAnimation(alphaAnimation);
                heartView.startAnimation(alphaAnimation);*/

                heartView(heartView);


                //剑 动画
                AnimationSet arrSet = new AnimationSet(false);
                Animation endAnim = AnimationUtils.loadAnimation(getApplication(), R.anim.ship_arrow_end);
                endAnim.setStartOffset(1000);
                endAnim.setDuration(1500);
                endAnim.setInterpolator(new LinearInterpolator());

                Animation startAnim = AnimationUtils.loadAnimation(getApplication(), R.anim.ship_arrow_start);
                startAnim.setStartOffset(500);
                startAnim.setDuration(1500);
                startAnim.getFillBefore();
                startAnim.setInterpolator(new LinearInterpolator());

                arrSet.addAnimation(startAnim);
                arrSet.addAnimation(endAnim);
                arrSet.setFillAfter(true);
                arrSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        arrowIV.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        arrowIV.setVisibility(View.GONE);
                    }
                });
                arrowIV.setAnimation(arrSet);

                /*AnimationSet arrSet = new AnimationSet(false);
//                RotateAnimation rotateArrow1=new RotateAnimation();
                TranslateAnimation transArrow1 = new TranslateAnimation(relativeToParent, -1, relativeToParent, 0, relativeToParent, 0.3f, relativeToParent, 0f);
                TranslateAnimation transArrow2 = new TranslateAnimation(relativeToParent, 0, relativeToParent, 1, relativeToParent, 0f, relativeToParent, 0.3f);
                transArrow1.setDuration(1000);
                transArrow2.setDuration(1000);

                RotateAnimation rotateArrow = new RotateAnimation(-20, 10);
                rotateArrow.setDuration(1000);
                //arrowIV.startAnimation(rotateArrow);


                arrSet.addAnimation(transArrow1);

                //arrSet.addAnimation(transArrow2);
                //arrSet.addAnimation(rotateArrow);
                arrSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        arrowIV.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        arrowIV.setVisibility(View.GONE);
                    }
                });
                transArrow2.setStartOffset(1000);
                arrSet.setFillAfter(true);
                arrowIV.setAnimation(arrSet);*/


                //rotateViewDelay(500, arrowIV);
                //arrowLy.startAnimation(arrSet);
            }
        }, 1800);
    }

    /*private void shearTransformation(ImageView view) {
        Matrix matrix = new Matrix();
        matrix.setSkew(0.0f, 0.5f);

        *//*Canvas canvas = new Canvas();
        canvas.drawBitmap(BitmapFactory.decodeResource(res, R.drawable.ic_launcher), matrix, null);*//*
        view.setImageMatrix(matrix);

    }*/
    /*private void shearTransformation(MatrixDraweeView view) {
        Matrix matrix = new Matrix();
        // 输出图像的宽度和高度(162 x 251)
        //Log.e("TestTransformMatrixActivity", "image size: width x height = " + view.getImageBitmap().getWidth() + " x " + view.getImageBitmap().getHeight());
        // 1. 平移
        //matrix.postTranslate(view.getImageBitmap().getWidth(), view.getImageBitmap().getHeight());
        // 在x方向平移view.getImageBitmap().getWidth()，在y轴方向view.getImageBitmap().getHeight()
        matrix.setSkew(0.0f, 0.5f);
        view.setImageMatrix(matrix);

        // 下面的代码是为了查看matrix中的元素
        *//*float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        for (int i = 0; i < 3; ++i) {
            String temp = new String();
            for (int j = 0; j < 3; ++j) {
                temp += matrixValues[3 * i + j] + "\t";
            }
            Log.e("TestTransformMatrixActivity", temp);
        }*//*


//          // 2. 旋转(围绕图像的中心点)
//          matrix.setRotate(45f, view.getImageBitmap().getWidth() / 2f, view.getImageBitmap().getHeight() / 2f);
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(view.getImageBitmap().getWidth() * 1.5f, 0f);
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }


//          // 3. 旋转(围绕坐标原点) + 平移(效果同2)
//          matrix.setRotate(45f);
//          matrix.preTranslate(-1f * view.getImageBitmap().getWidth() / 2f, -1f * view.getImageBitmap().getHeight() / 2f);
//          matrix.postTranslate((float)view.getImageBitmap().getWidth() / 2f, (float)view.getImageBitmap().getHeight() / 2f);
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate((float)view.getImageBitmap().getWidth() * 1.5f, 0f);
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }

//          // 4. 缩放
//          matrix.setScale(2f, 2f);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(view.getImageBitmap().getWidth(), view.getImageBitmap().getHeight());
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }


//          // 5. 错切 - 水平
//          matrix.setSkew(0.5f, 0f);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(view.getImageBitmap().getWidth(), 0f);
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }

//          // 6. 错切 - 垂直
//          matrix.setSkew(0f, 0.5f);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(0f, view.getImageBitmap().getHeight());
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }

//          7. 错切 - 水平 + 垂直
//          matrix.setSkew(0.5f, 0.5f);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(0f, view.getImageBitmap().getHeight());
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }

//          // 8. 对称 (水平对称)
//          float matrix_values[] = {1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
//          matrix.setValues(matrix_values);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(0f, view.getImageBitmap().getHeight() * 2f);
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }

//          // 9. 对称 - 垂直
//          float matrix_values[] = {-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
//          matrix.setValues(matrix_values);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(view.getImageBitmap().getWidth() * 2f, 0f);
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }


//          // 10. 对称(对称轴为直线y = x)
//          float matrix_values[] = {0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f};
//          matrix.setValues(matrix_values);
//          // 下面的代码是为了查看matrix中的元素
//          float[] matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }
//
//          // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
//          matrix.postTranslate(view.getImageBitmap().getHeight() + view.getImageBitmap().getWidth(),
//                  view.getImageBitmap().getHeight() + view.getImageBitmap().getWidth());
//          view.setImageMatrix(matrix);
//
//          // 下面的代码是为了查看matrix中的元素
//          matrixValues = new float[9];
//          matrix.getValues(matrixValues);
//          for(int i = 0; i < 3; ++i)
//          {
//              String temp = new String();
//              for(int j = 0; j < 3; ++j)
//              {
//                  temp += matrixValues[3 * i + j ] + "\t";
//              }
//              Log.e("TestTransformMatrixActivity", temp);
//          }

        view.invalidate();
    }*/

    private void moveYechtWaterOne() {
        yacht_water_view_one.setVisibility(View.VISIBLE);
        /*AnimationSet arrSet = new AnimationSet(false);
        Animation animation_left = new TranslateAnimation(0, 100, 0, 0);
        animation_left.setDuration(1000);
        animation_left.setFillAfter(true);

        Animation animation_right = new TranslateAnimation(0, -100, 0, 0);
        animation_right.setDuration(1000);
        animation_right.setFillAfter(true);

        arrSet.addAnimation(animation_left);
        arrSet.addAnimation(animation_right);
        yacht_water_view_one.setAnimation(arrSet);

        arrSet.start();*/
        final AnimatorSet arrSet = new AnimatorSet();
        ObjectAnimator translate_left = ObjectAnimator.ofFloat(yacht_water_view_one, View.TRANSLATION_X, 0, 70);
        ObjectAnimator translate_right = ObjectAnimator.ofFloat(yacht_water_view_one, View.TRANSLATION_X, 70, 0);
        arrSet.setDuration(1000);
        arrSet.playSequentially(translate_left, translate_right);
        arrSet.setTarget(yacht_water_view_one);
        translate_left.setRepeatMode(ValueAnimator.INFINITE);
        arrSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                arrSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        arrSet.start();
        //arrSet.setStartDelay(500);

        //yacht_water_view_one.startAnimation(animation_left);
        //yacht_water_view_one.startAnimation(animation_right);
    }

    private void moveYechtWaterTwo() {
        yacht_water_view_two.setVisibility(View.VISIBLE);
        final AnimatorSet arrSetTwo = new AnimatorSet();

        /*Animator animation_right = new TranslateAnimation(0, -100, 0, 0);
        animation_right.setDuration(1000);
        animation_right.setFillAfter(true);

        Animator animation_left = new TranslateAnimation(0, 100, 0, 0);
        animation_left.setDuration(1000);
        animation_left.setFillAfter(true);
        //yacht_water_view_two.startAnimation(animation_left);

        //arrSet.addAnimation(animation_right);
        //arrSet.addAnimation(animation_left);
        arrSet.playSequentially(animation_right, animation_left);
        //arrSet.setRepeatCount(10);
        yacht_water_view_one.setAnimation(arrSet);

        arrSet.startNow();*/

        //yacht_water_view_two.startAnimation(animation_right);

        ObjectAnimator translate_left = ObjectAnimator.ofFloat(yacht_water_view_two, View.TRANSLATION_X, 0, 70);
        ObjectAnimator translate_right = ObjectAnimator.ofFloat(yacht_water_view_two, View.TRANSLATION_X, 70, 0);

        arrSetTwo.setDuration(1000);

        arrSetTwo.playSequentially(translate_left, translate_right);
        arrSetTwo.setTarget(yacht_water_view_two);
        translate_left.setRepeatMode(ValueAnimator.INFINITE);
        arrSetTwo.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                arrSetTwo.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        arrSetTwo.start();
    }


    private void rotateViewDelay(final long delay, final View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RotateAnimation rotateArrow = new RotateAnimation(0, 10);
                rotateArrow.setDuration(1000);
                view.startAnimation(rotateArrow);
            }
        }, delay);
    }

    private void heartView(View view) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f);
        scaleX.setStartDelay(200);
        scaleX.setDuration(1500);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f);
        scaleX.setStartDelay(200);
        scaleY.setDuration(1500);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        alpha.setDuration(500);

        AnimatorSet.Builder builder = animatorSet.play(scaleX);
        builder.with(scaleY);
        builder.before(alpha);


        animatorSet.setTarget(view);
        animatorSet.start();

    }
}
