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
 * on date 2016-03-26
 */
public class GridNotesAdapter extends RecyclerView.Adapter<GridNotesAdapter
        .GridViewHolder> {


    private Context context;
    private List<Notes> list;
    private OnNotesItemClickListener listener;

    public GridNotesAdapter(Context context) {

        this.context = context;
        this.list = new ArrayList<>();

    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = ViewUtils.inflate(R.layout.item_grid_notes);
        GridViewHolder holder = new GridViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {

       holder.item.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {


           @Override
           public void onComplete(RippleView rippleView) {

               listener.onClick(list.get(position),position);
           }
       });

        holder.content.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class GridViewHolder extends RecyclerView.ViewHolder {

        RippleView item;
        TextView content;
        TextView time;

        public GridViewHolder(View itemView) {
            super(itemView);
            item = ViewUtils.findViewById(itemView,R.id.item_grid_ripple);
            content = ViewUtils.findViewById(itemView,R.id.item_grid_content);
            time = ViewUtils.findViewById(itemView,R.id.item_grid_time);
        }
    }

    public void setOnGridItemListener(OnNotesItemClickListener listener){
        this.listener = listener;
    }


    public void updateData(List<Notes> list){

        this.list = list;
        notifyDataSetChanged();

    }

}
