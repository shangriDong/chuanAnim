package com.dqs.shangri.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;


import com.dqs.shangri.shipainmation.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Shangri on 2016/6/7.
 */
public class MatrixDraweeView extends SimpleDraweeView {
    private Bitmap bitmap;
    private Bitmap background;
    private Matrix matrix;
    private float density;


    public MatrixDraweeView(Context context) {
        super(context);

        density = getResources().getDisplayMetrics().density;


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.yacht_sender_bg);
        matrix = new Matrix();


    }

    public MatrixDraweeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.yacht_sender_bg);
        matrix = new Matrix();
        density = getResources().getDisplayMetrics().density;
    }
/*
    public MatrixDraweeView(Context context, String url) {
        super(context);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yacht_sender_bg);
        matrix = new Matrix();
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 缩放原图
        matrix.postScale(0.7492f, 0.7492f);
        canvas.drawBitmap(background, matrix, null);
        Bitmap round = toRoundBitmap(bitmap);


        /*Matrix matrix1 = new Matrix();

        matrix1.setSkew(0f, 0.5f);*/
        // 下面的代码是为了查看matrix中的元素
        // 下面的代码是为了查看matrix中的元素
        //canvas.setMatrix(matrix);
        canvas.drawBitmap(round, 5 * density, 5 * density, null);
        super.onDraw(canvas);

    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        this.matrix.set(matrix);
        super.setImageMatrix(matrix);
    }

    public Bitmap getImageBitmap() {
        return bitmap;
    }

    //裁图
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        /*int width = bitmap.getWidth();
        int height = bitmap.getHeight();*/
        int width = (int) (49 * density);
        int height = (int) (49 * density);
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    //private ImageView image;
    //private Button start, stop;

}

