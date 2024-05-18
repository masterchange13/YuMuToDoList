package qzhenghao.cn.yumunote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.adapter.SearchAdapter;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.dao.DBManager;

import qzhenghao.cn.yumunote.utils.StringUtil;

public class SearchActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected TextView toolbar_center_title;
    protected boolean isShowToolbar = true;
    protected boolean isSetNavigationIcon = true;
    protected boolean isSetLogo = false;
    protected String title="";
    protected String centertitle="备忘录";
    protected String subtitle=null;
    private RecyclerView rvNote;
    private DBManager dbManager;
    private List<NoteItemBean> noteItemBeanList;
    private SearchAdapter searchAdapter;
    private String FOLDER_ID;
    private SearchView search_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notes);
        //FOLDER_ID = getIntent().getStringExtra("folder_id");
        initView();
        //搜索
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("qzh14", s + "4");
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("qzh14", s + "2");
                //做本地查询
                if (!StringUtil.isNullOrEmpty(s)) {
                    noteItemBeanList.addAll(dbManager.search(s));
                    //刷新adapter
                    searchAdapter.notifyDataSetChanged();
                } else {
                    searchAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        initData();
    }

    private void initView() {
        setActivityToolbar();
        //滑动刷新
        rvNote =  this.findViewById(R.id.rvNote);
        rvNote.setLayoutManager(new GridLayoutManager(this, GridLayoutManager.VERTICAL, 1, false));
//        refreshNote = (XRefreshView) this.findViewById(R.id.refreshNote);
//        refreshNote.setPullLoadEnable(true);
//        refreshNote.setAutoLoadMore(true);
        //搜索视图
        search_view = findViewById(R.id.search_view);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) search_view.getLayoutParams();
        //将文字内容略微下移，SearchView  bug
        params.bottomMargin = -3;
        search_view.setLayoutParams(params);
        search_view.onActionViewExpanded();
        //initSearchView();
        Log.e("qzh14", search_view + "1");

    }
    private void initSearchView() {
        //一处searchView进入屏幕时候的焦点
        search_view.clearFocus();
        Class<? extends SearchView> aClass = search_view.getClass();
        try {
            //去掉SearchView自带的下划线
            Field mSearchPlate = aClass.getDeclaredField("mSearchPlate");
            mSearchPlate.setAccessible(true);
            View o = (View) mSearchPlate.get(search_view);
            //o.setBackgroundColor(getColor(R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        assert search_view != null;
        imm.hideSoftInputFromWindow(search_view.getWindowToken(), 0);
    }
    private void setActivityToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_note);
        toolbar_center_title = (TextView) findViewById(R.id.toolbar_note_center_title);
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
//                由于要兼容低版本，所以采用这个划杠的方法，需要自己根据需求替换图片
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_back));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
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

    private void initData() {
        noteItemBeanList = new ArrayList<>();
        dbManager = new DBManager(this);
        //适配器
        searchAdapter = new SearchAdapter(this, noteItemBeanList);

        rvNote.setAdapter(searchAdapter);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        searchAdapter.notifyDataSetChanged();
//    }
}
