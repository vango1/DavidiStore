package com.example.hodael.davidimarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
        imgView = (ImageView) findViewById(R.id.img);
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

    // when you click on finish, the user deatils and picture will upload to the fireBase for authentication reason
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




    // open the gallery when click upload
    public  void onClickUp(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GRESULT);

    }

    // open the camera when you click capture
    public void onClickCapture(View v){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,CRESULT);
    }


    //upload a file picture to the DB
    private void upladFile() {


        StorageReference riversRef = storageRef.child("images/" + userName.getText().toString() + ".jpg");

        // when the file is from Gallery
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

        // when the file is from Camera
        else if (camOrGal == CRESULT) {

            if (bit != null) {
                final ProgressDialog pd = new ProgressDialog(this);
                pd.setTitle("Uploading Image to storage...");
                pd.show();
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
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            // Do what you want
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double pogress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        pd.setMessage((int) pogress + "% Uploaded ... ");

                    }
                });
                }
            }

        else{

            Toast.makeText(this, "error on upload to storage ", Toast.LENGTH_LONG).show();
        }

    }

    // put the picture we took from the camera / gallery to our screen(image view)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null){

            // when the file from Gallery
            if(requestCode == GRESULT) {
                camOrGal = GRESULT;
                selectedImg = data.getData();
///////////////////// just a code for given the extension ////////////////////////////////
                String extension = "";
                String[] filePathColumn = {MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME};
                Cursor cursor =
                        getContentResolver().query(selectedImg, filePathColumn, null, null, null);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    int fileNameIndex = cursor.getColumnIndex(filePathColumn[1]);
                    String fileName = cursor.getString(fileNameIndex);
                    // Here we get the extension you want
                    extension = fileName.replaceAll("^.*\\.", "");

                }
                cursor.close();

////////////////////////////////////////////////////////////////////////////////////////
                if (extension.equals("jpg") || extension.equals("png")){
                    imgView.setImageURI(selectedImg);

                    Toast.makeText(this, "images uploaded from Gallery ", Toast.LENGTH_LONG).show();

                }


                else
                    Toast.makeText(this, "cannot upload it has to be jpg / png file only", Toast.LENGTH_LONG).show();



            }

            // when the file from camera
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