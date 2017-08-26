package tdde.online.conorgildea.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int age;
    private int height;
    private double bodyfatPercent = -1;
    private double mass;
    private int activityLevel=0;
    private boolean male = true;
    private boolean female = false;

    private RadioGroup radioGroup;
    private EditText ageInput;
    private EditText heightInput;
    private EditText bodyfatPercentage;
    private EditText massInput;

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_male:
                male = true;
                female = false;
                break;
            case R.id.rb_female:
                female = true;
                male = false;
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        final Spinner mySpinner = (Spinner) findViewById(R.id.activity_input);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.activity));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter((myAdapter));
        mySpinner.setSelection(0);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityLevel = mySpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onCalculate(View view) {
        try {
            ageInput = (EditText) findViewById(R.id.age_input);
            heightInput = (EditText) findViewById(R.id.height_input);
            bodyfatPercentage = (EditText) findViewById(R.id.bodyfat_input);
            massInput = (EditText) findViewById(R.id.mass_input);
            if (isEmpty(ageInput) && isEmpty(heightInput) && isEmpty(massInput) &&
                    !massInput.toString().equals(".") && !bodyfatPercentage.toString().equals(".")) {
                age = Integer.parseInt(ageInput.getText().toString());
                height = Integer.parseInt(heightInput.getText().toString());
                Intent passdata_intent = new Intent(this, secondActivity.class);
                if (isEmpty(bodyfatPercentage)) {
                    bodyfatPercent = Double.parseDouble(bodyfatPercentage.getText().toString());
                    passdata_intent.putExtra("bodyfatPercent", bodyfatPercent);
                } else {
                    passdata_intent.putExtra("bodyfatPercent", -1);
                }
                mass = Double.parseDouble(massInput.getText().toString());
                passdata_intent.putExtra("mass", mass);
                passdata_intent.putExtra("activityLevel", activityLevel);
                passdata_intent.putExtra("height", height);
                passdata_intent.putExtra("age", age);
                if (male == true) {
                    passdata_intent.putExtra("ifMale", true);
                } else {
                    passdata_intent.putExtra("ifMale", false);
                }
                if((age<=120)&&(age>=0)&&(height<=250)&&(bodyfatPercent<100)&&mass<500)
                {
                    startActivity(passdata_intent);
                }
                else
                {
                    Toast notFinished = new Toast(getApplicationContext());
                    notFinished.makeText(getApplicationContext(), "Your Figures Seem Incorrect, Please Enter Valid Figures.", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast notFinished = new Toast(getApplicationContext());
                notFinished.makeText(getApplicationContext(), "Please Complete The Necessary Sections Correctly", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast notFinished = new Toast(getApplicationContext());
            notFinished.makeText(getApplicationContext(), "Please Complete The Necessary Sections Correctly", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() != 0;
    }
}
