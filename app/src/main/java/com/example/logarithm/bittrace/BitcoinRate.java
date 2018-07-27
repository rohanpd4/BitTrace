package com.example.logarithm.bittrace;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitcoinRate extends AppCompatActivity {


    TextView codeshower,country,rateshower;
    class  JSONDownloader extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                HttpURLConnection connection;
                connection = (HttpURLConnection) url.openConnection();
                InputStream ip=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(ip);
                int  data=reader.read();
                String result="";
                while(data!=-1){
                    char a=(char)data;
                    result=result+a;
                    data=reader.read();
                }
                return result;


            }catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin_rate);
        codeshower=findViewById(R.id.countryCode);
        country=findViewById(R.id.countryshower);
        rateshower=findViewById(R.id.rateshower);

        try {
            Intent intent=getIntent();
            String code=intent.getStringExtra("Code");

            codeshower.setText("Rate for Country Code: " + code);
            JSONDownloader task = new JSONDownloader();
            String json = task.execute("https://api.coindesk.com/v1/bpi/currentprice/" + code + ".json").get();
            JSONObject jsonObject2=new JSONObject(json);
            JSONObject time=new JSONObject(jsonObject2.get("time").toString());
            String updated=time.get("updated").toString();
            JSONObject bpi=new JSONObject(jsonObject2.get("bpi").toString());
            JSONObject CURR=new JSONObject(bpi.get(code).toString());


            rateshower.setText(CURR.get("rate").toString());
            country.setText(CURR.get("description").toString());
            Log.i("testsdD",CURR.get("rate_float").toString());



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
