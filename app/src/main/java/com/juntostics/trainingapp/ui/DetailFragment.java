package com.juntostics.trainingapp.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Juntostics on 6/14/15.
 */
public class DetailFragment extends Fragment {

    private static final String EXTRA_PROJECT = "project";
    private Project mProject;

    public static DetailFragment createInstance(String projectName) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PROJECT, projectName);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        String projectName = getArguments().getString(EXTRA_PROJECT);
        mProject = findProject(projectName);

        //if there's no project
        if(mProject == null){
            getActivity().finish();
            return null;
        }

        //init widgets
        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView descTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        nameTextView.setText(projectName);
        distanceTextView.setText(projectName);
        descTextView.setText(projectName);

        int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size)
                * Constants.IMAGE_ANIM_MULTIPLIER;
        Glide.with(getActivity())
                .load(mProject.imageUri)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.color.lighter_gray)
                .override(imageSize, imageSize)
                .into(imageView);
        return view;

    }

    private Project findProject(String projectName) {
        return new Project("Test");
    }
}
