package com.alwar.elayavalli.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.alwar.elayavalli.R;


public class HomeRoundedLayout extends LinearLayout {


    private int backgroundColor,strokeColor;

    private float topLeft,topRight,bottomRight,bottomLeft, cornerRadius;

    private GradientDrawable roundCorner = new GradientDrawable();


    public HomeRoundedLayout(Context context) {
        super(context);

        initView();
    }

    public HomeRoundedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HomeRoundedLayout);

        backgroundColor = a.getColor(R.styleable.HomeRoundedLayout_hom_background_color, getResources().getColor(R.color.mild_grayColor));
        strokeColor = a.getColor(R.styleable.HomeRoundedLayout_hom_stroke_color, backgroundColor);
        topLeft = a.getDimensionPixelSize(R.styleable.HomeRoundedLayout_hom_top_left_corner, 0);
        topRight = a.getDimensionPixelSize(R.styleable.HomeRoundedLayout_hom_top_right_corner, 0);
        bottomRight = a.getDimensionPixelSize(R.styleable.HomeRoundedLayout_hom_bottom_right_corner, 0);
        bottomLeft = a.getDimensionPixelSize(R.styleable.HomeRoundedLayout_hom_bottom_left_corner, 0);
        cornerRadius = a.getDimensionPixelSize(R.styleable.HomeRoundedLayout_hom_corner_radius, -1);

        initView();
    }

    public void initView() {

        setCorners();
        this.setBackground(roundCorner);

        setStrokeColor();

        if (backgroundColor != 0){
            setBackgroundColor();
        }

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

    public void setCorners(int dimenId) {
        this.topLeft = getResources().getDimensionPixelOffset(dimenId);;
        this.topRight = getResources().getDimensionPixelOffset(dimenId);;
        this.bottomLeft = getResources().getDimensionPixelOffset(dimenId);;
        this.bottomRight = getResources().getDimensionPixelOffset(dimenId);;
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

    public void setTopLeftCornerRadius(int dimenId) {
        cornerRadius = -1;
        topLeft = getResources().getDimensionPixelOffset(dimenId);
        setCorners();
    }

    public void setBottomLeftCornerRadius(int dimenId) {
        cornerRadius = -1;
        bottomLeft = getResources().getDimensionPixelOffset(dimenId);
        setCorners();
    }

    public void setBottomRightCornerRadius(int dimenId) {
        cornerRadius = -1;
        bottomRight = getResources().getDimensionPixelOffset(dimenId);
        setCorners();
    }

    public void setTopRightCornerRadius(int dimenId) {
        cornerRadius = -1;
        topRight = getResources().getDimensionPixelOffset(dimenId);
        setCorners();
    }

}