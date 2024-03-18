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
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class compose extends AppCompatActivity {
    Button number1, number2, random1, random2, random3, random4, clear, next;
    ImageButton back;
    TextView message, sign, question, symbol;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        sign = findViewById(R.id.sign);
        question = findViewById(R.id.question);
        symbol = findViewById(R.id.symbol);
        random1 = findViewById(R.id.random1);
        random2 = findViewById(R.id.random2);
        random3 = findViewById(R.id.random3);
        random4 = findViewById(R.id.random4);
        clear = findViewById(R.id.clear_button);
        message = findViewById(R.id.message);
        back = findViewById(R.id.arrow_button);
        next = findViewById(R.id.next_button);
        Button[] numbers = {random1, random2, random3, random4};

        Random random = new Random();
        double p = random.nextDouble();
        boolean isAddition = p < 0.5; // if false, then is Subtraction

        int ans = random.nextInt(9) + 1;
        int num1, num2;

        final int options = 4;
        int[] input = new int[2];
        ArrayList<Integer> optionsList = new ArrayList<>(options);
        Arrays.fill(input, -1);
        question.setText(getString(R.string.equal_ques, getString(R.string.equal), ans));
        symbol.setTextColor(Color.BLACK);
        symbol.setText("?");

        if (isAddition) {
            do {
                num1 = random.nextInt(ans);
            } while (num1 == ans);
            num2 = ans - num1;
            sign.setText("+");
        }
        else {
            num1 = random.nextInt(9) + 11;
            num2 = num1 - ans;
            sign.setText("-");
        }

        optionsList.add(num1);
        optionsList.add(num2);

        for (int i = 2; i < options; i++) {
            do {
                num1 = random.nextInt(10);
            } while (optionsList.contains(num1));
            optionsList.add(num1);
        }

        Collections.shuffle(optionsList);

        for (int i = 0; i < options; i++) {
            int num = optionsList.get(i);
            String numString = Integer.toString(num);
            numbers[i].setText(numString);

            numbers[i].setOnClickListener(e -> {
                message.setText("");

                if (input[0] == -1) {
                    input[0] = num;
                    number1.setText(numString);
                } else if (input[1] == -1) {
                    input[1] = num;
                    number2.setText(numString);

                    boolean isCorrect = (input[0] + input[1] == ans || input[0] - input[1] == ans);

                    if (isCorrect) {
                        message.setTextColor(Color.GREEN);
                        message.setText(R.string.bingo);
                        symbol.setText("✔");
                        symbol.setTextColor(Color.GREEN);
                        Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                        playSound(R.raw.bingo);

                    } else {
                        message.setTextColor(Color.RED);
                        message.setText(R.string.wrong);
                        symbol.setText("✘");
                        symbol.setTextColor(Color.RED);
                        Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                        playSound(R.raw.wrong);
                    }
                }
            });

            clear.setOnClickListener(e -> {
                number1.setText("");
                number2.setText("");
                message.setText("");
                symbol.setText("?");
                symbol.setTextColor(Color.BLACK);
                Arrays.fill(input, -1);
            });

            back.setOnClickListener(e -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Exit to Home Page")
                        .setMessage("Are you sure you want to quit <Compose Numbers> ?")
                        .setPositiveButton("Quit", (dialog, id) -> {
                            Intent intent = new Intent(compose.this, MainActivity.class);
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
    }
    private void playSound(int soundResourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }
}