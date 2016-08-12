package org.yqlee.apparchitecturedemo.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;
import org.yqlee.apparchitecturedemo.ui.adapter.common.CommonAdapter;
import org.yqlee.apparchitecturedemo.ui.adapter.common.ViewHolder;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-12 上午 9:34
 * 描述 ：ListView的Adapter
 */
public class ListViewAdapter extends CommonAdapter<SubjectEntity> {

    public ListViewAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindData(int position, ViewHolder holder) {
        TextView tv_title = holder.getView(R.id.tv_title);
        tv_title.setText(mDatas.get(position).getTitle());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_listview_adapter;
    }
}
