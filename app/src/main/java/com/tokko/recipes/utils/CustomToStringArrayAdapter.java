package com.tokko.recipes.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;

import java.util.ArrayList;
import java.util.List;

public class CustomToStringArrayAdapter<T> extends ArrayAdapter<Wrapper<T>> {

    private Function<T, String> stringifier;

    public CustomToStringArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public void setStringifier(Function<T, String> stringifier) {
        this.stringifier = stringifier;
    }

    public List<T> getData() {
        List<T> data = new ArrayList<>();
        for (int i = 0; i < this.getCount(); i++) {
            data.add(getItem(i).getT());
        }
        return data;
    }

    public void setData(List<T> data) {
        addAll(Stream.of(data).map(d -> new Wrapper<>(d, stringifier)).collect(Collectors.toList()));
    }
}
