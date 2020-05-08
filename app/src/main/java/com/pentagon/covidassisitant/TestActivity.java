package com.pentagon.covidassisitant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    private EditText mAge;
    private Button mSubmit;
    private RadioGroup RGgender, RGcough, RGfever, RGdib, RGdiabetes, RGht, RGld, RGhd, RGth;
    private RadioButton RBgender, RBcough, RBfever, RBdib, RBdiabetes, RBht, RBld, RBhd, RBth;
    int tot = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        RGgender = findViewById(R.id.rg_gender);
        RGcough = findViewById(R.id.rg_cough);
        RGfever = findViewById(R.id.rg_fever);
        RGdib = findViewById(R.id.rg_dib);
        RGdiabetes = findViewById(R.id.rg_diabetes);
        RGht = findViewById(R.id.rg_ht);
        RGld = findViewById(R.id.rg_ld);
        RGhd = findViewById(R.id.rg_hd);
        RGth = findViewById(R.id.rg_th);
        mAge = findViewById(R.id.activity_test_edit_text_age);
        mSubmit = findViewById(R.id.activity_test_button_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitActivity();
            }
        });
    }
    private void submitActivity() {
        tot = 10;
        try {
            if (RBdiabetes.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBdib.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBfever.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBcough.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBhd.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBht.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBld.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (RBth.getText().toString().equals("Yes")){
                tot+=10;
            }
            if (!mAge.getText().toString().trim().equals("")){
                int age = Integer.parseInt(mAge.getText().toString().trim());
                if (age<=20){
                    tot+=2;
                }else if (age <= 40){
                    tot+=5;
                }else if (age <= 60){
                    tot+=8;
                }else {
                    tot+=10;
                }
                Toast.makeText(this, String.valueOf(tot), Toast.LENGTH_SHORT).show();
                if (tot<30){
                    dialogBuilder("Safe", "Though you have very less chances of being infected we suggest you to visit nearby hospital.");
                }else if (tot<50){
                    dialogBuilder("Slight chances", "seems like you have some chances of being infected we suggest you to visit nearby hospital.");
                }else {
                    dialogBuilder("High chances", "you have high chances of being infected we suggest you to visit nearby hospital.");
                }
            }else {
                Toast.makeText(this, "सर्व तपशील भरा", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "सर्व तपशील भरा", Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogBuilder(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(TestActivity.this, MainActivity.class));
            }
        });
        builder.show();
    }


    public void checkGender(View view){
        int RBid = RGgender.getCheckedRadioButtonId();
        RBgender = findViewById(RBid);
    }
    public void checkCough(View view){
        int RBid = RGcough.getCheckedRadioButtonId();
        RBcough = findViewById(RBid);
    }
    public void checkFever(View view){
        int RBid = RGfever.getCheckedRadioButtonId();
        RBfever = findViewById(RBid);
    }
    public void checkDIB(View view){
        int RBid = RGdib.getCheckedRadioButtonId();
        RBdib = findViewById(RBid);
    }
    public void checkDiabetes(View view){
        int RBid = RGdiabetes.getCheckedRadioButtonId();
        RBdiabetes = findViewById(RBid);
    }
    public void checkHT(View view){
        int RBid = RGht.getCheckedRadioButtonId();
        RBht = findViewById(RBid);
    }
    public void checkLD(View view){
        int RBid = RGld.getCheckedRadioButtonId();
        RBld = findViewById(RBid);
    }
    public void checkHD(View view){
        int RBid = RGhd.getCheckedRadioButtonId();
        RBhd = findViewById(RBid);
    }
    public void checkTH(View view){
        int RBid = RGth.getCheckedRadioButtonId();
        RBth = findViewById(RBid);
    }
}
