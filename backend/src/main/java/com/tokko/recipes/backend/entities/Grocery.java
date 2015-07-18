package com.tokko.recipes.backend.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import com.tokko.recipes.backend.services.RecipeEntity;

@Entity
public class Grocery implements RecipeEntity<Long, RecipeUser> {

    @Id
    private Long id;
    private String title;

    @Parent
    private Ref<RecipeUser> user;
    public Grocery() {
    }

    public Grocery(String title){
        this(null, title);
    }

    public Grocery(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setAncestor(RecipeUser recipeUser) {
        user = Ref.create(recipeUser);
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

    @Override
    public int hashCode(){
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Grocery i = (Grocery) obj;
            return id.longValue() == i.id.longValue();
        }catch(Exception ignored){
            return false;
        }
    }

}
