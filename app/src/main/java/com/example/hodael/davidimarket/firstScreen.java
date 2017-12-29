package com.example.hodael.davidimarket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.SignInButton;

import java.util.HashMap;

public class firstScreen extends AppCompatActivity {


    EditText userName,password;
    Firebase Ref ;
    Firebase RefStock ;
    Firebase RefEmp ;
    Firebase CurrentPos;
    String globalUname;
    static String globalPosition;
    static String userN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        Firebase.setAndroidContext(this);
        userName = (EditText)findViewById(R.id.userIn);
        password = (EditText)findViewById(R.id.Password_IN);
        Ref = new Firebase("https://my-project-1512230573726.firebaseio.com/Users");
        RefStock = new Firebase("https://my-project-1512230573726.firebaseio.com/Stock");
        RefEmp = new Firebase("https://my-project-1512230573726.firebaseio.com/Employees");
        CurrentPos = new Firebase("https://my-project-1512230573726.firebaseio.com/CurrentPos");








    }



    public  void onClickReadme(View v){

        Intent i = new Intent(this,Readme.class);
        startActivity(i);

    }
    public  void onClickMap(View v){

        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);

    }

    public  void onClIcKsIgN(View v){

        Intent i = new Intent(this,SIGNIN.class);
        startActivity(i);

    }

    public  void onClickLogIn(View v){

         userN = userName.getText().toString();
        final String pass = password.getText().toString();


        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d :dataSnapshot.getChildren()){

                    if(userN.equals(d.getKey())){

                        for (DataSnapshot s : d.getChildren()){
                            boolean flag = false ;

                            if (s.getKey().equals("password")){



                                if (s.getValue().toString().equals(pass)){

                                        Intent i = new Intent(firstScreen.this , customerProfile.class);
                                        startActivity(i);



                                }
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public  void onClickLogInEmp(View v){

        userN = userName.getText().toString();
        final String pass = password.getText().toString();


        RefEmp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d :dataSnapshot.getChildren()){

                    if(userN.equals(d.getKey())){

                        for (DataSnapshot s : d.getChildren()){

                            if (s.getKey().equals("empPosition")){
                                globalPosition =s.getValue().toString();

                            }

                            if (s.getKey().equals("empPass")){



                                if (s.getValue().toString().equals(pass)){

                                    globalUname = userN;

                                    Intent i = new Intent(firstScreen.this , afterLogIn.class);
                                    startActivity(i);



                                }
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            public  String retGlobalPosition(){
                String ans = globalPosition;


                return ans;
            }

        });

    }











}
