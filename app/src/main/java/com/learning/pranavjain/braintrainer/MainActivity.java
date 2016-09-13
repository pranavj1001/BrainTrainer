package com.learning.pranavjain.braintrainer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView question;
    int randomChoice;
    boolean optionSelected ;
    int count = 0, ccount = 0;
    int i = 0 ;
    CountDownTimer countDownTimer;
    String start = "00:30";
    String reset = "00:00";
    String astart = "0/0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-------------------------------------------------------*/

        // Get control over the elements
        final TextView timer = (TextView) findViewById(R.id.timer);
        final GridLayout options = (GridLayout) findViewById(R.id.options);
        final Button playAgain = (Button) findViewById(R.id.playAgain);
        final TextView question = (TextView) findViewById(R.id.question);
        final TextView correctAnswers = (TextView) findViewById(R.id.correctAnswers);
        timer.setText(start);
        correctAnswers.setText(astart);

        /*-------------------------------------------------------*/

        // Count Down Timer
        countDownTimer = new CountDownTimer(31500, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

                int seconds = (int) (millisUntilFinished/1000);

                String secString = String.valueOf(seconds);

                if(secString.length() == 1){
                    secString = "0" + secString;
                }

                String finalString = "00:"+secString;

                timer.setText(finalString);
            }

            @Override
            public void onFinish() {
                i = 10;
                timer.setText(reset);
            }
        }.start();

         /*-------------------------------------------------------*/

        // Hide the main content and wait for the user.
        options.setVisibility(View.INVISIBLE);
        timer.setVisibility(View.INVISIBLE);
        correctAnswers.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);

        // Mechanism to reveal the main content.
        Button play = (Button) findViewById(R.id.play);
        final LinearLayout startPage = (LinearLayout) findViewById(R.id.startPage);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPage.setVisibility(View.INVISIBLE);
                options.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                correctAnswers.setVisibility(View.VISIBLE);
                question.setVisibility(View.VISIBLE);
            }
        });


        /*-------------------------------------------------------*/

        mainLogic();

    }


    public void mainLogic (){

            i++;
            Button option1 = (Button) findViewById(R.id.Option1);
            Button option2 = (Button) findViewById(R.id.Option2);
            Button option3 = (Button) findViewById(R.id.Option3);
            Button option4 = (Button) findViewById(R.id.Option4);
            question = (TextView) findViewById(R.id.question);

            //Main Logic
            int firstOperand = (int) Math.ceil(Math.random() * 100);
            int secondOperand = (int) Math.ceil(Math.random() * 100);

            String questionToDisplay = firstOperand + "+" + secondOperand;
            question.setText(questionToDisplay);

            int answer = firstOperand + secondOperand;

            randomChoice = (int) Math.ceil(Math.random() * 4);

            switch (randomChoice) {
                case 1:
                    option1.setText(String.valueOf(answer));
                    break;
                case 2:
                    option2.setText(String.valueOf(answer));
                    break;
                case 3:
                    option3.setText(String.valueOf(answer));
                    break;
                case 4:
                    option4.setText(String.valueOf(answer));
                    break;
            }

            if (option1.getText().toString() == "") {
                int random = (int) Math.ceil(Math.random() * 100);
                option1.setText(String.valueOf(random));
            }
            if (option2.getText().toString() == "") {
                int random = (int) Math.ceil(Math.random() * 100);
                option2.setText(String.valueOf(random));
            }
            if (option3.getText().toString() == "") {
                int random = (int) Math.ceil(Math.random() * 100);
                option3.setText(String.valueOf(random));
            }
            if (option4.getText().toString() == "") {
                int random = (int) Math.ceil(Math.random() * 100);
                option4.setText(String.valueOf(random));
            }
    }

    public void optionSelected(View view){

        Button options = (Button) view;
        Button option1 = (Button) findViewById(R.id.Option1);
        Button option2 = (Button) findViewById(R.id.Option2);
        Button option3 = (Button) findViewById(R.id.Option3);
        Button option4 = (Button) findViewById(R.id.Option4);
        TextView correctAnswers = (TextView) findViewById(R.id.correctAnswers);
        Button playAgain = (Button) findViewById(R.id.playAgain);

        int tag = Integer.parseInt(options.getTag().toString());


        // if the 10 questions are done
        if(i==10){
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            countDownTimer.cancel();
            playAgain.setVisibility(View.VISIBLE);
            playAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
            if(ccount == 10){
                Toast.makeText(getApplicationContext(),"Perfect Score!",Toast.LENGTH_SHORT).show();
            }else if(ccount > 8 && ccount <= 9){
                Toast.makeText(getApplicationContext(),"Awesome!",Toast.LENGTH_SHORT).show();
            }else if(ccount > 5 && ccount <= 8 ){
                Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_SHORT).show();
            }else if(ccount > 2 && ccount <= 5){
                Toast.makeText(getApplicationContext(),"Average",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Need Practice",Toast.LENGTH_SHORT).show();
            }
        }


        //if user selects the right answer or the wrong answer.
        if(tag == randomChoice){
            ccount++;
            count++;
            String display = String.valueOf(ccount)+"/"+String.valueOf(count);
            correctAnswers.setText(display);
            optionSelected = true;
            if(i <= 10)
            mainLogic();
        }else {
            count++;
            String display = String.valueOf(ccount)+"/"+String.valueOf(count);
            correctAnswers.setText(display);
            optionSelected = true;
            if(i <= 10)
            mainLogic();
        }

    }

}
