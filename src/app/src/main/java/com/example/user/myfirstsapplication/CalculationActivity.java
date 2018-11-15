package com.example.user.myfirstsapplication;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private TextView firstCurr;
    private TextView secondCurr;
    private EditText number1;
    private EditText number2;

    private String titleUsd;
    private String titleEur;
    private String titleRub;

    private int position;
    private Button convert;

    FileOutputStream file;
    FileInputStream fileInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.numbers, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        readFile();
        initTextView();
        addListenerOnButton();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String textFirstCur = parent.getItemAtPosition(position).toString();
        String textSecondCur = parent.getItemAtPosition(position).toString();

        firstCurr.setText(textFirstCur.substring(0,3));
        secondCurr.setText(textSecondCur.substring(5,8));

        this.position = position;
    }


    public double converter(double number1) {

        double numResult = 0;
        double number2;

        switch (position) {

            case 0:
                number2 = Double.parseDouble(titleUsd);
                numResult = new BigDecimal(number1*number2).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
            case 1:
                number2 = Double.parseDouble(titleEur);
                numResult = new BigDecimal(number1*number2).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
            case 2:
                number2 = Double.parseDouble(titleRub);
                numResult = new BigDecimal(number1*number2/100).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
            case 3:
                number2 = Double.parseDouble(titleUsd);
                numResult = new BigDecimal(number1/number2).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
            case 4:
                number2 = Double.parseDouble(titleEur);
                numResult = new BigDecimal(number1/number2).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
            case 5:
                number2 = Double.parseDouble(titleRub);
                numResult = new BigDecimal(number1/number2*100).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
        }

        return numResult;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.position = 0;
    }

    public void initTextView() {
        firstCurr = (TextView)findViewById(R.id.firstCurrency);
        secondCurr = (TextView) findViewById(R.id.secondCurrency);
        convert = (Button) findViewById(R.id.buttonConvert);
    }

    public void addListenerOnButton () {

        number1 = (EditText) findViewById(R.id.number_1);
        number2 = (EditText) findViewById(R.id.number_2);

        number2.setEnabled(false);

        convert.setOnClickListener(
                new View.OnClickListener() {      // создаем новый метод(переопределяем)
                    @Override
                    public void onClick(View v) {

                        double num1 = Double.parseDouble(number1.getText().toString());
                        double num2 = converter(num1);
                        String stringNumber2 = String.valueOf(num2);
                        number2.setText(stringNumber2);
                    }
                }
        );

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

            fileInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
