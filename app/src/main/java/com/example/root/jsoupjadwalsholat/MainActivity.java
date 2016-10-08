package com.example.root.jsoupjadwalsholat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button bt;
    List<String> list = new ArrayList<String>();
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.text);
        bt = (Button)findViewById(R.id.button);
        sp = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new scrap().execute();
            }
        });

    }

    public class scrap extends AsyncTask<Void,Void,Void>{

        String hasil = "";
        String title = "";


        @Override
        protected Void doInBackground(Void... params) {

            try{

                Document doc = Jsoup.connect("http://jadwalsholatimsak.info/").get();
                Elements el = doc.getElementsByTag("option");

                title = doc.title();

                for (Element data: el ) {
//                    String attr = data.attr("");
                    String attr = data.text();
                    list.add(attr);
                }

            }catch (Exception e){
//                Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            if (title.equals("")){
                tv.setText("kosong bro");
            }else{
                tv.setText(title);
            }

        }
    }

}
