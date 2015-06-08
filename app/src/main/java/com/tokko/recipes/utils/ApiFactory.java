package com.tokko.recipes.utils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.tokko.recipes.BuildConfig;
import com.tokko.recipes.backend.ingredients.ingredientApi.IngredientApi;
import com.tokko.recipes.backend.recipeApi.RecipeApi;

import java.lang.reflect.InvocationTargetException;

public class ApiFactory {

    public static <T extends AbstractGoogleJsonClient.Builder> AbstractGoogleJsonClient createApi(Class<T> clz) {
        try {

            return clz.getConstructor(HttpTransport.class, JsonFactory.class, HttpRequestInitializer.class)
                    .newInstance(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(getRootUrl()).build();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static RecipeApi createRecipeApi() {
        return (RecipeApi) createApi(RecipeApi.Builder.class);
    }

    public static IngredientApi createIngredientApi(){
        return (IngredientApi) createApi(IngredientApi.Builder.class);
    }

    private static String getRootUrl() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("release"))
            return ""; //TODO: release backend url
        return "http://192.168.2.13:8080/_ah/api/";
    }
}
