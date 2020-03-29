package com.alwar.elayavalli.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.alwar.elayavalli.R;


public class RoundCornerButton extends android.support.v7.widget.AppCompatButton {
    private int backgroundColor, strokeColor, backgroundImage;

    private int topLeft,topRight,bottomRight,bottomLeft,cornerRadius;

    private GradientDrawable roundCorner = new GradientDrawable();




    public RoundCornerButton(Context context) {
        super(context);

        initView();
    }

    public RoundCornerButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.roundCornerButton);
        backgroundColor = a.getColor(R.styleable.roundCornerButton_background_color, getResources().getColor(R.color.colorPrimary));
        strokeColor = a.getColor(R.styleable.roundCornerButton_stroke_color, backgroundColor);
        topLeft = a.getDimensionPixelSize(R.styleable.roundCornerButton_top_left_corner, 0);
        topRight = a.getDimensionPixelSize(R.styleable.roundCornerButton_top_right_corner, 0);
        bottomRight = a.getDimensionPixelSize(R.styleable.roundCornerButton_bottom_right_corner, 0);
        bottomLeft = a.getDimensionPixelSize(R.styleable.roundCornerButton_bottom_left_corner, 0);
        cornerRadius = a.getDimensionPixelSize(R.styleable.roundCornerButton_corner_radius, -1);

        initView();
    }

    private void initView() {

        //roundCorner.setColor(Color.parseColor("#de545f"));

        setCorners();
        //roundCorner.setColor(getResources().getColor(R.color.white));
        this.setBackgroundDrawable(roundCorner);

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

    public void assignGradientColor(int startColor, int endColor) {
        int []color = new int[] {startColor,endColor};
        roundCorner.setColors(color);
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

    public void setBackgroundImage() {
       this.setBackground(getResources().getDrawable(backgroundImage));
    }

    public void topCornerRadius(int dimenId) {
        cornerRadius = -1;

        topLeft = getResources().getDimensionPixelOffset(dimenId);
        topRight = getResources().getDimensionPixelOffset(dimenId);

        setCorners();
    }

    public void bottomCornerRadius(int dimenId) {
        cornerRadius = -1;

        bottomRight = getResources().getDimensionPixelOffset(dimenId);
        bottomLeft = getResources().getDimensionPixelOffset(dimenId);

        setCorners();
    }

}

