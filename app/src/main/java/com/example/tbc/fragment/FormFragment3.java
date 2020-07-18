package com.example.tbc.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import com.example.tbc.R;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.SelectImageDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.rengwuxian.materialedittext.MaterialEditText;

public class FormFragment3 extends Fragment {
    private static FormFragment3 instance;

    MaterialEditText nameNominee,relationNominee,occupationNominee,ageNominee,aadharNominee,mobileNominee;
    Button next;
    int position = 0;

    /* renamed from: sb */
    OpenF4 f53sb;
    SelectImageDialog selectImageDialog;
    Button submit;
    SurveyFormData surveyFormData;
    CheckBox wallet;

    public interface OpenF4 {
        void openFragment4();
    }

    private void setInstance(FormFragment3 instance2) {
        instance = instance2;
    }

    public static FormFragment3 getInstance() {
        return instance;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        setInstance(this);
        setRetainInstance(true);
        this.surveyFormData = (SurveyFormData) new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);
        if (this.surveyFormData == null) {
            this.surveyFormData = new SurveyFormData();
        }
        if (savedInstanceState == null && getArguments() != null) {
            this.position = getArguments().getInt("position", 0);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form3, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
         submit = (Button) view.findViewById(R.id.form_bt_next);
         nameNominee = (MaterialEditText) view.findViewById(R.id.name_nominee);
         relationNominee = (MaterialEditText) view.findViewById(R.id.relationship_nominee);
         ageNominee = (MaterialEditText) view.findViewById(R.id.age_nominee);
         aadharNominee = (MaterialEditText) view.findViewById(R.id.aadhar_nominee);
         mobileNominee = view.findViewById(R.id.mobile_nominee);

         submit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                /*if (!nameNominee.getText().toString().equalsIgnoreCase(str) && !relationNominee.getText().equals(str) &&
                        !ageNominee.getText().toString().equalsIgnoreCase(str) &&
                        !aadharNominee.getText().toString().equalsIgnoreCase(str) && !mobileNominee.getText().toString().equalsIgnoreCase(str)) {*/
                    surveyFormData.setNomineeAge(ageNominee.getText().toString());
                    surveyFormData.setNomineeRelationship(relationNominee.getText().toString());
                   surveyFormData.setNameNominee(nameNominee.getText().toString());
                    surveyFormData.setAdharCardNominee(aadharNominee.getText().toString());
                    surveyFormData.setMobileNumberNominee(mobileNominee.getText().toString());

                    AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson((Object) surveyFormData));
                    f53sb.openFragment4();

                /*} else {
                    Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_SHORT).show();
                }*/

            }});

         }


        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            try {
                this.f53sb = (FormFragment3.OpenF4) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException("Error in retrieving data. Please try again");
            }
        }
}
