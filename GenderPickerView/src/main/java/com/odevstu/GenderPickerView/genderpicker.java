package com.odevstu.GenderPickerView;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class genderpicker extends RelativeLayout {

    private Context context;
    private AttributeSet attrs;
    private int styleAttr;
    private OnClickListener mListener;
    private TextView textView;
    private Button rightBtn, leftBtn;
    private View view;
    private OnValueChangeListener mOnValueChangeListener;


    private String[] aio;

    private String first;
    private String second;
    private String third;

    private int actualGender = 0;
    private int lastGender = actualGender;

    public genderpicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public genderpicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.styleAttr = defStyleAttr;
        initView();
    }

    public genderpicker(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public void initView(){
        this.view = inflate(context, R.layout.layout_gender, this);

        final Resources res = getResources();
        final int defaultColor = res.getColor(R.color.colorPrimary);
        final int defaultTextColor = res.getColor(R.color.colorText);
        final Drawable defaultDrawable = res.getDrawable(R.drawable.background);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GenderChoice, styleAttr, 0);

        float textSize = a.getDimension(R.styleable.GenderChoice_textSize, 13);
        int color = a.getColor(R.styleable.GenderChoice_backGroundColor, defaultColor);
        int textColor = a.getColor(R.styleable.GenderChoice_textColor, defaultTextColor);
        Drawable drawable = a.getDrawable(R.styleable.GenderChoice_backgroundDrawable);


        first = a.getString(R.styleable.GenderChoice_first);
        second = a.getString(R.styleable.GenderChoice_second);
        third = a.getString(R.styleable.GenderChoice_third);

        if(CheckAttrs(first) && CheckAttrs(second) && CheckAttrs(third)){
            first = res.getString(R.string.first);
            second = res.getString(R.string.second);
            third = res.getString(R.string.third);
        }

        leftBtn = findViewById(R.id.subtract_btn);
        rightBtn = findViewById(R.id.add_btn);
        textView = findViewById(R.id.number_counter);
        LinearLayout mLayout = findViewById(R.id.layout);

        leftBtn.setTextColor(textColor);
        rightBtn.setTextColor(textColor);
        textView.setTextColor(textColor);

        leftBtn.setTextSize(textSize);
        rightBtn.setTextSize(textSize);
        textView.setTextSize(textSize);

        if (drawable == null) {
            drawable = defaultDrawable;
        }
        assert drawable != null;
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
        if (Build.VERSION.SDK_INT > 16)
            mLayout.setBackground(drawable);
        else
            mLayout.setBackgroundDrawable(drawable);


        aio = new String[]{first, second, third};
        textView.setText(aio[0]);


        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcHandler(lastGender - 1);
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcHandler(lastGender + 1);
            }
        });

        a.recycle();

    }

    private void switcHandler(int newPos){
        switch (newPos){
            case 0:
                leftBtn.setEnabled(false);
                textView.setText(aio[0]);
                lastGender = newPos;
                notifyListner();

                break;
            case 1:
                leftBtn.setEnabled(true);
                rightBtn.setEnabled(true);
                textView.setText(aio[1]);
                lastGender = newPos;
                notifyListner();


                break;
            case 2:
                rightBtn.setEnabled(false);
                textView.setText(aio[2]);
                lastGender = newPos;
                notifyListner();


                break;
        }
    }

    private void notifyListner(){
        if(mOnValueChangeListener != null){
            mOnValueChangeListener.onValueChange(this,  textView.getText().toString());
        }
    }

    private boolean CheckAttrs(String txt){
        return TextUtils.isEmpty(txt);
    }

//    public String getNumber() {
//        return String.valueOf(currentNumber);
//    }
//
//    public void setNumber(String number) {
//        lastNumber = currentNumber;
//        this.currentNumber = Integer.parseInt(number);
//        if (this.currentNumber > finalNumber) {
//            this.currentNumber = finalNumber;
//        }
//        if (this.currentNumber < initialNumber) {
//            this.currentNumber = initialNumber;
//        }
//        textView.setText(String.valueOf(currentNumber));
//    }
//
//    public void setNumber(String number, boolean notifyListener) {
//        setNumber(number);
//        if (notifyListener) {
//            callListener(this);
//        }
//    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        mOnValueChangeListener = onValueChangeListener;
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnValueChangeListener {
        void onValueChange(genderpicker view, String newValue);
    }
}
