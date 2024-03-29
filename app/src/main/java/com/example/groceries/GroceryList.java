package com.example.groceries;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class GroceryList extends AppCompatActivity {

    ImageView add;
    String name;
    Query databasereference2;
    LinearLayout list;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        name=getIntent().getStringExtra("Name");
        list=findViewById(R.id.Grocerylist);
        getSupportActionBar().setTitle("Grocery List");

        databasereference2 = FirebaseDatabase.getInstance().getReference("Users").child(name);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        databasereference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LinearLayout ll = findViewById(R.id.Grocerylist);
                for (DataSnapshot preSnapshot : snapshot.getChildren()){
                if(preSnapshot.getKey().equals("name") || preSnapshot.getKey().equals("password")||preSnapshot.getKey().equals("pricetracker")){

                }
                else {
                    ll.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout ll1 = new LinearLayout(getApplicationContext());
                    ll1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout ll2 = new LinearLayout(getApplicationContext());
                    ll1.setOrientation(LinearLayout.HORIZONTAL);
                        TextView a = new TextView(getApplicationContext());
                        Button b = new Button(getApplicationContext());
                        a.setMaxWidth(2000);
                        a.setTextSize(20);
                        a.setText(preSnapshot.getKey());
                        b.setText("Delete");
                        a.setPadding(20,0,90,0);
                        ll1.addView(ll2);
                        ll2.addView(a);
                        ll2.setMinimumWidth(500);
                        ll1.addView(b);
                        View aa = new View(GroceryList.this);
                        aa.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                        aa.setBackgroundColor(Color.BLACK);
                        ll.addView(ll1);
                        ll.addView(aa);



                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GroceryList.this);
                                alertDialog.setTitle("Are you want to delete list \"\""+preSnapshot.getKey()) ;
                                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        databaseReference.child(name).child(preSnapshot.getKey()).removeValue();
                                        Intent go = new Intent(getApplicationContext(), GroceryList.class);
                                        go.putExtra("Name", name);
                                        startActivity(go);
                                    }
                                });

                                alertDialog.create();
                                alertDialog.show();

                            }
                        });
                        a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent go = new Intent(getApplicationContext(), Grocery.class);
                                go.putExtra("listname", a.getText().toString());
                                go.putExtra("name", name);
                                startActivity(go);
                            }
                        });


                }
                    }
                }


            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroceryList.this);

                final EditText input = new EditText(getApplicationContext());
                builder.setTitle("Please enter your list name");
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String m_Text = input.getText().toString();

                        databasereference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
//                                    for(DataSnapshot presnapshot: snapshot.getChildren()) {
//                                        if (m_Text.equals()) {
//                                                Toast.makeText(GroceryList.this, "List already exist", Toast.LENGTH_LONG);
//                                        }
//                                    }
//                                }
//                                else{
                                    databaseReference.child(name).child(m_Text).setValue("");
                                    Toast.makeText(GroceryList.this, "List added", Toast.LENGTH_SHORT).show();
                                    Intent a = new Intent(getApplicationContext(), GroceryList.class);
                                    a.putExtra("Name",name);
                                    startActivity(a);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }

                        });
                    }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }
}