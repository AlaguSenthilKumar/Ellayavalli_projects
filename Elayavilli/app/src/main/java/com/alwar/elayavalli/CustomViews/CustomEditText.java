package com.alwar.elayavalli.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alwar.elayavalli.Interfaces.CustomEditTextInterface;
import com.alwar.elayavalli.R;


public class CustomEditText extends LinearLayout {

    private TextInputEditText editText;
    private ImageView icon;
    private TextInputLayout textInput;
    private RoundCornerConstraintLayout mainLay;

    private String placeholder;
    private Drawable imageIcon;
    private int inputType;
    private CustomEditTextInterface customEditTextInterface;
    private Boolean isEditable = true;

    public CustomEditText(Context context) {
        super(context);

        initLay();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);

        placeholder = a.getString(R.styleable.CustomEditText_placeholder);
        imageIcon  = a.getDrawable(R.styleable.CustomEditText_iconId);
        inputType = a.getInteger(R.styleable.CustomEditText_inputType, 1);

        initLay();
    }

    private void initLay() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (inflater != null) {
            view = inflater.inflate(R.layout.custom_edit_text, this, true);

            this.setBackgroundColor(Color.TRANSPARENT);

            editText = view.findViewById(R.id.editText);
            icon = view.findViewById(R.id.icon);
            mainLay = view.findViewById(R.id.mainLay);
            textInput = view.findViewById(R.id.textInput);

            if (placeholder != null) {
                textInput.setHint(placeholder);
            } else {
                textInput.setHint("");
            }

            if (inputType == 1) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                editText.setInputType(inputType);
            }

            mainLay.assignStrokeColor(R.color.mild_grayColor);
            if (imageIcon != null) {
                icon.setImageDrawable(imageIcon);
            }

            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        mainLay.assignStrokeColor(R.color.colorPrimary);
                    } else {
                        mainLay.assignStrokeColor(R.color.mild_grayColor);
                    }
                    if (customEditTextInterface != null) {
                        customEditTextInterface.onFocusChanged(b);
                    }
                }
            });


            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (customEditTextInterface != null) {
                        customEditTextInterface.onTextChanged(getText().toString());
                    }
                }
            });
        }

        this.setClickable(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEditable) {
                    editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }

                if (customEditTextInterface != null) {
                    customEditTextInterface.onClicked();
                }
            }
        });

    }

    public Editable getText() {
        return editText.getText();
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setKeyListener(OnKeyListener keyListener) {
        editText.setOnKeyListener(keyListener);
    }

    public void setHint(String hint) {
        textInput.setHint(hint);
    }
    public void setEditEnabled(boolean enabled) { this.isEditable = enabled;}
    public void setEditClickable(boolean isClicked) {editText.setClickable(isClicked);}
    public void setEditTextColor(int color) {editText.setTextColor(color);}

    public void setCustomEditTextInterface(CustomEditTextInterface customEditTextInterface) {
        this.customEditTextInterface = customEditTextInterface;
    }
}
