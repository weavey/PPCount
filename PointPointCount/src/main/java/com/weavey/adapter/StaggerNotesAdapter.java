package com.weavey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.weavey.fragment.view.OnNotesItemClickListener;
import com.weavey.litepal.Notes;
import com.weavey.utils.LogUtils;
import com.weavey.utils.UiUtils;
import com.weavey.utils.ViewUtils;
import com.zqu.weavey.ppcount.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-26
 */
public class StaggerNotesAdapter extends RecyclerView
        .Adapter<StaggerNotesAdapter.StaggerViewHolder> {


    private Context context;
    private List<Notes> list;
    private List<Integer> maxLine;
    private List<Integer> minLine;
    private OnNotesItemClickListener listener;

    public StaggerNotesAdapter(Context context) {

        this.context = context;
        this.list = new ArrayList<>();
        this.maxLine = new ArrayList<>();
        this.minLine = new ArrayList<>();
        getRandomHeight();

    }

    @Override
    public StaggerViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {

        View view = ViewUtils.inflate(R.layout.item_grid_notes);
        StaggerViewHolder holder = new StaggerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(StaggerViewHolder holder, final int position) {

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
//                (ViewGroup.LayoutParams.WRAP_CONTENT, heights.get(position));
        //得到item的LayoutParams布局参数
//        ViewGroup.LayoutParams params = holder.root.getLayoutParams();
//        params.height = 34;//把随机的高度赋予itemView布局
//        holder.content.setLayoutParams(params);//把params设置给itemView布局
        holder.content.setMaxLines(maxLine.get(position));
        holder.content.setMinLines(minLine.get(position));
        holder.item.setOnRippleCompleteListener(new RippleView
                .OnRippleCompleteListener() {


            @Override
            public void onComplete(RippleView rippleView) {
                listener.onClick(list.get(position), position);
            }

        });

        holder.content.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void getRandomHeight() {//得到随机item的高度

        for (int i = 0; i < 20; i++) {
            maxLine.add((int) (5 + Math.random() * 10));
            minLine.add((int) (2 + Math.random() * 5));
        }
    }

    class StaggerViewHolder extends RecyclerView.ViewHolder {

        RippleView item;
        TextView content;
        TextView time;

        public StaggerViewHolder(View itemView) {
            super(itemView);

            content = ViewUtils.findViewById(itemView, R.id.item_grid_content);
            time = ViewUtils.findViewById(itemView, R.id.item_grid_time);
            item = ViewUtils.findViewById(itemView, R.id.item_grid_ripple);

        }
    }

    public void setOnStaggerItemListener(OnNotesItemClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<Notes> list) {

        this.list = list;
        notifyDataSetChanged();

    }
}