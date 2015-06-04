package com.tokko.recipes.backend.recipes;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.tokko.recipes.backend.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Recipe implements Iterable<Ingredient> {
    private
    @Id
    Long id;
    private String title;
    private List<Ref<Ingredient>> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(Ref.create(ingredient));
    }

    @Override
    public Iterator<Ingredient> iterator() {
        return new Iterator<Ingredient>() {
            Iterator<Ref<Ingredient>> it = ingredients.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Ingredient next() {
                return it.next().safe();
            }
        };
    }

}
