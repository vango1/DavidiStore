package com.example.hodael.davidimarket;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.FileLoader;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.util.HashMap;

public class customerProfile extends AppCompatActivity {

        static Firebase Ref ;
        static TextView uname_p,fname_p,lname_p,city_p,email_p;
        private Button searchButton ;
        private EditText searchItemCust;
        private EditText showItemDetails;
        private Firebase mStockRef;
        private StorageReference refProfPic;
        private FirebaseAnalytics mFirebaseAnalytics ;


        ImageView profPic;





        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_customer_profile);
            profPic = (ImageView)findViewById(R.id.ImgProfileView) ;

            Firebase.setAndroidContext(this);

            uname_p = (TextView) findViewById(R.id.uNameP);
            fname_p = (TextView) findViewById(R.id.fNameP);
            city_p = (TextView) findViewById(R.id.cityP);
            email_p = (TextView) findViewById(R.id.emailP);
            lname_p = (TextView) findViewById(R.id.lNameP);

            searchButton = (Button)findViewById(R.id.custSearchBtn);
            searchItemCust = (EditText) findViewById(R.id.custItemNameTxt);
            showItemDetails = (EditText) findViewById(R.id.showItemCustom);

            refProfPic = FirebaseStorage.getInstance().getReference();
            final StorageReference riversRef =    refProfPic.child("images/" + firstScreen.userN + ".jpg");
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);





            Ref = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Users/" + firstScreen.userN);
            mStockRef = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Stock");




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

                        Glide.with(getApplicationContext())
                                .using(new FirebaseImageLoader())
                                .load(riversRef)
                                .into(profPic);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });



            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle params = new Bundle();
                    params.putInt("SearchCustItem putInt" , R.id.custSearchBtn);
                    mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.SEARCH, params );
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);








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
