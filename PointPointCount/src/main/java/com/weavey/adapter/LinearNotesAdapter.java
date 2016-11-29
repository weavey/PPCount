package com.weavey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.weavey.fragment.view.OnNotesItemClickListener;
import com.weavey.litepal.Notes;
import com.weavey.utils.LogUtils;
import com.weavey.utils.ViewUtils;
import com.zqu.weavey.ppcount.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-24
 */
public class LinearNotesAdapter extends RecyclerView
        .Adapter<LinearNotesAdapter.LinearViewHolder> {


    private Context context;
    private List<Notes> list;
    private OnNotesItemClickListener listener;

    public LinearNotesAdapter(Context context) {

        this.context = context;
        this.list = new ArrayList<>();

    }

    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {

        View view = ViewUtils.inflate(R.layout.item_linear_note);
        LinearViewHolder holder = new LinearViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(LinearViewHolder holder, final int position) {

        holder.item.setOnRippleCompleteListener(new RippleView
                .OnRippleCompleteListener() {


            @Override
            public void onComplete(RippleView rippleView) {

                listener.onClick(list.get(position), position);
            }
        });

        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder {

        RippleView item;
        TextView content;
        TextView time;
        TextView title;

        public LinearViewHolder(View itemView) {
            super(itemView);

            item = ViewUtils.findViewById(itemView, R.id.item_lin_ripple);
            title = ViewUtils.findViewById(itemView, R.id.item_lin_title);
            content = ViewUtils.findViewById(itemView, R.id.item_lin_content);
            time = ViewUtils.findViewById(itemView, R.id.item_lin_time);

        }
    }

    public void setOnLinearItemListener(OnNotesItemClickListener listener) {
        this.listener = listener;
    }


    public void updateData(List<Notes> list) {

        this.list = list;
        notifyDataSetChanged();

    }

}
