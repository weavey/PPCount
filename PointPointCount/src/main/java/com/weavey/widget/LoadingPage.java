//package com.zqu.weavey.widget;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.FrameLayout;
//
//import com.zqu.weavey.ppcount.R;
//import LogUtils;
//import UiUtils;
//
//
///**
// * 加载进度条
// *
// * @author jayden 2015年7月22日
// */
//public abstract class LoadingPage extends FrameLayout {
//
//	public static final int STATE_UNKOWN = 0;
//	public static final int STATE_LOADING = 1;
//	public static final int STATE_ERROR = 2;
//	public static final int STATE_EMPTY = 3;
//	public static final int STATE_SUCCESS = 4;
//	public int state = STATE_UNKOWN;
//	public boolean flag = false;
//
//	private View loadingView;// 加载中的界面
//	private View errorView;// 错误界面
//	private View emptyView;// 空界面
//	private View successView;// 加载成功的界面
//
//	public LoadingPage(Context context) {
//		super(context);
//		init();
//	}
//
//	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init();
//	}
//
//	public LoadingPage(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init();
//	}
//
//	private void init() {
//
//		//设置了加载进度条
//		if(showLoading()){
//		loadingView = createLoadingView(); // 创建了加载中的界面
//		if (loadingView != null) {
//			this.addView(loadingView, new LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//		errorView = createErrorView(); // 加载错误界面
//		if (errorView != null) {
//			this.addView(errorView, new LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//		emptyView = createEmptyView(); // 加载空的界面
//		if (emptyView != null) {
//			this.addView(emptyView, new LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//
//		//加载主视图
//		if (successView == null) {
//			successView = createView();
//			this.addView(successView, new LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//		successView.setVisibility(View.GONE);
//
//
//		showPageOrNot(showLoading());// 根据不同的状态显示不同的界面
//		}
//		//没设置加载进度条
//		else{
//			if (successView == null) {
//				successView = createView();
//				this.addView(successView, new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//			}
//			successView.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	// 根据不同的状态显示不同的界面
//	private void showPage() {
//		if (loadingView != null) {
//			loadingView.setVisibility(state == STATE_UNKOWN
//					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
//		}
//		if (errorView != null) {
//			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
//					: View.INVISIBLE);
//		}
//		if (emptyView != null) {
//			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
//					: View.INVISIBLE);
//		}
//		if (state == STATE_SUCCESS) {
//			if (successView == null) {
//				LogUtils.log("successView：：：：：：：：：：：：：：：：：：：：：：：：：；");
//				successView = createView();
//				this.addView(successView, new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//			}
//			successView.setVisibility(View.VISIBLE);
//		} else {
//			if (successView != null) {
//				successView.setVisibility(View.INVISIBLE);
//			}
//		}
//
//	}
//
//	/* 创建了空的界面 */
//	private View createEmptyView() {
//		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_empty,
//				null);
//		return view;
//	}
//
//	/* 创建了错误界面 */
//	private View createErrorView() {
//		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_error,
//				null);
//		Button page_bt = (Button) view.findViewById(R.id.page_bt);
//		page_bt.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				successView = createView();
//				showPageOrNot(showLoading());
//			}
//		});
//		return view;
//	}
//
//	/* 创建加载中的界面 */
//	private View createLoadingView() {
//		View view = View.inflate(UiUtils.getContext(),
//				R.layout.loadpage_loading, null);
//		return view;
//	}
//
//	public enum LoadResult {
//		error(2), empty(3), success(4);
//
//		int value;
//
//		LoadResult(int value) {
//			this.value = value;
//		}
//
//		public int getValue() {
//			return value;
//		}
//
//	}
//
//	public void stopLoading(LoadResult result) {
//		this.state = result.getValue();
//		showPage();
//	}
//
//	private void showPageOrNot(boolean flag) {
//		if (flag) {
//			state = STATE_LOADING;
//			showPage();
//		} else {
//			if (successView == null) {
//				successView = createView();
//				this.addView(successView, new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//			}
//			successView.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	/***
//	 * 创建成功的界面
//	 *
//	 * @return
//	 */
//	public abstract View createView();
//
//	/**
//	 * 是否显示加载条
//	 * @return
//	 */
//	protected abstract boolean showLoading();
//}
