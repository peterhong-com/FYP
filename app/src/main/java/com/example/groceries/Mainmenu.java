package com.example.groceries;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mainmenu extends AppCompatActivity {
    ArrayList<String> showproduct = new ArrayList<>();
    ArrayList<TextView> text1;
    SearchView searchView;
    double[] mi={0,0,0};
    String[] groc = {"100Plus Isotonic Zero Sugar 1500ml",
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
            "Munchy's Lexus Chocolate Cream Sandwich 418gm",
            "Munchy's Lexus Peanut Butter Sandwich 418gm",
            "Munchy's Lexus Sandwich Himalaya Salt Chocolate Cream 418gm",
            "Munchy's Lexus Sandwich Himalaya Salt Vanilla Cream 418gm",
            "Munchy's Wheat Crackers 276gm",
            "Oreo Chocolate Creme 120gm",
            "Oreo Double Stuff 131gm",
            "Oreo Peanut Butter & Chocolate Creme 120gm",
            "Oreo Regular 120gm",
            "Sprite 1500ml",
            "Spritzer Mineral Water 550ml",
            "Tao Kae Noi Crispy Seaweed Hot & Spicy 32gm",
            "Tao Kae Noi Crispy Seaweed Original 32gm",
            "Yeo's Chrysanthemum Tea 350ml",
            "Yeo's Lychee Tea 350ml",
    };
    ArrayList<String> grocery = new ArrayList<String>(Arrays.asList(groc));
    ArrayList<String> groce = new ArrayList<>();
    ArrayList<String> groceryActualPrice1=new ArrayList<>();
    Query databasereference3;
    DatabaseReference databasereference4;
    ArrayList<String> pricee = new ArrayList<>();
    ArrayList<String> groceryActualPrice = new ArrayList<>();
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
    double econsave;
    double giant;
    double lotus;
    double d1=0;
    double d2=0;
    double d3=0;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        linear1 = findViewById(R.id.layout1);
        linear2 = findViewById(R.id.layout2);
        text1 = new ArrayList<TextView>();

        name = getIntent().getStringExtra("Name");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, grocery);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        ImageButton search = findViewById(R.id.search1);
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
                                ImageView rImage = new ImageView(Mainmenu.this);
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
                                                gro = String.valueOf(i.getText()).toString();
                                            databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(String.valueOf(gro).trim());
                                            databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot snapshot) {
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    if(snapshot.child("Econsave").getValue().toString().isEmpty()){
                                                        d1=0;
                                                    }
                                                    else
                                                        d1=Double.parseDouble(snapshot.child("Econsave").getValue().toString());
                                                    if(snapshot.child("Lotus").getValue().toString().isEmpty()){
                                                        d2=0;
                                                    }
                                                    else
                                                        d2=Double.parseDouble(snapshot.child("Lotus").getValue().toString());
                                                    if(snapshot.child("Giant").getValue().toString().isEmpty()){
                                                        d3=0;
                                                    }
                                                    else
                                                        d3=Double.parseDouble(snapshot.child("Giant").getValue().toString());

                                                    double[] dd = {d1, d2,d3}; double[] price1 = sortPrice(dd);
                                                    String[] mar1 = sortMarket(dd);
                                                    String[] ss = {mar1[0] + ":RM" + df.format(price1[0]) + "", mar1[1] + ":   RM" + df.format(price1[1]) + "", mar1[2] + ":   RM" + df.format(price1[2]) + ""};
                                                    Intent a = new Intent(getApplicationContext(), PriceList.class);
                                                    a.putExtra("price", ss);
                                                    a.putExtra("groceryname",i.getText().toString());
                                                    startActivity(a);

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError error) {

                                                }
                                            });

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


        for (int j = 0; j < 3; j++) {
            int s = (int) (Math.random() * (grocery.size()));
            System.out.println(s
            );
            String gro = "";
            while(showproduct.contains(grocery.get(s))){
                s = (int) (Math.random() * (grocery.size()));
            }
                gro = grocery.get(s);
            showproduct.add(grocery.get(s));
            databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(gro.trim());
            int finalS = s;
            databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ImageView rImage = new ImageView(getApplicationContext());
                        Picasso.get().load(String.valueOf(snapshot.child("image").getValue())).resize(160, 160).into(rImage);
                        rImage.setPadding(10, 0, 0, 0);
                        linear1.addView(rImage);
                        TextView text = new TextView(getApplicationContext());
                        text.setText(grocery.get(finalS));
                        text1.add(text);
                        for (TextView i : text1) {
                            i.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String gro = "";
                                    if (String.valueOf(i.getText()).contains(".")) {
                                        gro = String.valueOf(i.getText()).replace(".", "%2E");
                                    } else
                                        gro = String.valueOf(i.getText());
                                    databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(String.valueOf(gro.trim()));
                                    databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            DecimalFormat df = new DecimalFormat("0.00");
                                            double d1=0;
                                            double d2=0;
                                            double d3=0;
                                            if(snapshot.child("Econsave").getValue().toString().isEmpty()){
                                                d1=0;
                                            }
                                            else
                                                d1=Double.parseDouble(snapshot.child("Econsave").getValue().toString());
                                            if(snapshot.child("Lotus").getValue().toString().isEmpty()){
                                                d2=0;
                                            }
                                            else
                                                d2=Double.parseDouble(snapshot.child("Lotus").getValue().toString());
                                            if(snapshot.child("Giant").getValue().toString().isEmpty()){
                                                d3=0;
                                            }
                                            else
                                                d3=Double.parseDouble(snapshot.child("Giant").getValue().toString());
                                            double[] dd = {d1, d2,d3};
                                            double[] price1 = sortPrice(dd);
                                            String[] mar1 = sortMarket(dd);


                                            String[] ss = {mar1[0] + ":RM" + df.format(price1[0]) + "", mar1[1] + ":   RM" + df.format(price1[1]) + "", mar1[2] + ":   RM" + df.format(price1[2]) + ""};
                                            Intent a = new Intent(getApplicationContext(), PriceList.class);
                                            a.putExtra("price", ss);
                                            a.putExtra("groceryname",i.getText().toString());
                                            startActivity(a);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {

                                        }
                                    });

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
        for (int k = 0; k < 3; k++) {
            int b = (int) (Math.random() * (grocery.size()));
            System.out.println(b
            );
            String gro = "";
            while(showproduct.contains(grocery.get(b))){
                b = (int) (Math.random() * (grocery.size()));
            }
                gro = grocery.get(b);
            showproduct.add(grocery.get(b));
            databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(gro);
            int finalB = b;
            databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ImageView rImage = new ImageView(getApplicationContext());
                        Picasso.get().load(String.valueOf(snapshot.child("image").getValue())).resize(160, 160).into(rImage);
                        rImage.setPadding(-100, 0, 0, 0);
                        linear2.addView(rImage);

                        TextView text = new TextView(getApplicationContext());
                        int s= finalB;
                        text.setText(grocery.get(s));
                        text1.add(text);
                        for (TextView i : text1) {
                            i.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String gro = "";

                                        gro = String.valueOf(i.getText());
                                    databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(String.valueOf(gro));
                                    databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            DecimalFormat df = new DecimalFormat("0.00");
                                            double d1=0;
                                            double d2=0;
                                            double d3=0;
                                            if(snapshot.child("Econsave").getValue().toString().isEmpty()){
                                                d1=0;
                                            }
                                            else
                                                d1=Double.parseDouble(snapshot.child("Econsave").getValue().toString());
                                            if(snapshot.child("Lotus").getValue().toString().isEmpty()){
                                                d2=0;
                                            }
                                            else
                                                d2=Double.parseDouble(snapshot.child("Lotus").getValue().toString());
                                            if(snapshot.child("Giant").getValue().toString().isEmpty()){
                                                d3=0;
                                            }
                                            else
                                                d3=Double.parseDouble(snapshot.child("Giant").getValue().toString());
                                            double[] dd = {d1, d2,d3};
                                            double[] price1 = sortPrice(dd);
                                            String[] mar1 = sortMarket(dd);


                                            String[] ss = {mar1[0] + ":RM" + df.format(price1[0]) + "", mar1[1] + ":   RM" + df.format(price1[1]) + "", mar1[2] + ":   RM" + df.format(price1[2]) + ""};
                                            Intent a = new Intent(getApplicationContext(), PriceList.class);
                                            a.putExtra("price", ss);
                                            a.putExtra("groceryname",String.valueOf(i.getText()));
                                            startActivity(a);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {

                                        }
                                    });

                                }
                            });
                        }
                        linear2.addView(text);

                    }

                }


                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

        }

        drawerLayout = findViewById(R.id.menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_calculate, R.string.nav_LogOut) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitleSection);
                ActivityCompat.invalidateOptionsMenu(Mainmenu.this);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.app_name);
                ActivityCompat.invalidateOptionsMenu(Mainmenu.this);
            }
        };
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nvdrawer = (NavigationView) findViewById(R.id.menu1);
        setupDrawerContent(nvdrawer);
        getSupportActionBar().setHomeButtonEnabled(true);

    }







    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        if (item.getItemId() == R.id.nav_logout) {

                            // Highlight the selected item has been done by NavigationView
                            item.setChecked(true);
                            drawerLayout.closeDrawers();
                            return true;
                        } else if (item.getItemId() == R.id.nav_account) {

                            market.add("Econsave");
                            market.add("Lotus");
                            market.add("Giant");
                            Intent a = new Intent(getApplicationContext(), Calculation.class);
                            a.putExtra("market", market);
                            a.putExtra("missing",mi);
                            a.putExtra("Name", name);
                            startActivity(a);
                            return  true;

                        }
                        else if(item.getItemId()==R.id.nav_list)
                        {
                            Intent a = new Intent(getApplicationContext(), Grocery.class);
                            a.putExtra("Name", name);
                            startActivity(a);
                        }
                        else if(item.getItemId()==R.id.nav_pricelist)
                        {
                            Intent a = new Intent(getApplicationContext(), pricetracker.class);
                            a.putExtra("Name", name);
                            startActivity(a);
                        }
                return true;
                    }

                });
    }
    public void startService() {
        Intent serviceIntent = new Intent(this, Background.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        serviceIntent.putExtra("Name", name);
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService() {
        Intent serviceIntent = new Intent(this, Background.class);
        stopService(serviceIntent);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

                return true;


        }
        return super.onOptionsItemSelected(item);
    }
    public String[] sortMarket(double[] dd){
        String[] market={"","",""};
        if (dd[0] <= dd[2] && dd[0] <= dd[1]) {

                market[0] = "Econsave";

                if (dd[2] < dd[1])
                    market[2] = "Lotus";

                    market[1] = "Giant";

             if (dd[1] <= dd[2]){
                market[2] = "Giant";

                market[1] = "Lotus";
            }

        } else if (dd[1] <= dd[2]&& dd[1]<=dd[0]) {

            if(dd[1] < dd[0])
                market[0] ="Lotus";

                if (dd[2] < dd[0])
                    market[1] = "Giant";
                    market[2] = "Econsave";

                 if (dd[0] <= dd[2]) {
                    market[2] = "Giant";
                    market[1] = "Econsave";
                }


        }else if (dd[2] <= dd[1] && dd[2]<=dd[0]) {

            if(dd[2]<dd[0]) {
                market[0] = "Giant";

                if (dd[0] < dd[1])
                    market[1] = "Econsave";
                market[2] = "Lotus";

                if (dd[1] <= dd[0]) {
                    market[1] = "Lotus";

                    market[2] = "Econsave";

                }
            }

        }

        else
            return market;
        return market;
    }
    public double[] sortPrice(double[] dd){
        double[] market={0,0,0};
        if (dd[0] <= dd[2] && dd[0] <= dd[1]) {

            market[0] = dd[0];

            if (dd[2] < dd[1])
                market[2] = dd[1];

                market[1] = dd[2];

            if (dd[1] <= dd[2]){
                market[2] =dd[2];

                market[1] = dd[1];
            }

        } else if (dd[1] <= dd[2] && dd[1] <= dd[0]) {

            market[0] =dd[1];

            if (dd[2] < dd[0])
                market[1] = dd[2];
                market[2] = dd[0];

            if (dd[0] <= dd[2]) {
                market[2] = dd[2];
                market[1] = dd[0];
            }

        } else if (dd[2] <= dd[1] && dd[2] <= dd[0]) {

            market[0] = dd[2];

            if (dd[0] < dd[1])
                market[1] = dd[0];
                market[2] = dd[1];

            if (dd[1] <= dd[0]) {
                market[1] = dd[1];

                market[2] = dd[0];

            }
        } else
            return market;
        return market;
    }
}