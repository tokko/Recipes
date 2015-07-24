package com.tokko.recipes.utils;

import com.annimon.stream.function.Function;

class Wrapper<T> {
    private T t;
    private Function<T, String> stringifier;

    public Wrapper(T t, Function<T, String> stringifier) {
        this.t = t;
        this.stringifier = stringifier;
    }

    public T getT() {
        return t;
    }

    @Override
    public String toString() {
        return stringifier.apply(t);
    }
}
