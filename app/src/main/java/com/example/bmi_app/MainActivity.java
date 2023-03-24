package com.example.bmi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView message,heading1,bmi_score;

    private EditText cm,lbs,kg,age,inch,ft;

    private ToggleButton metrics;

    private Button calculate;

    private Spinner gender;

    private LinearLayout linear;

    boolean inmetrics;

    String[]gen={"Female","Male","Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear=findViewById(R.id.linear);
        message=findViewById(R.id.message);
        heading1=findViewById(R.id.heading1);
        bmi_score=findViewById(R.id.bmi_score);
        cm=findViewById(R.id.cm);
        lbs=findViewById(R.id.lbs);
        kg=findViewById(R.id.kg);
        age=findViewById(R.id.age);
        inch=findViewById(R.id.inch);
        ft=findViewById(R.id.ft);
        calculate=findViewById(R.id.calculate);
        gender=findViewById(R.id.gender);
        metrics=findViewById(R.id.metrics);

        message.setVisibility(View.GONE);
        inmetrics=true;
        updateinputVisibility();
        metrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               inmetrics=!inmetrics;
               updateinputVisibility();
            }
        });

        gender.setOnItemSelectedListener(this);

        ArrayAdapter ad=new ArrayAdapter(this,R.layout.gender,gen);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        gender.setAdapter(ad);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inmetrics)
                {
                    if(cm.length()==0 ||kg.length()==0)
                    {
                        Toast.makeText(MainActivity.this,"Enter Height and Weight  to calculate bmi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        double heightcm=Double.parseDouble(cm.getText().toString());
                        double weightkg=Double.parseDouble(kg.getText().toString());
                        double b=calculateBmiMetrics(weightkg,heightcm);
                        displayBMI(b);

                    }
                }
                else
                {
                    if(ft.length()==0||inch.length()==0||lbs.length()==0)
                    {
                        Toast.makeText(MainActivity.this,"Enter Height and Weight  to calculate bmi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        double heightft=Double.parseDouble(ft.getText().toString());
                        double heightinch=Double.parseDouble(inch.getText().toString());
                        double weightlbs=Double.parseDouble(lbs.getText().toString());
                        double b=calculateBmiImperial(heightft,heightinch,weightlbs);
                        displayBMI(b);

                    }
                }
            }
        });






    }

    private  void displayBMI( double bmi)
    {
            heading1.setText("YOUR RESULT");
            bmi_score.setVisibility(View.VISIBLE);
            message.setVisibility(View.VISIBLE);
            bmi_score.setText(String.format("%.2f",bmi));
            String msg=CLASSIFYBMI(bmi);
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            message.setText(msg);
            bmi_score.setTextSize(70);
            switch(msg)
            {
                case "YOU ARE UNDERWEIGHT ! STARTING EATING HEALTHY SNACKS FROM TODAY!":

                    linear.setBackgroundResource(R.color.under);
                    heading1.setTextColor(Color.WHITE);
                    bmi_score.setTextColor(Color.WHITE);
                    message.setTextColor(Color.WHITE);
                    break;

                case "YOU ARE IN GREAT SHAPE!"    :
                    linear.setBackgroundResource(R.color.normal);
                    heading1.setTextColor(Color.WHITE);
                    bmi_score.setTextColor(Color.WHITE);
                    message.setTextColor(Color.WHITE);
                    break;

                case "OVERWEIGHT!":

                    linear.setBackgroundResource(R.color.over);
                    heading1.setTextColor(Color.WHITE);
                    bmi_score.setTextColor(Color.WHITE);
                    message.setTextColor(Color.WHITE);

                    break;


                case "Obese start running from today" :
                    linear.setBackgroundResource(R.color.obese);
                    heading1.setTextColor(Color.BLACK);
                    bmi_score.setTextColor(Color.BLACK);
                    message.setTextColor(Color.BLACK);

            }



    }



    private String CLASSIFYBMI(double bmi)
    {

        if(bmi<18.5)
        {
            return "YOU ARE UNDERWEIGHT ! STARTING EATING HEALTHY SNACKS FROM TODAY!";
        }
        else if(bmi>=18.5 && bmi<25){

            return "YOU ARE IN GREAT SHAPE!";
        }
        else if(bmi>=25 && bmi<30)
        {
            return "OVERWEIGHT!";
        }
        else {
            return "Obese start running from today";
        }
    }
    private void updateinputVisibility()
    {
          if(inmetrics)
          {
              cm.setVisibility(View.VISIBLE);
              kg.setVisibility(View.VISIBLE);
              lbs.setVisibility(View.GONE);
              ft.setVisibility(View.GONE);
              inch.setVisibility(View.GONE);
          }
          else {
              cm.setVisibility(View.GONE);
              kg.setVisibility(View.GONE);
              lbs.setVisibility(View.VISIBLE);
              ft.setVisibility(View.VISIBLE);
              inch.setVisibility(View.VISIBLE);
          }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

        public double  calculateBmiMetrics(double Weightkg,double Heightcm)
    {
        return Weightkg/((Heightcm/100)*(Heightcm/100));
    }
    public double calculateBmiImperial(double heightft, double heightinch,double weightlbs )
    {
        double totalheight=heightinch+(12*heightft);
        return ((703*weightlbs)/(totalheight*totalheight));
    }
}