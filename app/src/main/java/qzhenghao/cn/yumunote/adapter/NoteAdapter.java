package qzhenghao.cn.yumunote.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.activity.NoteEditActivity;
import qzhenghao.cn.yumunote.activity.NotesActivity;
import qzhenghao.cn.yumunote.bean.FolderItemBean;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.refreshview.recyclerview.BaseRecyclerAdapter;
import qzhenghao.cn.yumunote.utils.RelativeDateFormat;

/**
 * @author suiyue
 * @ClassName MainAdapter
 * @Description TODO
 * @date 2019/10/15 13:03
 */
public class NoteAdapter extends BaseRecyclerAdapter<NoteAdapter.MainViewHolder> {
    private Context context;
    private List<NoteItemBean> noteItemBeanList;

    private OnDeleteClickLister mDeleteClickListener;
    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }


    public NoteAdapter(Context context, List<NoteItemBean> noteItemBeanList) {
        this.context = context;
        this.noteItemBeanList = noteItemBeanList;
    }

    @Override
    public MainViewHolder getViewHolder(View view) {
        return new MainViewHolder(view);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_layout, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position, boolean isItem) {
        MainViewHolder mainViewHolder = holder;
        mainViewHolder.tv_delete.setTag(position);
        if (!mainViewHolder.tv_delete.hasOnClickListeners()) {
            mainViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        NoteItemBean noteItemBean = noteItemBeanList.get(position);
        //设置备忘录每条时间
        mainViewHolder.tv_note_time.setText(RelativeDateFormat.getTimeStringAutoShort(noteItemBean.getUp_data(),true));
        //设置每条内容
        mainViewHolder.tv_noteContent.setText(noteItemBean.getContent());
        //闹钟图片显示与否
//        if (noteItemBean.getIs_warn() == 0) {
//            holder.img_clock.setVisibility(View.INVISIBLE);
//        } else {
//            //过期不显示
//            if (noteItemBean.getWarn_data().getTime() - new Date().getTime() < 0) {
//                holder.img_clock.setVisibility(View.INVISIBLE);
//            }
//        }
        mainViewHolder.img_clock.setTag(noteItemBean.getId());
        if (noteItemBean.getIs_warn() == 1&&mainViewHolder.img_clock.getTag().equals(noteItemBean.getId())) {
//            holder.img_clock.setVisibility(View.VISIBLE);//过期不显示
            if (noteItemBean.getWarn_data().getTime() - new Date().getTime() > 0) {
                mainViewHolder.img_clock.setImageResource(R.drawable.note_clock_waker);

            } else {
                mainViewHolder.img_clock.setVisibility(View.INVISIBLE);
            }
        } else {
            mainViewHolder.img_clock.setVisibility(View.INVISIBLE);
        }



        //点击事件，跳转详情页
        mainViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteEditActivity.class);
            intent.putExtra("note_id",  String.valueOf(noteItemBean.getId()));

            context.startActivity(intent);

        });
    }

    @Override
    public int getAdapterItemCount() {
        return noteItemBeanList.size();
    }


    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView tv_noteContent;
        TextView tv_note_time;
        TextView tv_delete;
        ImageView img_clock;

        public MainViewHolder(View itemView) {
            super(itemView);
            tv_noteContent = (TextView) itemView.findViewById(R.id.tv_noteContent);
            tv_note_time = itemView.findViewById(R.id.tv_note_time);
            img_clock = itemView.findViewById(R.id.img_clock);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }


}
