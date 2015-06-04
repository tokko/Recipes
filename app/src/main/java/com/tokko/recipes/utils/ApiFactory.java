package com.tokko.recipes.utils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.tokko.recipes.BuildConfig;
import com.tokko.recipes.backend.recipeApi.RecipeApi;

public class ApiFactory {

    public static RecipeApi createRecipeApi() {
        return new RecipeApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl(getRootUrl()).build();
    }

    private static String getRootUrl() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("release"))
            return ""; //TODO: release backend url
        return "http://192.168.2.13:8080/_ah/api/";
    }
}
