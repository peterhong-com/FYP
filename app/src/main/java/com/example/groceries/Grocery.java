package com.example.groceries;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Grocery extends AppCompatActivity {

    ImageView add;
    Button save;
    String name;
    Boolean c;
    LinearLayout list;

    ArrayList<String> grocery = new ArrayList<String>();
    ArrayList<String> quantity = new ArrayList<>();
    DatabaseReference databaseReference;
    DatabaseReference databasereference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        add = findViewById(R.id.imageView11);
        save = findViewById(R.id.Save);
        c = false; getSupportActionBar().setTitle("Grocery List");
        c = getIntent().getBooleanExtra("c", false);
        Intent b = getIntent();
        name = b.getStringExtra("Name");

        String listname = getIntent().getStringExtra("listname");
        databasereference2 = FirebaseDatabase.getInstance().getReference("Users").child(name);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        if (!c) {

            databasereference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals("name") || postSnapshot.getKey().equals("password") || postSnapshot.getKey().equals("pricetracker")) {

                        } else if (postSnapshot.getKey().equals("list")) {
                            grocery = new ArrayList<>();
                            for (DataSnapshot preSnapshot : postSnapshot.getChildren()) {
                                grocery.add(preSnapshot.getValue().toString());

                            }
                        } else if (postSnapshot.getKey().equals("quantity")) {
                            quantity = new ArrayList<>();
                            for (DataSnapshot preSnapshot : postSnapshot.getChildren()) {
                                quantity.add(preSnapshot.getValue().toString());

                            }
                        }
                    }
                    list = findViewById(R.id.grocerylist2);
                    list.setOrientation(LinearLayout.VERTICAL);

                    ;
                    for (int i = 0; i < grocery.size(); i++) {

                        int index = i;

                        TextView grocer = new TextView(getApplicationContext());
                        TextView quantt= new TextView(getApplicationContext());
                        quantt.setText("Quantity: "+quantity.get(i));
                        quantt.setTextSize(16);
                        grocer.setText(grocery.get(i));
                        grocer.setTypeface(Typeface.SANS_SERIF);
                        grocer.setTextColor(Color.BLUE);
                        grocer.setTextSize(16);
                        grocer.setShadowLayer(12F, 1.2F, 1.2F, Color.BLACK);
                        Button delete1 = new Button(getApplicationContext());
                        delete1.setText("Delete ");
                        LinearLayout ll = new LinearLayout(getApplicationContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout ll1 = new LinearLayout(getApplicationContext());
                        ll1.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout ll2 = new LinearLayout(getApplicationContext());
                        ll2.setOrientation(LinearLayout.HORIZONTAL);
                        grocer.setMaxWidth(330);
                        grocer.setMinWidth(330);
                        quantt.setMaxWidth(150);
                        ll1.setPadding(10, 0, 90, 0);
                        ll1.addView(grocer);
                        ll1.addView(quantt);
                        grocer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(String.valueOf(grocer.getText().toString()));
                                databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        double d1 = 0;
                                        double d2 = 0;
                                        double d3 = 0;
                                        if (snapshot.child("Econsave").getValue().toString().isEmpty()) {
                                            d1 = 0;
                                        } else
                                            d1 = Double.parseDouble(snapshot.child("Econsave").getValue().toString());
                                        if (snapshot.child("Lotus").getValue().toString().isEmpty()) {
                                            d2 = 0;
                                        } else
                                            d2 = Double.parseDouble(snapshot.child("Lotus").getValue().toString());
                                        if (snapshot.child("Giant").getValue().toString().isEmpty()) {
                                            d3 = 0;
                                        } else
                                            d3 = Double.parseDouble(snapshot.child("Giant").getValue().toString());
                                        double[] dd = {d1, d2, d3};
                                        double[] price1 = dd;
                                        String[] mar1 = {"Econsave","Lotus","Giant"};


                                        String[] ss = {mar1[0] + ":RM" + df.format(price1[0]) + "", mar1[1] + ":   RM" + df.format(price1[1]) + "", mar1[2] + ":   RM" + df.format(price1[2]) + ""};
                                        Intent a = new Intent(getApplicationContext(), PriceList.class);
                                        a.putExtra("price", ss);
                                        a.putExtra("groceryname", String.valueOf(grocer.getText()));
                                        startActivity(a);

                                    }


                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });


                            }
                        });

                        ll1.setMinimumWidth(500);
                        ll2.addView(delete1);

                        ll.addView(ll1);
                        ll.addView(ll2);

                        delete1.setId(i);
                        delete1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Grocery.this);

                                alertDialog.setTitle("Are you want to delete grocery \"\"" + grocery.get(delete1.getId()));
                                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        list.removeAllViews();
                                        int n = delete1.getId();
                                        grocery.remove(index);
                                        quantity.remove(index);
                                        databaseReference.child(name).child("list").setValue(grocery);
                                        databaseReference.child(name).child("quantity").setValue(quantity);
                                        Intent b = new Intent(getApplicationContext(), Grocery.class);
                                        b.putExtra("Name", name);
                                        b.putExtra("listname", listname);
                                        startActivity(b);

                                    }
                                });

                                alertDialog.create();
                                alertDialog.show();
                            }
                        });
                        list.addView(ll);

                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
        if (c) {
            final int[] tr = {0};
            databasereference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals("name") || postSnapshot.getKey().equals("password") || postSnapshot.getKey().equals("pricetracker")) {

                        } else if (postSnapshot.getKey().equals("list")) {
                            grocery = new ArrayList<>();
                            for (DataSnapshot preSnapshot : postSnapshot.getChildren()) {
                                grocery.add(preSnapshot.getValue().toString());

                            }
                        } else if (postSnapshot.getKey().equals("quantity")) {
                            quantity = new ArrayList<>();
                            for (DataSnapshot preSnapshot : postSnapshot.getChildren()) {
                                quantity.add(preSnapshot.getValue().toString());

                            }
                        }

                    }


                    for (int i = 0; i < grocery.size(); i++) {
                        list = findViewById(R.id.grocerylist2);
                        int index = i;


                        TextView grocer = new TextView(getApplicationContext());
                        TextView quantt = new TextView(getApplicationContext());
                        quantt.setText("Quantity"+quantity.get(i));
                        quantt.setTextSize(16);
                        grocer.setText(grocery.get(i));
                        grocer.setTypeface(Typeface.SANS_SERIF);
                        grocer.setTextColor(Color.BLUE);
                        grocer.setTextSize(16);
                        grocer.setShadowLayer(12F, 1.2F, 1.2F, Color.BLACK);
                        Button delete1 = new Button(getApplicationContext());
                        delete1.setText("Delete ");
                        LinearLayout ll = new LinearLayout(getApplicationContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout ll1 = new LinearLayout(getApplicationContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout ll2 = new LinearLayout(getApplicationContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll1.setPadding(10, 0, 90, 0);
                        grocer.setMaxWidth(330);
                        grocer.setMinWidth(330);
                        quantt.setMaxWidth(150);
                        ll1.setPadding(10, 0, 30, 0);
                        ll1.addView(grocer);
                        ll1.addView(quantt);
                        grocer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String gro = "";
                                if (String.valueOf(grocer.getText()).contains(".")) {
                                    gro = String.valueOf(grocer.getText()).replace(".", "%2E");
                                } else
                                    gro = String.valueOf(grocer.getText());
                                DatabaseReference databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(String.valueOf(gro));
                                databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        double[] dd = {Double.parseDouble(snapshot.child("Econsave").getValue().toString()), Double.parseDouble(snapshot.child("Lotus").getValue().toString()), Double.parseDouble(snapshot.child("Giant").getValue().toString())};

                                        String[] ss = {"Econsave :RM" + df.format(dd[0]) + "", "Lotus :   RM" + df.format(dd[1]) + "", "Giant :   RM" + df.format(dd[2]) + ""};
                                        Intent a = new Intent(getApplicationContext(), PriceList.class);
                                        a.putExtra("price", ss);
                                        startActivity(a);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });

                            }
                        });

                        ll1.setMinimumWidth(500);
                        ll2.addView(delete1);

                        ll.addView(ll1);
                        ll.addView(ll2);

                        delete1.setId(i);
                        delete1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Grocery.this);

                                alertDialog.setTitle("Are you want to delete grocery \"\"" + grocery.get(delete1.getId()));
                                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        int n = delete1.getId();
                                        grocery.remove(index);
                                        quantity.remove(index);
                                        databaseReference.child(name).child("list").setValue(grocery);
                                        databaseReference.child(name).child("quantity").setValue(quantity);
                                        name = b.getStringExtra("Name");
                                        list.removeAllViews();
                                        Intent b = new Intent(getApplicationContext(), Grocery.class);
                                        b.putExtra("Name", name);
                                        b.putExtra("listname", listname);
                                        startActivity(b);
                                    }
                                });

                                alertDialog.create();
                                alertDialog.show();
                            }
                        });
                        list.addView(ll);

                    }
                    if (tr[0] == 0) {
                        InputStream in = null;
                        String goce = "";
                        ArrayList<String> a = getIntent().getStringArrayListExtra("grocerssy");
                        int d = a.size();
                        try {
                            in = getAssets().open("MBA.txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        InputStreamReader ss = new InputStreamReader(in);
                        try {
                            MarketBasketAnalysis as = new MarketBasketAnalysis(ss);
                            goce = as.returne(a.get(d - 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        AlertDialog.Builder ds = new AlertDialog.Builder(Grocery.this);
                        TextView s = new TextView(getApplicationContext());
                        s.setText("We recommend you to add " + goce + " inside the list!");
                        ds.setView(s);


                        String finalGoce = goce;
                        ds.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.removeAllViews();
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Grocery.this);
                                EditText input = new EditText(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                alertDialog.setTitle("Quantity");
                                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    Boolean s=false;
                                        String value = String.valueOf(input.getText());
                                        for (int i = 0; i < value.length(); i++) {
                                            if ((!Character.isDigit(value.charAt(i))))
                                            s = true;
                                        }
                                        check(s,input.getText().toString(),finalGoce,grocery,quantity);


                                    }
                                });
                                alertDialog.create();
                                alertDialog.show();

                            }
                        });
                        ds.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent b = new Intent(getApplicationContext(), Grocery.class);
                                b.putExtra("Name", name);
                                b.putExtra("listname", listname);
                                startActivity(b);
                            }
                        });
                        ds.create();
                        ds.show();
                        tr[0]++;
                    }
                }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                        });


                    }

  // pass the Open and Close toggle for the drawer layout listener
                    // to toggle the button





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), AddgroceryforList.class);
                add.putExtra("grocery", grocery);
                add.putExtra("Name", name);
                add.putExtra("listname", listname);
                add.putExtra("quantity", quantity);
                startActivity(add);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasereference2 = FirebaseDatabase.getInstance().getReference("Users").child(name);

                if (!c) {

                    databasereference2.addValueEventListener(new ValueEventListener() {
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

                            SharedPreferences sharedPreferences = getSharedPreferences("quantity", 0);
                            String gro = "";
                            String qunat = "";
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            for (int i = 0; i < grocery.size(); i++) {
                                gro = gro + grocery.get(i) + ",";
                                qunat = qunat + quantity.get(i) + ",";
                            }
                            editor.putString("grocery", gro);
                            editor.putString("Quanttity", qunat);
                            editor.commit();
                            System.out.println(sharedPreferences.getAll() + "sssss");
                            Intent b = new Intent(getApplicationContext(), Mainmenu2.class);
                            b.putExtra("Name", name);
                            startActivity(b);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });


                }
            }

        });
    }
    public String check(Boolean s, String qq,String qq2, ArrayList groce,ArrayList quanti) {
        final String[] qq1 = {""};
        if (s) {
            final Boolean[] finals = {s};
            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(Grocery.this);
            EditText input1 = new EditText(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input1.setLayoutParams(lp);
            alertDialog1.setView(input1);
            alertDialog1.setTitle("Please input correct Quantity");
            alertDialog1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finals[0] = false;
                    String value = String.valueOf(input1.getText());
                    for (int i = 0; i < value.length(); i++) {
                        if ((!Character.isDigit(value.charAt(i))))
                            finals[0] = true;
                    }
                    qq1[0] = check(finals[0], input1.getText().toString(),qq2,groce,quanti);
                }

            });

            alertDialog1.create();
            alertDialog1.show();
            return qq1[0];
        } else {
            quanti.add(qq);
            groce.add(qq2);
            System.out.println(groce.size());
            databaseReference.child(name).child("list").setValue(groce);
            databaseReference.child(name).child("quantity").setValue(quanti);
            Intent b = new Intent(getApplicationContext(), Grocery.class);
            b.putExtra("Name", name);
            startActivity(b);

            return qq;
        }
    }
}