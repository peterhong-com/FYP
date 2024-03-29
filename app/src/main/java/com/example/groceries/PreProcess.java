package com.example.groceries;

import android.content.res.AssetManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PreProcess extends AppCompatActivity {

	public void preProcess(InputStreamReader in) throws IOException {

		String line = null;
		try {

			;

            BufferedReader bufferedReader = 
                new BufferedReader(in);
            
            


            int count = 0;
            while((line = bufferedReader.readLine()) != null) {
                
            	if(line=="")
            		continue;
            	
            	String[] splitted_ip = line.split("\\s+");
            	for(String temp : splitted_ip) {


            	
            			
            			line.replaceFirst(temp+" ", "");
            		
            	}
            	

            	count++;
            }

            //close files.

        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file");                  
        }
	}
	
	public void main(String[] args) throws IOException {


	}

}
