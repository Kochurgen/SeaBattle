package com.example.vladimir.seabattle.validators;


import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

public class StringValidator {

    public static boolean notBlank(EditText field, TextInputLayout wrapper) {
        return notBlank(wrapper.getHint(), field, wrapper);
    }

    private static boolean notBlank(CharSequence fieldName, EditText field, TextInputLayout wrapper) {
        wrapper.setError(null);
        wrapper.setErrorEnabled(false);
        String value = field.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            wrapper.setError(fieldName + " cannot be blank");
            return false;
        } else {
            return true;
        }
    }
}
