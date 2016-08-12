package org.yqlee.apparchitecturedemo.ui.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yqlee.apparchitecturedemo.R;

import java.util.List;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-12 下午 12:03
 * 描述 ：RecyclerView.Adapter的再封装
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context mContext;
    private boolean mShowFooter = true;
    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public CommonRecyclerAdapter(Context context) {
        mContext = context;
    }

    public CommonRecyclerAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public void setDatas(List<T> datas) {
        if (datas == null)
            return;
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        if (mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mDatas == null) {
            return begin;
        }
        return mDatas.size() + begin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            return onCreateItemViewHolder(parent, viewType);
        } else {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading_view,
                    null);
            footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(footView);
        }
    }

    protected abstract ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    public void isShowFooter(boolean showFooter) {
        mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return mShowFooter;
    }


    /**
     * ItemHolder
     */
    public abstract class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ItemViewHolder(View itemView) {
            super(itemView);
            findHolderView(itemView);
            itemView.setOnClickListener(this);
        }

        protected abstract void findHolderView(View v);

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

}
