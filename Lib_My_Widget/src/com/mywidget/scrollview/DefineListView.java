package com.mywidget.scrollview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写ListView,用于ScrollView内部嵌套
 * 
 *
 */
public class DefineListView extends ListView {
	public DefineListView(Context context) {
		super(context);
	}

	public DefineListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DefineListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效�?
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}

