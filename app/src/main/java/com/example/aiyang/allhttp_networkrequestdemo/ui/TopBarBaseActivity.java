package com.example.aiyang.allhttp_networkrequestdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.aiyang.allhttp_networkrequestdemo.R;

/**
 * Created by aiyang on 2017/5/4.
 */

public abstract class TopBarBaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    FrameLayout viewContent;

    TextView tvTitle;

    onClickListener onClickListenerTopleft;

    onClickListener onClickListenerTopRight;
    //icon 图片id
    int menuResId;
    String menuStr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        viewContent= (FrameLayout) findViewById(R.id.viewContent);
        setSupportActionBar(toolbar);
        //设置不现实自带的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //将继承TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(TopBarBaseActivity.this).inflate(getConentView(),viewContent);

        init(savedInstanceState);
    }

    protected void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
    }

    protected abstract  int getConentView();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onClickListenerTopleft.onClick();
        }
        return true;//告诉系统 我们自己处理事件
    }

    protected abstract void init(Bundle savedInstanceState);

    public interface  onClickListener{
        void onClick();
    }

    protected void setTopLeftButton(int iconResId,onClickListener onClickListener){
        toolbar.setNavigationIcon(iconResId);
        this.onClickListenerTopleft = onClickListener;
    }

    protected void setTopRightButton(String menuStr,onClickListener onClickListener){
        this.onClickListenerTopRight = onClickListener;
        this.menuStr=menuStr;
    }

    protected void setTopRightButton(String menuStr,int menuResId,onClickListener onClickListener){
        this.onClickListenerTopRight = onClickListener;
        this.menuStr=menuStr;
        this.menuResId=menuResId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId !=0|| !TextUtils.isEmpty(menuStr)){
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar,menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0){
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)){
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
