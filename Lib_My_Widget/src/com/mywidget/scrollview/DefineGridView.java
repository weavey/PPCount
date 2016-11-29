package com.mywidget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 重写GridView,用于ScrollView内部嵌套
 * 
 *
 */
public class DefineGridView extends GridView {
	public DefineGridView(Context context) {
		super(context);
	}

	public DefineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DefineGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使GridView适应ScrollView的效�?
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
