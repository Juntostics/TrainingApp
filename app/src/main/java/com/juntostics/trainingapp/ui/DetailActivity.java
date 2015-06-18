package com.juntostics.trainingapp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.juntostics.trainingapp.R;

import java.lang.annotation.Target;

public class DetailActivity extends Activity{

    private static final String EXTRA_PROJECT = "project";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String project = getIntent().getStringExtra(EXTRA_PROJECT);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container,DetailFragment.createInstance(project)).commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void launch(Activity activity, String project, View heroView) {
        Intent intent = getLaunchIntent(activity, project);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,heroView,heroView.getTransiticd onName());
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        }else{
            activity.startActivity(intent);
        }
    }

    public static Intent getLaunchIntent(Context context, String project){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_PROJECT,project);
        return intent;
    }




}
