package com.twiceyuan.pressableview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by twiceYuan on 2017/7/29.
 * <p>
 * 具有按下效果的 ImageView
 */
public class PressableImageView extends AppCompatImageView {

    private int pressedFilter = 0x40000000;

    public PressableImageView(Context context) {
        super(context);
        initView();
    }

    public PressableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PressableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        setClickable(true);

        refreshPressableState();
    }

    public void setPressedFilter(@ColorInt int pressedFilter) {
        this.pressedFilter = pressedFilter;
        refreshPressableState();
    }

    private void refreshPressableState() {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        // 判断是否是支持处理的 Drawable 类型，不是的话不进行处理
        boolean isRawDrawable = drawable instanceof BitmapDrawable ||
                drawable instanceof ShapeDrawable ||
                drawable instanceof GradientDrawable ||
                drawable instanceof ColorDrawable;

        if (!isRawDrawable) {
            return;
        }

        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return;
        }

        Drawable pressedDrawable = constantState.newDrawable().mutate();
        drawable.mutate();

        StateListDrawable stateListDrawable = new StateListDrawable();

        pressedDrawable.setColorFilter(pressedFilter, PorterDuff.Mode.SRC_ATOP);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{0}, drawable);

        setImageDrawable(stateListDrawable);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        refreshPressableState();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        refreshPressableState();
    }
}
