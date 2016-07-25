package com.tonikamitv.loginregister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;


public class Login extends Activity implements View.OnClickListener {
    Button bLogin;
    TextView registerLink;
    EditText etUsername, etPassword;

    UserLocalStore userLocalStore;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Our custom font
        getLayoutInflater().setFactory(new LayoutInflater.Factory() {

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                View v = tryInflate(name, context, attrs);
                /*
                if (v instanceof TextView) {
                    setTypeFace((TextView) v);
                }*/
                if (v instanceof TextView) {
                    // setTypeFace((TextView) v);
                    ((TextView) v).setTypeface(EasyFonts.robotoBlack(v.getContext()));
                }
                return v;
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(R.id.bLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        registerLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View view) {



        switch (view.getId()) {
            case R.id.bLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                user = new User(username, password);

                authenticate(user);
                break;
            case R.id.tvRegisterLink:
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
                break;
        }
    }

    private void authenticate(User user) {
        ServerRequests serverRequest = new ServerRequests(this);


        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser.id == 0 ) {
                    showErrorMessage();
                } else {

                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        returnedUser.status = true;
        user = returnedUser;
        Bundle extras = new Bundle();
        extras.putSerializable("user", returnedUser);  //userLocalStore.getLoggedInUser());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(extras);
       // Log.i("Username: ", userLocalStore.getLoggedInUser().username.toString());
        startActivity(intent);
    }


    private View tryInflate(String name, Context context, AttributeSet attrs) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = null;
        try {
            v = li.createView(name, null, attrs);
        } catch (Exception e) {
            try {
                v = li.createView("android.widget." + name, null, attrs);
            } catch (Exception e1) {
            }
        }
        return v;
    }

    private void setTypeFace(TextView tv) {
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "AManoRegulold.ttf"));
    }

}
