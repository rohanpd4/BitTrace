
package com.example.logarithm.bittrace;

import android.content.Context;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.logarithm.bittrace.BitcoinRate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String countrySelectedCode;
    ArrayList<String> countryArray;
    Intent intent;
    public static String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        spinner= findViewById(R.id.spinner);
        countryArray=new ArrayList<>();
        ArrayAdapter<String>adapter = new ArrayAdapter<>(getApplication(),
                android.R.layout.simple_spinner_item,countryArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        countryArray.add("Nothing Selected");

        try {
            String countrylist = AssetJSONFile("Countries.json",getApplicationContext());
            jsonArray=new JSONArray(countrylist);
            for(int i=0;i<jsonArray.length();i++){
                String x;
                jsonObject=new JSONObject(jsonArray.get(i).toString());
                x=jsonObject.get("currency").toString();
                x=x+ " - " +jsonObject.get("country").toString();
                countryArray.add(x);
                adapter.notifyDataSetChanged();

            }



        }catch (Exception e){
            e.printStackTrace();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    if(position==0){

                    }else {
                        JSONObject jsonObject1 = new JSONObject(jsonArray.get(position-1).toString());
                        countrySelectedCode = jsonObject1.get("currency").toString();
                        intent = new Intent(getApplicationContext(), BitcoinRate.class);
                        intent.putExtra("Code", countrySelectedCode);
                        startActivity(intent);
                    }              }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
