package com.alwar.elayavalli.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alwar.elayavalli.Constants;
import com.alwar.elayavalli.CustomViews.AutoFitTextView.AutoFitTextView;
import com.alwar.elayavalli.R;


public class CustomInputView extends ConstraintLayout {

    private AutoFitTextView centerTextView,topHintTextView;
    private EditText centerEditText;
    private OnClickListener clickListener;
    private float width, height;
    private Boolean isTextView = true;
    private Boolean isHintVisible = false;

    private String hintText = "";
    private String centerText = "";


    private Float textViewSize = 14f;
    private Float editTextViewSize =  18f;
    private Float hintTextViewsize = 12f;
    private Float topHintTextViewSize = 12f;

    private int topHintTextViewColorId = R.color.white;

    private int topHintTextViewBackgroundColorId = R.color.blue;

    private int backgroundColor, strokeColor = R.color.white;

    private int darkColor, lightColor, textColor;

    boolean defaultStroke;

    public CustomInputView(Context context) {
        super(context);
        initMyView();
    }

    public CustomInputView(Context context, Boolean isTextView, String hintText) {
        super(context);

        this.isTextView = isTextView;
        this.hintText = hintText;
        initMyView();
    }

    public CustomInputView(Context context, LayoutParams layoutParams) {
        super(context);

        this.setLayoutParams(layoutParams);
        invalidate();

        initMyView();

    }

    public CustomInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public CustomInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomInputView);
        backgroundColor = a.getColor(R.styleable.CustomInputView_civ_background_color, getResources().getColor(R.color.textColor));
        defaultStroke = a.getBoolean(R.styleable.CustomInputView_civ_default_stroke, false);
        initMyView();
    }

    public void initMyView(){
        this.setClickable(true);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_input_view, this, true);

        centerTextView = view.findViewById(R.id.textView);
        topHintTextView = view.findViewById(R.id.hintTextView);
        centerEditText = view.findViewById(R.id.editText);
        darkColor = R.color.blue;
        lightColor = R.color.blue;
        textColor = R.color.blue;

        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (getParent() != null) {
                    ((View) getParent()).addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            Log.v("debug","onLayoutChange cricket input view");
                                updateAllViews();
                                ((View) getParent()).removeOnLayoutChangeListener(this);
                        }
                    });
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }

    private void updateAllViews() {
        assignCenterView();
        assignText();
        assignTextColor();
        if (isHintVisible || !isTextView) {
            setHint();
        }
        setTextViewSize();
        setTopCenterHintTextView();
        setTopHintViewSize();
        setTopHintViewColor();
        setHintColor();
        setTopHintBackgroundDrawable();
        setHintViewSize();
        alignSubViews();
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

    private void alignSubViews() {

        int topTextHeight = (int)height;
        int totalWidth = (int) (width - 2);

        LayoutParams topHintTextViewparams = (LayoutParams) topHintTextView.getLayoutParams();
        topHintTextViewparams.topMargin = (int)(topTextHeight * 0.4);
        topHintTextViewparams.height = 2 * (topTextHeight - topHintTextViewparams.topMargin);
        if(topHintTextViewparams.width > totalWidth) {
            topHintTextViewparams.width = (int) (totalWidth - (totalWidth * 0.1));
        }

        topHintTextView.setLayoutParams(topHintTextViewparams);

        LayoutParams editTextViewParams = (LayoutParams) centerEditText.getLayoutParams();
        centerEditText.setLayoutParams(editTextViewParams);

        LayoutParams centerTextViewParams = (LayoutParams) centerTextView.getLayoutParams();
        centerTextView.setLayoutParams(centerTextViewParams);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.v("debug","on Draw cricket input");


    }

    public void setOnClick(OnClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.v("action","move");
                break;
            case MotionEvent.ACTION_DOWN:
                Log.v("action","down");
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX()<0 || event.getY()<0 || event.getX()>getMeasuredWidth() || event.getY()>getMeasuredHeight()) {
                    Log.i("action", "TOUCH OUTSIDE");
                }
                else {
                    Log.i("action", "TOUCH INSIDE");
                    if (clickListener != null) {
                        //hexaGonDrawable.setStrokeStyle(Paint.Style.FILL);
                        clickListener.onClick(this);
                    }
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                Log.v("action","cancel");
                break;
            case MotionEvent.ACTION_HOVER_ENTER:
                Log.v("action","hover enter");
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                Log.v("action","hover exit");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.v("action","hover move");
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Log.v("action","outside");
                break;
        }

        return super.onTouchEvent(event);
    }


    public void assignCenterView(Boolean isTextView) {
        this.isTextView = isTextView;
       // assignCenterView();
        updateAllViews();
    }

    private void assignCenterView() {
        if(this.isTextView) {
            centerTextView.setVisibility(VISIBLE);
            centerEditText.setVisibility(GONE);
        } else {
            centerEditText.setVisibility(VISIBLE);
            centerTextView.setVisibility(GONE);
        }
    }

    private void assignText() {
        if (isTextView) {
            centerTextView.setText(this.centerText);
            this.isHintVisible = this.centerText.isEmpty();
        } else {
            centerEditText.setText(this.centerText);
        }
    }

    public void assignText(String text) {
        this.centerText = text;
        this.isHintVisible = text.isEmpty();
        updateAllViews();
    }

    private void assignTextColor() {
        if (isTextView) {
            centerTextView.setTextColor(getResources().getColor(darkColor));
        } else {
            centerEditText.setTextColor(getResources().getColor(textColor));
        }
    }

    private void setHintColor() {
        if (isTextView) {
            if (isHintVisible) {
                centerTextView.setTextColor(getResources().getColor(R.color.mild_grayColor));
            }
        } else {
            centerEditText.setHintTextColor(getResources().getColor(R.color.mild_grayColor));
        }
    }

    private void setHint() {
        if (isTextView) {
            if (isHintVisible) {
                centerTextView.setText(this.hintText);
            }
        } else {
            centerEditText.setHint(this.hintText);
        }
    }

    public void setHint(String text) {
        this.hintText = text;
        updateAllViews();
    }

    public String  getHint() {
        return this.hintText;
    }

    private  void setHintViewSize() {
        if(isTextView) {
            centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,hintTextViewsize);
        }
    }

    public void setHintViewSize(Float dimensionId){
        this.hintTextViewsize = dimensionId;
        updateAllViews();
    }

    private void setTextViewSize() {
        if(isTextView) {
            centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textViewSize);
        } else  {
            centerEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,editTextViewSize);
        }
    }

    public void setTextViewSize(Float dimensionId){
        if(isTextView) {
            this.textViewSize = dimensionId;
        } else {
            this.editTextViewSize = dimensionId;
        }
        //setTextViewSize();
        updateAllViews();
    }

    public String getCenterText() {
        if (isTextView) {
            if (!isHintVisible) {
                return centerTextView.getText().toString();
            } else {
                return "";
            }
        } else {
            return centerEditText.getText().toString();
        }
    }


    private void setTopCenterHintTextView() {
        if (isTextView && isHintVisible) {
            this.topHintTextView.setVisibility(GONE);

        } else {
            this.topHintTextView.setVisibility(VISIBLE);
            this.topHintTextView.setText(this.hintText);
        }
    }

    public void setTopCenterHintTextView(String centerHintText){
        this.hintText = centerHintText;
        //setTopCenterHintTextView();
        updateAllViews();
    }

    private  void setTopHintViewSize() {
        topHintTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,topHintTextViewSize);
    }

    public void setTopHintViewSize(Float dimensionId){
        this.topHintTextViewSize = dimensionId;
       // setTopHintViewSize();
        updateAllViews();
    }

    private void setTopHintViewColor() {
        topHintTextView.setTextColor(getContext().getResources().getColor(this.topHintTextViewColorId));
    }

    public void setTopHintViewColor(int colorId){
        this.topHintTextViewColorId = colorId;
        //setTopHintViewColor();
        updateAllViews();
    }

    private void setTopHintBackgroundColor() {
//        topHintTextView.setBackgroundColor(getContext().getResources().getColor(this.topHintTextViewBackgroundColorId));
        GradientDrawable bgShape = (GradientDrawable)topHintTextView.getBackground();
        bgShape.setColor(getContext().getResources().getColor(this.topHintTextViewBackgroundColorId));
    }

    public void setTopHintBackgroundColor(int colorId){
        this.topHintTextViewBackgroundColorId = colorId;
        //setTopHintBackgroundColor();
        updateAllViews();
    }

    public void setTopHintBackgroundDrawable(int darkColor, int lightColor){
        this.darkColor = darkColor;
        this.lightColor = lightColor;
        this.textColor = darkColor;
        updateAllViews();
    }

    private void setTopHintBackgroundDrawable(){
        GradientDrawable gradientDrawable = Constants.getGradientDrawable(getResources().getColor(darkColor), getResources().getColor(lightColor), GradientDrawable.Orientation.LEFT_RIGHT, 0.5f);
        topHintTextView.setBackground(gradientDrawable);
    }

    public void setInputType(Boolean isNumber) {
        if(isNumber) {
            centerEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            centerEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public ViewGroup.LayoutParams getEditTextParams() {
        return centerEditText.getLayoutParams();
    }

    public void setCenterTextParams(ViewGroup.LayoutParams params) {
        centerEditText.setLayoutParams(params);
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        if (centerEditText != null && centerEditText.getVisibility() == VISIBLE) {
            centerEditText.addTextChangedListener(textWatcher);
        } else if (centerTextView != null  && centerTextView.getVisibility() == VISIBLE) {
            centerTextView.addTextChangedListener(textWatcher);
        }
    }

    public void cursorVisible(boolean isVisible) {
        if (centerEditText != null && centerEditText.getVisibility() == VISIBLE) {
            centerEditText.setCursorVisible(isVisible);
            centerEditText.setEnabled(isVisible);
        }
    }

    public void setEditTextColor(int colorId) {
        if (centerEditText != null && centerEditText.getVisibility() == VISIBLE) {
            textColor = colorId;
            assignTextColor();
        }
    }

}