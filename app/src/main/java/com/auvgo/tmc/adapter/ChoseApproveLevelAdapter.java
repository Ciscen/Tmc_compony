package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.views.MyDialog;

import java.util.List;

/**
 * Created by lc on 2016/11/23
 */

public class ChoseApproveLevelAdapter extends BaseAdapter {
    private List<ApproveLevelBean> list;
    private LayoutInflater inflater;
    private Context context;

    public ChoseApproveLevelAdapter(Context context, List<ApproveLevelBean> list) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public List<ApproveLevelBean> getList() {
        return list;
    }

    public void setList(List<ApproveLevelBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_approve_chose, null);
        TextView name = (TextView) view.findViewById(R.id.item_approve_chose_name);
        TextView see = (TextView) view.findViewById(R.id.item_approve_chose_see);
        ImageView flag = (ImageView) view.findViewById(R.id.item_approve_chose_flag);
        final ApproveLevelBean approveLevelBean = list.get(position);
        name.setText(approveLevelBean.getName());
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = getContent(approveLevelBean.getShenpirens());
                MyDialog dialog = new MyDialog(context, "审批人", "", "关闭", content, null);
                dialog.show();
            }
        });
        if (approveLevelBean.isCheck()) {
            flag.setVisibility(View.VISIBLE);
        } else {
            flag.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private String getContent(List<ApproveLevelBean.ShenpirensBean> shenpirens) {
        StringBuilder sb = new StringBuilder();
        if (shenpirens != null) {
            for (int i = 0; i < shenpirens.size(); i++) {
                ApproveLevelBean.ShenpirensBean bean = shenpirens.get(i);
                sb.append(bean.getLevel());
                sb.append("级审批");
                sb.append("  ");
                sb.append(bean.getName());
                if (i != shenpirens.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
}
