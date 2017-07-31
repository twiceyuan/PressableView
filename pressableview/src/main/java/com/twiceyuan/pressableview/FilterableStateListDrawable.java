package com.twiceyuan.pressableview;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.SparseArray;

class FilterableStateListDrawable extends StateListDrawable {

    private int currIdx       = -1;
    private int childrenCount = 0;
    private SparseArray<ColorFilter> filterMap;

    FilterableStateListDrawable() {
        super();
        filterMap = new SparseArray<>();
    }

    @Override
    public void addState(int[] stateSet, Drawable drawable) {
        super.addState(stateSet, drawable);
        childrenCount++;
    }

    /**
     * Same as {@link #addState(int[], android.graphics.drawable.Drawable)}, but allow to set a colorFilter associated to this Drawable.
     *
     * @param stateSet    - An array of resource Ids to associate with the image.
     *                    Switch to this image by calling setState().
     * @param drawable    - The image to show.
     * @param colorFilter - The {@link android.graphics.ColorFilter} to apply to this state
     */
    void addState(int[] stateSet, Drawable drawable, ColorFilter colorFilter) {
        // this is a new custom method, does not exist in parent class
        int currChild = childrenCount;
        addState(stateSet, drawable);
        filterMap.put(currChild, colorFilter);
    }

    @Override
    public boolean selectDrawable(int idx) {
        if (currIdx != idx) {
            setColorFilter(getColorFilterForIdx(idx));
        }
        boolean result = super.selectDrawable(idx);
        // check if the drawable has been actually changed to the one I expect
        currIdx = result ? idx : currIdx;
        if (!result) {
            // it has not been changed, meaning, back to previous filter
            setColorFilter(getColorFilterForIdx(currIdx));
        }
        return result;
    }

    private ColorFilter getColorFilterForIdx(int idx) {
        return filterMap != null ? filterMap.get(idx) : null;
    }
}