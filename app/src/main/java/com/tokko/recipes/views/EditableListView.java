package com.tokko.recipes.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.tokko.recipes.R;

import java.util.List;

public class EditableListView<D> extends LinearLayout implements Editable<List<D>> {

    private ToStringer toStringer;
    private ArrayAdapter<Wrapper<D>> adapter;
    List<Wrapper<D>> data;
    Button addButton;
    ListView lv;
    private Context context;

    public EditableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.editablelistview, this, true);
       // lv = (ListView) inflate.findViewById(R.id.editableListView_list);
       // addButton = (Button) inflate.findViewById(R.id.editableListView_addButton);
        setAdapter(android.R.layout.simple_list_item_1);
    }

    private void setAdapter(int itemLayout){
        adapter = new ArrayAdapter<>(context, itemLayout, android.R.id.text1);
        lv.setAdapter(adapter);
    }

    public void setOnEditButtonClickListener(OnClickListener clickListener){
        addButton.setOnClickListener(clickListener);
    }

    public void addItem(D d){
        data.add(new Wrapper<>(d));
    }

    private class Wrapper<D>{
        private D d;

        public Wrapper(D d) {
            this.d = d;
        }

        @Override
        public String toString() {
            if(toStringer == null) throw new IllegalStateException("ToStringer must be set");
            return toStringer.toString(d);
        }

        public D getData() {
            return d;
        }
    }
    public interface ToStringer<D>{
        String toString(D d);
    }

    public void setToStringer(ToStringer toStringer) {
        this.toStringer = toStringer;
    }

    @Override
    public void edit() {
    }

    @Override
    public void discard() {

    }

    @Override
    public void accept() {

    }

    @Override
    public List<D> getData() {
        return Stream.of(data).map(w -> w.getData()).collect(Collectors.toList());
    }

    @Override
    public void setData(List<D> data) {
        this.data = Stream.of(data).map(d -> new Wrapper<>(d)).collect(Collectors.toList());
    }
}
