package com.testapp.socialuserinfo.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;


public class FieldsValidator {

    public static boolean checkPhoneNumberIsValid(TextInputLayout inputLayout, EditText editText, Context context) {
//        if (!checkFieldIsNotEmpty(editText, inputLayout, context.getString(R.string.error_empty_number))) return false;
        if (editText.getText().length() != 9) {
//            inputLayout.setError(context.getString(R.string.error_incorrect_number));
            return false;
        }

        inputLayout.setError(null);
        return true;
    }

    public static boolean checkEmailIsValid(TextInputLayout inputLayout, EditText editText, Context context) {
//        if (!checkFieldIsNotEmpty(editText, inputLayout, context.getString(R.string.error_empty_email))) return false;
        if (!editText.getText().toString().contains("@")) {
//            inputLayout.setError(context.getString(R.string.error_incorrect_email));
            return false;
        }
        if (!editText.getText().toString().matches(".+@.+\\..+")) {
//            inputLayout.setError(context.getString(R.string.email_not_correct));
            return false;
        }

        inputLayout.setError(null);
        return true;
    }

    public static boolean checkPasswordIsValid(TextInputLayout inputLayout, EditText editText, Context context) {
//        if (!checkFieldIsNotEmpty(editText, inputLayout, context.getString(R.string.error_empty_password))) return false;
        if (editText.getText().length() < 6) {
//            inputLayout.setError(context.getString(R.string.error_incorrect_password));
            return false;
        }

        inputLayout.setError(null);
        return true;
    }

    public static boolean checkPasswordAgainIsValid(TextInputLayout inputLayout, EditText passwordEditText, EditText passwordAgainEditText, Context context) {
//        if (!checkFieldIsNotEmpty(passwordAgainEditText, inputLayout, context.getString(R.string.error_empty_password_confirmation))) return false;
        if (!passwordEditText.getText().toString().equals(passwordAgainEditText.getText().toString())) {
//            inputLayout.setError(context.getString(R.string.error_not_equal_passwords));
            return false;
        }

        inputLayout.setError(null);
        return true;
    }

    public static boolean checkFieldIsNotEmpty(EditText editText, TextInputLayout inputLayout, String errorText) {
        if (editText != null && TextUtils.isEmpty(editText.getText())) {
            inputLayout.setError(errorText);
            return false;
        }
        return true;
    }

}
