package com.tokko.recipes.utilactivities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.tokko.recipes.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class NavigationDrawerActivity extends RoboActivity {

    @InjectView(R.id.drawer_layout)
    private DrawerLayout drawerLayout;
    @InjectView(R.id.navigation_drawer_list)
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

    }
}
