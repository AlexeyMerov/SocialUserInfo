package com.testapp.socialuserinfo.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import io.reactivex.functions.Action;

public class ViewHelper {

    public static void initClearErrorOnTextChangeListeners(final EditText editText, final TextInputLayout inputLayout) {
        initClearErrorOnTextChangeListeners(editText, inputLayout, null);
    }

    public static void initClearErrorOnTextChangeListeners(final EditText editText, final TextInputLayout inputLayout, Action action) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputLayout.setError(null);
                if (action != null) {
                    try {
                        action.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
