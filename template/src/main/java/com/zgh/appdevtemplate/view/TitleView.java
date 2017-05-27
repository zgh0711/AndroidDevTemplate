package com.zgh.appdevtemplate.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgh.appdevtemplate.R;


/**
 * 自定义标题栏
 */

public class TitleView extends LinearLayout implements OnClickListener {
    private LinearLayout mLinearLayout;
    private TabLayout    tabLayout;
    private TextView     left_tv, left_tv1, center_tv, right_tv, right_tv1;
    private ImageButton left_ibtn, right_ibtn,rightSecond_ibtn,center_ibtn;
    private Drawable leftDrawable, rightDrawable,rightSecondDrawable,centerDrawable;
    private String leftText, centerText, rightText;

    private TitleClickListener mClickListener;

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context) {
        this(context, null);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    private void init(Context context, AttributeSet attrs) {
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.title_view, null);
        left_tv = (TextView) mLinearLayout.findViewById(R.id.tv_left_titleView);
        left_tv1 = (TextView) mLinearLayout.findViewById(R.id.tv_leftBadge_titleView);
        center_tv = (TextView) mLinearLayout.findViewById(R.id.tv_center_titleView);
        right_tv = (TextView) mLinearLayout.findViewById(R.id.tv_right_titleView);
        right_tv1 = (TextView) mLinearLayout.findViewById(R.id.tv_rightBadge_titleView);
        tabLayout = (TabLayout) mLinearLayout.findViewById(R.id.tabLayout_titleView);
        left_ibtn = (ImageButton) mLinearLayout.findViewById(R.id.ibtn_left_titleView);
        right_ibtn = (ImageButton) mLinearLayout.findViewById(R.id.ibtn_right_titleView);
        rightSecond_ibtn = (ImageButton) mLinearLayout.findViewById(R.id.ibtn_right_second);
        center_ibtn = (ImageButton) mLinearLayout.findViewById(R.id.ibtn_center);

        left_tv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
        left_ibtn.setOnClickListener(this);
        right_ibtn.setOnClickListener(this);
        rightSecond_ibtn.setOnClickListener(this);
        android.view.ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(mLinearLayout, params);
        readAttrs(context, attrs);
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        final int count = types.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = types.getIndex(i);
            if (attr == R.styleable.TitleView_leftIbtn) {
                leftDrawable = types.getDrawable(attr);
                if (leftDrawable != null) {
                    left_ibtn.setVisibility(View.VISIBLE);
                    left_tv.setVisibility(View.GONE);
                    left_ibtn.setImageDrawable(leftDrawable);
                }

            } else if (attr == R.styleable.TitleView_rightIbtn) {
                rightDrawable = types.getDrawable(attr);
                if (rightDrawable != null) {
                    right_ibtn.setVisibility(View.VISIBLE);
                    right_tv.setVisibility(View.GONE);
                    right_ibtn.setImageDrawable(rightDrawable);
                }

            } else if (attr == R.styleable.TitleView_centerIbtn) {
                centerDrawable = types.getDrawable(attr);
                if (centerDrawable != null) {
                    center_ibtn.setVisibility(VISIBLE);
                    center_tv.setVisibility(GONE);
                    center_ibtn.setImageDrawable(centerDrawable);
                }

            } else if (attr == R.styleable.TitleView_rightSecondIbtn) {
                rightSecondDrawable = types.getDrawable(attr);
                if (rightSecondDrawable != null) {
                    rightSecond_ibtn.setVisibility(VISIBLE);
                    rightSecond_ibtn.setImageDrawable(rightSecondDrawable);
                }

            } else if (attr == R.styleable.TitleView_leftTv) {
                leftText = types.getString(attr);
                left_tv.setVisibility(View.VISIBLE);
                left_ibtn.setVisibility(View.GONE);
                left_tv.setText(leftText);

            } else if (attr == R.styleable.TitleView_centerTv) {
                centerText = types.getString(attr);
                center_tv.setVisibility(VISIBLE);
                tabLayout.setVisibility(GONE);
                center_tv.setText(centerText);

            } else if (attr == R.styleable.TitleView_rightTv) {
                rightText = types.getString(attr);
                right_tv.setVisibility(View.VISIBLE);
                right_ibtn.setVisibility(View.GONE);
                right_tv.setText(rightText);

            }
        }
        types.recycle();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_left_titleView) {
            if (mClickListener != null) {
                mClickListener.onLeftClick(v);
            }

        } else if (i == R.id.ibtn_left_titleView) {
            if (mClickListener != null) {
                mClickListener.onLeftClick(v);
            }

        } else if (i == R.id.tv_right_titleView) {
            if (mClickListener != null) {
                mClickListener.onRightClick(v);
            }

        } else if (i == R.id.ibtn_right_titleView) {
            if (mClickListener != null) {
                mClickListener.onRightClick(v);
            }

        }
    }

    public interface TitleClickListener {
        void onLeftClick(View v);

        void onRightClick(View v);
    }

    public void setOnTitleClickListener(TitleClickListener titleClickListener) {
        mClickListener = titleClickListener;
    }

    // 设置左边文字的点击选择器
    public void setLeftDrawableSelector(int resid) {
        left_tv.setBackgroundResource(resid);
    }

    // 设置右边文字的点击选择器
    public void setRightDrawableSelector(int resid) {
        right_tv.setBackgroundResource(resid);
    }

    // 设置左边文字大小，单位sp，默认13sp
    public void setLeftTextSize(int size) {
        left_tv.setTextSize(size);
    }

    // 设置中间文字大小，单位sp，默认13sp
    public void setCenterTextSize(int size) {
        center_tv.setTextSize(size);
    }

    // 设置右边文字大小，单位sp，默认13sp
    public void setRightTextSize(int size) {
        right_tv.setTextSize(size);
    }

    // 设置左边文字颜色，默认黑色
    public void setLeftTextColor(int color) {
        left_tv.setTextColor(color);
    }

    // 设置中间文字颜色，默认黑色
    public void setCenterTextColor(int color) {
        center_tv.setTextColor(color);
    }

    // 设置右边文字颜色，默认黑色
    public void setRightTextColor(int color) {
        right_tv.setTextColor(color);
    }

    // 设置中间文字
    public void setCenterText(String text) {
        center_tv.setText(text);
    }

    // 设置左边文字
    public void setLeftText(String text) {
        left_tv.setVisibility(View.VISIBLE);
        left_ibtn.setVisibility(View.GONE);
        left_tv.setText(text);
    }

    // 设置左边浮动文字
    public void setLeftText1(int number) {
        if (number > 0) {
            if (number <= 99) {
                left_tv1.setText(number+"");
            } else if (number > 99) {
                left_tv1.setText("99+");
            }
            left_tv1.setVisibility(View.VISIBLE);
        }else{
            left_tv1.setVisibility(View.GONE);
        }
    }

    // 设置左边图片
    public void setLeftImage(int resId) {
        left_ibtn.setVisibility(View.VISIBLE);
        left_tv.setVisibility(View.GONE);
        left_ibtn.setImageResource(resId);
    }

    // 设置右边文字
    public void setRightText(String text) {
        right_tv.setVisibility(View.VISIBLE);
        right_ibtn.setVisibility(View.GONE);
        right_tv.setText(text);
    }

    // 设置右边浮动文字
    public void setRightText1(int number) {
        if (number > 0) {
            if (number <= 99) {
                right_tv1.setText(number+"");
            } else if (number > 99) {
                right_tv1.setText("99+");
            }
            right_tv1.setVisibility(View.VISIBLE);
        }else{
            right_tv1.setVisibility(View.GONE);
        }
    }

    // 设置右边图片
    public void setRightImage(int resId) {
        right_ibtn.setVisibility(View.VISIBLE);
        right_tv.setVisibility(View.GONE);
        right_ibtn.setImageResource(resId);
    }

    // 设置右边第二按钮图片
    public void setRightSecondImage(int resId) {
        rightSecond_ibtn.setVisibility(View.VISIBLE);
        rightSecond_ibtn.setImageResource(resId);
    }

    //
    public LinearLayout getmLinearLayout() {
        return mLinearLayout;
    }

    //
    public TextView getLeft_tv() {
        return left_tv;
    }

    public void setLeft_tv(TextView left_tv) {
        this.left_tv = left_tv;
    }

    public TextView getRight_tv1() {
        return right_tv1;
    }

    public void setRight_tv1(TextView right_tv1) {
        this.right_tv1 = right_tv1;
    }

    public TextView getLeft_tv1() {
        return left_tv1;
    }

    public void setLeft_tv1(TextView left_tv1) {
        this.left_tv1 = left_tv1;
    }

    //
    public TextView getCenter_tv() {
        return center_tv;
    }

    //
    public TextView getRight_tv() {
        return right_tv;
    }

    //
    public ImageButton getLeft_ibtn() {
        return left_ibtn;
    }

    //
    public ImageButton getRight_ibtn() {
        return right_ibtn;
    }

    //
    public ImageButton getRightSecond_ibtn() {
        return rightSecond_ibtn;
    }

    //
    public ImageButton getCenter_ibtn() {
        return center_ibtn;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
