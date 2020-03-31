package com.example.wannahouse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentAddress extends Fragment {
    private ViewGroup viewGroup_district;
    private ViewGroup viewGroup_city;

    private TextView textView_city;
    private TextView textView_district;

    private TextInputEditText editText_ward;
    private TextInputEditText editText_streetName;
    private TextInputEditText editText_houseNumber;

    private TextInputLayout textInput_city;
    private TextInputLayout textInput_district;
    private TextInputLayout textInput_ward;
    private TextInputLayout textInput_streetName;
    private TextInputLayout textInput_houseNumber;

    private Button button_next2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        viewGroup_city = view.findViewById(R.id.viewGroup_city);
        viewGroup_district = view.findViewById(R.id.viewGroup_district);

        textInput_city = view.findViewById(R.id.text_input_city);
        textView_city = view.findViewById(R.id.textView_city);
        textInput_district = view.findViewById(R.id.text_input_district);
        textView_district = view.findViewById(R.id.textView_district);
        textInput_ward = view.findViewById(R.id.text_input_ward);
        textInput_streetName = view.findViewById(R.id.text_input_streetName);
        textInput_houseNumber = view.findViewById(R.id.text_input_houseNumber);

        button_next2 = view.findViewById(R.id.next2);

        viewGroup_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialog(R.array.choose_city);
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getFragmentManager(), "Choose City");
            }
        });

        viewGroup_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialog(R.array.choose_district_HaNoi);
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getFragmentManager(), "Choose District");
            }
        });

        button_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next2(v);
            }
        });
        return view;
    }

    private boolean validate_city() {
        String city = textView_city.getText().toString().trim();
        Log.d("KEYAABB", city);
        if (city.isEmpty()) {
            textInput_city.setError("Press to choose your city");
            return false;
        } else {
            textInput_city.setError(null);
            return true;
        }
    }

    private boolean validate_district() {

        String district = textView_district.getText().toString().trim();
        //city = "safsd";
        Log.d("KEYAABB", district);
        if (district.isEmpty()) {
            textInput_district.setError("Press to choose your district");
            return false;
        } else {
            textInput_district.setError(null);
            return true;
        }
    }

    private boolean validate_ward() {

        String ward = textInput_ward.getEditText().getText().toString().trim();
        if (ward.isEmpty()) {
            textInput_ward.setError("Please enter the number of unit/address");
            textInput_ward.setHintEnabled(false);
            return false;
        } else {
            textInput_ward.setError(null);
            return true;
        }
    }

    private boolean validate_streetName() {

        String streetName = textInput_streetName.getEditText().getText().toString().trim();
        if (streetName.isEmpty()) {
            textInput_streetName.setError("Please enter your ward");
            textInput_streetName.setHintEnabled(false);
            return false;
        } else {
            textInput_streetName.setError(null);
            return true;
        }
    }

    private boolean validate_houseNumber() {

        String houseNumber = textInput_houseNumber.getEditText().getText().toString().trim();
        if (houseNumber.isEmpty()) {
            textInput_houseNumber.setError("Please enter your ward");
            textInput_houseNumber.setHintEnabled(false);
            return false;
        } else {
            textInput_houseNumber.setError(null);
            return true;
        }
    }

    public void next2(View v) {
        if (!validate_city() | !validate_district() | !validate_ward() |
                !validate_streetName() | !validate_houseNumber()) return;
        else {
            ((ListYourSpaceActivity) getActivity()).next22(v);
        }
    }

}
