package com.tokko.recipes.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.backend.recipeApi.model.Recipe;

public class RecipeDetailFragment extends AbstractDetailFragment<Recipe> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView t = new TextView(getActivity());
        t.setText("MADAFAKA!");
        return t;
    }
}
