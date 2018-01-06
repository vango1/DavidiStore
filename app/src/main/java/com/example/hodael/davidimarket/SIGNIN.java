package com.example.hodael.davidimarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SIGNIN extends AppCompatActivity {

    private  static int i = 1;
    private static final int CRESULT = 1;
    private static final int GRESULT = 2;
    private static int camOrGal ;


    private EditText userName;
    private EditText firstname;
    private EditText lastName;
    private EditText city;
    private EditText email;
    private EditText password;
    private  Firebase mRootRef;
    DatabaseReference x;
    ImageView imgView;
    private static Uri selectedImg ;
    private StorageReference storageRef ;
    private Bitmap bit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        imgView = findViewById(R.id.img);
        Firebase.setAndroidContext(this);
        mRootRef = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Users");
        storageRef = FirebaseStorage.getInstance().getReference();
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


        upladFile();


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

    private void upladFile() {


        StorageReference riversRef = storageRef.child("images/" + userName.getText().toString() + ".jpg");


        if (camOrGal == GRESULT) {
            if (selectedImg != null) {
                final ProgressDialog pd = new ProgressDialog(this);
                pd.setTitle("Uploading Image to storage...");
                pd.show();
                riversRef.putFile(selectedImg)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double pogress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        pd.setMessage((int) pogress + "% Uploaded ... ");

                    }
                })
                ;
            }
        }


                else if (camOrGal == CRESULT) {

                if (bit != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    //StorageReference storageRef = storage.getReferenceFromUrl("gs://you_firebase_app.appspot.com");
                    //  StorageReference imagesRef = storageRef.child("images/name_of_your_image.jpg");
                    StorageReference imagesRef = storageRef.child("images/" + userName.getText().toString() + ".jpg");
                    UploadTask uploadTask = imagesRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            // Do what you want
                        }
                    });
                }
            }

        else{

            Toast.makeText(this, "error on upload to storage ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null){

            if(requestCode == GRESULT) {
                camOrGal = GRESULT;
                selectedImg = data.getData();
                imgView.setImageURI(selectedImg);

                Toast.makeText(this, "images uploaded from Gallery ", Toast.LENGTH_LONG).show();


            }
            else {
                camOrGal = CRESULT;
                bit = (Bitmap) data.getExtras().get("data");
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