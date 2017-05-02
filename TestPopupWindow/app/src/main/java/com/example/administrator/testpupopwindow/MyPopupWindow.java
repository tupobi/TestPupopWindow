package com.example.administrator.testpupopwindow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPopupWindow extends AppCompatActivity {
    private EditText etInput;
    private ImageView ivDownArrow;
    private PopupWindow popupWindow;
    private ListView listView;
    private ArrayList<String> msgs;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setOnClick();
    }

    private void setOnClick() {
        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MyPopupWindow.this);
                    popupWindow.setWidth(etInput.getWidth());
                    popupWindow.setHeight(DensityUtil.dip2px(MyPopupWindow.this, 190));
                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);
                }
                popupWindow.showAsDropDown(etInput, 0, 0);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_my_popup_window);
        etInput = (EditText) findViewById(R.id.et_input);
        ivDownArrow = (ImageView) findViewById(R.id.iv_downArrow);
        listView = new ListView(this);
        msgs = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            msgs.add(i + "aaaaaaaa" + i);
        }
        myAdapter = new MyAdapter();
        listView.setBackgroundColor(Color.LTGRAY);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String msg = msgs.get(i);
                etInput.setText(msg);

                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (view == null) {
                view = View.inflate(MyPopupWindow.this, R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvMsg = (TextView) view.findViewById(R.id.tv_msg);
                viewHolder.ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final String msg = msgs.get(i);
            viewHolder.tvMsg.setText(msg);

            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    msgs.remove(msg);
                    myAdapter.notifyDataSetChanged();
                }
            });

            return view;
        }
    }

    static class ViewHolder {
        TextView tvMsg;
        ImageView ivDelete;
    }
}
