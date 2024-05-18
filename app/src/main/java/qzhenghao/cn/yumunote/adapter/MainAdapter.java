package qzhenghao.cn.yumunote.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import qzhenghao.cn.yumunote.activity.NotesActivity;
import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.bean.FolderItemBean;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.refreshview.recyclerview.BaseRecyclerAdapter;

/**
 * @author suiyue
 * @ClassName MainAdapter
 * @Description TODO
 * @date 2019/10/15 13:03
 */
public class MainAdapter extends BaseRecyclerAdapter<MainAdapter.MainViewHolder> {
    private Context context;
    private List<FolderItemBean> folderItemBeanList;

    private NoteAdapter.OnDeleteClickLister mDeleteClickListener;
    public void setOnDeleteClickListener(NoteAdapter.OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }



    public MainAdapter(Context context, List<FolderItemBean> folderItemBeanList) {
        this.context = context;
        this.folderItemBeanList = folderItemBeanList;
    }

    @Override
    public MainViewHolder getViewHolder(View view) {
        return new MainViewHolder(view);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_folder_layout, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position, boolean isItem) {
        MainViewHolder MainViewHolder = holder;
        MainViewHolder.tv_delete.setTag(position);
        if (!MainViewHolder.tv_delete.hasOnClickListeners()) {
            MainViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        final FolderItemBean folderItemBean = folderItemBeanList.get(position);
        MainViewHolder.tv_folder.setText(folderItemBean.getFolderName());
        MainViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NotesActivity.class);
            Log.e("qzh1_1k", String.valueOf(folderItemBean.getId()));
            intent.putExtra("folder_id", String.valueOf(folderItemBean.getId()));

            context.startActivity(intent);
        });
    }

    @Override
    public int getAdapterItemCount() {
        return folderItemBeanList.size();
    }


    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView tv_folder;
        TextView tv_delete;
        public MainViewHolder(View itemView) {
            super(itemView);
            tv_folder = (TextView) itemView.findViewById(R.id.tv_folder);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }


}
