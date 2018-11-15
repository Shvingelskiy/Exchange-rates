package com.example.user.myfirstsapplication;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class BestBankActivity extends  AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button search;
    private int position;
    private TextView saleField;
    private TextView saleFieldBank;
    private TextView buyField;
    private TextView buyFieldBank;

    String usdSale;
    String eurSale;
    String rubSale;
    String usdBuy;
    String eurBuy;
    String rubBuy;

    String usdSaleBank;
    String eurSaleBank;
    String rubSaleBank;
    String usdBuyBank;
    String eurBuyBank;
    String rubBuyBank;

    FileInputStream fileInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        readFile("BanksUsd.txt");
        readFile("BanksEur.txt");
        readFile("BanksRub.txt");

        initTextView();
        addListenerOnButton();
    }

    public void initTextView() {

        search = (Button) findViewById(R.id.buttonSearch);
        saleField = (TextView) findViewById(R.id.saleField);
        saleFieldBank = (TextView) findViewById(R.id.saleFieldBank);
        buyField = (TextView) findViewById(R.id.buyField);
        buyFieldBank = (TextView) findViewById(R.id.buyFieldBank);
    }

    public void addListenerOnButton () {

        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outputData();
                    }
                }
        );
    }

    public void outputData(){
        if (position == 0){
            saleField.setText("Продажа USD:         " + usdSale);
            saleFieldBank.setText("Банк:         " + usdSaleBank);
            buyField.setText("Покупка USD:         " + usdBuy);
            buyFieldBank.setText("Банк:         " + usdBuyBank);
        }
        if (position == 1){
            saleField.setText("Продажа EUR:          " + eurSale);
            saleFieldBank.setText("Банк:         " + eurSaleBank);
            buyField.setText("Покупка EUR:         " +  eurBuy);
            buyFieldBank.setText("Банк:         " + eurBuyBank);
        }
        if (position == 2){
            saleField.setText("Продажа RUB:         " + rubSale);
            saleFieldBank.setText("Банк:         " + rubSaleBank);
            buyField.setText("Покупка RUB:         " + rubBuy);
            buyFieldBank.setText("Банк:         " + rubBuyBank);
        }
    }

    public void readFile(String fileName){

        try {
            fileInput = openFileInput(fileName);
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strBuffer = new StringBuffer();
            String lines;

            if (fileName.equals("BanksUsd.txt")) {
                lines = buffer.readLine();
                usdSale = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                usdSaleBank = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                usdBuy = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                usdBuyBank = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());
            }
            else if (fileName.equals("BanksEur.txt")) {
                lines = buffer.readLine();
                eurSale = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                eurSaleBank = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                eurBuy = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                eurBuyBank = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());
            }
            else if (fileName.equals("BanksRub.txt")) {
                lines = buffer.readLine();
                rubSale = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                rubSaleBank = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                rubBuy = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());

                lines = buffer.readLine();
                rubBuyBank = strBuffer.append(lines).toString();
                strBuffer.delete(0, strBuffer.length());
            }

            fileInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.position = 0;
    }
}