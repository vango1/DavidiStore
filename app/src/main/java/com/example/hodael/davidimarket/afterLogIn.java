package com.example.hodael.davidimarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class afterLogIn extends AppCompatActivity {

    Firebase RefEmp ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_log_in);



    }


    public  void onClickStock(View v){

        Intent i = new Intent(this,Stock.class);
        startActivity(i);

    }


    public  void onClickManageEmp(View v){

        if (firstScreen.globalPosition.equals("Manager")) {
            Intent i = new Intent(this, EmployeeManage.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Welcome Manager.", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(getApplicationContext(), "No Permission.", Toast.LENGTH_SHORT).show();


        }

    }
}


