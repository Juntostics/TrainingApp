package com.juntostics.trainingapp.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.juntostics.trainingapp.R;
import com.juntostics.trainingapp.common.Constants;
import com.juntostics.trainingapp.model.Project;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private int mImageSize;
    private MainAdapter mAdapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mImageSize = getResources().getDimensionPixelSize(R.dimen.image_size) * Constants.IMAGE_ANIM_MULTIPLIER;

        //itemの生成
        List<Project> projects = createSampleProjects();
        //adapter
        mAdapter = new MainAdapter(getActivity(),projects);

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        SimpleRecyclerView recyclerView = (SimpleRecyclerView) view.findViewById(R.id.list);
        recyclerView.setEmptyView(view.findViewById(R.id.empty));
        //TODO:change below
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getActivity(), getResources().getInteger(R.integer.list_columns));
        recyclerView.setLayoutManager(new GridLayoutManager(
                getActivity(), getResources().getInteger(R.integer.list_columns)
        ));
        recyclerView.setAdapter(mAdapter);



        return view;
    }

    private List<Project> createSampleProjects() {
        List<Project> rtnList = new ArrayList<Project>();
        for(int i=0;i<8;i++){
            rtnList.add(new Project("Test Project"));
        }
        return rtnList;
    }

    private class MainAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemClickListener {

        public List<Project> mProjects;
        private Context mContext;

        public MainAdapter(Context mContext, List<Project> projects) {
            super();
            this.mProjects = projects;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_row, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Project project = mProjects.get(position);

            holder.mTitleTextView.setText(project.getTitle());

            Glide.with(mContext)
                    .load(project.imageUri)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.empty_photo)
                    .override(mImageSize, mImageSize)
                    .into(holder.mImageView);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mProjects == null ? 0 : mProjects.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            View heroView = view.findViewById(R.id.icon);
            DetailActivity.launch(getActivity(),mAdapter.mProjects.get(position).getTitle(), heroView);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener mItemClickListener;
        TextView mTitleTextView;
        ImageView mImageView;


        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.mItemClickListener = itemClickListener;
            this.mTitleTextView = (TextView) itemView.findViewById(R.id.text1);
            mImageView = (ImageView) itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getPosition());
        }
    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
