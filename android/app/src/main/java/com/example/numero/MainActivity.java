package com.example.numero;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNumber = findViewById(R.id.tv_number);
        FloatingActionButton fab = findViewById(R.id.fab);


        tvNumber.setText(getDayNumber());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNumber.setText(Integer.toString(getRandomNumber(1,9)));
            }
        });
    }

    private String getDayNumber() {
        Date dateNow = new Date();
        SimpleDateFormat simpleDate =  new SimpleDateFormat("MM/dd/yyyy");
        String strDateNow = simpleDate.format(dateNow)+" 00:00:00 AM";


        String dateStringThen = "01/01/0001 00:00:00 AM";
        Date convertedDateThen  = getDateByString(dateStringThen);
        Date convertedDateNow = getDateByString(strDateNow);

        long timeInBetween = getUnitBetweenDates(convertedDateThen ,convertedDateNow, TimeUnit.DAYS)+1;

        String strSumOfDigits = Long.toString( sumOfDigits((int) timeInBetween));

        return strSumOfDigits;
    }

    private Date getDateByString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            tvNumber.setText(getDayNumber());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public int sumOfDigits(int num)
    {
        int sum = 0;

        while (num > 0)
        {
            sum = sum + num % 10;
            num = num / 10;
        }

        sum = (sum <10) ? sum : sumOfDigits(sum);

        return sum;
    }
}
