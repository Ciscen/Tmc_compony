package com.auvgo.tmc.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2016/11/21
 */

public class MyPickerView {
    private View cover;
    private Context context;
    private List<? extends Selection> selections;
    private PickerListAdapter2 adapter2;
    private int mPosition = -1;
    private OnPickerViewSure listener;
    private String title;
    private MyCustomDialog.OndismissListener l;
    public static final int WRAP_HEIGHT = 0;
    public static final int SURE_HEIGHT = 1;
    public static final int SINGLE_CHOICE = 0;
    public static final int MULTI_CHOICE = 1;
    private int currentChoiceMode;
    private List<Integer> mPositions;

    public MyPickerView setPosition(List<Integer> mPositions) {
        this.mPositions = mPositions;
        return this;
    }

    public interface OnPickerViewSure {
        /**
         * 单选时会被调用
         */
        void onSingleSureClick(int p);

        /**
         * 多选点击确定时会被调用
         */
        void onMultiSureClick(List<Integer> s);
    }


    public static abstract class Selection implements Cloneable {
        protected boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public abstract String getName();

        @Override
        public Object clone() throws CloneNotSupportedException {
            Selection s = null;
            s = (Selection) super.clone();
            return s;
        }
    }

    public MyPickerView(Context context, View cover) {
        this.cover = cover;
        this.context = context;
    }


    public MyPickerView setSelections(List<? extends Selection> list) {
        this.selections = list;
        adapter2 = new PickerListAdapter2(context, list);
        return this;
    }

    public MyPickerView setPosition(int position) {
        this.mPosition = position;
        return this;
    }

    public MyPickerView setListener(OnPickerViewSure listener) {
        this.listener = listener;
        return this;
    }

    public MyPickerView setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 完全展开的,单选模式
     */
    public void showSingleChoseExpandable() {
        show(WRAP_HEIGHT, SINGLE_CHOICE);
    }


    public void showSingleChoiceWithHeightMode(int mode) {
        show(mode, SINGLE_CHOICE);
    }

    public void show(int heightMode, final int choiseMode) {
        this.currentChoiceMode = choiseMode;
        if (choiseMode == SINGLE_CHOICE)
            initSelectedItem2(mPosition);
        else
            initSelectedItem2(mPositions);
        KeyBoardUtils.closeKeybord(context);
        View contentView = View.inflate(context, R.layout.layout_picker_pop, null);
        ListView mlv = (MyListView2) contentView.findViewById(R.id.picker_pop_lv);
        ViewGroup.LayoutParams lp = mlv.getLayoutParams();
        if (heightMode == SURE_HEIGHT) {
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 190,
                    context.getResources().getDisplayMetrics());
        } else {
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180,
                    context.getResources().getDisplayMetrics());
        }
        mlv.setLayoutParams(lp);
        TextView tv_title = (TextView) contentView.findViewById(R.id.pop_title);
        View cancle = contentView.findViewById(R.id.pop_cancle);
        View sure = contentView.findViewById(R.id.pop_sure);
        tv_title.setText(title);
        mlv.setAdapter(adapter2);
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (choiseMode == SINGLE_CHOICE) {
                    initSelectedItem2(position);
                } else {
                    Selection bean = selections.get(position);
                    bean.setChecked(!bean.isChecked());
                    if (position == 0)
                        initSelectedItem2(0);
                    else selections.get(0).setChecked(false);
                    initMultiSelections();
                }
                adapter2.notifyDataSetChanged();
            }
        });
        final MyCustomDialog dialog = new MyCustomDialog(contentView, cover);
        dialog.show();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectedItems = getSelectedItem();
                mPosition = selectedItems.size() == 0 ? -1 : selectedItems.get(0);
                if (listener != null) {
                    if (choiseMode == SINGLE_CHOICE) {
                        listener.onSingleSureClick(mPosition);
                    } else {
                        listener.onMultiSureClick(selectedItems);
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.setDismissListener(new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                if (l != null)
                    l.onDismiss();
            }
        });
    }

    private void initMultiSelections() {
        if (selections.get(0).isChecked()) {
            for (int i = 0; i < selections.size(); i++) {
                if (i > 0) {
                    selections.get(i).setChecked(false);
                }
            }
            return;
        }
        boolean b = false;
        for (int i = 0; i < selections.size(); i++) {
            if (selections.get(i).isChecked()) {
                b = true;
                break;
            }
        }
        if (!b) {
            selections.get(0).setChecked(true);
        }
//        b = false;
//        if (!selections.get(0).isChecked()) {
//            for (int i = 0; i < selections.size(); i++) {
//                if (i > 0 && !selections.get(i).isChecked()) {
//                    b = true;
//                    break;
//                }
//            }
//        } else {
//            b = true;
//        }
//        if (!b) {
//            selections.get(0).setChecked(true);
//            initMultiSelections();
//        }
    }

    private void initSelectedItem2(List<Integer> mPositions) {
        if (mPositions != null) {
            for (int i = 0; i < mPositions.size(); i++) {
                Selection bean = selections.get(mPositions.get(i));
                bean.setChecked(true);
            }
        }
    }

    /**
     * 多选模式
     *
     * @param mode WRAP_HEIGHT 根据内容数量变动高度；SURE_HEIGHT 定高
     */
    public void showMultiChoseWithHeightMode(int mode) {
        show(mode, MULTI_CHOICE);
    }

    public MyPickerView setMyPickerViewDismiss(MyCustomDialog.OndismissListener l) {
        this.l = l;
        return this;
    }

    /*
     设置初始选择项目
      */
    private void initSelectedItem2(int position) {
        for (int i = 0; i < selections.size(); i++) {
            Selection selectionBean = selections.get(i);
            if (i == position) {
                selectionBean.setChecked(true);
            } else {
                selectionBean.setChecked(false);
            }
        }
    }


    private List<Integer> getSelectedItem() {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < selections.size(); i++) {
            if (selections.get(i).isChecked()) {
                l.add(i);
            }
        }
        return l;
    }

}
