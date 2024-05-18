package qzhenghao.cn.yumunote.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.Sideslip.SlideRecyclerView;
import qzhenghao.cn.yumunote.adapter.NoteAdapter;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.dao.DBManager;
import qzhenghao.cn.yumunote.refreshview.XRefreshView;
import qzhenghao.cn.yumunote.refreshview.XRefreshViewFooter;

public class NotesActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected TextView toolbar_center_title;
    protected boolean isShowToolbar = true;
    protected boolean isSetNavigationIcon = true;
    protected boolean isSetLogo = false;
    protected String title="";
    protected String centertitle="备忘录";
    protected String subtitle=null;
    private SlideRecyclerView rvNote;
    private DBManager dbManager;
    private List<NoteItemBean> noteItemBeanList;
    private NoteAdapter noteAdapter;
    private String FOLDER_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        FOLDER_ID = getIntent().getStringExtra("folder_id");
        initView();
        initData();
        //侧滑删除
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        noteAdapter.setOnDeleteClickListener(new NoteAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                NoteItemBean noteItemBean = noteItemBeanList.get(position);
                dbManager.delNote(noteItemBean.getId());
                noteItemBeanList.remove(position);
                noteAdapter.notifyDataSetChanged();
                rvNote.closeMenu();
            }
        });
    }

    private void initView() {
        setActivityToolbar();
        //滑动刷新
        rvNote =  this.findViewById(R.id.rvNote);
        rvNote.setLayoutManager(new GridLayoutManager(this, GridLayoutManager.VERTICAL, 1, false));
//        refreshNote = (XRefreshView) this.findViewById(R.id.refreshNote);
//        refreshNote.setPullLoadEnable(true);
//        refreshNote.setAutoLoadMore(true);
        rvNote.setItemAnimator(null);

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
        loadData();
        //适配器
        noteAdapter = new NoteAdapter(this, noteItemBeanList);
        Log.e("qzh123", noteItemBeanList + "");
        //noteAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        rvNote.setAdapter(noteAdapter);
//        refreshNote.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(() -> {
//                    dbManager = new DBManager(getApplicationContext());
//                    noteAdapter.notifyDataSetChanged();
//                    //noteAdapter.notifyItemChanged(0,noteItemBeanList.size());
//                        //rvNote.setAdapter(new NoteAdapter(getApplicationContext(), noteItemBeanList));
//                    refreshNote.stopRefresh();
//                }, 1000);
//            }
//            @Override
//            public void onLoadMore(boolean isSilence) {
//                new Handler().postDelayed(() ->{
//                    dbManager = new DBManager(getApplicationContext());
//                    noteItemBeanList.clear();
//                    try {
//                        noteItemBeanList.addAll(dbManager.queryAllNotes(Integer.valueOf(FOLDER_ID)));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    //noteAdapter.notifyItemChanged(0,noteItemBeanList.size());
//                    noteAdapter.notifyDataSetChanged();
//                    refreshNote.stopLoadMore();
//                }, 1000);
//            }
//        });
    }
    private void loadData() {
        noteItemBeanList = new ArrayList<>();
        dbManager = new DBManager(this);
        try {
            noteItemBeanList = dbManager.queryAllNotes(Integer.valueOf(FOLDER_ID));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        //默认隐藏setting按钮
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
                menuAddNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuAddNote() {
        Intent intent = new Intent(getApplicationContext(), NoteEditActivity.class);
        intent.putExtra("folder_id",FOLDER_ID);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == 1) {

            noteItemBeanList.removeAll(noteItemBeanList);
            dbManager = new DBManager(this);
            try {
                noteItemBeanList.addAll(dbManager.queryAllNotes(Integer.valueOf(FOLDER_ID)) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            noteAdapter.notifyItemInserted(noteItemBeanList.size());
        }

    }
}
