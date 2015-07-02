package com.tokko.recipes.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextViewSwitchable extends Editable<TextView, EditText, String> {

    public EditTextViewSwitchable(Context context, AttributeSet attrs) {
        super(context, attrs, new TextView(context, attrs), new EditText(context, attrs));
    }

    public EditTextViewSwitchable setHint(String hint) {
        edit.setHint(hint);
        return this;
    }

    public String getText() {
        return edit.getText().toString();
    }

    public EditTextViewSwitchable setText(String s) {
        edit.setText(s);
        label.setText(s);
        return this;
    }

    @Override
    protected void onDiscard() {
        edit.setText(label.getText());
    }

    @Override
    protected void onAccept() {
        label.setText(edit.getText());
    }

    @Override
    public String getData() {
        return label.getText().toString();
    }

    @Override
    protected void setData(String data) {
        edit.setText(data);
        label.setText(data);
    }
}
