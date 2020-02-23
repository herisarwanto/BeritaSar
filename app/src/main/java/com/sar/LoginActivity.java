package com.sar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sar.dao.LoginDao;
import com.sar.tools.SharedPrefManager;
import com.sar.tools.Toolkit;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etNumb1, etNumb2, etNumb3;
    private ShowHidePasswordEditText scotPass;
    private Button btnLogin;
    private Spinner spStatus;
    private SpotsDialog spotsDialog;

    private String username, password, status, answer;
    private String numb1, numb2, numb3;
    private int num1, num2, num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        scotPass = findViewById(R.id.scot_password);
        btnLogin = findViewById(R.id.btn_login);
        spStatus = findViewById(R.id.sp_status);
        etNumb1 = findViewById(R.id.et_numb1);
        etNumb2 = findViewById(R.id.et_numb2);
        etNumb3 = findViewById(R.id.et_numb3);

        spotsDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(this).
                setTheme(R.style.LoadingDialog).build();
        addItemsOnSpinnerStatus();

        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            openDashboard();
        }

        getNumb();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinnerStatus() {

        List<String> list = new ArrayList<String>();
        list.add("Mahasiswa");
        list.add("Dosen");
        list.add("Civitas");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(dataAdapter);

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.GRAY);
                ((TextView) parentView.getChildAt(0)).setTextSize(18);
                    status = spStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void getNumb() {
        numb1 = Toolkit.generateRandomString();
        numb2 = Toolkit.generateRandomString();

        num1 = Integer.parseInt(numb1);
        num2 = Integer.parseInt(numb2);
        num3 = num1 + num2;

        etNumb1.setText(numb1);
        etNumb2.setText(numb2);
    }

    public void validasiData(){
        Log.e(this.getClass().getName(), "validasiData : status => "+status);
        username = etUsername.getText().toString();
        password = scotPass.getText().toString();
        answer = etNumb3.getText().toString();

        numb3 = String.valueOf(num3);

        if(TextUtils.isEmpty(username)){
            etUsername.setError(getString(R.string.txt_username_empty));
            etUsername.requestFocus();
            return;
        } else if(TextUtils.isEmpty(password)){
            scotPass.setError(getString(R.string.txt_password_empty));
            scotPass.requestFocus();
            return;
        } else if(TextUtils.isEmpty(answer)){
            etNumb3.setError(getString(R.string.txt_nothing_answer));
            etNumb3.requestFocus();
            return;
        } else if(!answer.equals(numb3)){
            etNumb3.setError(getString(R.string.txt_wrong_answer));
            etNumb3.requestFocus();
            return;
        }else {
            loginDatabase(username, password, status);
        }
    }

    public void loginDatabase(String username, String password, String status){
        spotsDialog.show();
        LoginDao.loginUser(getApplicationContext(), username, password, status, this);
    }

    public void setResultLogin(int code, String message) {
        if (code==200) {
            Log.e(this.getClass().getName(), "setResultLoad => IF START "+message);
            spotsDialog.cancel();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

            toastIconSuccess();

        } else {
            Log.e(this.getClass().getName(), "setResultLoad => ELSE START "+message);
            spotsDialog.cancel();
            toastIconFailed();

        }
    }

    private void openDashboard() {
        Intent dt = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(dt);
        finish();
    }

    private void toastIconSuccess() {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("Login Berhasil!");
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

        toast.setView(custom_view);
        toast.show();
    }

    private void toastIconFailed() {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("Login Gagal!");
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_A700));

        toast.setView(custom_view);
        toast.show();
    }

}
