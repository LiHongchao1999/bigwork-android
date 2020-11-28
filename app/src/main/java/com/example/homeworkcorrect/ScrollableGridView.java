package com.example.homeworkcorrect;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class ScrollableGridView extends GridView {

    public ScrollableGridView(Context context) {
        super(context);
    }

    public ScrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /*
    * 设置不滚动
    * */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}