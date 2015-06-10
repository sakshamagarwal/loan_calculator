package com.example.saksham.loancalculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    EditText principal_input,rate_input,year_input,month_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        principal_input = (EditText)findViewById(R.id.editText);
        rate_input = (EditText)findViewById(R.id.editText2);
        year_input = (EditText)findViewById(R.id.editText3);
        month_input = (EditText)findViewById(R.id.editText4);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void calculate(View view) {
        hideKeyboard();
        String principal = principal_input.getText().toString();
        String rate = rate_input.getText().toString();
        String years = year_input.getText().toString();
        String months = month_input.getText().toString();
        TextView t = (TextView)findViewById(R.id.emi_text);
        TextView interest = (TextView)findViewById(R.id.textView6);
        interest.setText("");
        TextView total = (TextView)findViewById(R.id.textView7);
        total.setText("");
        try {
            int P = Integer.parseInt(principal);
            double R = Double.parseDouble(rate);
            double Y;
            try {
                Y = Double.parseDouble(years);
            }
            catch (Exception e) {
                Y = 0;
            }
            int M;
            try {
                M = Integer.parseInt(months);
            }
            catch (Exception e) {
                M = 0;
            }
            if ((Y*12) != (int)(Y*12)) {
                t.setText("Tenure needs to be in multiples of 1 month");
            }
            else {
                int n = M + (int)(Y*12);
                if (n==0) {
                    t.setText("Tenure cannot be 0");
                }
                else {
                    double r = R / 1200;
                    double temp = Math.pow(1 + r, n);
                    double E = P * r * temp / (temp - 1);
                    t.setText("Loan EMI = " + E);
                    double amount = E * n;
                    double ip = amount - P;
                    interest.setText("Total Interest Payable = " + ip);
                    total.setText("Total Payment = " + amount);
                }
            }


        } catch (Exception e) {
            t.setText("Invalid Loan Request");
        }

    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
