package com.example.coearth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button loginButton = (Button) findViewById(R.id.login_btn);
        Button signButton = (Button) findViewById(R.id.sign_btn);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        signButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),SignActivity.class);
                startActivity(intent);
            }
        });

        TextView email = (TextView) findViewById(R.id.mail_btn);
        email.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"coearthcs@example.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "test@test");
                email.putExtra(Intent.EXTRA_TEXT, "문의내용을 자세하게 적어주세요! 더 노력하는 coearth가 되겠습니다.");
                startActivity(email);
            }
        });

    }
}