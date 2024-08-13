package com.example.appbanmypham.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText edtEmail, edtPhoneNum, edtUsername, edtPassword;
    ImageView imgPassword;
    TextView signInBtn;
    AppCompatButton signUpBtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    private boolean passwordShowing = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNum = findViewById(R.id.edtPhoneNum);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        imgPassword = findViewById(R.id.imgPassword);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        imgPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiem tra xem password co dang show hay khong
                if(passwordShowing){
                    passwordShowing = false;

                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgPassword.setImageResource(R.drawable.password_show);
                }else{
                    passwordShowing = true;

                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imgPassword.setImageResource(R.drawable.password_hide);
                }
                //di chuyen con tro chuot den cuoi
                edtPassword.setSelection(edtPassword.length());
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //firebase realtime
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                //firebase auth
                firebaseAuth = FirebaseAuth.getInstance();

                String email = edtEmail.getText().toString();
                String phone = edtPhoneNum.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // Kiểm tra xem các trường dữ liệu có hợp lệ không
                if (!validateEmail() | !validatePhone() | !validateUsername() | !validatePassword()) {
                    // Nếu không hợp lệ, không thực hiện đăng ký
                    return;
                }
//                else{
//                    // Nếu dữ liệu hợp lệ, tiến hành đăng ký người dùng và chuyển đến màn hình đăng nhập
//                    User user = new User(email, phone, username, password);
//                    reference.child(username).setValue(user);
//                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    // Nếu đăng ký thành công, lưu thông tin người dùng vào Realtime Database
                                    User user = new User(email, phone, username, password);
                                    reference.child(username).setValue(user);

                                    // Gửi email xác thực
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    if (firebaseUser != null){
                                        firebaseUser.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> emailTask) {
                                                        if (emailTask.isSuccessful()) {
                                                            Toast.makeText(RegisterActivity.this,
                                                                    "Email xác thực đã được gửi, vui lòng kiểm tra hộp thư đến của bạn.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(RegisterActivity.this,
                                                                    "Không thể gửi email xác thực. Vui lòng thử lại sau.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    // Chuyển đến màn hình đăng nhập
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    // Nếu đăng ký không thành công, hiển thị thông báo lỗi
                                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công. Vui lòng thử lại sau.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public Boolean validateEmail(){
        String val = edtEmail.getText().toString();
        if (val.isEmpty()){
            edtEmail.setError("Email không thể để trống");
            return false;
        }else{
            edtEmail.setError(null);
            return true;
        }
    }
    public Boolean validatePhone(){
        String val = edtPhoneNum.getText().toString();
        if (val.isEmpty()){
            edtPhoneNum.setError("Số điện thoại không thể để trống");
            return false;
        }else{
            edtPhoneNum.setError(null);
            return true;
        }
    }
    public Boolean validateUsername(){
        String val = edtUsername.getText().toString();
        if (val.isEmpty()){
            edtUsername.setError("Tên đăng nhập không thể để trống");
            return false;
        }else{
            edtUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = edtPassword.getText().toString();
        if (val.isEmpty()){
            edtPassword.setError("Mật khẩu không thể để trống");
            return false;
        }else{
            edtPassword.setError(null);
            return true;
        }
    }
}