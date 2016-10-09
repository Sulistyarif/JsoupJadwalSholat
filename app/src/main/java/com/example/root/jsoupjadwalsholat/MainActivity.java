package com.example.root.jsoupjadwalsholat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    TextView tv,tvImsyak,tvShubuh,tvTerbit,tvDhuhur,tvAshar,tvMagrib,tvIsya;
    Button bt;
    List<String> list = new ArrayList<String>();
    Spinner sp;
    int cityNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.text);
        bt = (Button)findViewById(R.id.button);
        sp = (Spinner)findViewById(R.id.spinner);
        tvImsyak = (TextView)findViewById(R.id.textView2);
        tvShubuh = (TextView)findViewById(R.id.textView4);
        tvTerbit = (TextView)findViewById(R.id.textView8);
        tvDhuhur = (TextView)findViewById(R.id.textView6);
        tvAshar = (TextView)findViewById(R.id.textView10);
        tvMagrib = (TextView)findViewById(R.id.textView12);
        tvIsya = (TextView)findViewById(R.id.textView14);


        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapt);

        new scrap().execute();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityNumb = sp.getSelectedItemPosition();
                new scrapTime().execute();
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

    public class scrapTime extends AsyncTask<Void,Void,Void>{

        String jamImsyak;
        String jamShubuh;
        String jamTerbit;
        String jamDhuhur;
        String jamAshar;
        String jamMagrib;
        String jamIsya;

        @Override
        protected Void doInBackground(Void... params) {

            try{
                Document doc = Jsoup.connect("http://jadwalsholatimsak.info/daily.php?id=" + cityNumb).get();
                Element table = doc.select("table").get(0);
                Elements rows = table.select("tr");

                Element rowSelected = rows.get(2);
                Elements colms = rowSelected.select("td");

                jamImsyak = colms.get(1).text();
                jamShubuh = colms.get(2).text();
                jamTerbit = colms.get(3).text();
                jamDhuhur = colms.get(4).text();
                jamAshar = colms.get(5).text();
                jamMagrib = colms.get(6).text();
                jamIsya = colms.get(7).text();


            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvImsyak.setText(jamImsyak);
            tvShubuh.setText(jamShubuh);
            tvTerbit.setText(jamTerbit);
            tvDhuhur.setText(jamDhuhur);
            tvAshar.setText(jamAshar);
            tvMagrib.setText(jamMagrib);
            tvIsya.setText(jamIsya);
        }
    }

}
