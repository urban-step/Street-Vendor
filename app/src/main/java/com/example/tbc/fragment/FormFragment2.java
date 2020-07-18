package com.example.tbc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import com.example.tbc.R;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.AppSharedPrefernce;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class FormFragment2 extends Fragment {
    MaterialEditText scheme_other_mention;
    LinearLayout benefirLinlay, sourceloanLinlay,disability_layout;
    CheckBox checkBox_Cards;
    CheckBox checkBox_Cash,checkBox_Wallets,checkBox_benefit1,checkBox_benefit2,checkBox_benefit3,checkBox_benefit4,checkBox_benefit0;
    /* renamed from: no */
    MaterialEditText f51no;
    int position = 0;
    MaterialEditText bankname,branchname,yearsOfVending;

    /* renamed from: sb */
    openF3 f52sb;
    /* access modifiers changed from: private */
    Button submit;
    SurveyFormData surveyFormData;
    /* access modifiers changed from: private */
    public AppCompatSpinner uisp_abled,type_disability,readOrWrite;
    /* access modifiers changed from: private */
    public AppCompatSpinner uisp_qualificatioin,daily_income,idproof,bank_account,govtscheme,sourceLoan,other_qualification;
    LinearLayout linlay_other_qualification;

    public interface openF3 {
        void openFragment3();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        setRetainInstance(true);
        surveyFormData = (SurveyFormData) new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);
        if (surveyFormData == null) {
            surveyFormData = new SurveyFormData();
        }
        String str = "position";
        if (savedInstanceState == null && getArguments() != null) {
            position = getArguments().getInt(str, 0);
        }
        if (savedInstanceState == null && getArguments() != null) {
            position = getArguments().getInt(str, 0);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form2, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        submit = (Button) view.findViewById(R.id.form_bt_next);
        daily_income =  view.findViewById(R.id.daily_income);
        uisp_abled = (AppCompatSpinner) view.findViewById(R.id.activitySp_abled);
        uisp_qualificatioin = (AppCompatSpinner) view.findViewById(R.id.activitySp_qualification);
        f51no = (MaterialEditText) view.findViewById(R.id.no_of_member);
        disability_layout = view.findViewById(R.id.disability_layout);
        scheme_other_mention = view.findViewById(R.id.scheme_other_mention);
        yearsOfVending = view.findViewById(R.id.yearsOfVending);
        checkBox_Cash = (CheckBox) view.findViewById(R.id.checkBox_Cash);
        checkBox_Cards = (CheckBox) view.findViewById(R.id.checkBox_Cards);
        checkBox_Wallets = (CheckBox) view.findViewById(R.id.checkBox_Wallets);
        bank_account =  view.findViewById(R.id.bank_account);
        bankname = view.findViewById(R.id.bank_name);
        idproof = view.findViewById(R.id.proof_option);
        branchname = view.findViewById(R.id.branch_name);
        govtscheme = (AppCompatSpinner) view.findViewById(R.id.gov_scheme);
       // typebenefit =  view.findViewById(R.id.benefit);
        sourceLoan= view.findViewById(R.id.source_loan);
        sourceloanLinlay = view.findViewById(R.id.source_loan_layout);
        benefirLinlay = view.findViewById(R.id.type_benefit_layout);
        type_disability = view.findViewById(R.id.type_disability);
        readOrWrite  = view.findViewById(R.id.readOrWrite);
        other_qualification = view.findViewById(R.id.other_qualification);
        linlay_other_qualification = view.findViewById(R.id.linlay_other_qualification);
        checkBox_benefit1 = view.findViewById(R.id.checkBox_benefit1);
        checkBox_benefit2 = view.findViewById(R.id.checkBox_benefit2);
        checkBox_benefit3 = view.findViewById(R.id.checkBox_benefit3);
        checkBox_benefit4 = view.findViewById(R.id.checkBox_benefit4);
        checkBox_benefit0 = view.findViewById(R.id.checkBox_benefit0);

     //   adhaar = (MaterialEditText) view.findViewById(R.id.adhar_Card);
        submit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                String str2 = "select one";
                if (!f51no.getText().toString().equalsIgnoreCase(str) && !daily_income.getSelectedItem().equals(str2) && !uisp_abled.getSelectedItem().equals(str2)
                        && !uisp_qualificatioin.getSelectedItem().equals(str2)
                        && !bank_account.getSelectedItem().equals(str2) && !govtscheme.getSelectedItem().equals(str2)
                        && !yearsOfVending.getText().equals(str) && !readOrWrite.getSelectedItem().equals(str2) ) {


                    ArrayList<String> list = new ArrayList<>();
                    if (checkBox_Cash.isChecked() || checkBox_Cards.isChecked() || checkBox_Wallets.isChecked()) {

                        if (checkBox_Cash.isChecked()) {
                            list.add("cash");
                        }
                        if (checkBox_Cards.isChecked()) {
                            list.add("card");
                        }
                        if (checkBox_Wallets.isChecked()) {
                            list.add("wallet");
                        }
                    }  else {
                        Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JsonArray benefitList = new JsonArray();
                    if(govtscheme.getSelectedItem().equals("Yes")) {
                        if (checkBox_benefit1.isChecked() || checkBox_benefit2.isChecked() || checkBox_benefit3.isChecked() || checkBox_benefit4.isChecked() || checkBox_benefit0.isChecked()) {

                            if (checkBox_benefit1.isChecked()) {
                                benefitList.add(checkBox_benefit1.getText().toString());
                            }
                            if (checkBox_benefit2.isChecked()) {
                                benefitList.add(checkBox_benefit2.getText().toString());
                            }
                            if (checkBox_benefit3.isChecked()) {
                                benefitList.add(checkBox_benefit3.getText().toString());
                            }
                            if (checkBox_benefit4.isChecked()) {
                                benefitList.add(checkBox_benefit4.getText().toString());
                            }
                        } else {
                            Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                            surveyFormData.setPaymentOption(list.toString());
                            surveyFormData.setAbled(uisp_abled.getSelectedItem().toString());
                            surveyFormData.setTypes_of_abled(type_disability.getSelectedItem().toString());
                            surveyFormData.setReads_sec(readOrWrite.getSelectedItem().toString());
                            surveyFormData.setQualification(uisp_qualificatioin.getSelectedItem().toString());
                            surveyFormData.setFamilyEarning(f51no.getText().toString());
                            surveyFormData.setDailyIncome(daily_income.getSelectedItem().toString());
                            surveyFormData.setYears_vending(yearsOfVending.getText().toString());
                         surveyFormData.setBankAccount(bank_account.getSelectedItem().toString());
                         surveyFormData.setGovtScheme(govtscheme.getSelectedItem().toString());
                        surveyFormData.setTypeOfBenefit(benefitList.toString());
                        surveyFormData.setBankName(bankname.getText().toString());
                        surveyFormData.setBranchName(branchname.getText().toString());
                        surveyFormData.setOther_scheme_name(scheme_other_mention.getText().toString());
                        surveyFormData.setOther_qualification(other_qualification.getSelectedItem().toString());

                        AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson((Object) surveyFormData));
                        f52sb.openFragment3();


                } else {
                    Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_SHORT).show();
                }
            }});

        bank_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(bank_account.getSelectedItem().equals("Yes")){
                    bankname.setVisibility(View.VISIBLE);
                    branchname.setVisibility(View.VISIBLE);
                }else{
                    bankname.setVisibility(View.GONE);
                    branchname.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        govtscheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(govtscheme.getSelectedItem().equals("Yes")){
                    benefirLinlay.setVisibility(View.VISIBLE);
                }else{
                    benefirLinlay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        uisp_abled.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(uisp_abled.getSelectedItem().equals("Yes")){
                    disability_layout.setVisibility(View.VISIBLE);
                }else{
                    disability_layout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uisp_qualificatioin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(uisp_qualificatioin.getSelectedItem().equals("Other")){
                    linlay_other_qualification.setVisibility(View.VISIBLE);
                }else{
                    linlay_other_qualification.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox_benefit0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (isChecked) {
                                                           scheme_other_mention.setVisibility(View.VISIBLE);
                                                       } else {
                                                           scheme_other_mention.setVisibility(View.GONE);
                                                       }
                                                   }
                                               }
        );

    }

    public void updateView(int position2) {
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            f52sb = (openF3) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
