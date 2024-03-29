package com.example.groceries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculation extends AppCompatActivity {
    ImageView add;
    Button calculate;
    String name;
    LinearLayout list;
    ArrayList<String> findMar = new ArrayList<String>();
    ;
    ArrayList<String> grocery = new ArrayList<>();
    ;
    ArrayList<String> lowestPrice = new ArrayList<String>();
    ArrayList<String> quant = new ArrayList<>();
    ArrayList<String> grocery1 = new ArrayList<>();
    ArrayList<String> quant1 = new ArrayList<>();
    ArrayList<String> EconsavelowestPricelist = new ArrayList<>();
    ArrayList<String> LotuslowestPricelist = new ArrayList<>();
    ArrayList<String> GiantlowestPricelist = new ArrayList<>();
    double EconsavelowestPrice=0;
    double LotuslowestPrice=0;
    double GiantlowestPrice=0;
    double[] mi = {0, 0, 0};
    Query databaseReference;
    DatabaseReference databasereference1;
    ArrayList<String> mark = new ArrayList<>();
    ArrayList<Button> aa = new ArrayList<>();
    Boolean reload = false;
    Boolean added = true;
    String name1;
    Boolean stop = true;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_list);
        mark.add("Econsave");
        mark.add("Lotus");
        mark.add("Giant");
        double[] dd;
        stop=getIntent().getBooleanExtra("stop",true);
        dd = getIntent().getDoubleArrayExtra("price1");
        name1 = getIntent().getStringExtra("Name");
        added = getIntent().getBooleanExtra("add", true);
        add = findViewById(R.id.imageView);
        calculate = findViewById(R.id.calculate);
        mi = getIntent().getDoubleArrayExtra("missing");
        Intent b = getIntent();
        name = b.getStringExtra("name");
        grocery = b.getStringArrayListExtra("grocery");
        lowestPrice = b.getStringArrayListExtra("price");
        quant = b.getStringArrayListExtra("quant");
        if (grocery == null) {
            grocery=new ArrayList<>();

            quant=new ArrayList<>();
        }
        if(lowestPrice==null){
            lowestPrice=new ArrayList<>();
        }
        getSupportActionBar().setTitle("Calculation List");
        reload = b.getBooleanExtra("reload", false);
        SharedPreferences sas = getSharedPreferences("quantity", 0);
System.out.println(sas.getAll());
        String lis1t = sas.getString("grocery", "");
        String listquantity = sas.getString("Quanttity", "");
        String[] list11 = lis1t.split(",");
        String[] list2 = listquantity.split(",");
        if(stop) {

            if (!(list11.length == 0) && list11[0] != "") {
                for (int i = 0; i < list11.length; i++) {
                    grocery.add(list11[i]);
                    quant.add(list2[i]);
                }
            }
        }
        index=0;
        double[] mi={0,0,0};
        if (reload) {

            if (grocery.size() > 0) {
                for (int i = 0; i < grocery.size(); i++) {

                    databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(grocery.get(i));
                    databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {


                            double Econsave;
                            double Lotus;
                            double Giant;
                            double Econsave1;
                            double Lotus1;
                            double Giant1;
                            if (!String.valueOf(snapshot.child("Econsave").getValue()).isEmpty()) {
                                Econsave = Double.valueOf(String.valueOf(snapshot.child("Econsave").getValue()));
                                Econsave1 = Double.valueOf(String.valueOf(snapshot.child("Econsave").getValue()));
                            } else {
                                Econsave = 100000;
                                mi[0]++;
                                Econsave1 = 0;
                            }
                            if (!String.valueOf(snapshot.child("Lotus").getValue()).isEmpty()) {
                                Lotus = Double.parseDouble(String.valueOf(snapshot.child("Lotus").getValue()));
                                Lotus1 = Double.parseDouble(String.valueOf(snapshot.child("Lotus").getValue()));
                            } else {
                                Lotus = 100000;
                                Lotus1 = 0;
                                mi[1]++;
                            }
                            if (String.valueOf(snapshot.child("Giant").getValue()).isEmpty()) {
                                Giant = 100000;
                                Giant1 = 0;
                                mi[2]++;
                            } else {
                                Giant = Double.parseDouble(String.valueOf(snapshot.child("Giant").getValue()));
                                Giant1 = Double.parseDouble(String.valueOf(snapshot.child("Giant").getValue()));
                            }
                            double[] ddd = {Econsave, Lotus, Giant};
                            EconsavelowestPricelist.add(String.valueOf(Econsave1));
                            GiantlowestPricelist.add(String.valueOf(Giant1));
                            LotuslowestPricelist.add(String.valueOf(Lotus1));
                            String supermarket = findMarket(ddd, mark);
                            double ss = findPrice(ddd, mark);
                            for (int i = 0; i < 3; i++) {
                                if (ddd[i] == 100000)
                                    ddd[i] = 0;
                            }

                            EconsavelowestPrice = EconsavelowestPrice + ddd[0] * Integer.parseInt(quant.get(index));
                            LotuslowestPrice = LotuslowestPrice + ddd[1] * Integer.parseInt(quant.get(index));
                            GiantlowestPrice = GiantlowestPrice + ddd[2] * Integer.parseInt(quant.get(index));
                            findMar.add(supermarket);
                            System.out.println(lowestPrice.size() + "   " + ss + "   " + quant.size());
                            lowestPrice.add(String.valueOf(ss * Integer.parseInt(quant.get(index))));

                            System.out.println(199);
                            list = findViewById(R.id.list1);

                            LinearLayout ll = new LinearLayout(getApplicationContext());
                            ll.setOrientation(LinearLayout.HORIZONTAL);
                            TextView grocer = new TextView(getApplicationContext());
                            grocer.setText(grocery.get(index));
                            grocer.setMaxWidth(300);
                            TextView grocer1 = new TextView(getApplicationContext());
                            grocer1.setText("Quantity: " + quant.get(index));
                            grocer1.setPadding(20, 0, 0, 0);
                            Button delete1 = new Button(getApplicationContext());
                            delete1.setText("Delete ");
                            delete1.setId(index);
                            aa.add(delete1);
                            ll.addView(grocer);
                            ll.addView(grocer1);
                            ll.addView(delete1);
                            ll.setPadding(10, 10, 10, 10);
                            list.setBackgroundResource(R.drawable.shadow_132534);
                            list.addView(ll);


                            for (Button delete2 : aa) {
                                delete2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        grocery.remove(delete2.getId());
                                        lowestPrice.remove(delete2.getId());
                                        findMar.remove(delete2.getId());
                                        String quantity2 = quant.get(delete2.getId());
                                        quant.remove(delete2.getId());
                                        int quantity1 = Integer.parseInt(quantity2);
                                        EconsavelowestPrice = EconsavelowestPrice - Double.parseDouble(EconsavelowestPricelist.get(delete2.getId())) * quantity1;
                                        LotuslowestPrice = LotuslowestPrice - Double.parseDouble(LotuslowestPricelist.get(delete2.getId())) * quantity1;
                                        GiantlowestPrice = GiantlowestPrice - Double.parseDouble(GiantlowestPricelist.get(delete2.getId())) * quantity1;
                                        double[] dd = {Double.parseDouble(EconsavelowestPricelist.get(delete2.getId())), Double.parseDouble(LotuslowestPricelist.get(delete2.getId())), Double.parseDouble(GiantlowestPricelist.get(delete2.getId()))};
                                        double ss = findPrice(dd, mark);
                                        lowestPrice.remove(String.valueOf(ss * quantity1));
                                        EconsavelowestPricelist.remove(delete2.getId());
                                        LotuslowestPricelist.remove(delete2.getId());
                                        GiantlowestPricelist.remove(delete2.getId());
                                        Intent add = new Intent(getApplicationContext(), Calculation.class);
                                        add.putExtra("grocery", grocery);
                                        add.putExtra("quant", quant);
                                        reload = true;
                                        stop=false;
                                        add.putExtra("reload", reload);
                                        add.putExtra("Name", name1);
                                        add.putExtra("stop",stop);
                                        startActivity(add);
                                    }
                                });
                            }
                            index++;
                        }


                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }
            }
        }



        if(grocery.size()>0&& !reload) {
            for (int i = 0; i < grocery.size(); i++) {

                databasereference1 = FirebaseDatabase.getInstance().getReference("Groceries").child(grocery.get(i));
                databasereference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {


                        double Econsave;
                        double Lotus;
                        double Giant;
                        double Econsave1;
                        double Lotus1;
                        double Giant1;
                        if (!String.valueOf(snapshot.child("Econsave").getValue()).isEmpty()) {
                            Econsave = Double.valueOf(String.valueOf(snapshot.child("Econsave").getValue()));
                            Econsave1 = Double.valueOf(String.valueOf(snapshot.child("Econsave").getValue()));
                        } else {
                            Econsave = 100000;
                            mi[0]++;
                            Econsave1 = 0;
                        }
                        if (!String.valueOf(snapshot.child("Lotus").getValue()).isEmpty()) {
                            Lotus = Double.parseDouble(String.valueOf(snapshot.child("Lotus").getValue()));
                            Lotus1 = Double.parseDouble(String.valueOf(snapshot.child("Lotus").getValue()));
                        } else {
                            Lotus = 100000;
                            Lotus1 = 0;
                            mi[1]++;
                        }
                        if (String.valueOf(snapshot.child("Giant").getValue()).isEmpty()) {
                            Giant = 100000;
                            Giant1 = 0;
                            mi[2]++;
                        } else {
                            Giant = Double.parseDouble(String.valueOf(snapshot.child("Giant").getValue()));
                            Giant1 = Double.parseDouble(String.valueOf(snapshot.child("Giant").getValue()));
                        }
                        double[] ddd = {Econsave, Lotus, Giant};
                        EconsavelowestPricelist.add(String.valueOf(Econsave1));
                        GiantlowestPricelist.add(String.valueOf(Giant1));
                        LotuslowestPricelist.add(String.valueOf(Lotus1));
                        String supermarket = findMarket(ddd, mark);
                        double ss = findPrice(ddd, mark);
                        for (int i = 0; i < 3; i++) {
                            if (ddd[i] == 100000)
                                ddd[i] = 0;
                        }

                        EconsavelowestPrice = EconsavelowestPrice + ddd[0] * Integer.parseInt(quant.get(index));
                        LotuslowestPrice = LotuslowestPrice + ddd[1] * Integer.parseInt(quant.get(index));
                        GiantlowestPrice = GiantlowestPrice + ddd[2] * Integer.parseInt(quant.get(index));
                        findMar.add(supermarket);
System.out.println(lowestPrice.size()+"   "+ss+"   "+quant.size());
                        lowestPrice.add(String.valueOf(ss * Integer.parseInt(quant.get(index))));

                            System.out.println(199);
                            list = findViewById(R.id.list1);

                            LinearLayout ll = new LinearLayout(getApplicationContext());
                            ll.setOrientation(LinearLayout.HORIZONTAL);
                            TextView grocer = new TextView(getApplicationContext());
                            grocer.setText(grocery.get(index));
                            grocer.setMaxWidth(300);
                            TextView grocer1 = new TextView(getApplicationContext());
                            grocer1.setText("Quantity: " + quant.get(index));
                            grocer1.setPadding(20, 0, 0, 0);
                            Button delete1 = new Button(getApplicationContext());
                            delete1.setText("Delete ");
                            delete1.setId(index);
                            aa.add(delete1);
                            ll.addView(grocer);
                            ll.addView(grocer1);
                            ll.addView(delete1);
                            ll.setPadding(10, 10, 10, 10);
                            list.setBackgroundResource(R.drawable.shadow_132534);
                            list.addView(ll);


                        for (Button delete2 : aa) {
                            delete2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    grocery.remove(delete2.getId());
                                    lowestPrice.remove(delete2.getId());
                                    findMar.remove(delete2.getId());
                                    String quantity2 = quant.get(delete2.getId());
                                    quant.remove(delete2.getId());
                                    int quantity1 = Integer.parseInt(quantity2);
                                    EconsavelowestPrice = EconsavelowestPrice - Double.parseDouble(EconsavelowestPricelist.get(delete2.getId())) * quantity1;
                                    LotuslowestPrice = LotuslowestPrice - Double.parseDouble(LotuslowestPricelist.get(delete2.getId())) * quantity1;
                                    GiantlowestPrice = GiantlowestPrice - Double.parseDouble(GiantlowestPricelist.get(delete2.getId())) * quantity1;
                                    double[] dd = {Double.parseDouble(EconsavelowestPricelist.get(delete2.getId())), Double.parseDouble(LotuslowestPricelist.get(delete2.getId())), Double.parseDouble(GiantlowestPricelist.get(delete2.getId()))};
                                    double ss = findPrice(dd, mark);
                                    lowestPrice.remove(String.valueOf(ss * quantity1));
                                    EconsavelowestPricelist.remove(delete2.getId());
                                    LotuslowestPricelist.remove(delete2.getId());
                                    GiantlowestPricelist.remove(delete2.getId());
                                    Intent add = new Intent(getApplicationContext(), Calculation.class);
                                    add.putExtra("grocery", grocery);
                                    add.putExtra("quant", quant);
                                    reload = true;
                                    stop=false;
                                    add.putExtra("reload", reload);
                                    add.putExtra("Name", name1);
                                    add.putExtra("stop",stop);
                                    startActivity(add);
                                }
                            });
                        }
                        index++;
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }

        }
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), ResultList.class);
                double[] price = {EconsavelowestPrice, LotuslowestPrice, GiantlowestPrice};
                String[] mar = sortMarket(price);
                next.putExtra("SortMarket", mar);
                next.putExtra("lowest", price);
                next.putExtra("price", lowestPrice);
                next.putExtra("grocery", grocery);
                next.putExtra("Market", findMar);
                next.putExtra("missing", mi);
                next.putExtra("Name", name1);
                startActivity(next);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), Addgrocery.class);
                add.putExtra("grocery", grocery);
                add.putExtra("stop",false);
                add.putExtra("quant", quant);
                add.putExtra("missing", mi);
                add.putExtra("Name", name1);

                startActivity(add);

            }
});

    }


    public String[] sortMarket(double[] dd){
        String[] market={"","",""};
        if (dd[0] <= dd[2] && dd[0] <= dd[1]) {
            if (mark.contains("Econsave")) {
                market[0] = "Econsave";

                if (dd[2] < dd[1])
                    market[2] = "Lotus";

                market[1] = "Giant";
            }
            if (dd[1] < dd[2]){
                market[2] = "Giant";

            market[1] = "Lotus";
        }

        } else if (dd[1] <= dd[2] && dd[1] <= dd[0]) {
            if (mark.contains("Lotus")) {
                market[0] ="Lotus";

                if (dd[2] < dd[0]) {
                    market[1] = "Giant";
                    market[2] = "Econsave";
                }
                if (dd[0] < dd[2]) {
                    market[2] = "Giant";
                    market[1] = "Econsave";
                }
            }
        } else if (dd[2] <= dd[1] && dd[2] <= dd[0]) {
            if (mark.contains("Giant")) {
                market[0] = "Giant";

                if (dd[0] < dd[1]) {
                    market[1] = "Econsave";
                    market[2] = "Lotus";
                }
                if (dd[1] < dd[0]) {
                    market[1] = "Lotus";

                    market[2] = "Econsave";
                }
            }
        } else
            return market;
        return market;
    }


    public String findMarket(double[] ss, ArrayList<String> mark) {
        String market = "";
        if (ss[0] <= ss[2] && ss[0] <= ss[1]) {
            if (mark.contains("Econsave")) {
                market = "Econsave";
            } else if (mark.contains("Giant")&&mark.contains("Lotus")) {
                if (ss[2] < ss[1])
                    market = "Giant";
                else
                    market= "Lotus";
            } else if (mark.contains("Lotus")) {

                market ="Lotus";
            } else if (mark.contains("Giant")) {
                market = "Giant";
            }  else
                return "";
        } else if (ss[1] <= ss[2] && ss[1] <= ss[0]) {
            if (mark.contains("Lotus")) {
                market = "Lotus";
            } else if (mark.contains("Giant")&&mark.contains("Econsave")) {
                if (ss[2] < ss[0])
                    market = "Giant";
                else
                    market= "Econsave";
            } else if (mark.contains("Econsave")) {

                market ="Econsave";
            } else if (mark.contains("Giant")) {
                market = "Giant";
            }else
                return "";
        } else if (ss[2] <= ss[1] && ss[2] <= ss[0]) {
            if (mark.contains("Giant")) {
                market = "Giant";
            } else if (mark.contains("Econsave")&&mark.contains("Lotus")) {
                if (ss[0] < ss[1])
                    market = "Econsave";
                else
                    market= "Lotus";
            } else if (mark.contains("Lotus")) {

                market ="Lotus";
            } else if (mark.contains("Econsave")) {
                market = "Econsave";
            }else
                return "";
        } else
            return "";
        return market;
    }

    public double findPrice(double[] dd, ArrayList<String> mark) {
        double market = 0;
        if (dd[0] <= dd[2] && dd[0] <= dd[1]) {
            if (mark.contains("Econsave")) {
                market = dd[0];
            }else if (mark.contains("Giant")&&mark.contains("Lotus")) {
                    if (dd[2] < dd[1])
                        market = dd[2];
                    else
                        market= dd[1];
            } else if (mark.contains("Lotus")) {

                    market =dd[1];
            } else if (mark.contains("Giant")) {
                    market = dd[2];
            } else
                return 0;
        } else if (dd[1] <= dd[2] && dd[1] <= dd[0]) {
            if (mark.contains("Lotus")) {
                market =dd[1];
            } else if (mark.contains("Giant")&&mark.contains("Econsave")) {
                if (dd[2] < dd[0])
                    market = dd[2];
                else
                    market= dd[0];
            } else if (mark.contains("Econsave")) {

                market =dd[0];
            } else if (mark.contains("Giant")) {
                market = dd[2];
            } else
                return 0;
        } else if (dd[2] <= dd[1] && dd[2] <= dd[0]) {
            if (mark.contains("Giant")) {
                market = dd[2];
            } else if (mark.contains("Econsave")&&mark.contains("Lotus")) {
                if (dd[0] < dd[1])
                    market = dd[0];
                else
                    market= dd[1];
            } else if (mark.contains("Lotus")) {

                market =dd[1];
            } else if (mark.contains("Econsave")) {
                market = dd[0];
            }else
                return 0;
        } else
            return 0;
        return market;
    }
}