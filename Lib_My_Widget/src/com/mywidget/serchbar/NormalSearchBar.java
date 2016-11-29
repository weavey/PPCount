package com.mywidget.serchbar;

import com.example.lib_my_widget.R;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * 
 * 搜索栏
 * **/

public class NormalSearchBar extends RelativeLayout {

	private Context context;
	private EditText editText;
	private Button btn;
	private String hintStr;

	public NormalSearchBar(Context context) {
		this(context, null);

	}

	public NormalSearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;

		// 从xml布局文件中获取初始化参数
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.NormalSearchBar);
		hintStr = a.getString(R.styleable.NormalSearchBar_editHint);
		a.recycle();

		View.inflate(context, R.layout.normal_search_bar, this);
		editText = (EditText) findViewById(R.id.normal_searchbar_edittext);
		btn = (Button) findViewById(R.id.normal_searchbar_btn);

		editText.setHint(hintStr);
		editText.addTextChangedListener(new TextWatcher() {
			
			@SuppressLint("NewApi")
			@Override
			public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3) {

				if(!str.toString().equals("")){
					 btn.setBackgroundResource(R.drawable.normal_searchbar_btn);
				}else
				{
					btn.setBackgroundResource(R.drawable.serch_gray);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
			}
		});
	}

	public void setCleanLisetner(OnClickListener onClickListener) {

		btn.setOnClickListener(onClickListener);

	}

	public EditText getEditTextView() {

		return editText;
	}

	public Button getButtonView() {

		return btn;
	}

}
