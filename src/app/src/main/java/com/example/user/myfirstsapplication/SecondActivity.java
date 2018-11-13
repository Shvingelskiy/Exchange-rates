package com.example.user.myfirstsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SecondActivity extends  AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView firstCurr;
    private TextView secondCurr;

    //private Number number1;
    //private Number number2;

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
        setContentView(R.layout.activity_second);





        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
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


        //Toast.makeText(parent.getContext(), textFirstCur, Toast.LENGTH_SHORT).show();
        //Toast.makeText(parent.getContext(), textSecondCur, Toast.LENGTH_SHORT).show();
        Toast.makeText(parent.getContext(), textSecondCur, Toast.LENGTH_SHORT).show();


        firstCurr.setText(textFirstCur.substring(0,3));
        secondCurr.setText(textSecondCur.substring(5,8));

        this.position = position;

    }


    public double converter(double number1) {

           /*if (position == 0) {


               double number2 = Double.parseDouble(titleUsd);
               double num = number1/number2;

               double numM = new BigDecimal(num).setScale(4, RoundingMode.HALF_UP).doubleValue();

               String text = String.valueOf(num);

               Toast.makeText(SecondActivity.this, text ,Toast.LENGTH_LONG).show();

               return numM;
           }
           else return 0;*/

        double numResult = 0;
        double number2;

        switch (position) {

            case 0:
                number2 = Double.parseDouble(titleUsd);
                numResult = new BigDecimal(number1*number2).setScale(4, RoundingMode.HALF_UP).doubleValue();
                break;
            case 1:
                number2 = Double.parseDouble(titleEur);
                numResult = new BigDecimal(number1*number2).setScale(4, RoundingMode.HALF_UP).doubleValue();
                break;
            case 2:
                number2 = Double.parseDouble(titleRub);
                numResult = new BigDecimal(number1*number2/100).setScale(4, RoundingMode.HALF_UP).doubleValue();
                break;
            case 3:
                number2 = Double.parseDouble(titleUsd);
                numResult = new BigDecimal(number1/number2).setScale(4, RoundingMode.HALF_UP).doubleValue();
                break;
            case 4:
                number2 = Double.parseDouble(titleEur);
                numResult = new BigDecimal(number1/number2).setScale(4, RoundingMode.HALF_UP).doubleValue();
                break;
            case 5:
                number2 = Double.parseDouble(titleRub);
                numResult = new BigDecimal(number1/number2*100).setScale(4, RoundingMode.HALF_UP).doubleValue();
                break;
        }

        return numResult;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initTextView() {

        firstCurr = (TextView)findViewById(R.id.firstCurrency);
        secondCurr = (TextView) findViewById(R.id.secondCurrency);

        //number1 = (Number)findViewById(R.id.number_1);


        convert = (Button) findViewById(R.id.buttonConvert); // наша кнопка
    }






    public void addListenerOnButton () {

        number1 = (EditText) findViewById(R.id.number_1); // считываем значение
        number2 = (EditText) findViewById(R.id.number_2); // считываем значение

        number2.setEnabled(false);

        convert.setOnClickListener(                  // при нажатии
                new View.OnClickListener() {      // создаем новый метод(переопределяем)
                    @Override
                    public void onClick(View v) {
                        //btn.setText("Done");
                        //btn.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        /*Toast.makeText(
                                MainActivity.this, pass.getText(), // отображаем в главном окне, показываем сообщение с пароля
                                Toast.LENGTH_SHORT                         // недолго
                        ).show();*/



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
            titleRub = titleRub.replace(',','.');
            strBuffer.delete(0, strBuffer.length()); // чистим буфер

            lines = buffer.readLine();
            titleEur = strBuffer.append(lines).toString();
            titleEur = titleEur.replace(',','.');
            strBuffer.delete(0, strBuffer.length());

            lines = buffer.readLine();
            titleUsd = strBuffer.append(lines).toString();
            titleUsd = titleUsd.replace(',','.');
            strBuffer.delete(0, strBuffer.length());


            //Toast.makeText(SecondActivity.this, strBuffer.toString(),Toast.LENGTH_LONG).show();


            fileInput.close();




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






}

/*class Currencies extends MainActivity{

    public String getTextLol(){
        return "HUI";
    }

}*/


