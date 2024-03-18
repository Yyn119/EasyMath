package my.edu.utar.easymath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class order extends AppCompatActivity {
    Button number1, number2, number3, number4, number5, check, next, clear;
    ImageButton back;
    TextView message, question, answer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        answer = findViewById(R.id.answer);
        question = findViewById(R.id.question);
        message = findViewById(R.id.message);
        check = findViewById(R.id.check_button);
        back = findViewById(R.id.arrow_button);
        next = findViewById(R.id.next_button);
        clear = findViewById(R.id.clear_button);
        Button[] numbers = {number1, number2, number3, number4, number5};

        Random random = new Random();
        final int arrSize = 5;
        ArrayList<Integer> numberList = new ArrayList<>(arrSize);
        ArrayList<Integer> answerList = new ArrayList<>(arrSize);

        for(int i = 0; i < arrSize; i++){
            int randomNum;
            do{
                randomNum = random.nextInt(99) + 1;
            }while(numberList.contains(randomNum));

            numberList.add(randomNum);
            numbers[i].setText(String.valueOf(numberList.get(i)));

            int num = randomNum;

            numbers[i].setOnClickListener(e -> {

                message.setText("");

                if (answerList.size() == arrSize){
                    message.setTextColor(Color.GREEN);
                    message.setText(R.string.complete);
                    playSound(R.raw.wrong);
                }

                else if (answerList.contains(num)){
                    message.setTextColor(Color.RED);
                    message.setText(R.string.repeat);
                    playSound(R.raw.wrong);
                }

                else{
                    answerList.add(num);
                    answer.append(num + " ");
                }
            });
        }

        double p = random.nextDouble();
        boolean isAsc = p < 0.5;       // if false, isDesc

        if(isAsc){
            question.setText(R.string.asc);
            numberList.sort(null);

        }else{
            question.setText(R.string.dsc);
            numberList.sort(Collections.reverseOrder());
        }

        check.setOnClickListener(e -> {
            if (answerList.size() != arrSize) {
                message.setTextColor(Color.RED);
                message.setText(R.string.undone);
                playSound(R.raw.wrong);
            }
            else {
                boolean isCorrect = true;
                for (int i = 0; i < arrSize; i++) {
                    if (!numberList.get(i).equals(answerList.get(i))) {
                        isCorrect = false;
                        break;
                    }
                }
                if (isCorrect) {
                    message.setTextColor(Color.GREEN);
                    message.setText(R.string.bingo);
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    playSound(R.raw.bingo);

                } else {
                    message.setTextColor(Color.RED);
                    message.setText(R.string.wrong);
                    Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                    playSound(R.raw.wrong);
                    answer.setText("");
                    answerList.clear();
                }
            }
        });

        clear.setOnClickListener(e -> {
            answer.setText("");
            answerList.clear();
            message.setText("");
        });

        back.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit to Home Page")
                    .setMessage("Are you sure you want to quit <Numbers Ordering> ?")
                    .setPositiveButton("Quit", (dialog, id) -> {
                        Intent intent = new Intent(order.this, MainActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        next.setOnClickListener(e -> {
            @SuppressLint("UnsafeIntentLaunch") Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

    }
    private void playSound(int soundResourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }
}