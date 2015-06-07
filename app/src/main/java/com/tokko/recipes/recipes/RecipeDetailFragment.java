package com.tokko.recipes.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tokko.recipes.R;
import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.utils.RecipeWrapper;

import roboguice.inject.InjectView;

public class RecipeDetailFragment extends AbstractDetailFragment<RecipeWrapper> {
    @InjectView(R.id.detail_title)
    TextView title;
    @InjectView(R.id.edit_title)
    EditText edit_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_detail, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(entity.getTitle());
        edit_title.setText(entity.getTitle());
    }
}
