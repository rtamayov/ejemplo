package com.example.numero;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView tvNumber;
    TextView tvPopWindows;

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

                onButtonShowPopupWindowClick(tvNumber);

            }
        });
    }

    private String getDayNumber() {
        Date dateNow = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
        String strDateNow = simpleDate.format(dateNow) + " 00:00:00 AM";


        String dateStringThen = "01/01/0001 00:00:00 AM";
        Date convertedDateThen = getDateByString(dateStringThen);
        Date convertedDateNow = getDateByString(strDateNow);

        long timeInBetween = getUnitBetweenDates(convertedDateThen, convertedDateNow, TimeUnit.DAYS) + 1;

        String strSumOfDigits = Long.toString(sumOfDigits((int) timeInBetween));

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


    private int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public int sumOfDigits(int num) {
        int sum = 0;

        while (num > 0) {
            sum = sum + num % 10;
            num = num / 10;
        }

        sum = (sum < 10) ? sum : sumOfDigits(sum);

        return sum;
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        tvPopWindows = popupView.findViewById(R.id.pw_text);
        tvPopWindows.setText(Integer.toString(getRandomNumber(1,9)));
    }
}
