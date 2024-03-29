package com.example.groceries;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;

public class Addgrocery extends AppCompatActivity {
    ArrayList<TextView> text1;
    SearchView searchView;
    String[] groc = {  "100Plus Isotonic Zero Sugar 1500ml",
            "100Plus Regular Isotonic 1500ml",
            "Coca-Cola Rasa Asli 1500ml","Dutch Lady Pasteurised Chocolate Milk 1lit",
            "Dutch Lady UHT Full Cream Milk 1lit",

            "Gardenia Breakthru Whole Wheat Bread 400gm",
            "Gardenia Original Classic 400gm",
            "Jack N Jill Roller Coaster BBQ 60gm",
            "Jack N Jill Roller Coaster Cheese 60gm",
            "Lactel Bliss Low Fat Yogurt Drink Apple Kiwi 700ml",
            "Lactel Bliss Low Fat Yogurt Drink Strawberry 700ml",
            "Lay's Stax Potato Crisps BBQ 135gm",
            "Lay's Stax Potato Crisps Extra Cheese 135gm",
            "Lay's Stax Potato Crisps Original 135gm",
            "Lay's Stax Potato Crisps Sour Cream & Onion 135gm",
            "M&M's Milk Chocolate Fun Size 176gm",
            "M&M's Peanut Fun Size 176gm",
            "Maggi 2 minutes Noodle Chicken 5x77gm",
            "Maggi 2 minutes Noodle Curry 5x79gm",
            "Maggi Pedas Giler Mi Goreng Tom Yummz 5x76gm",
            "Mamee Monster Noodle Snack BBQ 8x25gm",
            "Mamee Monster Noodle Snack Chicken 8x25gm",
            "Mamee Monster Noodle Snack Spicy 8x25gm",
            "Mamee Premium Mi Tarik Duck 5x76gm",
            "Marigold Low Fat Yogurt Natural 130gm",
            "Marigold Low Fat Yogurt Peach 130gm",
            "Marigold Low Fat Yogurt Strawberry 130gm",
            "Marigold Peel Fresh Orange Juice 1lit",
            "Marigold Peel Fresh Power Juice Mixed Fruit Mangosteen Juice 1lit",
            "Marigold Peel Fresh Power Juice Mixed Kale & Power Berries Juice 1lit",
            "Marigold Peel Fresh Power Juice Mixed Kale & Power Veggies Juice 1lit",
            "Marigold Peel Fresh Power Juice Mixed Kale & Veggies Juice 1lit",
            "Massimo Sandwich Wheat Germ 400gm",
            "Massimo White Sandwich Loaf 400gm",
            "Mister Potato Crisps BBQ 125gm",
            "Mister Potato Crisps Honey Cheese 125gm",
            "Mister Potato Crisps Hot and Spicy 125gm",
            "Mister Potato Crisps Original 125gm",
            "Mister Potato Crisps Sour Cream & Onion 125gm",
            "Mister Potato Crisps Sweet Potato 118gm",
            "Mister Potato Crisps Sweet Potato with Sweet Corn 118gm",
            "Mister Potato Crisps Tomato 125gm",
        "Munchy's Lexus Chocolate Coated Cream 360gm",
            "Munchy's Cream Crackers 300gm",
            "Munchy's Lexus Chocolate Coated, Cream 360gm",
            "Munchy's Lexus Chocolate Cream Sandwich 418gm",
            "Munchy's Lexus Peanut Butter Sandwich 418gm",
            "Munchy's Lexus Sandwich Himalaya Salt Chocolate Cream 418gm",
            "Munchy's Lexus Sandwich Himalaya Salt Vanilla Cream 418gm",
            "Munchy's Wheat Crackers 276gm",
            "Oreo Chocolate Creme 120gm",
            "Oreo Double Stuff 131gm",
            "Oreo Peanut Butter & Chocolate Creme 120gm",
            "Oreo Regular 120gm",
            "Sprite 1.5lit",
            "Spritzer Mineral Water 550ml",
            "Tao Kae Noi Crispy Seaweed Hot & Spicy 32gm",
            "Tao Kae Noi Crispy Seaweed Original 32gm",
            "Yeo's Chrysanthemum Tea 350ml",
            "Yeo's Lychee Tea 350ml",
    };
    ArrayList<String> grocery = new ArrayList<String>(Arrays.asList(groc));
    ArrayAdapter<String> adapter;
    Query databasereference1;
    DrawerLayout drawerLayout;
    LinearLayout linear1;
    LinearLayout linear2;
    ArrayList<String> lowest1 = new ArrayList<>();
    ArrayList<String> listgrocery = new ArrayList<>();
    ArrayList<String> listquantity = new ArrayList<>();
    ArrayList<String> grocery1 = new ArrayList<String>();
    ArrayList<String> Market1 = new ArrayList<>();
    ArrayList<String> markw = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> EconsavelowestPricelist = new ArrayList<>();
    ArrayList<String> LotuslowestPricelist = new ArrayList<>();
    ArrayList<String> GiantlowestPricelist = new ArrayList<>();
    double lowestcon = 0;
    double lowestgiant = 0;
    double lowesttec = 0;
    Boolean added = true;
    String name;
    Boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgrocery);
        linear1 = findViewById(R.id.layout3);
        linear2 = findViewById(R.id.layout4);
        text1 = new ArrayList<TextView>();
        getSupportActionBar().setTitle("Add Grocery");
        Intent c = getIntent();

        stop = getIntent().getBooleanExtra("stop", true);
        grocery1 = c.getStringArrayListExtra("grocery");
        name = getIntent().getStringExtra("Name");

        quantity = c.getStringArrayListExtra("quant");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, grocery);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        ImageButton search = findViewById(R.id.search2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = actv.getText().toString();
                if (grocery.contains(query)) {

                    adapter.getFilter().filter(query);
                    String gro = "";

                    gro = query;
                    databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(gro.trim());
                    databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                ImageView rImage = new ImageView(Addgrocery.this);
                                Picasso.get().load(String.valueOf(snapshot.child("image").getValue())).resize(160, 160).into(rImage);
                                rImage.setPadding(10, 0, 0, 0);
                                linear1.removeAllViews();
                                linear2.removeAllViews();

                                linear1.addView(rImage);
                                TextView text = new TextView(getApplicationContext());

                                text.setText(query);
                                text1.add(text);
                                for (TextView i : text1) {
                                    i.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            String gro = "";

                                            if (String.valueOf(i.getText()).contains(".")) {
                                                gro = String.valueOf(i.getText().toString()).replace(".", "%2E");
                                            } else
                                                gro = String.valueOf(i.getText()).toString();

                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Addgrocery.this);
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
                                                    final Boolean[] correct = {false};
                                                    final String[] value = {String.valueOf(input.getText()).toString()};
                                                    grocery1.add(i.getText().toString());
                                                    for (int i = 0; i < value[0].length(); i++) {
                                                        if (!Character.isDigit(value[0].charAt(i)) || !(value[0].charAt(i) != '.'))
                                                            correct[0] = true;
                                                    }

                                                    value[0] = check(correct[0], value[0],grocery1);


                                                }
                                            });
                                            alertDialog.create();
                                            alertDialog.show();
                                        }


                                    });
                                }
                                linear1.addView(text);

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
            }
        });


    }

    public String check(Boolean s, String qq, ArrayList grocery2) {
        final String[] qq1 = {""};
        if (s) {
            final Boolean[] finals = {s};
            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(Addgrocery.this);
            EditText input1 = new EditText(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input1.setLayoutParams(lp);
            alertDialog1.setView(input1);
            alertDialog1.setTitle("Please input correct quantity");
            alertDialog1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finals[0] = false;
                    String value = String.valueOf(input1.getText());
                    for (int i = 0; i < value.length(); i++) {
                        if (!Character.isDigit(value.charAt(i)))
                            finals[0] = true;
                    }
                    qq1[0] = check(finals[0], input1.getText().toString(),grocery2);
                }

            });

            alertDialog1.create();
            alertDialog1.show();
            return qq1[0];
        } else {
            Intent a = new Intent(getApplicationContext(), Calculation.class);

            a.putExtra("grocery", grocery2);
            ;
            quantity.add(String.valueOf(qq));
            a.putExtra("quant", quantity);
            a.putExtra("Name", name);
            a.putExtra("stop", stop);
            startActivity(a);

            return qq;
        }
    }
}
