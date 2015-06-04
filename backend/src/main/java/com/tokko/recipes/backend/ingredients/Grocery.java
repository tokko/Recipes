package com.tokko.recipes.backend.ingredients;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Grocery {

    @Id
    private Long id;
    private String title;

    public Grocery() {
    }

    public Grocery(Long id, String title) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        try {
            Grocery i = (Grocery) obj;
            return id.longValue() == i.id.longValue();
        } catch (ClassCastException ignored) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
