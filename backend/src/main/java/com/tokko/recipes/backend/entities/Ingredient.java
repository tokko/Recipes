package com.tokko.recipes.backend.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Ingredient implements Comparable<Ingredient> {
    private Ref<Grocery> grocery;
    private Quantity quantity;
    @Id
    private Long id;

    public Ingredient() {
    }

    public Ingredient(Grocery grocery, Quantity quantity) {
        setGrocery(grocery);
        this.quantity = quantity;
    }

    public Grocery getGrocery() {
        return grocery.safe();
    }

    public void setGrocery(Grocery grocery) {
        this.grocery = Ref.create(grocery);
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return grocery.get().getId().equals(((Ingredient) obj).getGrocery().getId());
        } catch (ClassCastException | NullPointerException ignored) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return grocery.get().hashCode();
    }

    @Override
    public int compareTo(Ingredient o) {
        return quantity.compareTo(o.getQuantity());
    }
}
