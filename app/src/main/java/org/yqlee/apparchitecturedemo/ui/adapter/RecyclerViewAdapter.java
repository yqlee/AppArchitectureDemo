package org.yqlee.apparchitecturedemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;
import org.yqlee.apparchitecturedemo.ui.adapter.common.CommonRecyclerAdapter;

import java.util.List;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-12 下午 2:52
 * 描述 ：TODO 请描述该类职责
 */
public class RecyclerViewAdapter extends CommonRecyclerAdapter<SubjectEntity> {

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    public RecyclerViewAdapter(Context mContext, List<SubjectEntity> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_adapter,
                parent, false);
        SubjectItemViewHolder holder = new SubjectItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SubjectItemViewHolder) {
            SubjectEntity subjectEntity = mDatas.get(position);
            ((SubjectItemViewHolder) holder).tv_title.setText(subjectEntity.getTitle());
        }
    }

    public class SubjectItemViewHolder extends ItemViewHolder {

        TextView tv_title;

        public SubjectItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void findHolderView(View v) {
            tv_title = (TextView) v.findViewById(R.id.tv_title);
        }
    }

}
