package com.example.tbc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.tbc.R;
import com.example.tbc.activity.ServerForm;
import com.example.tbc.activity.VendorRegisteration;
import com.example.tbc.activity.VendorUtility;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.SelectImageDialog;
import com.google.gson.Gson;

public class FormFragmentVendor5 extends Fragment {
    private static FormFragmentVendor5 instance;

    AppCompatTextView registration_no;
    Button next;
    int position = 0;

    /* renamed from: sb */
    SelectImageDialog selectImageDialog;
    Button submit;
    SurveyFormData surveyFormData;
    ImageView imageView;



    private void setInstance(FormFragmentVendor5 instance2) {
        instance = instance2;
    }

    public static FormFragmentVendor5 getInstance() {
        return instance;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        setInstance(this);
        getActivity().setRequestedOrientation(1);
        setRetainInstance(true);
        this.surveyFormData =  new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);
        if (this.surveyFormData == null) {
            this.surveyFormData = new SurveyFormData();
        }
        if (savedInstanceState == null && getArguments() != null) {
            this.position = getArguments().getInt("position", 0);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form5_vendor, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        submit = view.findViewById(R.id.vendor_succesful_next);
        registration_no = view.findViewById(R.id.vendor_Succesful_registration);
        registration_no.setText("You are Registered, your Registration no: "+ VendorRegisteration.getInstance().registrationNo +"\n आपका पंजीकरण हो गया है |");
        AppSharedPrefernce.getInstance().setVendorData("");
        AppSharedPrefernce.getInstance().setPersistenceKeyVendorDpFile("");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VendorUtility.class);
                AppSharedPrefernce.getInstance().setIsVendorLogin(true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                VendorRegisteration.getInstance().finish();
            }
        });
    }

}
