package com.example.vladimir.seabattle.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.validators.StringValidator;

public class AddUserNameDialog extends AlertDialog implements View.OnClickListener {

    public interface OnAddUserNameListener {
        void onAddedUserName(final String userName, final String lastName);
    }

    private final OnAddUserNameListener onAddUserNameListener;

    public AddUserNameDialog(final Context context, final OnAddUserNameListener onAddUserNameListener) {
        super(context);
        this.onAddUserNameListener = onAddUserNameListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_user_name);
        setTitle(R.string.title_add_user_dialog);
        final EditText editTextUserName = (EditText) findViewById(R.id.firstName);
        final EditText editTextLastName = (EditText) findViewById(R.id.lastName);
        final TextInputLayout firstNameWrapper = (TextInputLayout) findViewById(R.id.firstNameWrapper);
        final TextInputLayout lastNameWrapper = (TextInputLayout) findViewById(R.id.lastNameWrapper);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        Button buttonYes = (Button) findViewById(R.id.btnYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddUserNameListener != null) {
                    if(StringValidator.notBlank(editTextUserName, firstNameWrapper)) {
                        if(StringValidator.notBlank(editTextLastName, lastNameWrapper)) {
                            onAddUserNameListener.onAddedUserName(editTextUserName.getText().toString(), editTextLastName.getText().toString());
                            dismiss();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
