package com.alwar.elayavalli.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.alwar.elayavalli.R;


public class RoundCornerLinearLayout extends LinearLayout {


    private int backgroundColor,strokeColor;

    private float topLeft,topRight,bottomRight,bottomLeft, cornerRadius;

    private GradientDrawable roundCorner = new GradientDrawable();


    public RoundCornerLinearLayout(Context context) {
        super(context);

        initView();
    }

    public RoundCornerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerLinearLayout);

        backgroundColor = a.getColor(R.styleable.RoundCornerLinearLayout_lin_background_color, getResources().getColor(R.color.colorPrimary));
        strokeColor = a.getColor(R.styleable.RoundCornerLinearLayout_lin_stroke_color, backgroundColor);
        topLeft = a.getDimensionPixelSize(R.styleable.RoundCornerLinearLayout_lin_top_left_corner, 0);
        topRight = a.getDimensionPixelSize(R.styleable.RoundCornerLinearLayout_lin_top_right_corner, 0);
        bottomRight = a.getDimensionPixelSize(R.styleable.RoundCornerLinearLayout_lin_bottom_right_corner, 0);
        bottomLeft = a.getDimensionPixelSize(R.styleable.RoundCornerLinearLayout_lin_bottom_left_corner, 0);
        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundCornerLinearLayout_lin_corner_radius, -1);

        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        android.graphics.Path path = new android.graphics.Path();
        path.addRoundRect(new RectF(canvas.getClipBounds()), cornerRadius, cornerRadius, android.graphics.Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }

    public void initView() {

        setCorners();
        this.setBackground(roundCorner);

        setStrokeColor();

        if (backgroundColor != 0){
            setBackgroundColor();
        }

//        setWillNotDraw(false);
    }

    public void setCorners() {
        if (cornerRadius > 0) {
            roundCorner.setCornerRadii(new float[]{
                    cornerRadius,cornerRadius,
                    cornerRadius,cornerRadius,
                    cornerRadius,cornerRadius,
                    cornerRadius,cornerRadius
            });
        } else {
            roundCorner.setCornerRadii(new float[]{
                    topLeft,topLeft,
                    topRight,topRight,
                    bottomRight,bottomRight,
                    bottomLeft,bottomLeft
            });
        }
    }

    public void setBackgroundDrawable(GradientDrawable gradientDrawable) {
        gradientDrawable.setCornerRadius(cornerRadius);
        this.roundCorner = gradientDrawable;
        this.setBackground(gradientDrawable);
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setCorners(float cornerRadii) {
        this.topLeft = cornerRadii;
        this.topRight = cornerRadii;
        this.bottomLeft = cornerRadii;
        this.bottomRight = cornerRadii;
        setCorners();
    }

    public void assignBackgroundColor(int color)  {
        this.backgroundColor = getResources().getColor(color);
        setBackgroundColor();
    }

    public void assignStrokeColor(int color) {
        this.strokeColor = getResources().getColor(color);
        setStrokeColor();
    }

    public void setStrokeColor() {
        roundCorner.setStroke(2,strokeColor);
    }

    public void setBackgroundColor() {
        GradientDrawable drawable = (GradientDrawable) this.getBackground();

        drawable.setColor(backgroundColor);
    }
}