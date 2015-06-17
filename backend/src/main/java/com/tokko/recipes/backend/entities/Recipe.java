package com.tokko.recipes.backend.entities;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Recipe implements Iterable<Ingredient> {
    private
    @Id
    Long id;
    private String title;
    @Parent
    @JsonIgnore
    Key<RecipeUser> user;
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

    public Key<RecipeUser> getUser() {
        return user;
    }

    public void setUser(RecipeUser user) {
        this.user = Key.create(user);
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

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        try{
            return ((Recipe)obj).getId().equals(id);
        }
        catch (ClassCastException ignored){
            return false;
        }
    }
}
