package com.zgh.appdevtemplate.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zgh.appdevtemplate.R;


/**
 *  带圆角的 ImageView，可以直接在布局文件中指定所有角弧度大小，或者某个角的弧度
 *  XML 属性：
 *      radius  给所有角都设置弧度
 *      leftUpRadius        左上角
        rightUpRadius       右上角
        leftDownRadius      左下角
        rightDownRadius     右下角
 *
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint paint;
    private int allRadius       = 0;
    private int leftUpRadius    = 0;
    private int rightUpRadius   = 0;
    private int leftDownRadius  = 0;
    private int rightDownRadius = 0;
    private Paint paint2;

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            allRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_radius, allRadius);
            leftUpRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_leftUpRadius, leftUpRadius);
            rightUpRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_rightUpRadius, rightUpRadius);
            leftDownRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_leftDownRadius, leftDownRadius);
            rightDownRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_rightDownRadius, rightDownRadius);
        } 

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        drawLeftUp(canvas2);
        drawLeftDown(canvas2);
        drawRightUp(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }

    private void drawLeftUp(Canvas canvas) {
        if (allRadius > 0) {
            drawLeftUp(canvas,allRadius);
            return;
        }
        if (leftUpRadius > 0) {
            drawLeftUp(canvas, leftUpRadius);
        }
    }

    private void drawLeftUp(Canvas canvas,int radius) {
        Path path = new Path();
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(radius, 0);
        path.arcTo(new RectF(0, 0, radius * 2, radius * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftDown(Canvas canvas) {
        if (allRadius > 0) {
            drawLeftDown(canvas, allRadius);
            return;
        }
        if (leftDownRadius > 0) {
            drawLeftDown(canvas, leftDownRadius);
        }
    }

    private void drawLeftDown(Canvas canvas,int radius) {
        Path path = new Path();
        path.moveTo(0, getHeight() - radius);
        path.lineTo(0, getHeight());
        path.lineTo(radius, getHeight());
        path.arcTo(new RectF(0, getHeight() - radius * 2, radius * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        if (allRadius > 0) {
            drawRightUp(canvas, allRadius);
            return;
        }
        if (rightUpRadius > 0) {
            drawRightUp(canvas, rightUpRadius);
        }
    }

    private void drawRightUp(Canvas canvas,int radius) {
        Path path = new Path();
        path.moveTo(getWidth(), radius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radius, 0);
        path.arcTo(new RectF(getWidth() - radius * 2, 0, getWidth(), 0 + radius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        if (allRadius > 0) {
            drawRightDown(canvas, allRadius);
            return;
        }
        if (rightDownRadius > 0) {
            drawRightDown(canvas, rightDownRadius);
        }
    }

    private void drawRightDown(Canvas canvas,int radius) {
        Path path = new Path();
        path.moveTo(getWidth() - radius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radius);
        path.arcTo(new RectF(getWidth() - radius * 2, getHeight() - radius * 2, getWidth(), getHeight()), -0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

}
