package com.twiceyuan.pressableview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

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

        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return;
        }

        Drawable pressedDrawable = constantState.newDrawable().mutate();
        drawable.mutate();

        FilterableStateListDrawable stateListDrawable = new FilterableStateListDrawable();

        pressedDrawable.setColorFilter(pressedFilter, PorterDuff.Mode.SRC_ATOP);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable, new PorterDuffColorFilter(pressedFilter, PorterDuff.Mode.SRC_ATOP));
        stateListDrawable.addState(new int[]{0}, drawable);

        super.setImageDrawable(stateListDrawable);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Log.i("drawableStateChanged", "drawableStateChanged");
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
