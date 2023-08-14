package com.sumeyyebuyukguclu.ckoguzelkalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText number1;
    EditText number2;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number1=findViewById(R.id.editTextTextPersonName);
        number2=findViewById(R.id.editTextTextPersonName2);
        result=findViewById(R.id.textView);
    }
    public void topla(View view)
    {
        int numb1 = Integer.parseInt(number1.getText().toString());
        int numb2 = Integer.parseInt(number2.getText().toString());

        int res =  numb1 + numb2;

        result.setText(String.valueOf(res));
    }

    public void çıkar(View view)
    {
        int numb1 = Integer.parseInt(number1.getText().toString());
        int numb2 = Integer.parseInt(number2.getText().toString());

        int res =  numb1 - numb2;

        result.setText(String.valueOf(res));
    }
}