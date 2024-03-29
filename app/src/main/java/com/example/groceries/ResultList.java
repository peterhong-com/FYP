package com.example.groceries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.api.Distribution;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultList extends AppCompatActivity {

    LinearLayout ll;
    ArrayList<String> Market= new ArrayList<>();
    ArrayList<String> grocery= new ArrayList<>();
    ArrayList<String> grocery1 = new ArrayList<>();
    ArrayList<String> qut1= new ArrayList<>();
    ArrayList<String> lowest=new ArrayList<>();
  DatabaseReference databaseReference;
  DatabaseReference databasereference1;
    double[] sss;
    String[] ma;
    double[] mi;
    double total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        ll= findViewById(R.id.linearLayout10);
        ll.setBackgroundColor(getResources().getColor(R.color.white));
        Intent c =getIntent();
        String name= getIntent().getStringExtra("Name");
        mi=c.getDoubleArrayExtra("missing");
        sss=getIntent().getDoubleArrayExtra("lowest");
        DecimalFormat df = new DecimalFormat("0.00");
        lowest=c.getStringArrayListExtra("price");
        grocery=c.getStringArrayListExtra("grocery");
        Market=c.getStringArrayListExtra("Market");


                TextView price1= findViewById(R.id.price2);
                if(mi[0]!=0){
                    price1.setText("");
        }
                else
                    price1.setText("Econsave :   RM" + String.valueOf(df.format(sss[0]))+"\nLack some grocery");

                TextView price2= findViewById(R.id.price4);
        if(mi[1]!=0){ price2.setText("");
        }
            else

            price2.setText("Lotus :   RM" + String.valueOf(df.format(sss[1])));

                TextView price3= findViewById(R.id.price5);
        if(mi[2]!=0){
            price3.setText("");
        }
        else
           price3.setText("Giant :    RM" + String.valueOf(df.format(sss[2])));

        LinearLayout a1= new LinearLayout(getApplicationContext());
        a1.setOrientation(LinearLayout.HORIZONTAL);


        ll.setOrientation(LinearLayout.VERTICAL);
        TextView differ = new TextView(getApplicationContext());
        differ.setText("Grocery Name");
        differ.setMinWidth(200);
        TextView differ1 = new TextView(getApplicationContext());
        differ1.setText("Price");
        differ1.setMinWidth(100);
        differ1.setPadding(50,0,50,10);
        TextView differ2 = new TextView(getApplicationContext());
        differ2.setText("Market");
        differ2.setMinWidth(100);
        ll.addView(a1);
        a1.addView(differ);
        a1.addView(differ1);
        a1.addView(differ2);
        for (int i = 0; i<grocery.size();i++){
            LinearLayout a2= new LinearLayout(getApplicationContext());
            a2.setOrientation(LinearLayout.HORIZONTAL);
            TextView name11 = new TextView(getApplicationContext());
            name11.setText(grocery.get(i));
            name11.setMaxWidth(200);
            TextView pricee = new TextView(getApplicationContext());
            pricee.setText(df.format(Double.parseDouble(lowest.get(i))));
            pricee.setMinWidth(100);
            pricee.setPadding(60,0,50,10);
            TextView market1= new TextView(getApplicationContext());
            market1.setText((Market.get(i)));
            market1.setMinWidth(100);
            ll.addView(a2);
            a2.addView(name11);
            a2.addView(pricee);
            a2.addView(market1);

        }

        for(int i=0; i<lowest.size();i++){
            total+=Double.parseDouble(lowest.get(i));

        }

                TextView price= findViewById(R.id.price);
                price.setText("RM"+String.valueOf(df.format(total)));


        Button Finish=findViewById(R.id.button6);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b= new Intent(getApplicationContext(),Mainmenu2.class);
                b.putExtra("Name",name);
                startActivity(b);

            }
        });


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