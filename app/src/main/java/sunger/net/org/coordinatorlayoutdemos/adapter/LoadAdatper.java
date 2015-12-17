package sunger.net.org.coordinatorlayoutdemos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sunger.net.org.coordinatorlayoutdemos.R;
import sunger.net.org.coordinatorlayoutdemos.refresh.BaseLoadMoreRecyclerAdapter;

/**
 * Created by sunger on 2015/12/17.
 */
public class LoadAdatper extends BaseLoadMoreRecyclerAdapter<String, LoadAdatper.ViewHolder> {

    public LoadAdatper( ) {
     }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position) {
        try{
            holder.mTextView.setText(getItem(position));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }

}
