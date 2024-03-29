package com.example.groceries;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Mainmenu2 extends AppCompatActivity {
    double[] mi= {0,0,0};
    ArrayList<String> groce = new ArrayList<>();
    Query databasereference3;
    DatabaseReference databasereference4;
    ArrayList<String> pricee = new ArrayList<>();
    ArrayList<String> groceryActualPrice1 = new ArrayList<>();
    ArrayList<String> getGroceryActualPrice = new ArrayList<>();
    LinearLayout list;
    ArrayAdapter<String> adapter;
    Query databasereference1;
    Boolean tr = false;
    DrawerLayout drawerLayout;
    ArrayList<String> market = new ArrayList<>();
    LinearLayout linear1;
    LinearLayout linear2;
    String name;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String mTitleSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu2);
        name = getIntent().getStringExtra("Name");
        Button b1 = findViewById(R.id.button4);
        Button b2 = findViewById(R.id.button);
        Button b3 = findViewById(R.id.button2);
        Button b4 = findViewById(R.id.button3);
        Button button5 = findViewById(R.id.button5);
        b1.setShadowLayer(1.0f, 1, 1, Color.BLACK);
        b2.setShadowLayer(1.0f, 1, 1, Color.BLACK);
        b3.setShadowLayer(1.0f, 1, 1, Color.BLACK);
        b4.setShadowLayer(1.0f, 1, 1, Color.BLACK);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                market.add("Econsave");
                market.add("Lotus");
                market.add("Giant");
                Intent a = new Intent(getApplicationContext(), Calculation.class);
                a.putExtra("market", market);
                a.putExtra("missing", mi);
                a.putExtra("Name", name);
                startActivity(a);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), Grocery.class);
                a.putExtra("Name", name);

                a.putExtra("missing", mi);
                startActivity(a);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), pricetracker.class);
                a.putExtra("Name", name);
                startActivity(a);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), Mainmenu.class);
                a.putExtra("Name", name);
                startActivity(a);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(a);
            }
        });
        new Runnable() {
            @Override
            public void run() {
                DatabaseReference databasereference4 = FirebaseDatabase.getInstance().getReference("Users").child(name);
                DatabaseReference databasereference3 = FirebaseDatabase.getInstance().getReference("Groceries");

                databasereference4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        groce = new ArrayList<>();
                        pricee = new ArrayList<>();
                        market = new ArrayList<>();
                        groceryActualPrice1 = new ArrayList<>();
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
                                    double econsave = 0;
                                    if (snapshot.child(groce.get(i)).child("Econsave").getValue().toString().isEmpty()) {
                                        econsave = -1;
                                    } else {


                                        econsave = Double.parseDouble(snapshot.child(groce.get(i)).child("Econsave").getValue().toString());
                                    }
                                    double lotus = 0;
                                    if (snapshot.child(groce.get(i)).child("Lotus").getValue().toString().isEmpty()) {
                                        lotus = -1;
                                    } else {
                                        lotus = Double.parseDouble(snapshot.child(groce.get(i)).child("Lotus").getValue().toString());
                                    }
                                    double giant = 0;
                                    if (snapshot.child(groce.get(i)).child("Giant").getValue().toString().isEmpty()) {
                                        giant = -1;
                                    } else {
                                        giant = Double.parseDouble(snapshot.child(groce.get(i)).child("Giant").getValue().toString());
                                    }
                                    double[] dd = {econsave, lotus, giant};
                                    if (market.get(i).equals("Econsave"))
                                        if (String.valueOf(dd[0]) == "-1") {
                                            groceryActualPrice1.add("Not available");
                                        } else {
                                            groceryActualPrice1.add(String.valueOf(dd[0]));

                                        }

                                    else if (market.get(i).equals("Giant")) {
                                        if (String.valueOf(dd[1]) == "-1") {
                                            groceryActualPrice1.add("Not available");
                                        } else {
                                            groceryActualPrice1.add(String.valueOf(dd[2]));

                                        }
                                    } else {
                                        if (String.valueOf(dd[2]) == "-1") {
                                            groceryActualPrice1.add("Not available");
                                        } else {
                                            groceryActualPrice1.add(String.valueOf(dd[2]));

                                        }
                                    }


                                }
                                for (int i = 0; i < groce.size(); i++) {
                                    int index = i;

                                    String a = pricee.get(i);
                                    String b = groceryActualPrice1.get(i);
                                    double e;
                                    double c = Double.parseDouble(a);
                                    try {
                                        e = Double.parseDouble(b);
                                    } catch (NumberFormatException exep) {
                                        e = -100;
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
            }



        }.run();
    }

    public void startService() {
if(!tr) {
    tr=true;
    Intent serviceIntent = new Intent(this, Background.class);
    serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
    ContextCompat.startForegroundService(this, serviceIntent);

}}
        }
