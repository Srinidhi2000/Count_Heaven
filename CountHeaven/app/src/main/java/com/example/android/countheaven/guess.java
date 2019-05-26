package com.example.android.countheaven;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView;

public class guess extends AppCompatActivity {

    TextView result;
    TextView correct;
    TextView wrong;
    TextView noofguess;
    EditText number;
    LinearLayout colours;
    Button button;
    TextView nogh;
    LinearLayout removing;
    ScrollView sv;
    Button b;
    String gage;

    final int j = MainActivity.guess2;
    int guesstrack = MainActivity.guess2;
    int agetrack = MainActivity.age2;
    int cnt = 0;
    int ageno = 0;
    static int nowon = 0;
    static int nolost = 0;
    public static final String SHARED_PREFS="sharedPrefs";
    public static final String corval="correct";
    public static final String wroval="wrong";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        result = (TextView) findViewById(R.id.result);
        correct = (TextView) findViewById(R.id.win);
        number = (EditText) findViewById(R.id.guessedage);
        b = (Button) findViewById(R.id.guess);
        button = (Button) findViewById(R.id.end);
        sv = (ScrollView) findViewById(R.id.sv);
        wrong = (TextView) findViewById(R.id.lost);
        noofguess = (TextView) findViewById(R.id.noofguess);
        nogh = (TextView) findViewById(R.id.nogh);
        colours = (LinearLayout) findViewById((R.id.colours));
        removing = (LinearLayout) findViewById(R.id.remove);


        noofguess.setText(String.valueOf("Guess the age to reap the soul\nYou can make " + j + " guesses"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                gage = number.getText().toString();
                check(gage);
                saveData();
            }


        });
           loadData();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String gage = savedInstanceState.getString("gage");
        String text = savedInstanceState.getString("text");
        int no = savedInstanceState.getInt("count");
        int guess1 = savedInstanceState.getInt("guesstrack");
        int now = savedInstanceState.getInt("now");
        int nol = savedInstanceState.getInt("nol");
        int color = savedInstanceState.getInt("color");
        String corr = savedInstanceState.getString("correct");
        String wron = savedInstanceState.getString("wrong");
        String but = savedInstanceState.getString("button");
        String nog = savedInstanceState.getString("nogh");
        cnt = no;
        guesstrack = guess1;
        nowon = now;
        nolost = nol;
        button.setText(but);
        sv.setBackgroundColor(color);
        result.setText(text);
        number.setText(gage);
        correct.setText(corr);
        wrong.setText(wron);
        nogh.setText(nog);
        colours.setVisibility(savedInstanceState.getInt("col"));
        removing.setVisibility(savedInstanceState.getInt("rem"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        String text = result.getText().toString();
        String gage = number.getText().toString();
        String cor = correct.getText().toString();
        String wro = wrong.getText().toString();
        String but = button.getText().toString();
        String nog = nogh.getText().toString();
        String rem = nogh.getText().toString();
        int no = cnt;
        int now = nowon;
        int nol = nolost;
        int color = ((ColorDrawable) sv.getBackground()).getColor();
        int guess1 = guesstrack;
        outstate.putInt("col", colours.getVisibility());
        outstate.putInt("rem", removing.getVisibility());
        outstate.putString("button", but);
        outstate.putString("text", text);
        outstate.putString("gage", gage);
        outstate.putInt("count", no);
        outstate.putInt("guesstrack", guess1);
        outstate.putInt("now", now);
        outstate.putInt("nol", nol);
        outstate.putInt("color", color);
        outstate.putString("nogh", nog);
        outstate.putString("correct", cor);
        outstate.putString("wrong", wro);
    }

    //TO start the game from the beginning
    public void reset(View view) {
        Intent myIntent = new Intent(guess.this, MainActivity.class);
        startActivity(myIntent);

    }

    //To set the backgroung colour based on how close the guessed answer is to the the actual age
    private void checkcolour(int x) {
        sv = (ScrollView) findViewById(R.id.sv);
        int a = agetrack - x;
        if (a > 0) {
            a = a + 0;
        }
        if (a < 0) {
            a = a * (-1);
        }
        /*if difference is
        1.=0:Green
        2.(+/-)10:Bluish-green
        3.(+/-)11 to 25:Yellow
        4.(+/-)26 to 40:Orange
        5.(+/-)41-49:Red
         */
        if (a == 0)
            sv.setBackgroundColor(Color.parseColor("#4caf50"));
        if (a > 0 && a <= 10)
            sv.setBackgroundColor(Color.parseColor("#26a69a"));
        if (a > 10 && a <= 25)
            sv.setBackgroundColor(Color.parseColor("#ffd600"));
        if (a > 25 && a <= 50)
            sv.setBackgroundColor(Color.parseColor("#ff9800"));
        if (a > 51 && a <= 99)
            sv.setBackgroundColor(Color.parseColor("#dd2c00"));
    }

    //If the last guess is wrong this function is called
    private void finishguess(int x) {
        nolost++;
        correct = (TextView) findViewById(R.id.win);
        result = (TextView) findViewById(R.id.result);
        wrong = (TextView) findViewById(R.id.lost);
        removing = (LinearLayout) findViewById(R.id.remove);
        if (x < agetrack) {
            result.setText(String.valueOf("Oops..You are reaping a young soul!" + "\n GAME OVER\n Correct Answer=" + agetrack));
        }
        if (x > agetrack) {
            result.setText(String.valueOf("That's too long..The sole must be reaped earlier!" + "\nGAME OVER \nCorrect Answer=" + agetrack));
        }
        button.setText(String.valueOf("START AGAIN"));

        correct.setText(String.valueOf("No.of correct guess= " + nowon));

        wrong.setText(String.valueOf("No. of wrong guess= " + nolost));
        removing.setVisibility(View.GONE);

        return;
    }

    private void check(String gage) {
        result = (TextView) findViewById(R.id.result);
        correct = (TextView) findViewById(R.id.win);
        wrong = (TextView) findViewById(R.id.lost);
        button = (Button) findViewById(R.id.end);
        removing = (LinearLayout) findViewById(R.id.remove);
        number = (EditText) findViewById(R.id.guessedage);
        if (cnt != 0 && cnt != 1) {

            result.setText(String.valueOf("GAME OVER..You have exhausted the no. of guesses already!"));
            button.setText(String.valueOf("START AGAIN"));
        }
        if (TextUtils.isEmpty(gage)) {
            number.setError("Please Enter The Age To Reap The Soul");
            number.requestFocus();
            return;
        }
        ageno = Integer.parseInt(gage);
        if (ageno < 1 || ageno > 100) {
            number.setError("Please Enter a no. between 1 and 100");
            number.requestFocus();
            return;
        }

        guesstrack = guesstrack - 1;
        if (guesstrack == 0) {
            cnt = 1;
        }
        nogh = (TextView) findViewById(R.id.nogh);
        nogh.setText(String.valueOf("Guess" + (j - guesstrack) + ":"));

        if (ageno >= 1 && ageno <= 100) {
            if (cnt != 0 && cnt != 1) {

                result.setText(String.valueOf("GAME OVER..You have exhausted the no. of guesses already!"));
                button.setText(String.valueOf("START AGAIN"));
            } else {


                if (ageno == agetrack) {
                    nowon++;
                    checkcolour(ageno);
                    result.setText(String.valueOf("Yay!!You got the right answer.." + "\nCorrect Answer=" + agetrack));

                    correct.setText(String.valueOf("No. of correct guess= " + nowon));

                    wrong.setText(String.valueOf("No.of wrong guess= " + nolost));
                    button.setText(String.valueOf("START AGAIN"));
                    removing.setVisibility(View.GONE);
                    cnt = 2;

                } else if (ageno < agetrack) {
                    if (cnt == 1) {
                        checkcolour(ageno);
                        finishguess(ageno);
                        cnt = 2;
                    } else {
                        checkcolour(ageno);
                        result.setText(String.valueOf("Oops..You are reaping a young soul!"));
                    }
                } else if (ageno > agetrack) {
                    if (cnt == 1) {
                        checkcolour(ageno);
                        finishguess(ageno);
                        cnt = 2;

                    } else {
                        checkcolour(ageno);
                        result.setText(String.valueOf("That's too long..The sole must be reaped earlier!"));
                    }
                }


            }
        }

    }

    public void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(corval,nowon);
        editor.putInt(wroval,nolost);
        editor.apply();
    }

    public void loadData()
    {SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
    nowon=sharedPreferences.getInt(corval,0);
    nolost=sharedPreferences.getInt(wroval,0);

    }



}

