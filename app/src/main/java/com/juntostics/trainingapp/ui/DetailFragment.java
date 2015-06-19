package com.juntostics.trainingapp.ui;


import android.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.juntostics.trainingapp.R;
import com.juntostics.trainingapp.common.Constants;
import com.juntostics.trainingapp.model.Project;

import java.util.List;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Juntostics on 6/14/15.
 */
public class DetailFragment extends Fragment {

    private static final String EXTRA_PROJECT = "project";
    private Project mProject;
    private MainAdapter mAdapter;

    public static DetailFragment createInstance(Project project) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_PROJECT,project);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);

        //get project
        mProject = (Project) getArguments().getSerializable(EXTRA_PROJECT);

        //adapter
        mAdapter = new MainAdapter(getActivity(),mProject.getTasks());

        //colorize
        Bitmap photo = BitmapFactory.decodeResource(getResources(),mProject.getImageResourceId());
        colorize(photo,view);

        //if there's no project
        if(mProject == null){
            getActivity().finish();
            return null;
        }

        //init RecyclerView
        SimpleRecyclerView recyclerView = (SimpleRecyclerView) view.findViewById(R.id.list);
        recyclerView.setEmptyView(view.findViewById(R.id.empty));
        //spacing for RecyclerView
        int spacingForRecyclerView = getResources().getDimensionPixelSize(R.dimen.card_space);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingForRecyclerView));
        recyclerView.setLayoutManager(new GridLayoutManager(
                getActivity(), getResources().getInteger(R.integer.list_columns)));
        recyclerView.setAdapter(mAdapter);

        //attach itemTouchHelper to RecyclerView to enable Swiping to dismiss and Dragging
        attachItemTouchHelper(recyclerView,mAdapter);

        //init widgets

        //init widgets
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(mProject.getType());
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        //setting for Glide to bind image
        int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size)
                * Constants.IMAGE_ANIM_MULTIPLIER;
        Glide.with(getActivity())
                .load(mProject.getImageResourceId())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.color.lighter_gray)
                .override(imageSize,imageSize)
                .into(imageView);
        return view;
    }

    private void colorize(Bitmap photo,View view) {
        Palette palette = Palette.generate(photo);
        applyPalette(palette, view);
    }

    private void applyPalette(Palette palette, View view) {
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor(Color.WHITE)));
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(palette.getVibrantColor(R.color.theme_default_primary)));
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setTextColor(palette.getVibrantColor(R.color.background_floating_material_dark));
//
//        TextView descriptionView = (TextView) findViewById(R.id.description);
//        descriptionView.setTextColor(palette.getLightVibrantColor().getRgb());
//
//        colorRipple(R.id.info, palette.getDarkMutedColor().getRgb(),
//                palette.getDarkVibrantColor().getRgb());
//        colorRipple(R.id.star, palette.getMutedColor().getRgb(),
//                palette.getVibrantColor().getRgb());
//
//        View infoView = findViewById(R.id.information_container);
//        infoView.setBackgroundColor(palette.getLightMutedColor().getRgb());
//
//        AnimatedPathView star = (AnimatedPathView) findViewById(R.id.star_container);
//        star.setFillColor(palette.getVibrantColor().getRgb());
//        star.setStrokeColor(palette.getLightVibrantColor().getRgb());
    }

    private void attachItemTouchHelper(RecyclerView recyclerView, final MainAdapter adapter){
        ItemTouchHelper itemDecor = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = viewHolder1.getAdapterPosition();
                        adapter.notifyItemMoved(fromPos, toPos);
                        return true;                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        mProject.getTasks().remove(fromPos);
                        adapter.notifyItemRemoved(fromPos);
                    }
                });
        itemDecor.attachToRecyclerView(recyclerView);
    }



    private class MainAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemClickListener {

        public List<String> mTasks;
        private Context mContext;

        public MainAdapter(Context mContext, List<String> tasks) {
            super();
            this.mTasks = tasks;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_row_detail, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String task = mTasks.get(position);
            holder.mTitleTextView.setText(task);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mTasks == null ? 0 : mTasks.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            Log.d("onClick","onClick@Detail");
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener mItemClickListener;
        TextView mTitleTextView;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.mItemClickListener = itemClickListener;
            this.mTitleTextView = (TextView) itemView.findViewById(R.id.text1);
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
