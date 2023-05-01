package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (getCompoundDrawablesRelative()[2] != null) {
                    boolean isButtonClicked = false;

                    //untuk menentukan layout dari kanan ke kiri
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        //get dari button image + get padding start dari edit text
                        float clearButtonEndPosition = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        //sentuhan kita kurang dari yg di atas isbuttonclicked dijalankan
                        if (motionEvent.getX() < clearButtonEndPosition) {
                            isButtonClicked = true;
                        }
                    } else {
                        //untuk menentukan layout dari kiri ke kanan
                        float clearButtonStartPosition = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());
                        if (motionEvent.getX() > clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    }

                    //action kalo klik button down pas di klik, up habis di klik
                    //resourcecompat manggil layout button yg di dalam drawable
                    if (isButtonClicked) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }
                    else {
                        //ngembaliin if yg di atasnya
                        return false;
                    }
                }
                //ngembaliin if yg di atasnya lagi
                return false;
            }
        });

    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
        //menambah komponen drawable di komponen edit text, parameter start top end bottom
//        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
//        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
