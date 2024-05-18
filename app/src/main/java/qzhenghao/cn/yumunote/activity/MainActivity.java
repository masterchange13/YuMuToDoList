package qzhenghao.cn.yumunote.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.Sideslip.SlideRecyclerView;
import qzhenghao.cn.yumunote.adapter.MainAdapter;
import qzhenghao.cn.yumunote.adapter.NoteAdapter;
import qzhenghao.cn.yumunote.bean.FolderItemBean;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.dao.DBManager;
import qzhenghao.cn.yumunote.refreshview.XRefreshView;
import qzhenghao.cn.yumunote.refreshview.XRefreshViewFooter;

public class MainActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected TextView toolbar_center_title;
    protected boolean isShowToolbar = true;
    protected boolean isSetNavigationIcon = false;
    protected boolean isSetLogo = false;
    protected String title="";
    protected String centertitle="";
    protected String subtitle;
    private XRefreshView refreshMain;
    private SlideRecyclerView rvMain;
    private MainAdapter mainAdapter;
    private DBManager dbManager;
    private List<FolderItemBean> folderItemBeanList;
    private DrawerLayout drawer_layout;
    private NavigationView navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        //侧滑删除
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        mainAdapter.setOnDeleteClickListener(new NoteAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                FolderItemBean folderItemBean = folderItemBeanList.get(position);
                dbManager.delFolder(folderItemBean.getId());
                folderItemBeanList.remove(position);
                mainAdapter.notifyDataSetChanged();
                rvMain.closeMenu();
            }
        });
    }

    private void setActivityToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar_center_title = (TextView) findViewById(R.id.toolbar_center_title);
        if (toolbar != null) {
            if (isShowToolbar) {
                setSupportActionBar(toolbar);
            } else {
                toolbar.setVisibility(View.GONE);
            }

            if (title != null && !title.equals("")) {
                toolbar.setTitle(title);
            } else {
                toolbar.setTitle("");
            }
            if (subtitle != null && !subtitle.equals("")) {
                toolbar.setSubtitle(subtitle);
            }
            if (isSetNavigationIcon) {
                //由于要兼容低版本，所以采用这个划杠的方法，需要自己根据需求替换图片
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_back));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            if (isSetLogo) {
                //需要自己根据需求进行替换图片
                toolbar.setLogo(getResources().getDrawable(R.drawable.simle_logo_01));
            }
            if (toolbar_center_title != null) {
                if (centertitle != null && !centertitle.equals("")) {
                    toolbar_center_title.setText(centertitle);
                } else {
                    toolbar_center_title.setText("");
                }
            }
        }
    }

    private void initView() {
        setActivityToolbar();

        //滑动刷新
        rvMain = this.findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new GridLayoutManager(this, GridLayoutManager.VERTICAL, 1, false));
        refreshMain = (XRefreshView) this.findViewById(R.id.refreshMain);
        refreshMain.setPullLoadEnable(true);
        refreshMain.setAutoLoadMore(true);

        //
        drawer_layout = findViewById(R.id.drawer_layout);
        navigation_view = findViewById(R.id.navigation_view);
        //在布局左上角显示(系统默认)图标
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0);
        drawerToggle.syncState();

        //侧滑菜点击选择监听
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_navigation_0:
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.my_navigation_1:
                        System.exit(0);
                        break;
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initData() {
        loadData();
        //适配器
        mainAdapter = new MainAdapter(this, folderItemBeanList);
        mainAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));

        rvMain.setAdapter(mainAdapter);
        refreshMain.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> refreshData(), 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(() -> refreshData(), 1000);
            }
        });
    }
    private void refreshData() {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dbManager = new DBManager(getApplicationContext());
                folderItemBeanList.clear();
                refreshMain.stopRefresh();
                refreshMain.stopLoadMore();
                folderItemBeanList.addAll(dbManager.queryAllFolder());
                mainAdapter.notifyDataSetChanged();
                return;

            }

            @Override
            protected String doInBackground(String... params) {

                return "1";
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadData() {
        folderItemBeanList = new ArrayList<>();
        dbManager = new DBManager(this);
        folderItemBeanList = dbManager.queryAllFolder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        //        默认隐藏setting按钮
        if (toolbar != null) {
            MenuItem menuItem = toolbar.getMenu().getItem(0);
            if (menuItem != null) {
                menuItem.setTitle("添加");
                menuItem.setIcon(R.drawable.ic_note_add);
                menuItem.setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                //添加
                menuAddFolder();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuAddFolder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyUsualDialog);

        // 创建一个view，并且将布局加入view中
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.dialog_my, null, false);
        // 将view添加到builder中
        builder.setView(view);
        // 创建dialog
        final Dialog dialog = builder.create();
        // 初始化控件，注意这里是通过view.findViewById
        final EditText tv_dialog_folderName = (EditText) view.findViewById(R.id.tv_dialog_folderName);
        Button btn_dialog_cancel = (Button) view.findViewById(R.id.btn_dialog_cancel);
        Button btn_dialog_new = (Button) view.findViewById(R.id.btn_dialog_new);
        // 设置button的点击事件及获取editview中的文本内容
        btn_dialog_new.setOnClickListener(arg0 -> {
            String folderName = tv_dialog_folderName.getText() == null ? "" : tv_dialog_folderName.getText()
                    .toString();
            long l = dbManager.insertFolder(folderName);
            FolderItemBean folderItemBean = dbManager.queryOneFolder(l);
            folderItemBeanList.add(folderItemBean);
           mainAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        // 取消按钮
        btn_dialog_cancel.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

    private boolean firstBack = false;

    @Override
    public void onBackPressed() {
        if (!firstBack) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            firstBack = true;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        firstBack = false;
        return super.dispatchTouchEvent(ev);
    }


}
