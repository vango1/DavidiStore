package com.example.hodael.davidimarket;
import android.annotation.SuppressLint;
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
import com.google.firebase.analytics.FirebaseAnalytics;

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
    private FirebaseAnalytics mFirebaseAnalytics ;


    @SuppressLint("HardcodedText")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        Firebase.setAndroidContext(this);
        userName = (EditText)findViewById(R.id.userIn);
        password = (EditText)findViewById(R.id.Password_IN);
        Ref = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Users");
        RefStock = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Stock");
        RefEmp = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Employees");
        CurrentPos = new Firebase("https://davidimarket-7f5fd.firebaseio.com/CurrentPos");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }



    //when click on readme button the app changes from first-screen to readme
    public  void onClickReadme(View v){

        // saving the time data when the button being pressed to the FirebaseAnalytics
        Bundle params = new Bundle();

        params.putInt("testReadme putInt" , R.id.Readme);
        mFirebaseAnalytics.logEvent( "readme_int", params );
        params.putInt(FirebaseAnalytics.Param.CONTENT, R.id.Readme);
        //Logs an app event.
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
        Intent i = new Intent(this,Readme.class);
        startActivity(i);

    }

    //when click on Map button the app changes from first-screen to Map and save it to FirebaseAnalytics
    public  void onClickMap(View v){
        Bundle params = new Bundle();
        params.putInt("testMap putInt" , R.id.map);
        mFirebaseAnalytics.logEvent( "map", params );

        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }

    //when click on Sign-in button the app changes from first-screen to Sign-in and save it to FirebaseAnalytics
    public  void onClIcKsIgN(View v){
        Bundle params = new Bundle();
        params.putInt("testSignIn putInt" , R.id.sign_id);
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.LOGIN, params );

        Intent i = new Intent(this,SIGNIN.class);
        startActivity(i);
    }

    //when click on Log-in button the app changes from first-screen to customer-Profile(Log-in) and save it to FirebaseAnalytics
    public  void onClickLogIn(View v){

        Bundle params = new Bundle();
        params.putInt("testLogin putInt" , R.id.logIn);
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.LOGIN, params );
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);



        userN = userName.getText().toString();
        final String pass = password.getText().toString();

        // Check in the data base if the user is already exist
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d :dataSnapshot.getChildren()){

                    if(userN.equals(d.getKey())){

                        for (DataSnapshot s : d.getChildren()){

                            if (s.getKey().equals("password")){



                                if (s.getValue().toString().equals(pass)){

                                        Intent i = new Intent(firstScreen.this , customerProfile.class);
                                        startActivity(i);

                                        return;



                                }
                            }
                        }

                    }
                }

                Toast.makeText(getApplicationContext(), "Wrong User or Password ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    //when click on employee Log-in button the app changes from first-screen to after log-in(employee Log-in) and save it to FirebaseAnalytics
    public  void onClickLogInEmp(View v){

        userN = userName.getText().toString();
        final String pass = password.getText().toString();
        Bundle params = new Bundle();
        params.putInt("testLoginEmp putInt" , R.id.empLogin);
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.LOGIN, params );


        // Check in the data base if the employee is already exist
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
                                    return;



                                }
                            }
                        }

                    }
                }

                Toast.makeText(getApplicationContext(), "Wrong User or Password ", Toast.LENGTH_LONG).show();

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
