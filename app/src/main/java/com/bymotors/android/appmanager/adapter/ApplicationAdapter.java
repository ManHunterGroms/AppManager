package com.bymotors.android.appmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bymotors.android.appmanager.R;
import com.bymotors.android.appmanager.adapter.ApplicationAdapter.ApplicationViewHolder;
import com.bymotors.android.appmanager.model.AppModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by bymot on 27.06.2017.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationViewHolder> {

    private Context mContext;
    private List<AppModel> mAppModelList;
    private final ApplicationOnClickHandler mOnClickHandler;

    public ApplicationAdapter(Context context, List<AppModel> appModelList, ApplicationOnClickHandler onClickHandler) {
        this.mContext = context;
        this.mAppModelList = appModelList;
        this.mOnClickHandler = onClickHandler;
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.recycler_item_application, parent, false);

        ApplicationViewHolder viewHolder = new ApplicationViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        holder.setValues(mAppModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAppModelList.size();
    }

    public  interface ApplicationOnClickHandler {
        void onClickItem(AppModel appModel);
    }

    public class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mDescTextView;
        private TextView mSizeTextView;
        private TextView mDateTextView;
        private ImageView mIconImageView;

        public ApplicationViewHolder(View view) {
            super(view);

            mNameTextView = (TextView) view.findViewById(R.id.name_text_view);
            mDescTextView = (TextView) view.findViewById(R.id.description_text_view);
            mIconImageView = (ImageView) view.findViewById(R.id.icon_image_view);
            mDateTextView = (TextView) view.findViewById(R.id.date_text_view);
            mSizeTextView = (TextView) view.findViewById(R.id.size_text_view);

            view.setOnClickListener(this);
        }

        public void setValues(AppModel appModel) {
            mNameTextView.setText(appModel.getName());
            mDescTextView.setText(appModel.getDesc());
            mIconImageView.setImageBitmap(appModel.getIcon());
            mDateTextView.setText(new SimpleDateFormat("dd.MM.yyyy").format(appModel.getDate()));
            double size = (double)appModel.getSize() / (1024*1024);
            mSizeTextView.setText(String.format("%1$.2f мб", size));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("TAG", "" + position);
            mOnClickHandler.onClickItem(mAppModelList.get(position));
        }
    }
}
