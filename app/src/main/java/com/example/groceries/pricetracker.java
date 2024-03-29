package com.example.groceries;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.security.cert.CertPathBuilder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class pricetracker extends AppCompatActivity {

    ImageView add;
    Button save;
    String name;
    LinearLayout list;
    ArrayList<String> grocery = new ArrayList<String>();
    ArrayList<String> groceryActualPrice = new ArrayList<String>();
    double econsave;
    double giant;
    double lotus;
    DatabaseReference databaseReference;
    DatabaseReference databasereference2;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<String> market = new ArrayList<>();
    ArrayList<String> pricee= new ArrayList<>();
    ArrayList<String> priceee= new ArrayList<>();
    ArrayList<String> groceryActualPrice1= new ArrayList<>();
    ArrayList<String> groce= new ArrayList<>();
    ArrayList<String> market1= new ArrayList<>();
    String value;
        @Override
        protected  void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pricetracker);
            add = findViewById(R.id.add1);
            save = findViewById(R.id.save1w);
            getSupportActionBar().setTitle("Price Tracker");

            Intent b = getIntent();
            name = b.getStringExtra("Name");
            DatabaseReference databasereference4 = FirebaseDatabase.getInstance().getReference("Users").child(name);
            DatabaseReference databasereference3 = FirebaseDatabase.getInstance().getReference("Groceries");

            databasereference4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    groce=new ArrayList<>();
                    priceee=new ArrayList<>();
                    market1= new ArrayList<>();
                    groceryActualPrice1= new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals("pricetracker")) {
                            for (DataSnapshot preSnapshot : postSnapshot.child("name").getChildren()) {
                                groce.add(preSnapshot.getValue().toString());
                            }
                            for (DataSnapshot preSnapshot : postSnapshot.child("price").getChildren()) {
                                priceee.add(preSnapshot.getValue().toString());
                            }
                            for (DataSnapshot preSnapshot : postSnapshot.child("market").getChildren()) {
                                market1.add(preSnapshot.getValue().toString());


                            }
                        }
                    }
                    databasereference3.addValueEventListener(new ValueEventListener() {
                        @SuppressLint("WrongConstant")
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

                                else if (market1.get(i).equals("Giant")) {
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

                                String a = priceee.get(i);
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
                                    Notification.Builder builder = new Notification.Builder(getApplicationContext())// 设置PendingIntent
                                            .setSmallIcon(R.drawable.shopping)
                                            .setContentTitle(getResources().getString(R.string.app_name))
                                            .setContentText("Hi!! The price of grocery reach what you set!!")
                                            .setWhen(System.currentTimeMillis());
                                    builder.setOngoing(false);





                                    builder.setPriority(NotificationCompat.PRIORITY_LOW);
                                    builder.setVisibility(Notification.VISIBILITY_SECRET);
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                                        NotificationChannel notificationChannel = new NotificationChannel("1", "channel", NotificationManager.IMPORTANCE_MIN);
                                        notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
                                        notificationChannel.setShowBadge(false);//是否显示角标
                                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        manager.createNotificationChannel(notificationChannel);
                                        builder.setChannelId(String.valueOf(1));
                                        Notification notification = builder.build(); // 获取构建好的Notification
                                        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
                                        manager.notify(1,notification);

                                    }
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
            databasereference2 = FirebaseDatabase.getInstance().getReference("Users").child(name);
            databaseReference = FirebaseDatabase.getInstance().getReference("Groceries");
            databasereference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    grocery= new ArrayList<>();
                    pricee= new ArrayList<>();
                    market= new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals("pricetracker")) {
                            for (DataSnapshot preSnapshot : postSnapshot.child("name").getChildren()) {
                                grocery.add(preSnapshot.getValue().toString());
                            }
                            for (DataSnapshot preSnapshot : postSnapshot.child("price").getChildren()) {
                                pricee.add(preSnapshot.getValue().toString());
                            }
                            for (DataSnapshot preSnapshot : postSnapshot.child("market").getChildren()) {
                                market.add(preSnapshot.getValue().toString());
                            }

                        }
                    }
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (int i = 0; i < grocery.size(); i++) {
                                double econsave=0;
                                if(snapshot.child(grocery.get(i)).child("Econsave").getValue().toString().isEmpty()){
                                    econsave=-1;
                                }
                                else {


                                    econsave = Double.parseDouble(snapshot.child(grocery.get(i)).child("Econsave").getValue().toString());
                                }
                                double lotus=0;
                                if(snapshot.child(grocery.get(i)).child("Lotus").getValue().toString().isEmpty()){
                                    lotus=-1;
                                }
                                else {
                                    lotus = Double.parseDouble(snapshot.child(grocery.get(i)).child("Lotus").getValue().toString());
                                }
                                double giant=0;
                                if(snapshot.child(grocery.get(i)).child("Giant").getValue().toString().isEmpty()){
                                    giant=-1;
                                }
                                else {
                                    giant = Double.parseDouble(snapshot.child(grocery.get(i)).child("Giant").getValue().toString());
                                }
                                double[] dd = {econsave, lotus, giant};

                                if (market.get(i).equals("Econsave"))
                                    if(String.valueOf(dd[0]).equals("-1.0")){
                                        groceryActualPrice.add("Not available");
                                    }
                                    else{
                                        DecimalFormat dd1 = new DecimalFormat("0.00");
                                        groceryActualPrice.add("RM"+String.valueOf(dd1.format(dd[0])));

                                    }

                                else if (market.get(i).equals("Giant")) {
                                    if (String.valueOf(dd[2]).equals("-1.0")) {
                                        groceryActualPrice.add("Not available");
                                    } else {
                                        DecimalFormat dd1 = new DecimalFormat("0.00");
                                        groceryActualPrice.add("RM"+String.valueOf(dd1.format(dd[2])));

                                    }
                                }
                                else {
                                    if(String.valueOf(dd[1]).equals("-1.0")){
                                        groceryActualPrice.add("Not available");
                                    }
                                    else{
                                        DecimalFormat dd2 = new DecimalFormat("0.00");
                                        groceryActualPrice.add("RM"+String.valueOf(dd2.format(dd[2])));

                                    }
                                }

                            }
                            for (int i = 0; i < grocery.size(); i++) {
                                list = findViewById(R.id.pricetracker);
                                int index = i;
                                TextView grocer = new TextView(getApplicationContext());
                                grocer.setText(grocery.get(i));
                                grocer.setMaxWidth(350);
                                grocer.setTextSize(16);;

                                DecimalFormat dd = new DecimalFormat("0.00");


                                grocer.setPadding(30,1,20,1);
                                TextView groceractualprice = new TextView(getApplicationContext());
                                groceractualprice.setText("Price Tracked:"+groceryActualPrice.get(i));
                                TextView grocerpredictprice = new TextView(getApplicationContext());
                                grocerpredictprice.setText("Price Set:RM"+dd.format(Double.parseDouble(pricee.get(i))));
                                Button delete1 = new Button(getApplicationContext());
                                delete1.setText("Delete ");
                                Button change = new Button(getApplicationContext());
                                change.setText("Change Price");
                                LinearLayout ll = new LinearLayout(getApplicationContext());
                                ll.setOrientation(LinearLayout.HORIZONTAL);
                                LinearLayout ll1 = new LinearLayout(getApplicationContext());
                                ll1.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout ll2 = new LinearLayout(getApplicationContext());
                                ll2.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout ll3 = new LinearLayout(getApplicationContext());
                                ll3.setOrientation(LinearLayout.VERTICAL);
                                ll1.addView(grocer);
                                ll1.setPadding(20,0,10,0);
                                ll1.setMinimumWidth(500);

                                ll1.addView(delete1);
                                ll2.addView(groceractualprice);
                                ll2.addView(grocerpredictprice);
                                ll2.addView(change);
                                ll3.addView(ll2);

                                ll.addView(ll1);
                                ll.addView(ll3);
                                View aa = new View(getApplicationContext());
                                aa.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                aa.setBackgroundColor(Color.BLACK);
                                delete1.setId(i);
                                change.setId(i);
                                change.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(pricetracker.this);
                                        EditText input = new EditText(getApplicationContext());
                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                        input.setLayoutParams(lp);
                                        alertDialog1.setView(input);
                                        alertDialog1.setTitle("Price that you want to track");
                                        alertDialog1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                value = String.valueOf(input.getText());
                                                Boolean[] finals={false};
                                                for (int i = 0; i < value.length(); i++) {
                                                    if ((!Character.isDigit(value.charAt(0)) && !Character.isDigit(value.charAt(i))||(( !(value.charAt(0) != '.'))&& (value.charAt(i) == '.'))))
                                                        finals[0] = true;
                                                }
                                                if(value.isEmpty())
                                                    finals[0]=true;
                                                int i= change.getId();
                                                check(finals[0],value,pricee,i);
                                            }
                                        });
                                        alertDialog1.create();
                                        alertDialog1.show();
                                    }
                                });
                                delete1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int n =delete1.getId();
                                        System.out.println(n);
                                        grocery.remove(n);
                                        groceryActualPrice.remove(n);
                                        pricee.remove(n);
                                        market.remove(n);
                                        databasereference2.child("pricetracker").child("name").setValue(grocery);
                                        databasereference2.child("pricetracker").child("price").setValue(pricee);
                                        databasereference2.child("pricetracker").child("market").setValue(market);

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        name = b.getStringExtra("Name");
                                        Intent b = new Intent(getApplicationContext(), pricetracker.class);
                                        b.putExtra("Name", name);
                                        b.putExtra("price", pricee);
                                        startActivity(b);
                                    }
                                });
                                list.addView(ll);
                                list.addView(aa);
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


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add = new Intent(getApplicationContext(), pricetracker_add.class);
                    add.putExtra("grocery", grocery);
                    add.putExtra("Name", name);
                    add.putExtra("price", pricee);
                    startActivity(add);

                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent a= new Intent(getApplicationContext(),Mainmenu2.class);
                        a.putExtra("Name",name);
                        startActivity(a);
                        }




            });
        }



    public String check(Boolean s, String qq, ArrayList pricee,int ia) {
        final String[] qq1 = {""};
        if (s) {
            final Boolean[] finals = {s};
            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(pricetracker.this);
            EditText input1 = new EditText(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input1.setLayoutParams(lp);
            alertDialog1.setView(input1);
            alertDialog1.setTitle("Please input correct price");
            alertDialog1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finals[0] = false;
                    String value = String.valueOf(input1.getText());
                    for (int i = 0; i < value.length(); i++) {
                        if ((!Character.isDigit(value.charAt(0)) && !Character.isDigit(value.charAt(i))||(( !(value.charAt(0) != '.'))&& (value.charAt(i) == '.'))))
                            finals[0] = true;
                    }
                    qq1[0] = check(finals[0], input1.getText().toString(),pricee,ia);
                }

            });

            alertDialog1.create();
            alertDialog1.show();
            return qq1[0];
        } else {
            value=qq;
            pricee.set(ia,value);
            databasereference2.child("pricetracker").child("price").setValue(pricee);
            name = getIntent().getStringExtra("Name");
            Intent b = new Intent(getApplicationContext(), pricetracker.class);
            b.putExtra("Name", name);
            b.putExtra("price", pricee);
            startActivity(b);

            return qq;
        }
    }

}

