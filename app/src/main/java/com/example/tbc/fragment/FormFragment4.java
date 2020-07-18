package com.example.tbc.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.tbc.R;
import com.example.tbc.activity.ServerForm;
import com.example.tbc.activity.VendorRegisteration;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.FileEnum;
import com.example.tbc.utils.SelectImageDialog;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

public class FormFragment4 extends Fragment {
    private static FormFragment4 instance;
    MaterialEditText amount,licenseno,challanno;
    ImageView card;
    ImageView cash;
    ImageView userPic,VendorPic,idCardPic,licensePic,challanPic;

    Boolean isCash = Boolean.valueOf(false);
    int position = 0;
    AppCompatSpinner licenseOrchallan,proofOption;
    LinearLayout linlay_licenseorchallan;

    /* renamed from: sb */
    OpenF5 f54sb;
    SelectImageDialog selectImageDialog;
    Button submit;
    SurveyFormData surveyFormData;
    ImageView wallet;
    LinearLayout linlay_idcard;
    /*public interface Submit {
        void callSubmitApi();
    }*/

    private void setInstance(FormFragment4 instance2) {
        instance = instance2;
    }

    public static FormFragment4 getInstance() {
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
        return inflater.inflate(R.layout.fragment_form4, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.submit = (Button) view.findViewById(R.id.form_bt_next);
        this.cash = (ImageView) view.findViewById(R.id.frag4_iv_cash);
        this.card = (ImageView) view.findViewById(R.id.frag4_iv_card);
        this.wallet = (ImageView) view.findViewById(R.id.frag4_iv_wallet);
        this.amount = (MaterialEditText) view.findViewById(R.id.amount_earned);
        licenseno = view.findViewById(R.id.license_number) ;
        challanno = view.findViewById(R.id.challan_number) ;
        proofOption = view.findViewById(R.id.proof_option) ;
        linlay_idcard = view.findViewById(R.id.linlay_idcard);
        licenseOrchallan = view.findViewById(R.id.licenseOrchallan);
        linlay_licenseorchallan = view.findViewById(R.id.linlay_licenseorchallan);
        this.cash.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!FormFragment4.this.isCash.booleanValue()) {
                    FormFragment4.this.cash.setBackground(FormFragment4.this.getResources().getDrawable(R.drawable.green_background));
                    FormFragment4.this.isCash = Boolean.valueOf(true);
                    amount.setVisibility(View.VISIBLE);
                    return;
                }
                FormFragment4.this.cash.setBackground(FormFragment4.this.getResources().getDrawable(R.drawable.black_background));
                FormFragment4.this.isCash = Boolean.valueOf(false);
                amount.setVisibility(View.GONE);
            }
        });

        this.userPic = (ImageView) view.findViewById(R.id.fragment_userDp);
        this.userPic.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                 showImageDialog(ServerForm.getInstance(), FileEnum.file.toString());
            }
        });
        this.VendorPic = (ImageView) view.findViewById(R.id.vendor_stall_pic);
        this.VendorPic.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showImageDialog(ServerForm.getInstance(),FileEnum.file_stall.toString());
            }
        });
        this.idCardPic = (ImageView) view.findViewById(R.id.id_card_pic);
        this.idCardPic.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showImageDialog(ServerForm.getInstance(),FileEnum.file_id.toString());
            }
        });
        this.licensePic = (ImageView) view.findViewById(R.id.license_pic);
        this.licensePic.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showImageDialog(ServerForm.getInstance(),FileEnum.file_license.toString());
            }
        });
        this.challanPic = (ImageView) view.findViewById(R.id.challan_pic);
        this.challanPic.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showImageDialog(ServerForm.getInstance(),FileEnum.file_callan.toString());
            }
        });
        this.submit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!FormFragment4.this.isCash.booleanValue() ) {
                    Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_LONG).show();
                    return;
                }


                    surveyFormData.setLicense(licenseOrchallan.getSelectedItem().toString());
                    FormFragment4.this.surveyFormData.setAmount(amount.getText().toString());
                    FormFragment4.this.surveyFormData.setRegistrationPayment("cash");
                    surveyFormData.setLicenseNumber(licenseno.getText().toString());
                    surveyFormData.setCallan_number(challanno.getText().toString());
                    surveyFormData.setIdProof(proofOption.getSelectedItem().toString());
                    AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson((Object) FormFragment4.this.surveyFormData));
                    ServerForm.getInstance().callSubmitApi();
                    AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson(surveyFormData));;
            }
        });

        licenseOrchallan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(licenseOrchallan.getSelectedItem().equals("Yes")){
                    linlay_licenseorchallan.setVisibility(View.VISIBLE);
                }else{
                    linlay_licenseorchallan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proofOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(proofOption.getSelectedItem().equals("None")){
                    linlay_idcard.setVisibility(View.GONE);
                }else{
                    linlay_idcard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void updateView(int position2) {
    }

    /* access modifiers changed from: private */
    public void showImageDialog(AppCompatActivity activity, String file) {
        ServerForm.getInstance().konsiPic =file;
        this.selectImageDialog = new SelectImageDialog(activity,file);
        if (!this.selectImageDialog.isShowing()) {
            this.selectImageDialog.show();
        }
    }

    public void updateSelectedImage(String file,Uri bitmap,String url) {
        SurveyFormData surveyFormData = new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(),SurveyFormData.class);
        if(file.equalsIgnoreCase(FileEnum.file.toString())) {
            userPic.setImageURI(bitmap);
            AppSharedPrefernce.getInstance().setPersistenceKeyVendorDpFile(url);
            surveyFormData.setFile(url);
        }
        if(file.equalsIgnoreCase(FileEnum.file_id.toString())) {
            idCardPic.setImageURI(bitmap);
            surveyFormData.setFile_id(url);
        }
        if(file.equalsIgnoreCase(FileEnum.file_stall.toString())) {
            VendorPic.setImageURI(bitmap);
            AppSharedPrefernce.getInstance().setPersistenceKeyVendorStallFile(url);
            surveyFormData.setFile_stall(url);
        }
        if(file.equalsIgnoreCase(FileEnum.file_license.toString())) {
            licensePic.setImageURI(bitmap);
            surveyFormData.setFile_license(url);
        }
        if(file.equalsIgnoreCase(FileEnum.file_callan.toString())) {
            challanPic.setImageURI(bitmap);
            surveyFormData.setFile_callan(url);
        }
    }

    public interface OpenF5 {
        void openFragment5();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.f54sb = (OpenF5) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
