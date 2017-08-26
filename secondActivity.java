package tdde.online.conorgildea.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        boolean ifMale = getIntent().getExtras().getBoolean("ifMale");
        int age = getIntent().getExtras().getInt("age");
        int height = getIntent().getExtras().getInt("height");
        double mass = getIntent().getExtras().getDouble("mass");
        double bodyfat = getIntent().getExtras().getDouble("bodyfatPercent");
        double heightInMetres = (double)(height*1.0)/(100);
        int activityLevel=getIntent().getExtras().getInt(("activityLevel"));
        double activityMultiplier = 1;
        double bmi = (mass/(heightInMetres*heightInMetres));
        switch (activityLevel) {
            case 0:
                activityMultiplier=1.2;
                break;
            case 1:
                activityMultiplier=1.375;
                break;
            case 2:
                activityMultiplier=1.550;
                break;
            case (3):
                activityMultiplier=1.725;
                break;
            default:
                activityMultiplier=1.2;
                break;
        }

        if(bodyfat==0)
        {
            TextView bmiResultText = (TextView) findViewById (R.id.bmiResult);
            TextView bmrResultText = (TextView) findViewById (R.id.bmrResult);
            TextView tddeResultText = (TextView) findViewById (R.id.tddeResult);
            //Mifflin = (10.m + 6.25h - 5.0a) + s
            //m is mass in kg, h is height in cm, a is age in years, s is +5 for males and -151 for females
            int formulaNumber=-1;
            if(ifMale)
            {
                formulaNumber = 5;
            }
            else
            {
                formulaNumber = -151;
            }
            //Women: BMR = 655 + (9.6 x weight in kg) + (1.8 x height in cm) - (4.7 x age in years)

            //Men: BMR = 66 + (13.7 x weight in kg) + (5 x height in cm) - (6.8 x age in years)
                double bmrDouble = (((10)*mass)+(6.25*height)-(5*age+formulaNumber));
            double tddeDouble = bmrDouble*activityMultiplier;
            int bmr = (int)bmrDouble;
            bmiResultText.setText(""+round(bmi,2));
            tddeResultText.setText(""+tddeDouble);
            bmrResultText.setText(""+bmr);
        }
        else{
            TextView bmiResultText = (TextView) findViewById (R.id.bmiResult);
            TextView bmrResultText = (TextView) findViewById (R.id.bmrResult);
            TextView tddeResultText = (TextView) findViewById (R.id.tddeResult);
            //Katch = 370 + (21.6 * LBM)
            double lbm = mass-(((bodyfat*mass)/100));
            double bmrDouble = (370+(21.6*lbm));
            double tddeDouble = bmrDouble*activityMultiplier;
            int bmr = (int) bmrDouble;
            tddeResultText.setText(""+tddeDouble);
            bmiResultText.setText(""+round(bmi,2));
            bmrResultText.setText(""+bmr);
        }

    }
    public void onReturn(View view)
    {
        Intent swapIntent = new Intent(this, MainActivity.class);
        startActivity(swapIntent);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
