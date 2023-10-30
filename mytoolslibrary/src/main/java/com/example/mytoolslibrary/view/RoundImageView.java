package com.example.mytoolslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.mytoolslibrary.R;

public class RoundImageView extends AppCompatImageView {

    private Paint paint;
    private int radio = 0;
    private int radioTopLeft = 0;
    private int radioTopRight = 0;
    private int radioBottomLeft = 0;
    private int radioBottomRight = 0;
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

    public void setRadio(int radio) {
        this.radio = radio;
        this.radioTopLeft = radio;
        this.radioTopRight = radio;
        this.radioBottomLeft = radio;
        this.radioBottomRight = radio;
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            radio = a.getDimensionPixelSize(R.styleable.RoundImageView_radio, radio);
            radioTopLeft = a.getDimensionPixelSize(R.styleable.RoundImageView_radioTopLeft, radio);
            radioTopRight = a.getDimensionPixelSize(R.styleable.RoundImageView_radioTopRight, radio);
            radioBottomLeft = a.getDimensionPixelSize(R.styleable.RoundImageView_radioBottomLeft, radio);
            radioBottomRight = a.getDimensionPixelSize(R.styleable.RoundImageView_radioBottomRight, radio);
        }
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
//        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        drawLeftUp(canvas2);
        drawRightUp(canvas2);
        drawLeftDown(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        //bitmap.recycle();
    }

    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, radioTopLeft);
        path.lineTo(0, 0);
        path.lineTo(radioTopLeft, 0);
        path.arcTo(new RectF(
                        0,
                        0,
                        radioTopLeft * 2,
                        radioTopLeft * 2),
                -90,
                -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - radioBottomLeft);
        path.lineTo(0, getHeight());
        path.lineTo(radioBottomLeft, getHeight());
        path.arcTo(new RectF(
                        0,
                        getHeight() - radioBottomLeft * 2,
                        0 + radioBottomLeft * 2,
                        getHeight()),
                90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - radioBottomRight, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radioBottomRight);
        path.arcTo(new RectF(
                getWidth() - radioBottomRight * 2,
                getHeight() - radioBottomRight * 2,
                getWidth(),
                getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), radioTopRight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radioTopRight, 0);
        path.arcTo(new RectF(
                        getWidth() - radioTopRight * 2,
                        0,
                        getWidth(),
                        0 + radioTopRight * 2),
                -90,
                90);
        path.close();
        canvas.drawPath(path, paint);
    }

}

