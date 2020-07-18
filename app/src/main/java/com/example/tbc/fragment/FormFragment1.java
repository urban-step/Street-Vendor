package com.example.tbc.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.tbc.R;
import com.example.tbc.activity.ServerForm;
import com.example.tbc.activity.VendorRegisteration;
import com.example.tbc.model.City;
import com.example.tbc.model.State;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.multispinner.*;

public class FormFragment1 extends Fragment{
    private static FormFragment1 instance;
    MaterialEditText vendorName, age, homeHouseNo, homeLocality, homePincode, aadharno, ward;
    MaterialEditText vendingHouseNo, vendingLocality, vendingPincode, mobileno, vendingWard, please_mention;
    Button next;
    int position = 0;

    /* renamed from: sm */
    OpenF1 f50sm;
    SurveyFormData surveyFormData;
    /* access modifiers changed from: private */
    public AppCompatSpinner  homeState, homeCity, vendingState, vendingCity;
    /* access modifiers changed from: private */

    public LinearLayout linlay_nature_6am, linlay_nature_9am, linlay_nature_12pm, linlay_nature_3pm, linlay_nature_6pm;
    /* access modifiers changed from: private */
    public CheckBox checkBox6am, checkBox9am, checkBox12pm, checkBox3pm, checkBox6pm;

    // public AppCompatSpinner uisp_timing;
    List<State> stateList=new ArrayList<State>();
    List<City> cityList = new ArrayList<>();

    ArrayList<String> timing = new ArrayList<>();


    public interface OpenF1 {
        void openFragment2();
    }

    ProgressDialog progressDialog;
    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;

    TextView locationOfVendingGps;
    MultiSelectSpinner multiSelectCommodity,uisp_nature6am, uisp_nature9am, uisp_nature12pm, uisp_nature3pm, uisp_nature6pm;


    private void setInstance(FormFragment1 instance2) {
        instance = instance2;
    }

    public static FormFragment1 getInstance() {
        return instance;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInstance(this);
        Intent intent = getActivity().getIntent();
        setRetainInstance(true);
        surveyFormData = (SurveyFormData) new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);
        if (surveyFormData == null) {
            surveyFormData = new SurveyFormData();
        }
        if (savedInstanceState == null && getArguments() != null) {
            position = getArguments().getInt("position", 0);
        }
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        stateList.add(new State(0,"select one"));

        generateStateList();


    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form1, parent, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        //uisp_commodity = view.findViewById(R.id.activitySp_commodity);
        uisp_nature6am = view.findViewById(R.id.activitySp_nature_6am);
        uisp_nature9am = view.findViewById(R.id.activitySp_nature_9am);
        uisp_nature12pm = view.findViewById(R.id.activitySp_nature_12pm);
        uisp_nature3pm = view.findViewById(R.id.activitySp_nature_3pm);
        uisp_nature6pm = view.findViewById(R.id.activitySp_nature_6pm);
        checkBox6am = view.findViewById(R.id.checkBox_6am);
        checkBox9am = view.findViewById(R.id.checkBox_9am);
        checkBox12pm = view.findViewById(R.id.checkBox_12pm);
        checkBox3pm = view.findViewById(R.id.checkBox_3pm);
        checkBox6pm = view.findViewById(R.id.checkBox_6pm);

        linlay_nature_6am = view.findViewById(R.id.linlay_nature_6am);
        linlay_nature_9am = view.findViewById(R.id.linlay_nature_9am);
        linlay_nature_12pm = view.findViewById(R.id.linlay_nature_12pm);
        linlay_nature_3pm = view.findViewById(R.id.linlay_nature_3pm);
        linlay_nature_6pm = view.findViewById(R.id.linlay_nature_6pm);

        vendorName = view.findViewById(R.id.vendor_name);
        homeHouseNo = view.findViewById(R.id.home_houseno);
        homeLocality = view.findViewById(R.id.home_locality);
        homeCity = view.findViewById(R.id.home_city);
        homePincode = view.findViewById(R.id.home_pincode);
        homeState = view.findViewById(R.id.home_state);
        vendingHouseNo = view.findViewById(R.id.houseno);
        vendingCity = view.findViewById(R.id.city);
        vendingLocality = view.findViewById(R.id.locality);
        vendingPincode = view.findViewById(R.id.pincode);
        vendingState = view.findViewById(R.id.state);
        mobileno = view.findViewById(R.id.vendor_number);
        ward = view.findViewById(R.id.home_wardzone);
        vendingWard = view.findViewById(R.id.wardzone);
        aadharno = view.findViewById(R.id.adhar_Card);
        please_mention = view.findViewById(R.id.please_mention);

        locationOfVendingGps = view.findViewById(R.id.registration_locationOfVendingGps);

        age = view.findViewById(R.id.age);
        next = view.findViewById(R.id.form_bt_next);

        multiSelectCommodity = (MultiSelectSpinner) view.findViewById(R.id.activitySp_commodity);
        multiSelectCommodity.setItems(getResources().getStringArray(R.array.commodityOptions));
        multiSelectCommodity.hasNoneOption(true);
        multiSelectCommodity.setSelection(new int[]{0});

        uisp_nature6am.setItems(getResources().getStringArray(R.array.natureVendingOptions));
        uisp_nature6am.hasNoneOption(true);
        uisp_nature6am.setSelection(new int[]{0});

        uisp_nature3pm.setItems(getResources().getStringArray(R.array.natureVendingOptions));
        uisp_nature3pm.hasNoneOption(true);
        uisp_nature3pm.setSelection(new int[]{0});

        uisp_nature6pm.setItems(getResources().getStringArray(R.array.natureVendingOptions));
        uisp_nature6pm.hasNoneOption(true);
        uisp_nature6pm.setSelection(new int[]{0});

        uisp_nature9am.setItems(getResources().getStringArray(R.array.natureVendingOptions));
        uisp_nature9am.hasNoneOption(true);
        uisp_nature9am.setSelection(new int[]{0});

        uisp_nature12pm.setItems(getResources().getStringArray(R.array.natureVendingOptions));
        uisp_nature12pm.hasNoneOption(true);
        uisp_nature12pm.setSelection(new int[]{0});


        next.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                String str2 = "select one";
                if (!ServerForm.getInstance().isGPS) {
                    Toast.makeText(ServerForm.getInstance(),"Turn on GPS and provide your current location by clicking 'location of vending'",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!vendorName.getText().toString().equalsIgnoreCase(str) && !age.getText().toString().equalsIgnoreCase(str)
                        && !mobileno.getText().toString().equalsIgnoreCase(str)) {

                    surveyFormData.setVendorName(vendorName.getText().toString());
                    surveyFormData.setAdharCard(aadharno.getText().toString());
                    surveyFormData.setHomeNumber(homeHouseNo.getText().toString());
                    surveyFormData.setAge(age.getText().toString());
                    surveyFormData.setLocality(homeLocality.getText().toString());
                    surveyFormData.setVendorLocality(vendingLocality.getText().toString());
                    surveyFormData.setVendorHomeNumber(vendingHouseNo.getText().toString());
                    surveyFormData.setVendorCity(vendingCity.getSelectedItem().toString());
                    surveyFormData.setCity(homeCity.getSelectedItem().toString());
                    surveyFormData.setState(homeState.getSelectedItem().toString());
                    surveyFormData.setVendorState(vendingState.getSelectedItem().toString());
                    surveyFormData.setPinCode(homePincode.getText().toString());
                    surveyFormData.setVendorPinCode(vendingPincode.getText().toString());
                    surveyFormData.setTypeCommodity(multiSelectCommodity.getSelectedItemsAsString());
                    surveyFormData.setOther_commodity_option(please_mention.getText().toString());

                    surveyFormData.setTiming(timing.toString());
                    surveyFormData.setWardZone(ward.getText().toString());
                    surveyFormData.setVendorWardZone(vendingWard.getText().toString());
                    surveyFormData.setMobileNumber(mobileno.getText().toString());
                    AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson((Object) surveyFormData));
                    f50sm.openFragment2();

                } else {
                    Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* uisp_commodity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
        homeState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //  String spinnerServiceType = uisp_commodity.getItemAtPosition(i).toString();
                generateCityList(stateList.get(i).id,"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vendingState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                generateCityList(stateList.get(i).id,"vendor");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        checkBox6am.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (isChecked) {
                                                           linlay_nature_6am.setVisibility(View.VISIBLE);
                                                           timing.add("6am - 9am");
                                                       } else {
                                                           linlay_nature_6am.setVisibility(View.GONE);
                                                           timing.remove("6am - 9am");

                                                       }
                                                   }
                                               }
        );

        checkBox9am.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (isChecked) {
                                                           linlay_nature_9am.setVisibility(View.VISIBLE);
                                                           timing.add("9am - 12pm");
                                                       } else {
                                                           linlay_nature_9am.setVisibility(View.GONE);
                                                           timing.remove("9am - 12pm");

                                                       }
                                                   }
                                               }
        );

        checkBox12pm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if (isChecked) {
                                                            linlay_nature_12pm.setVisibility(View.VISIBLE);
                                                            timing.add("12pm - 3pm");

                                                        } else {
                                                            linlay_nature_12pm.setVisibility(View.GONE);
                                                            timing.remove("12pm - 3pm");
                                                        }
                                                    }
                                                }
        );

        checkBox3pm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (isChecked) {
                                                           linlay_nature_3pm.setVisibility(View.VISIBLE);
                                                           timing.add("3pm - 6pm");
                                                       } else {
                                                           linlay_nature_3pm.setVisibility(View.GONE);
                                                           timing.remove("3pm - 6pm");
                                                       }
                                                   }
                                               }
        );
        checkBox6pm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (isChecked) {
                                                           linlay_nature_6pm.setVisibility(View.VISIBLE);
                                                           timing.add("6pm - 9pm");

                                                       } else {
                                                           linlay_nature_6pm.setVisibility(View.GONE);
                                                           timing.remove("6pm - 9pm");
                                                       }
                                                   }
                                               }
        );

        locationOfVendingGps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerForm.getInstance().callForLocation();
            }
        });


        multiSelectCommodity.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {

            }

            @Override
            public void selectedStrings(List<String> strings) {
                if (strings.contains("Other")) {
                    please_mention.setVisibility(View.VISIBLE);
                } else {
                    please_mention.setVisibility(View.GONE);
                }
            }
        });
        uisp_nature6am.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }
            @Override
            public void selectedStrings(List<String> strings) {
                surveyFormData.setSix_am(uisp_nature6am.getSelectedItemsAsString().toString());
            }
        });

        uisp_nature9am.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }
            @Override
            public void selectedStrings(List<String> strings) {
                surveyFormData.setNine_am(uisp_nature9am.getSelectedItemsAsString().toString());
            }
        });

        uisp_nature12pm.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }
            @Override
            public void selectedStrings(List<String> strings) {
                surveyFormData.setTwell_pm(uisp_nature12pm.getSelectedItemsAsString());
            }
        });

        uisp_nature3pm.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }
            @Override
            public void selectedStrings(List<String> strings) {
                surveyFormData.setThree_pm(uisp_nature3pm.getSelectedItemsAsString());
            }
        });

        uisp_nature6pm.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }
            @Override
            public void selectedStrings(List<String> strings) {
                surveyFormData.setSix_pm(uisp_nature6pm.getSelectedItemsAsString());
            }
        });


    }

    public void updateView(int position2) {
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.f50sm = (OpenF1) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public void generateStateList() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(getContext(),"check your internet connection!",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.show();
        ApiClient.getApiService().getStateList().enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissWithTryCatch(progressDialog);
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("REAL_ESTATE_APP");
                        Type type = new TypeToken<ArrayList<State>>(){}.getType();
                        stateList = (new Gson()).fromJson(jsonArray.toString(), type);
                        stateArrayAdapter = new ArrayAdapter<State>( ServerForm.getInstance(), R.layout.support_simple_spinner_dropdown_item, stateList);
                        ServerForm.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                homeState.setAdapter(stateArrayAdapter);
                                vendingState.setAdapter(stateArrayAdapter );
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissWithTryCatch(progressDialog);
            }
        });
    }

    public void generateCityList(int id,String citytype){
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(getContext(),"check your internet connection! ",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.show();
        ApiClient.getApiService().getCityList(id).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissWithTryCatch(progressDialog);
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("REAL_ESTATE_APP");
                        Type type = new TypeToken<ArrayList<City>>(){}.getType();
                        cityList = (new Gson()).fromJson(jsonArray.toString(), type);
                        cityArrayAdapter = new ArrayAdapter<City>(getContext(), R.layout.support_simple_spinner_dropdown_item, cityList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!citytype.equals("vendor")) {
                                    homeCity.setAdapter(cityArrayAdapter);
                                }else
                                    vendingCity.setAdapter(cityArrayAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissWithTryCatch(progressDialog);

            }
        });
    }
    public void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
        } catch (final Exception e) {
            // Do nothing.
        } finally {
            dialog = null;
        }
    }


}