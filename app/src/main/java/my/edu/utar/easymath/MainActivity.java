package my.edu.utar.easymath;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button compareAct, orderAct, composeAct;
    ImageButton compareBt, orderBt, composeBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compareAct = findViewById(R.id.button_compare);
        compareBt = findViewById(R.id.img_but_compare);
        orderAct = findViewById(R.id.button_order);
        orderBt = findViewById(R.id.img_but_order);
        composeAct = findViewById(R.id.button_compose);
        composeBt = findViewById(R.id.img_but_compose);

        compareAct.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, compare.class);
            startActivity(intent);
        });

        compareBt.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, compare.class);
            startActivity(intent);
        });

        orderAct.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, order.class);
            startActivity(intent);
        });

        orderBt.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, order.class);
            startActivity(intent);
        });

        composeAct.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, compose.class);
            startActivity(intent);
        });

        composeBt.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, compose.class);
            startActivity(intent);
        });
    }
}
