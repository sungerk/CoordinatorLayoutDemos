package sunger.net.org.coordinatorlayoutdemos.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import sunger.net.org.coordinatorlayoutdemos.R;
import sunger.net.org.coordinatorlayoutdemos.widget.ProgressWheel;


/**
 * Created on 15/8/23.
 */
public abstract class BaseLoadMoreRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_HEADER = 2;
    private boolean hasHeader = false;
    private boolean hasFooter;
    private boolean hasMoreData;

    private final List<T> mList = new LinkedList<T>();

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public final ProgressWheel mProgressView;
        public final TextView mTextView;

        public FooterViewHolder(View view) {
            super(view);
            mProgressView = (ProgressWheel) view.findViewById(R.id.progress_view);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }
    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public HeaderViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {//底部 加载view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view_load_more, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view_load_more, parent, false);
            return new HeaderViewHolder(view);
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    public abstract void onBindItemViewHolder(final VH holder, int position);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            //没有更多数据
            if (hasMoreData) {
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).mTextView.setText("正在加载。。。");
            } else {
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.GONE);
                ((FooterViewHolder) holder).mTextView.setText("没有更多数据了。。。。");
            }
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).mTextView.setText("正在加载。。。");
        } else {
            onBindItemViewHolder((VH) holder, position);
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == getBasicItemCount() && hasFooter) {
            return TYPE_FOOTER;
        }
        if (position == 0 && hasHeader)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public List<T> getList() {
        return mList;
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public void append(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
    }

    public void appendToTop(T item) {
        if (item == null) {
            return;
        }
        mList.add(0, item);
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
    }


    public void remove(int position) {
        if (position < mList.size() - 1 && position >= 0) {
            mList.remove(position);
        }
    }

    public void clear() {
        mList.clear();
    }

    private int getBasicItemCount() {
        return mList.size();
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + (hasFooter ? 1 : 0) + (hasHeader ? 1 : 0);
    }

    public T getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setHasFooter(boolean hasFooter) {
        if (this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public void showHeader() {
        if (!hasHeader) {
            this.hasHeader = true;
            this.hasFooter = false;
            this.hasMoreData=true;
            notifyDataSetChanged();
        }
    }


    public void hideHeader() {
        if (hasHeader) {
            this.hasHeader = false;
            this.hasFooter = false;
            notifyDataSetChanged();
        }
    }

    public void setHasMoreData(boolean isMoreData) {
        if (this.hasMoreData != isMoreData) {
            this.hasMoreData = isMoreData;
            notifyDataSetChanged();
        }
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if (this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }
}
