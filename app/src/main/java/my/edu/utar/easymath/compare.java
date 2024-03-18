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
import java.util.Random;

public class compare extends AppCompatActivity {
    Button number1, number2, next;
    ImageButton back;
    TextView message, question;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        question = findViewById(R.id.question);
        message = findViewById(R.id.message);
        back = findViewById(R.id.arrow_button);
        next = findViewById(R.id.next_button);

        Random random = new Random();
        double p = random.nextDouble();
        boolean isGreater = p < 0.5; // if false, then is smaller

        int num1 = random.nextInt(99) + 1;
        int num2_temp;

        do {
            num2_temp = random.nextInt(99) + 1;
        } while (num1 == num2_temp);

        int num2 = num2_temp;

        if (isGreater)
            question.setText(R.string.larger);
        else
            question.setText(R.string.smaller);

        number1.setText(String.valueOf(num1));
        number2.setText(String.valueOf(num2));

        number1.setOnClickListener(e -> checkAnswer(num1, num2, isGreater));
        number2.setOnClickListener(e -> checkAnswer(num2, num1, isGreater));

        back.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit to Home Page")
                    .setMessage("Are you sure you want to quit <Compare Numbers> ?")
                    .setPositiveButton("Quit", (dialog, id) -> {
                        Intent intent = new Intent(compare.this, MainActivity.class);
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
    private void checkAnswer(int num1, int num2, boolean isGreater) {
        if ((isGreater && num1 > num2) || (!isGreater && num1 < num2)) {
            message.setTextColor(Color.GREEN);
            message.setText(R.string.bingo);
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            playSound(R.raw.bingo);
        }
        else {
            message.setTextColor(Color.RED);
            message.setText(R.string.wrong);
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            playSound(R.raw.wrong);
        }
    }
    private void playSound(int soundResourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }
}