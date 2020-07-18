package com.example.tbc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.tbc.R;
import com.example.tbc.activity.ServerForm;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.SelectImageDialog;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

public class FormFragment5 extends Fragment {
    private static FormFragment5 instance;

    AppCompatTextView name,registration_no;
    Button next;
    int position = 0;

    /* renamed from: sb */
    SelectImageDialog selectImageDialog;
    Button submit;
    SurveyFormData surveyFormData;
    ImageView imageView;



    private void setInstance(FormFragment5 instance2) {
        instance = instance2;
    }

    public static FormFragment5 getInstance() {
        return instance;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        setInstance(this);
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
        return inflater.inflate(R.layout.fragment_form5, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
      //  submit = view.findViewById(R.id.form_bt_next);
        name = view.findViewById(R.id.surveySubmission_name);
        imageView = view.findViewById(R.id.fragment_userDp);
        registration_no = view.findViewById(R.id.surveySubmission_registration);
        name.setText("1. Vendor Name : "+surveyFormData.getVendorName());
        registration_no.setText("2. Registration no : "+ServerForm.getInstance().registrationNo);
        ServerForm.getInstance().GlideImageViewer(AppSharedPrefernce.getInstance().getPersistenceKeyVendorDpFile(),R.drawable.user_defaultbio,imageView);
        AppSharedPrefernce.getInstance().setVendorData("");
        AppSharedPrefernce.getInstance().setPersistenceKeyVendorDpFile("");
    }



}
