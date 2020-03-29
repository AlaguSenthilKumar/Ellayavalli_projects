package com.alwar.elayavalli.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwar.elayavalli.R;


public class EmptyStateView extends ConstraintLayout {

    private float width, height;
    private int backgroundColor, textColor;
    TextView textView;
    ImageView imageView;

    public EmptyStateView(Context context) {
        super(context);
        initMyView();
    }

    public EmptyStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public EmptyStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmptyStateView);
        backgroundColor = a.getColor(R.styleable.EmptyStateView_esv_background_color, getResources().getColor(R.color.colorPrimary));
        textColor = a.getColor(R.styleable.EmptyStateView_esv_textViewColor, getResources().getColor(R.color.white));

        initMyView();
    }

    public void initMyView(){
        this.setClickable(true);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.empty_state_view, this, true);

        textView = view.findViewById(R.id.tv_empty);
        imageView = view.findViewById(R.id.iv_empty);

        view.setBackgroundColor(backgroundColor);
        textView.setTextColor(textColor);
    }

    // getting the view size and default radius
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.v("debug","on measure cricket input view");
        width = MeasureSpec.getSize(widthMeasureSpec);
        height =  MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension((int)width, (int) height);
    }

    @Override
    public void onSizeChanged(int w,int h,int ow,int oh){
        super.onSizeChanged(w,h,ow,oh);

        Log.v("debug","on size cricket input");

        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void assignData(String emptyText, int image) {
        textView.setText(emptyText);


        /*SharedPreferences cache = getContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        boolean isDark = cache.getBoolean(Constants.DARK_THEME, false);

        if (!isDark) {
            textView.setTextColor(getContext().getResources().getColor(R.color.app_back_ground_color));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.white));
        }

        imageView.setImageDrawable(getContext().getResources().getDrawable(image));*/
    }

}