package com.example.groceries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PriceList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        String s=getIntent().getStringExtra("groceryname");
        getSupportActionBar().setTitle(s);
        String[] ss=getIntent().getStringArrayExtra("price");
        int[] programImages ={R.drawable.econsave,R.drawable.tesco,R.drawable.giant};
        ProgramAdapter programAdapter = new ProgramAdapter(getApplicationContext(),ss,programImages);
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list,ss);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(programAdapter);
    }
}