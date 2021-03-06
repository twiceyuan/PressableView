package com.twiceyuan.pressableview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by twiceYuan on 2017/7/27.
 * <p>
 * 通过设置 Background 来实现任意图片、颜色、Shape 的变暗效果
 */
public class PressableLayout extends FrameLayout {

    private int pressedFilter = 0x40000000;

    public PressableLayout(Context context) {
        super(context);
        initView();
    }

    public PressableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PressableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setClickable(true);
        refreshForeground();
    }

    private void refreshForeground() {
        Drawable background = getBackground();
        setupPressDrawable(background);
    }

    private void setupPressDrawable(Drawable backgroundDrawable) {

        FilterableStateListDrawable stateListDrawable = new FilterableStateListDrawable();

        Drawable.ConstantState constantState = backgroundDrawable.getConstantState();

        if (constantState == null) {
            return;
        }

        Drawable pressedDrawable = constantState.newDrawable().mutate();
        backgroundDrawable.mutate();

        ColorFilter colorFilter = new PorterDuffColorFilter(pressedFilter, PorterDuff.Mode.SRC_ATOP);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable, colorFilter);
        stateListDrawable.addState(new int[]{0}, backgroundDrawable);

        super.setBackgroundDrawable(stateListDrawable);
    }

    public void setPressedFilter(@ColorInt int pressedFilter) {
        this.pressedFilter = pressedFilter;
        refreshForeground();
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        refreshForeground();
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resid) {
        super.setBackgroundResource(resid);
        refreshForeground();
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
        refreshForeground();
    }

    @Override
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        super.setBackgroundTintList(tint);
        refreshForeground();
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        refreshForeground();
    }

    @Override
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        super.setBackgroundTintMode(tintMode);
        refreshForeground();
    }
}
