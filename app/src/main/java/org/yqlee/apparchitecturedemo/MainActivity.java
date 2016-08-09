package org.yqlee.apparchitecturedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_common_page;
    private  Button bt_listview_page;
    private  Button bt_recyclerview_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bt_common_page = (Button) findViewById(R.id.bt_common_page);
        bt_common_page.setOnClickListener(this);
        bt_listview_page = (Button) findViewById(R.id.bt_listview_page);
        bt_listview_page.setOnClickListener(this);
        bt_recyclerview_page = (Button) findViewById(R.id.bt_recyclerview_page);
        bt_recyclerview_page.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_common_page:
                break;
            case R.id.bt_listview_page:
                break;
            case R.id.bt_recyclerview_page:
                break;
        }
    }
}
