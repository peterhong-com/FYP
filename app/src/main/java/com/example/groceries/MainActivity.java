package com.example.groceries;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MainActivity<mi> extends AppCompatActivity {
    private Button login;
    private EditText password, account;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ;
    DatabaseReference databaseReference = database.getReference("Users");
    ;
    double[] mi= new double[3];
    Query databasereference2;
    Query databasereference1;
    ArrayList<String> grocery;
    ArrayList<String> quantity;
    ArrayList<String> pricee = new ArrayList<>();
    ArrayList<String> groceryActualPrice = new ArrayList<>();
    ArrayList<String> groce = new ArrayList<>();
    ArrayList<String> market = new ArrayList<>();
    ArrayList<String>groceryActualPrice1 = new ArrayList<>();
    String name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_or_register);
        TextView register = findViewById(R.id.register);
        SpannableString mSpannableString = new SpannableString("Register");
        mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);
        register.setText(mSpannableString);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        account = findViewById(R.id.account);



       ;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(account.getText().toString()) && TextUtils.isEmpty(password.getText().toString())) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    // else call the method to add
                    // data to our database.
                    ArrayList<ArrayList<String>> list = new ArrayList<>();
                    String name = account.getText().toString();
                    String passwor = password.getText().toString();
                    user.setName(name);
                    user.setPassword(passwor);
                    user.setList(list);
                    addDatatoFirebase(user);
                }
            }

        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                // else call the method to add
                // data to our database.
                name = account.getText().toString();
                String passwor = password.getText().toString();
                user.setName(name);
                user.setPassword(passwor);
                getdata(user);
            }


        });
    }

    private void addDatatoFirebase(User user) {

        // inside the method of on Data change we are setting
        // our object class to our database reference.
        // data base reference will sends data to firebase.
        databasereference2 = FirebaseDatabase.getInstance().getReference("Users").child(user.getName());
        databasereference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "This User already exist", Toast.LENGTH_LONG).show();

                } else {
                    databaseReference.child(user.getName()).setValue(user);


                    // afteradding this data we are showing toast message.
                        Toast.makeText(MainActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                        

                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
            }
        });


    }


    private void getdata(User user) {

        // calling add value event listener method
        // for getting the values from database.
        databasereference1 = FirebaseDatabase.getInstance().getReference("Users").child(user.getName());
        databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pass = String.valueOf(snapshot.child("password").getValue());
                    System.out.println(pass + 12);
                    if (user.getPassword().equals(pass)) {
                        Toast.makeText(MainActivity.this, "login Successfully", Toast.LENGTH_LONG).show();
                        Intent a = new Intent(getApplicationContext(), Mainmenu2.class);
                        a.putExtra("Name", user.getName());

                        DatabaseReference databasereference4 = FirebaseDatabase.getInstance().getReference("Users").child(user.getName());
                        DatabaseReference databasereference3 = FirebaseDatabase.getInstance().getReference("Groceries");

                        databasereference4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                groce=new ArrayList<>();
                                pricee=new ArrayList<>();
                                market= new ArrayList<>();
                                groceryActualPrice1= new ArrayList<>();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    if (postSnapshot.getKey().equals("pricetracker")) {
                                        for (DataSnapshot preSnapshot : postSnapshot.child("name").getChildren()) {
                                            groce.add(preSnapshot.getValue().toString());
                                        }
                                        for (DataSnapshot preSnapshot : postSnapshot.child("price").getChildren()) {
                                            pricee.add(preSnapshot.getValue().toString());
                                        }
                                        for (DataSnapshot preSnapshot : postSnapshot.child("market").getChildren()) {
                                                market.add(preSnapshot.getValue().toString());


                                        }
                                    }
                                }
                                databasereference3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (int i = 0; i < groce.size(); i++) {
                                            double econsave=0;
                                            if(snapshot.child(groce.get(i)).child("Econsave").getValue().toString().isEmpty()){
                                                econsave=-1;
                                            }
                                            else {


                                                econsave = Double.parseDouble(snapshot.child(groce.get(i)).child("Econsave").getValue().toString());
                                            }
                                           double lotus=0;
                                            if(snapshot.child(groce.get(i)).child("Lotus").getValue().toString().isEmpty()){
                                                lotus=-1;
                                            }
                                            else {
                                                lotus = Double.parseDouble(snapshot.child(groce.get(i)).child("Lotus").getValue().toString());
                                            }
                                            double giant=0;
                                            if(snapshot.child(groce.get(i)).child("Giant").getValue().toString().isEmpty()){
                                                giant=-1;
                                            }
                                            else {
                                                giant = Double.parseDouble(snapshot.child(groce.get(i)).child("Giant").getValue().toString());
                                            }
                                            double[] dd = {econsave, lotus, giant};
                                            if (market.get(i).equals("Econsave"))
                                                if(String.valueOf(dd[0])=="-1"){
                                                    groceryActualPrice1.add("Not available");
                                                }
                                                else{ groceryActualPrice1.add(String.valueOf(dd[0]));

                                                }

                                            else if (market.get(i).equals("Giant")) {
                                                if (String.valueOf(dd[1]) == "-1") {
                                                    groceryActualPrice1.add("Not available");
                                                } else {
                                                    groceryActualPrice1.add(String.valueOf(dd[2]));

                                                }
                                            }
                                            else {
                                                if(String.valueOf(dd[2])=="-1"){
                                                    groceryActualPrice1.add("Not available");
                                                }
                                                else{ groceryActualPrice1.add(String.valueOf(dd[2]));

                                                }
                                            }


                                        }
                                        for (int i = 0; i < groce.size(); i++) {
                                            int index = i;

                                            String a = pricee.get(i);
                                            String b = groceryActualPrice1.get(i);
                                            double e;
                                            double c = Double.parseDouble(a);
                                            try{
                                                e=Double.parseDouble(b);
                                            }
                                            catch (NumberFormatException exep){
                                                e=-100;
                                            }
                                            System.out.println(c + e);
                                            double d = e;

                                            if (c == d) {
                                                System.out.println(c + e);
                                                startService();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                        DatabaseReference databaseReference5;
                        databaseReference5 = FirebaseDatabase.getInstance().getReference("Users").child(user.getName());



                            databaseReference5.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    grocery = new ArrayList<>();
                                    quantity = new ArrayList<>();
                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        if (postSnapshot.getKey().equals("name") || postSnapshot.getKey().equals("password") || postSnapshot.getKey().equals("pricetracker")) {
                                        } else if (postSnapshot.getKey().equals("list")) {

                                            for (DataSnapshot preSnapshot : postSnapshot.getChildren()) {
                                                grocery.add(preSnapshot.getValue().toString());

                                            }
                                        } else if (postSnapshot.getKey().equals("quantity")) {

                                            for (DataSnapshot preSnapshot : postSnapshot.getChildren()) {
                                                quantity.add(preSnapshot.getValue().toString());

                                            }
                                        }
                                    }

                                    SharedPreferences sharedPreferences = getSharedPreferences("quantity",0);
                                    String gro="";
                                    String qunat="";

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    for(int i =0; i<grocery.size();i++) {
                                        gro =gro+grocery.get(i)+",";
                                        qunat=qunat+quantity.get(i)+",";
                                    }
                                    editor.putString("grocery",gro);
                                    editor.putString("Quanttity",qunat);
                                    editor.commit();

                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                        startActivity(a);
                    }
                    else
                        Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void startService() {

        Intent serviceIntent = new Intent(this, Background.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, Background.class);
        stopService(serviceIntent);
    }



}