package com.example.coearth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.check).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check:
                    signUp();
                    break;
            }
        }
    };

    private void signUp() {
        String id =  ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
        String pw = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String pwck = ((EditText)findViewById(R.id.editTextPasswordck)).getText().toString();

        if(id.length()>0 && pw.length()>0&&pwck.length()>0){
            if(pw.equals(pwck)){
                mAuth.createUserWithEmailAndPassword(id,pw)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignActivity.this, "회원가입 성공! 반갑습니다:)", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    if(task.getException().toString() != null){
                                        Toast.makeText(SignActivity.this, "회원가입 실패했습니다", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                        });
            }
            else{
                Toast.makeText(SignActivity.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
    }

}
}