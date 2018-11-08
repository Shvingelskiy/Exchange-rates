package com.example.user.myfirstsapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;

import java.io.FileOutputStream;



public class MainActivity extends AppCompatActivity {

    Intent intent;
    private EditText RubID;
    private EditText EurID;
    private EditText UsdID;
    private EditText PlnID;
    private EditText UahID;
    Button upData;


    String titleUsd;
    String titleEur;
    String titleRub;
    String titlePln;
    String titleUah;

    String titleTest1 = "Hello", titleTest2 = "World", titleTest3 = "!!!";

    FileOutputStream file;
    FileInputStream fileInput;




    //String title2;
    //private Button btn_alert;
    //private Button act_change;

    //public Elements number; // с помощью данного класса будет производиться разбор данных на кусочки
    //public ArrayList<String> numList = new ArrayList<String>(); // для хранения данных
    //private ArrayAdapter<String> adapter; // с помощью адаптера будут выводиться данные

    //private ListView lv;

    class MyTask extends AsyncTask<Void, Void, Void> {

        String title1;
        String stringUsd, stringEur, stringRub, stringPln, stringUah ;
        Element row;
        FileOutputStream file;

        @Override
        protected Void doInBackground(Void... params) {

                Document doc = null;

                try {
                    doc = Jsoup.connect("https://www.nbrb.by/statistics/rates/ratesDaily.asp").get();
                } catch (IOException e) {
                    //Toast.makeText(MainActivity.this, "Невозможно обновить! \n Нет подключения к интернету.",Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

                if (doc != null) {
                    Element table = doc.select("table").first();
                    Elements rows = table.select("tr");

                /*for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i); //по номеру индекса получает строку
                    Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы
                    System.out.print(cols.get(0).text());// первый столбец
                    System.out.print(cols.get(1).text());
                    System.out.print(cols.get(2).text());
                    System.out.println();
                }*/

                    stringUah = rows.get(3).select("td").get(2).text();
                    stringUsd = rows.get(5).select("td").get(2).text();
                    stringEur = rows.get(6).select("td").get(2).text();
                    stringPln = rows.get(7).select("td").get(2).text();
                    stringRub = rows.get(18).select("td").get(2).text();



                    setExchangeRates(stringUsd, stringEur, stringRub, stringUah, stringPln);
                }
            return null;
        }


        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);


            /*try {
                fileInput = openFileInput("Rates.txt");
                InputStreamReader reader = new InputStreamReader(fileInput);
                BufferedReader buffer = new BufferedReader(reader);
                StringBuffer strBuffer = new StringBuffer();
                String lines;
                while((lines = buffer.readLine()) != null){
                    strBuffer.append(lines + "\n");
                }

                RubID.setText(strBuffer.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            //UpData.setText("ЗБС!");
            //UsdID.setText("kek");


            /*UsdID.setText("                     USD - " + titleUsd);
            EurID.setText("                     EUR - " + titleEur);
            RubID.setText("                     RUB - " + titleRub);*/



            outputExchangeRates();
            writeFile();       // записываем обновленные курсы валют

        }
    }

    public void initEditTexts() {

        UsdID = (EditText)findViewById(R.id.UsdID);
        RubID = (EditText)findViewById(R.id.rubID);
        EurID = (EditText)findViewById(R.id.eurID);
        UahID = (EditText)findViewById(R.id.UahID);
        PlnID = (EditText)findViewById(R.id.PlnID);


        upData = (Button) findViewById(R.id.UpData);
    }

    public void outputExchangeRates(){

        UsdID.setText("USD - " + titleUsd);
        EurID.setText("EUR - " + titleEur);
        RubID.setText("RUB - " + titleRub);
        UahID.setText("UAH - " + titleUah);
        PlnID.setText("PLN - " + titlePln);

    }

    public void setExchangeRates(String stringUsd, String stringEur, String stringRub, String stringUah, String stringPln){

        titleUsd = stringUsd;
        titleEur = stringEur;
        titleRub = stringRub;
        titleUah = stringUah;
        titlePln = stringPln;
    }


    public void addListenerOnButton() {

        upData.setOnClickListener(                  // при нажатии
                new View.OnClickListener() {      // создаем новый метод(переопределяем)
                    @Override
                    public void onClick(View v) {


                        if(isConnected()){
                            Toast.makeText(MainActivity.this, "Обновление курсов!",Toast.LENGTH_LONG).show();
                            MyTask mt = new MyTask();
                            mt.execute();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Невозможно обновить! \n Нет подключения к интернету.",Toast.LENGTH_LONG).show();
                        }

                        //Toast.makeText(MainActivity.this, "ЗБС!",Toast.LENGTH_LONG).show();
                        //MyTask mt = new MyTask();
                        //mt.execute();



                        /*Document doc;
                        Elements number,number2,number3; // с помощью данного класса будет производиться разбор данных на кусочки
                        String num;

                        try {
                            doc = Jsoup.connect("https://myfin.by/bank/kursy_valjut_nbrb/usd").timeout(6000).get();
                            number = doc.getElementsByClass("p.h1");
                            number2 = doc.getElementsByClass("col-xs-6 no-padding value");
                            number3 = doc.getElementsByClass("col-xs-6");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }(*/



                        /*try {
                            Document doc = Jsoup.connect("https://myfin.by/currency/usd").timeout(6000).get();
                            //Element table = doc.select("table").first();
                            //Elements rows = table.select("tr"); // разбиваем по тегу


                            /*for (int i = 1; i < rows.size(); i++) {
                                Element row = rows.get(i); //по номеру индекса получает строку
                                Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы
                                System.out.print(cols.get(0).text());// первый столбец
                                System.out.print(cols.get(1).text());
                                System.out.print(cols.get(2).text());
                                System.out.println();
                            }
                            String title = doc.title();

                            //Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT);

                            Toast.makeText(
                                    MainActivity.this, title, // отображаем в главном окне, показываем сообщение с пароля
                                    Toast.LENGTH_SHORT                         // недолго
                            ).show();

                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }*/



                    }
                }



        );


    }

    public boolean isConnected() {
        /*ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;*/

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addListenerOnButton(); // вызываем метод, котор. будет отслеживать нажатие на клавишу
        initEditTexts();
        readFile();
        outputExchangeRates();
        addListenerOnButton();
    }





    public void readFile(){
        try {
            fileInput = openFileInput("Rates.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            //StringBuffer strBuffer = new StringBuffer();
            StringBuffer strBuffer = new StringBuffer();
            StringBuffer strBuffer1 = new StringBuffer();
            StringBuffer strBuffer2 = new StringBuffer();
            StringBuffer strBuffer3 = new StringBuffer();
            String lines;
            /*while((lines = buffer.readLine()) != null){
                strBuffer.append(lines + "\n");
            }*/


            lines = buffer.readLine(); // считываем строку
            titleRub = strBuffer.append(lines).toString(); // заносим в поле
            strBuffer.delete(0, strBuffer.length()); // чистим буфер

            lines = buffer.readLine();
            titleEur = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());

            lines = buffer.readLine();
            titleUsd = strBuffer.append(lines).toString();
            strBuffer.delete(0, strBuffer.length());


            Toast.makeText(MainActivity.this, strBuffer1.toString(),Toast.LENGTH_LONG).show();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    public void writeFile(){
        try {
            file = openFileOutput("Rates.txt", MODE_PRIVATE);


            //file.write((titleUsd + "\n").getBytes());
            //file.write((titleEur + "\n").getBytes());
            //file.write((titleRub + "\n").getBytes());

            // Для тестирования
            file.write((titleTest1 + "\n").getBytes());
            file.write((titleTest2 + "\n").getBytes());
            file.write((titleTest3 + "\n").getBytes());


            file.close();
            Toast.makeText(MainActivity.this, "Текст сохранен",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


    /*public void addListenerOnButton () {
        pass = (EditText)findViewById(R.id.editText); // считываем пароль
        btn = (Button)findViewById(R.id.button); // наша кнопка

        btn.setOnClickListener(                  // при нажатии
                new View.OnClickListener(){      // создаем новый метод(переопределяем)
                    @Override
                    public void onClick(View v) {
                        btn.setText("Done");
                        btn.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        Toast.makeText(
                                MainActivity.this, pass.getText(), // отображаем в главном окне, показываем сообщение с пароля
                                Toast.LENGTH_SHORT                         // недолго
                        ).show();
                    }
                }
        );

        btn_alert = (Button)findViewById(R.id.btn_alert);

        btn_alert.setOnClickListener(                  // при нажатии
                new View.OnClickListener(){      // создаем новый метод(переопределяем)
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this); // создаем какой-то алерт

                        a_builder.setMessage("Еба, ты лох?") // подпись снизу

                                .setCancelable(false) // не закрывать всплывающее окно
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick (DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })

                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick (DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Закрытие приложения");
                        alert.show();


                    }
                }
        );



        act_change = (Button)findViewById(R.id.act_change);

        act_change.setOnClickListener(                  // при нажатии
                new View.OnClickListener(){      // создаем новый метод(переопределяем)
                    @Override
                    public void onClick(View v) {
                       //Intent intent = new Intent("com.example.user.myfirstsapplication.SecondActivity");
                        intent = new Intent(getApplicationContext(), SecondActivity.class);
                       startActivity(intent);
                    }
                }
        );



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton(); // вызываем метод, котор. будет отслеживать нажатие на клавишу
    }


    public int getFirstNum() {
        EditText el1 = (EditText)findViewById(R.id.num1);
        int num1 = Integer.parseInt(el1.getText().toString());

        return num1;
    }

    public int getSecondNum() {
        EditText el2 = (EditText)findViewById(R.id.num2);
        int num2 = Integer.parseInt(el2.getText().toString());

        return num2;
    }

    public void onButtonClickPlus(View v) {
        // находим нужное нам значение и помещаем в переменную
        TextView resText = (TextView)findViewById(R.id.result);

        int res = getFirstNum() + getSecondNum();
        resText.setText(Integer.toString(res));
    }

    public void onButtonClickMinus(View v) {
        // находим нужное нам значение и помещаем в переменную
        TextView resText = (TextView)findViewById(R.id.result);

        int res = getFirstNum() - getSecondNum();
        resText.setText(Integer.toString(res));
    }*/

