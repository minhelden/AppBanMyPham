package com.example.appbanmypham.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanmypham.R;
import com.example.appbanmypham.model.User;
import com.example.appbanmypham.db.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    ImageView imgPassword;
    TextView signUpBtn;
    AppCompatButton signInBtn;
    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        imgPassword = findViewById(R.id.imgPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);
        imgPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiem tra xem password co dang show hay khong
                if (passwordShowing) {
                    passwordShowing = false;

                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgPassword.setImageResource(R.drawable.password_show);
                } else {
                    passwordShowing = true;

                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imgPassword.setImageResource(R.drawable.password_hide);
                }
                //di chuyen con tro chuot den cuoi
                edtPassword.setSelection(edtPassword.length());
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                if (!validateUsername() | !validatePassword()) {

                } else if (edtUsername.getText().toString().equals("admin") && edtPassword.getText().toString().equals("admin")) {
                    SharedPref.write(SharedPref.IS_ADMIN, true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    checkUser();
                }

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = edtUsername.getText().toString();
        if (val.isEmpty()) {
            edtUsername.setError("Tên đăng nhập không thể để trống");
            return false;
        } else {
            edtUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = edtPassword.getText().toString();
        if (val.isEmpty()) {
            edtPassword.setError("Mật khẩu không thể để trống");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = edtUsername.getText().toString().trim();
        String userPassword = edtPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    edtUsername.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//
//                    if (!Objects.equals(passwordFromDB, userPassword)){
//                        edtUsername.setError(null);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }else{
//                        edtPassword.setError("Mật khẩu không chính xác");
//                        edtPassword.requestFocus();
//                    }
//                }else{
//                    edtUsername.setError("Tên đăng nhập không tồn tại");
//                    edtUsername.requestFocus();
//                }
                if (snapshot.exists()) {
                    // Lấy dữ liệu người dùng từ snapshot
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            SharedPref.write(SharedPref.USER_DATA, new Gson().toJson(user));
                            SharedPref.write(SharedPref.IS_ADMIN, false);
                        }
                        String passwordFromDB = user.getPassword();
//                        boolean isEmailVerified = FirebaseAuth.getInstance().getCurrentUser().isEmailVerified();
//
//                        // Kiểm tra xác thực email
//                        if (!isEmailVerified) {
//                            Toast.makeText(LoginActivity.this, "Vui lòng xác thực email trước khi đăng nhập.", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser!=null){
                            boolean isEmailVerified = currentUser.isEmailVerified();

                            // Kiểm tra xác thực email
                            if (!isEmailVerified) {
                                Toast.makeText(LoginActivity.this, "Vui lòng xác thực email trước khi đăng nhập.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        // So sánh mật khẩu từ cơ sở dữ liệu với mật khẩu nhập vào
                        if (passwordFromDB.equals(userPassword)) {
                            // Mật khẩu khớp, đăng nhập thành công
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Mật khẩu không khớp
                            edtPassword.setError("Mật khẩu không chính xác");
                            edtPassword.requestFocus();
                        }
                    }
                } else {
                    // Tên người dùng không tồn tại trong cơ sở dữ liệu
                    edtUsername.setError("Tên đăng nhập không tồn tại");
                    edtUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            currentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        boolean isEmailVerified = currentUser.isEmailVerified();

                        if(isEmailVerified){

                        }else{

                        }
                    }else{

                    }
                }
            });
        }
    }
}