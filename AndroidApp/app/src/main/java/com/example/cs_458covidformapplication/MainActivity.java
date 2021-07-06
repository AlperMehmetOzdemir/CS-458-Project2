package com.example.cs_458covidformapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String name, lastName, dateOfBirth, city, vaccineType, sideEffects;
    Boolean confirmed;

    TextInputLayout nameLayout;
    TextInputEditText nameEditText;

    TextInputLayout lastNameLayout;
    TextInputEditText lastNameEditText;

    TextInputLayout cityLayout;
    AutoCompleteTextView citySelection;

    RadioGroup genderGroup;
    RadioButton genderMaleRadio;
    RadioButton genderFemaleRadio;
    RadioButton genderOtherRadio;

    TextInputLayout vaccineLayout;
    AutoCompleteTextView vaccineSelection;


    Button submitButton;
    TextView resultText;

    private static final String[] CITIES = new String[]{
            "Adana", "Ankara", "Diyarbakır", "İstanbul", "İzmir", "Malatya", "Manisa"
    };

    private static final String[] VACCINE_TYPES = new String[]{
            "Pfizer-BionTech", "Moderna", "Astrazeneca", "Sputnik V", "Sinovac", "Sinopharm"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hooks for getting data
        nameLayout = (TextInputLayout) findViewById(R.id.name);
        nameEditText = (TextInputEditText) nameLayout.getEditText();

        lastNameLayout = (TextInputLayout) findViewById(R.id.last_name);
        lastNameEditText = (TextInputEditText) lastNameLayout.getEditText();


        // setup city spinner
        cityLayout = (TextInputLayout) findViewById(R.id.city_layout);
        citySelection = (AutoCompleteTextView) findViewById(R.id.city_selection);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CITIES);
        citySelection.setAdapter(cityAdapter);
        citySelection.setValidator(new AutoCompleteTextView.Validator() {
            @Override
            public boolean isValid(CharSequence text) {
                Arrays.sort(CITIES);
                if (Arrays.binarySearch(CITIES, text.toString()) > 0) {
                    cityLayout.setError(null);
                    cityLayout.setErrorEnabled(false);
                    return true;
                }
                return false;
            }

            @Override
            public CharSequence fixText(CharSequence invalidText) {
                cityLayout.setError("Select a city from the list");
                return "";
            }
        });

        // setup gender selection
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        genderMaleRadio = (RadioButton) findViewById(R.id.genderMale);
        genderFemaleRadio = (RadioButton) findViewById(R.id.genderFemale);
        genderOtherRadio = (RadioButton) findViewById(R.id.genderOther);


        // setup vaccine type spinner
        vaccineLayout = (TextInputLayout) findViewById(R.id.vaccine_type);
        vaccineSelection = (AutoCompleteTextView) findViewById(R.id.vaccine_type_selection);
        ArrayAdapter<String> vaccineAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, VACCINE_TYPES);
        vaccineSelection.setAdapter(vaccineAdapter);
        vaccineSelection.setValidator(new AutoCompleteTextView.Validator() {
            @Override
            public boolean isValid(CharSequence text) {
                Arrays.sort(VACCINE_TYPES);
                if (Arrays.binarySearch(VACCINE_TYPES, text.toString()) > 0) {
                    vaccineLayout.setError(null);
                    vaccineLayout.setErrorEnabled(false);
                    return true;
                }
                return false;
            }

            @Override
            public CharSequence fixText(CharSequence invalidText) {
                vaccineLayout.setError("Select a vaccine from the list");
                return "";
            }
        });

        submitButton = (Button) findViewById(R.id.submitButton);
        resultText = (TextView) findViewById(R.id.resultMessage);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    resultText.setText("Form saved successfully");
                    resultText.setTextColor(Color.parseColor("#10870e"));
                    resultText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_24, 0, 0, 0);
                } else {
                    resultText.setText("Please check the information you provided");
                    resultText.setTextColor(Color.parseColor("#961e2c"));
                    resultText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error_24, 0, 0, 0);
                }
            }
        });
    }


    /*
    Validation functions
     */


    private boolean validateForm() {
        validateName();
        validateLastName();
        validateCitySelection();
        validateGender();
        validateVaccineSelection();

        return (validateName() &&
                validateLastName() &&
                validateCitySelection() &&
                validateGender() &&
                validateVaccineSelection());
    }

    private boolean validateName() {
        String nameVal = nameLayout.getEditText().getText().toString().trim();
        Pattern namePattern = Pattern.compile("^[ \\p{L}]+$");
        Matcher matcher = namePattern.matcher(nameVal);

        if (nameVal.isEmpty()) {
            nameLayout.setError("Field can not be empty.");
            return false;
        } else if (nameVal.length() > 20) {
            nameLayout.setError("Name can not be longer than 20 characters.");
            return false;
        } else if (!matcher.matches()) {
            nameLayout.setError("Can only contain letters and whitespace.");
            return false;
        } else {
            nameLayout.setError(null);
            nameLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName() {
        String nameVal = lastNameLayout.getEditText().getText().toString().trim();
        Pattern namePattern = Pattern.compile("^[ \\p{L}]+$");
        Matcher matcher = namePattern.matcher(nameVal);

        if (nameVal.isEmpty()) {
            lastNameLayout.setError("Field can not be empty.");
            return false;
        } else if (nameVal.length() > 20) {
            lastNameLayout.setError("Name can not be longer than 20 characters.");
            return false;
        } else if (!matcher.matches()) {
            lastNameLayout.setError("Can only contain letters and whitespace.");
            return false;
        } else {
            lastNameLayout.setError(null);
            lastNameLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCitySelection() {
        String cityVal = citySelection.getText().toString().trim();
        if (cityVal.equals("") || cityVal == null) {
            cityLayout.setError("City selection cannot be empty");
            return false;
        } else {
            cityLayout.setError(null);
            cityLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateVaccineSelection() {
        String vaccineVal = vaccineSelection.getText().toString().trim();
        if (vaccineVal.equals("") || vaccineVal == null) {
            vaccineLayout.setError("Vaccine selection cannot be empty");
            return false;
        } else {
            vaccineLayout.setError(null);
            vaccineLayout.setErrorEnabled(false);
            return true;
        }
    }

//    private boolean validateDate(@Nullable TextInputLayout dateLayout) {
//        String dateVal = dateLayout.getEditText().getText().toString().trim();
//        Pattern datePattern = Pattern.compile("\\{d}");
//
//        return false;
//    }

    private boolean validateGender() {
        TextView genderText = (TextView) findViewById(R.id.genderText);
        if (genderGroup.getCheckedRadioButtonId() == -1 || (
                !genderMaleRadio.isChecked() &&
                        !genderFemaleRadio.isChecked() &&
                        !genderOtherRadio.isChecked())) {

            genderText.setTextColor(Color.RED);
            genderText.setError("Gender must be selected");
            return false;
        } else {
            genderText.setTextColor(Color.parseColor("#808080"));
            genderText.setError(null);
            return true;
        }
    }


}
