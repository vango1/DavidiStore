package com.example.hodael.davidimarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Stock extends AppCompatActivity {

    private Button AddItemButton ;
    private Button ShowItemDetailsButton ;
    private  Firebase mStockRef;
    private EditText ItemAddTxt;
    private EditText ItemPriceAddTxt;
    private EditText QuantityAddTxt;
    private EditText ShowitemTxt;
    private EditText ShowDetailsItem ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Firebase.setAndroidContext(this);
        mStockRef = new Firebase("https://davidimarket-7f5fd.firebaseio.com/Stock");
        AddItemButton = (Button) findViewById(R.id.addItem_Button);
        ShowItemDetailsButton = (Button) findViewById(R.id.showItem_Button);

        ItemAddTxt = (EditText)findViewById(R.id.add_item);
        ItemPriceAddTxt = (EditText)findViewById(R.id.addItemPrice);
        QuantityAddTxt= (EditText)findViewById(R.id.addQuantuty);
        ShowitemTxt = (EditText)findViewById(R.id.show_ItemText);
        ShowDetailsItem = (EditText)findViewById(R.id.ItemDetails);

        ShowItemDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String showItem = ShowitemTxt.getText().toString();

                mStockRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false ;

                        for (DataSnapshot d :dataSnapshot.getChildren()) {

                            if (showItem.equals(d.getKey())) {

                                ShowDetailsItem.setText(d.getValue().toString());
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

        // button for adding an item to the DB
        AddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ItemAdd = ItemAddTxt.getText().toString();

                Firebase childRef = mStockRef.child(ItemAdd);

                String PriceAdd = ItemPriceAddTxt.getText().toString();
                String QuantityAdd = QuantityAddTxt.getText().toString();



                childRef.setValue(new Item(PriceAdd , QuantityAdd) );
            }
        });

    }

    class Item {

        String itemName, itemPrice, itemQuantity;

        public Item( String itemPrice, String itemQuantity) {

            super();

            this.itemPrice = itemPrice;
            this.itemQuantity = itemQuantity;
        }

    }
}
