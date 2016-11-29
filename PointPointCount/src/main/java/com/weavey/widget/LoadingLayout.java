package com.weavey.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqu.weavey.ppcount.R;


/**
 * create by Weavey
 * on date 2016-03-12
 * TODO
 */
public class LoadingLayout extends FrameLayout {

    private Context context;
    private RelativeLayout loadingPage;
    private RelativeLayout errorPage;
    private RelativeLayout emptyPage;
    private ProgressBar progressbar;
    private TextView loadingText;
    private TextView errorText;
    private TextView emptyText;
    private Button reloadBtn;
    private View loadingView;
    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    private OnClickListener listener;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public LoadingLayout(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public LoadingLayout(Context context) {
        super(context);
        this.context = context;
    }

    public void build() {

        Log.i("mytag", "init loading view");
        loadingView = LayoutInflater.from(context).inflate(R.layout
                .widget_loading_layout, null);
        loadingPage = (RelativeLayout) loadingView.findViewById(R.id
                .loading_page);
        errorPage = (RelativeLayout) loadingView.findViewById(R.id.error_page);
        emptyPage = (RelativeLayout) loadingView.findViewById(R.id.empty_page);
        loadingText = (TextView) loadingView.findViewById(R.id.loading_text);
        errorText = (TextView) loadingView.findViewById(R.id.error_text);
        emptyText = (TextView) loadingView.findViewById(R.id.empty_text);
        progressbar = (ProgressBar) loadingView.findViewById(R
                .id.loading_progressbar);
        reloadBtn = (Button) loadingView.findViewById(R.id.error_reload);
        reloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

        this.addView(loadingView);

    }

    public void startLoading() {

//        progressbar.show();
        loadingPage.setVisibility(View.VISIBLE);
        errorPage.setVisibility(View.GONE);
        emptyPage.setVisibility(View.GONE);


    }

    public void stopLoading(int handler) {

        switch (handler) {

            case Success:

                loadingPage.setVisibility(View.GONE);

                break;

            case Empty:

                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.VISIBLE);
                break;

            case Error:
                loadingPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }

    }

    public void setReloadButtonVisible(boolean visible) {

        if (visible) {
            reloadBtn.setVisibility(View.VISIBLE);
        } else {
            reloadBtn.setVisibility(View.GONE);
        }

    }

    public void setReloadListener(OnClickListener listener){

        this.listener = listener;
    }

    public void setLoadingText(String loadling) {

        loadingText.setText(loadling);

    }

    public void setErrorText(String error) {

        errorText.setText(error);
    }

    public void setEmptyText(String empty) {

        emptyText.setText(empty);
    }


}
