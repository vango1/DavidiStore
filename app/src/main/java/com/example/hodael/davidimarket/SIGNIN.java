package com.example.hodael.davidimarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class SIGNIN extends AppCompatActivity {

    private  static int i = 1;
    private static final int CRESULT = 1;
    private static final int GRESULT = 2;

    private EditText userName;
    private EditText firstname;
    private EditText lastName;
    private EditText city;
    private EditText email;
    private EditText password;
    private  Firebase mRootRef;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        imgView = findViewById(R.id.img);
        Firebase.setAndroidContext(this);
        mRootRef = new Firebase("https://civic-boulevard-187917.firebaseio.com/Users");
        userName = (EditText)findViewById(R.id.editUser);
        firstname = (EditText)findViewById(R.id.editName);
        lastName = (EditText)findViewById(R.id.editLN);
        city = (EditText)findViewById(R.id.editCity);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);




    }

    public  void  onClickFinish(View v){
        String uName = userName.getText().toString();
        Firebase childRef = mRootRef.child(uName);

        String first = firstname.getText().toString();
        String last = lastName.getText().toString();
        String ct = city.getText().toString();
        String pass = password.getText().toString();
        String emailKey = email.getText().toString();


        childRef.setValue(new Person(first,last,ct,pass,emailKey) );
        Intent I = new Intent(this,firstScreen.class);
        startActivity(I);

    }





    public  void onClickUp(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GRESULT);


    }
    public void onClickCapture(View v){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,CRESULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null){
            if(requestCode == GRESULT) {
                Uri selectedImg = data.getData();
                imgView.setImageURI(selectedImg);
                Toast.makeText(this, "images uploaded from Gallery ", Toast.LENGTH_LONG).show();


            }
            else {
                Bitmap bit = (Bitmap) data.getExtras().get("data");
                imgView.setImageBitmap(bit);
                Toast.makeText(this, "images uploaded from Camera ", Toast.LENGTH_LONG).show();



            }

        }


    }
}



class Person{

    String firstname,lastName,city,password,email;

    public Person( String firstname, String lastName,
                  String city,String password,String email) {
        super();

        this.firstname = firstname;
        this.lastName = lastName;
        this.city = city;
        this.password = password;
        this.email = email;

    }


    @Override
    public String toString() {
        return password;
    }
}