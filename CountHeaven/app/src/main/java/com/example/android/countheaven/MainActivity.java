package com.example.android.countheaven;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.countheaven.R;

public class MainActivity extends AppCompatActivity {
    EditText age, guess;
    public static int age2, guess2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Button button = (Button) findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                age = (EditText) findViewById(R.id.setage);
                guess = (EditText) findViewById(R.id.setguess);
                String age1 = age.getText().toString();

                String guess1 = guess.getText().toString();

                if (TextUtils.isEmpty(age1)) {
                    age.setError("Please Enter The Age");
                    age.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(guess1)) {
                    guess.setError("Please Enter The No.of Guesses");
                    guess.requestFocus();
                    return;
                }
                age2 = Integer.parseInt(age1);
                guess2 = Integer.parseInt(guess1);


                Intent myIntent = new Intent(MainActivity.this, guess.class);
                startActivity(myIntent);

            }
        });

    }
}