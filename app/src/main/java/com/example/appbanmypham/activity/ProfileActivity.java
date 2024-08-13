package com.example.appbanmypham.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appbanmypham.R;
import com.example.appbanmypham.fragment.DanhMucFragment;
import com.example.appbanmypham.fragment.TaiKhoanFragment;

public class ProfileActivity extends AppCompatActivity {
ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TaiKhoanFragment.class);
                startActivity(intent);
            }
        });


    }


}