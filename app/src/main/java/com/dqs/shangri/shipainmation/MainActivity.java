package com.dqs.shangri.shipainmation;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.yacht_layout);
        heartView = (ImageView) findViewById(R.id.yacht_heart);
        arrowIV = (ImageView) findViewById(R.id.yacht_heart_arrow);
        iv_temp = findViewById(R.id.yacht_hull_container);
        yacht_user_portrait = (MatrixDraweeView) findViewById(R.id.yacht_user_portrait);

        //shearTransformation(yacht_user_portrait);
        Rotation rotation = new Rotation(yacht_user_portrait);
        rotation.startRotation(0, 40, 0, 20);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iv_temp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_temp.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeToParent = Animation.RELATIVE_TO_PARENT;
        TranslateAnimation trans1 = new TranslateAnimation(relativeToParent, -1, relativeToParent, 0, relativeToParent, 0, relativeToParent, 0);
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
                heartView.setBackgroundResource(R.drawable.hear_anim);
                AnimationDrawable background = (AnimationDrawable) heartView.getBackground();
                background.start();

                AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
                alphaAnimation.setDuration(500);
                alphaAnimation.setStartOffset(1800);
                alphaAnimation.setFillAfter(true);
                heartView.setAnimation(alphaAnimation);
                heartView.startAnimation(alphaAnimation);

                AnimationSet arrSet = new AnimationSet(false);
//                RotateAnimation rotateArrow1=new RotateAnimation();
                TranslateAnimation transArrow1 = new TranslateAnimation(relativeToParent, -1, relativeToParent, 0, relativeToParent, 0.3f, relativeToParent, 0f);
                TranslateAnimation transArrow2 = new TranslateAnimation(relativeToParent, 0, relativeToParent, 1, relativeToParent, 0f, relativeToParent, 0.3f);
                transArrow1.setDuration(1000);
                transArrow2.setDuration(1000);
                arrSet.addAnimation(transArrow1);
                arrSet.addAnimation(transArrow2);
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
                arrowIV.setAnimation(arrSet);
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
}
