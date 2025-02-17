package com.example.groceries;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] price;

    //String[] urls;

    public ProgramAdapter(Context context, String[] price, int[] images) {
        super(context, R.layout.activity_price_list, R.id.textView11, price);
        this.context = context;
        this.images = images;
        this.price = price;

    }

    /*
    Additional code
    public ProgramAdapter(Context context, String[] programName, int[] images, String[] programDescription, String[] urls) {
        super(context, R.layout.single_item, R.id.textView1, programName);
        this.context = context;
        this.images = images;
        this.programName = programName;
        this.programDescription = programDescription;
        this.urls = urls;
    }
     */

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // The parameter convertView is null when your app is creating a new item for the first time. It's not null when
        // recycling.
        // Assign the convertView in a View object
        View singleItem = convertView;
        // Find a View from the entire View hierarchy by calling findViewById() is a fairly expensive task.
        // So, you'll create a separate class to reduce the number of calls to it.
        // First, create a reference of ProgramViewHolder and assign it to null.
        ProgramViewHolder holder = null;
        // Since layout inflation is a very expensive task, you'll inflate only when creating a new item in the ListView. The first
        // time you're creating a new item, convertView will be null. So, the idea is when creating an item for the first time,
        // we should perform the inflation and initialize the ViewHolder.
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.list, parent, false);
            // Pass the singleItem to the constructor of ProgramViewHolder. This singleItem object contains a LinearLayout
            // as the root element for single_item.xml file that contains other Views as well for the ListView.
            holder = new ProgramViewHolder(singleItem);
            // When you create an object of ProgramViewHolder, you're actually calling findViewById() method inside the constructor.
            // By creating ProgramViewHolder only when making new items, you call findViewById() only when making new rows.
            // At this point all the three Views have been initialized. Now you need to store the holder so that you don't need to
            // create it again while recycling and you can do this by calling setTag() method on singleItem and passing the holder as a parameter.
            singleItem.setTag(holder);
        }
        // If singleItem is not null then we'll be recycling
        else{
            // Get the stored holder object
            holder = (ProgramViewHolder) singleItem.getTag();
        }
        // Set the values for your views in your item by accessing them through the holder
        if (price[position].contains("Econsave")) {
            holder.itemImage.setImageResource(images[0]);
            if(price[position].contains("0.00"))
            holder.programTitle.setText("No available");
            else
                holder.programTitle.setText(price[position]);
        }

        if (price[position].contains("Lotus")) {
            holder.itemImage.setImageResource(images[1]);
            if(price[position].contains("0.00"))
                holder.programTitle.setText("No available");
            else
                holder.programTitle.setText(price[position]);
        }
        if (price[position].contains("Giant")) {
            holder.itemImage.setImageResource(images[2]);
            if(price[position].contains("0.00"))
                holder.programTitle.setText("No available");
            else
                holder.programTitle.setText(price[position]);
        }
        return singleItem;
    }
}
