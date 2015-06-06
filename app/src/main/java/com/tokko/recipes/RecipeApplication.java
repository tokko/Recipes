package com.tokko.recipes;

import android.app.Application;

import com.tokko.recipes.utils.LoaderBindModule;

import roboguice.RoboGuice;

public class RecipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.overrideApplicationInjector(this, new LoaderBindModule());

    }
}
