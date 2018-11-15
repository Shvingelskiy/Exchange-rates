package com.example.user.myfirstsapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    private TextView RubID;
    private TextView EurID;
    private TextView UsdID;
    private TextView PlnID;
    private TextView UahID;
    private Button upData;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    ArrayList<String> arrayList;
    ArrayList<String> listUsd;
    ArrayList<String> listEur;
    ArrayList<String> listRub;

    String titleUsd;
    String titleEur;
    String titleRub;
    String titlePln;
    String titleUah;

    ArrayList listTestusd = new ArrayList<String>();
    ArrayList listTesteur = new ArrayList<String>();
    ArrayList listTestrub = new ArrayList<String>();

    FileOutputStream file;
    FileInputStream fileInput;

    class MyTask extends AsyncTask<Void, Void, Void> {

        String stringUsd, stringEur, stringRub, stringPln, stringUah ;
        FileOutputStream file;

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;

            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get();
            } catch (IOException e) {
                e.printStackTrace();

            }

            if (doc != null) {
                Element table = doc.select("table").first();
                Elements rows = table.select("tr");

                stringUah = rows.get(5).select("td").get(3).text();
                stringUsd = rows.get(1).select("td").get(3).text();
                stringEur = rows.get(2).select("td").get(3).text();
                stringPln = rows.get(4).select("td").get(3).text();
                stringRub = rows.get(3).select("td").get(3).text();

                setExchangeRates(stringUsd, stringEur, stringRub, stringUah, stringPln);

                Elements table2 = doc.select("tr.tr-tb > td.best");
                Element value;
                Element trBefore;
                String number;
                String bank;
                arrayList = new ArrayList<String>();

                for (int i = 0;i<table2.size();i++){
                    Element lol = table2.get(i);
                    value = table2.get(i);
                    number = value.text();
                    arrayList.add(number);
                    trBefore = lol.firstElementSibling();
                    bank = trBefore.text();
                    arrayList.add(bank);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            outputExchangeRates();
            writeFile();
            splitArrayOfCurrencies();
        }
    }

    public void splitArrayOfCurrencies() {

        listUsd = new ArrayList<String>();
        listEur = new ArrayList<String>();
        listRub = new ArrayList<String>();

        double usdBuy;
        double usdSale;
        double eurBuy;
        double eurSale;
        double rubBuy;
        double rubSale;

        for(int i = 0; i < arrayList.size(); i+=2) {

            double num = Double.parseDouble(arrayList.get(i));
            String bank = arrayList.get(i+1);
            String number;

            if ((num > (Double.parseDouble(titleUsd) - 0.1))&&(num < (Double.parseDouble(titleUsd) + 0.1))) {
                number = Double.toString(num);
                listUsd.add(number);
                listUsd.add(bank);
            }

            if ((num > (Double.parseDouble(titleEur) - 0.1))&&(num < (Double.parseDouble(titleEur) + 0.1))) {
                number = Double.toString(num);
                listEur.add(number);
                listEur.add(bank);
            }

            if ((num > (Double.parseDouble(titleRub) - 0.1))&&(num < (Double.parseDouble(titleRub) + 0.1))) {
                number = Double.toString(num);
                listRub.add(number);
                listRub.add(bank);
            }
        }

        sortListAndWriteFile(listUsd, listTestusd,"BanksUsd.txt");
        sortListAndWriteFile(listEur, listTesteur,"BanksEur.txt");
        sortListAndWriteFile(listRub, listTestrub,"BanksRub.txt");
    }

    public void sortListAndWriteFile(ArrayList list, ArrayList sortList, String nameOfFile){

        for (int i = 0; i < list.size(); i+=2){
            if (Double.parseDouble((String) list.get(i)) > Double.parseDouble((String) list.get(i + 2))) {
                sortList.add(list.get(i));
                sortList.add(list.get(i+1));
                sortList.add(list.get(i+2));
                sortList.add(list.get(i+3));
                writeFileBanks(nameOfFile, sortList);
                break;
            }
            else if (Double.parseDouble((String) list.get(i)) < Double.parseDouble((String) list.get(i + 2))){
                sortList.add(list.get(i+2));
                sortList.add(list.get(i+3));
                sortList.add(list.get(i));
                sortList.add(list.get(i+1));
                writeFileBanks(nameOfFile, sortList);
                break;
            }
        }

    }

    public void writeFileBanks(String nameOfFile, ArrayList sortList){
        try {
            file = openFileOutput(nameOfFile, MODE_PRIVATE);

            file.write((sortList.get(0) + "\n").getBytes());
            file.write((sortList.get(1) + "\n").getBytes());
            file.write((sortList.get(2) + "\n").getBytes());
            file.write((sortList.get(3) + "\n").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditTexts() {

        UsdID = (TextView)findViewById(R.id.UsdID);
        RubID = (TextView) findViewById(R.id.rubID);
        EurID = (TextView)findViewById(R.id.eurID);
        UahID = (TextView)findViewById(R.id.UahID);
        PlnID = (TextView)findViewById(R.id.PlnID);
        upData = (Button) findViewById(R.id.UpData);
    }

    public void outputExchangeRates(){

        UsdID.setText("USD - " + titleUsd);
        EurID.setText("EUR - " + titleEur);
        RubID.setText("100 RUB - " + titleRub);
        UahID.setText("100 UAH - " + titleUah);
        PlnID.setText("10 PLN - " + titlePln);
    }

    public void setExchangeRates(String stringUsd, String stringEur, String stringRub, String stringUah, String stringPln){

        titleUsd = stringUsd;
        titleEur = stringEur;
        titleRub = stringRub;
        titleUah = stringUah;
        titlePln = stringPln;
    }


    public void addListenerOnButton() {

        upData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(isConnected()){
                            Toast.makeText(MainActivity.this, "Обновление курсов!",Toast.LENGTH_LONG).show();
                            MyTask mt = new MyTask();
                            mt.execute();
                        } else {
                            Toast.makeText(MainActivity.this, "Невозможно обновить! \n Нет подключения к интернету.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initEditTexts();
        readFile();
        outputExchangeRates();
        addListenerOnButton();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_search:
                Intent intentSearch = new Intent(getApplicationContext(), BestBankActivity.class);
                startActivity(intentSearch);
                break;

            case R.id.nav_converter:
                Intent intent = new Intent(getApplicationContext(), CalculationActivity.class);
                startActivity(intent);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void readFile(){
        try {
            fileInput = openFileInput("Currencies.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);

            StringBuffer strBuffer = new StringBuffer();
            String lines;

            lines = buffer.readLine();
            titleRub = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());

            lines = buffer.readLine();
            titleEur = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());

            lines = buffer.readLine();
            titleUsd = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());

            lines = buffer.readLine();
            titleUah = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());

            lines = buffer.readLine();
            titlePln = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());

            fileInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(){
        try {
            file = openFileOutput("Currencies.txt", MODE_PRIVATE);

            file.write((titleRub + "\n").getBytes());
            file.write((titleEur + "\n").getBytes());
            file.write((titleUsd + "\n").getBytes());
            file.write((titleUah + "\n").getBytes());
            file.write((titlePln + "\n").getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


