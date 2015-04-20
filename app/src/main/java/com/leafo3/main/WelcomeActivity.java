package com.leafo3.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leafo3.main.R;
import com.leafo3.util.EnvironmentUtils;

public class WelcomeActivity extends ActionBarActivity {

    private boolean canShowThisWindow = true;
    private EditText emailEditText;
    private Button readyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(EnvironmentUtils.getUserEmail(WelcomeActivity.this) != null && ! EnvironmentUtils.getUserEmail(WelcomeActivity.this).isEmpty()){
            canShowThisWindow = false;
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        init();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!canShowThisWindow){
            finish();
        }
    }

    private void init(){
        this.emailEditText = (EditText)findViewById(R.id.activity_welcome_email_edit);
        this.readyButton = (Button)findViewById(R.id.activity_welcome_ready_button);
        //set listener
        this.readyButton.setOnClickListener(readyButtonListener);
    }

    private View.OnClickListener readyButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //save the email
            String email = emailEditText.getText().toString();
            if(email == null || email.isEmpty()){
                //shows a toast
                Toast.makeText(WelcomeActivity.this, "No email provided", Toast.LENGTH_LONG).show();
            }else{
                //saves the email
                EnvironmentUtils.saveUserEmail(WelcomeActivity.this, email);
                //start main activity
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    };
}
