package com.example.hodael.davidimarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class customerProfile extends AppCompatActivity {

    static Firebase Ref ;
    static TextView uname_p,fname_p,lname_p,city_p,email_p;
    private Button searchButton ;
    private EditText searchItemCust;
    private EditText showItemDetails;
    private Firebase mStockRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        Firebase.setAndroidContext(this);
        uname_p = (TextView) findViewById(R.id.uNameP);
        fname_p = (TextView) findViewById(R.id.fNameP);
        city_p = (TextView) findViewById(R.id.cityP);
        email_p = (TextView) findViewById(R.id.emailP);
        lname_p = (TextView) findViewById(R.id.lNameP);

        searchButton = (Button)findViewById(R.id.custSearchBtn);
        searchItemCust = (EditText) findViewById(R.id.custItemNameTxt);
        showItemDetails = (EditText) findViewById(R.id.showItemCustom);



        Ref = new Firebase("https://my-project-1512230573726.firebaseio.com/Users/" + firstScreen.userN);
        mStockRef = new Firebase("https://my-project-1512230573726.firebaseio.com/Stock");

        Toast.makeText(getApplicationContext(), ""+firstScreen.userN, Toast.LENGTH_SHORT).show();


        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uname_p.setText(firstScreen.userN);
                for (DataSnapshot d :dataSnapshot.getChildren()) {

                    if (d.getKey().equals("city")) {
                        city_p.setText(d.getValue().toString());
                    }
                    if (d.getKey().equals("email")) {
                        email_p.setText(d.getValue().toString());
                    }

                    if (d.getKey().equals("firstname")) {
                        fname_p.setText(d.getValue().toString());
                    }
                    if (d.getKey().equals("lastName")) {
                        lname_p.setText(d.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String showItem = searchItemCust.getText().toString();

                mStockRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false ;

                        for (DataSnapshot d :dataSnapshot.getChildren()) {

                            if (showItem.equals(d.getKey())) {

                                showItemDetails.setText(d.getValue().toString());
                                flag = true;

                            }
                        }
                        if (flag==false){
                            Toast.makeText(getApplicationContext(), "No Results", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });

    }


}
