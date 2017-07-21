package com.example.vladimir.seabattle.validators;


import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

public class StringValidator {

    public static boolean notBlank(EditText field, TextInputLayout wrapper) {
        return notBlank(wrapper.getHint().toString(), field, wrapper);
    }

    public static boolean notBlank(String fieldName, EditText field, TextInputLayout wrapper) {
        wrapper.setError(null);
        wrapper.setErrorEnabled(false);
        String value = field.getText().toString().trim();
        boolean result = true;
        if (TextUtils.isEmpty(value)) {
            result = false;
            field.setError(null);
            wrapper.setError(fieldName + " cannot be blank");
        }
        return result;
    }

    public static boolean lengthGreaterThan(EditText field, TextInputLayout wrapper
            , int minLength) {
        return lengthGreaterThan(wrapper.getHint().toString(), field, wrapper, minLength);
    }

    public static boolean lengthGreaterThan(String fieldName, EditText field,
                                            TextInputLayout wrapper, int minLength) {
        boolean result = notBlank(fieldName, field, wrapper);
        if (result) {
            String value = field.getText().toString().trim();
            if (value.length() < minLength) {
                result = false;
                wrapper.setError(fieldName + " must greater than " + minLength);
            }
        }
        return result;
    }

    public static boolean isEmailFormat(EditText field, TextInputLayout wrapper) {
        return isEmailFormat("Email", field, wrapper);
    }

    public static boolean isEmailFormat(String fieldName, EditText field, TextInputLayout wrapper) {
        wrapper.setError(null);
        wrapper.setErrorEnabled(false);
        boolean result = notBlank(fieldName, field, wrapper);
        Log.e("notBlank", result + "");
        if (result) {
            String value = field.getText().toString();
            result = android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches();
            if (!result) {
                field.setError(null);
                wrapper.setError("Please enter correct email");
            }
        }
        return result;
    }

    public static boolean confirmPassword(EditText field, EditText confirmField,
                                          TextInputLayout wrapper) {
        Log.e("isPasswordValid", "isPasswordValid");
        wrapper.setError(null);
        wrapper.setErrorEnabled(false);
        boolean result = false;
        if (notBlank(field, wrapper)) {
            if (notBlank(confirmField, wrapper)) {
                if (field.getText().toString().contentEquals(confirmField.getText())) {
                    result = true;
                } else {
//                    field.setError("Not equals");
                    confirmField.setError(null);
                    wrapper.setError("Password doesn't match confirmation");
                }
            }
        }

        return result;
    }


}
