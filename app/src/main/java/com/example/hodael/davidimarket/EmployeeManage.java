package com.example.hodael.davidimarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EmployeeManage extends AppCompatActivity {

    private Button AddEmpBtn ;
    private Button SearchEmpBtn ;
    private Firebase mEmpRef;
    private EditText empUNameTxt;
    private EditText searchEmpNameTxt;
    private EditText empIdTxt;
    private EditText empBdateTxt;
    private EditText empPassTxt ;
    private EditText empPostionTxt ;
    private EditText showEmpMulti;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage);


        Firebase.setAndroidContext(this);
        mEmpRef = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Employees");

        AddEmpBtn = (Button) findViewById(R.id.AddEmpButton);
        SearchEmpBtn = (Button) findViewById(R.id.SearchEmpButton);

        empUNameTxt = (EditText)findViewById(R.id.addEmpUNameTxt);
        empIdTxt = (EditText)findViewById(R.id.addEmpIdTxt);
        empBdateTxt= (EditText)findViewById(R.id.addEmpBdate);
        empPassTxt=(EditText)findViewById(R.id.setPassword);
        showEmpMulti = (EditText)findViewById(R.id.showEmpMulti);
        searchEmpNameTxt = (EditText)findViewById(R.id.SearchEmpByName);
        empPostionTxt = (EditText)findViewById(R.id.empPosition);





        SearchEmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle params = new Bundle();
                params.putInt("SearchEmployee putInt" , R.id.SearchEmpButton);

                final String empUname = searchEmpNameTxt.getText().toString();

                mEmpRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d :dataSnapshot.getChildren()) {

                            if (empUname.equals(d.getKey())) {

                                showEmpMulti.setText(d.getValue().toString());

                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });








        AddEmpBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {



                String empUname = empUNameTxt.getText().toString();

                Firebase childRef = mEmpRef.child(empUname);

                String empBdate = empBdateTxt.getText().toString();
                String empId = empIdTxt.getText().toString();
                String empPass = empPassTxt.getText().toString();
                String empPos = empPostionTxt.getText().toString();



                childRef.setValue(new Employee( empBdate , empId , empPass , empPos));


            }
        });


    }




    class Employee {

        String empUserName, empId, empBdate , empPass , empPosition;

        public Employee(  String empBdate , String empId , String empPass , String empPosition ) {

            super();

            this.empUserName = empUserName;
            this.empBdate = empBdate;
            this.empId = empId;
            this.empPass= empPass;
            this.empPosition = empPosition;
        }

    }
}
