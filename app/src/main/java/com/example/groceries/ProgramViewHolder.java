package com.example.groceries;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {
    // Declare the object references for our views
    ImageView itemImage;
    TextView programTitle;
    // Get the handles by calling findViewById() on View object inside the constructor
    ProgramViewHolder(View v)
    {
        itemImage = v.findViewById(R.id.imageView1111);
        programTitle = v.findViewById(R.id.textView11);
    }
}
