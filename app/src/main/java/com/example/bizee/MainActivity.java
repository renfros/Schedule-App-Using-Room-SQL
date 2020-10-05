package com.example.bizee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUi();

    }

    private void setUpUi(){

        title = findViewById(R.id.txtTitle);
        Animation fadeFromBottom = AnimationUtils.loadAnimation(this,R.anim.fade_from_bottom);
        title.startAnimation(fadeFromBottom);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                startActivity(new Intent(MainActivity.this,MainPage.class));
                finish();
            }
        }, 3000);

    }

}