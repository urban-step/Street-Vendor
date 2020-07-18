package com.example.tbc.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.tbc.R;
import com.example.tbc.activity.ServerForm;
import com.example.tbc.activity.VendorRegisteration;
import com.example.tbc.adapter.CommodityImageAdapter;
import com.example.tbc.model.City;
import com.example.tbc.model.State;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.FileEnum;
import com.example.tbc.utils.SelectImageDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.multispinner.MultiSelectSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragmentVendor1 extends Fragment {
    private static FormFragmentVendor1 instance;
    MaterialEditText vendorName, age, homeHouseNo, homeLocality, homePincode, aadharno, ward;
    MaterialEditText vendingHouseNo, vendingLocality, vendingPincode, mobileno, vendingWard;

    Button next;
    TextView locationOfVendingGps;
    int position = 0;
    GridView gridView;
    ImageView card;
    ImageView cash,wallet;
    OpenF1 f50sm;
    SurveyFormData surveyFormData;
    public AppCompatSpinner uisp_commodity, homeState, homeCity, vendingState, vendingCity;
    public MultiSelectSpinner uisp_nature6am, uisp_nature9am, uisp_nature12pm, uisp_nature3pm, uisp_nature6pm;
    public LinearLayout linlay_nature_6am, linlay_nature_9am, linlay_nature_12pm, linlay_nature_3pm, linlay_nature_6pm;
    public CheckBox checkBox6am, checkBox9am, checkBox12pm, checkBox3pm, checkBox6pm;
    public ImageView imageView;
    // public AppCompatSpinner uisp_timing;
    List<State> stateList = new ArrayList<State>();
    List<City> cityList = new ArrayList<>();
    List<String> listPayment=  new ArrayList<>();
    Boolean isCash = Boolean.valueOf(false);
    Boolean isCard = Boolean.valueOf(false);
    Boolean isWallet = Boolean.valueOf(false);
    String selectedItem;
    LinearLayout GridViewItems,BackSelectedItem;
    int backposition = -1;

    ArrayList<String> timing= new ArrayList<>();
    public interface OpenF1 {
        void openFragment2();
    }

    SelectImageDialog selectImageDialog;
    ProgressDialog progressDialog;
    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;
    private String commodity;
    public String[] mTexts= {"Clothes","fruits and vegetables","Prepared food","Mobile Accessories","Electronics Appliances","Jewellery","Shoes",
            "Leather Items","Tea and Coffee","Juices","Make-up","Home Utility"};
    public List<String> selectedCommodities = new ArrayList<>();
    CommodityImageAdapter adapter;

    private void setInstance(FormFragmentVendor1 instance2) {
        instance = instance2;
    }

    public static FormFragmentVendor1 getInstance() {
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
        stateList.add(new State(0, "select one"));

        generateStateList();
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form1_vendor, parent, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        //uisp_commodity = view.findViewById(R.id.registration_activitySp_commodity);
        uisp_nature6am = view.findViewById(R.id.registration_activitySp_nature_6am);
        uisp_nature9am = view.findViewById(R.id.registration_activitySp_nature_9am);
        uisp_nature12pm = view.findViewById(R.id.registration_activitySp_nature_12pm);
        uisp_nature3pm = view.findViewById(R.id.registration_activitySp_nature_3pm);
        uisp_nature6pm = view.findViewById(R.id.registration_activitySp_nature_6pm);
        checkBox6am = view.findViewById(R.id.registration_checkBox_6am);
        checkBox9am = view.findViewById(R.id.registration_checkBox_9am);
        checkBox12pm = view.findViewById(R.id.registration_checkBox_12pm);
        checkBox3pm = view.findViewById(R.id.registration_checkBox_3pm);
        checkBox6pm = view.findViewById(R.id.registration_checkBox_6pm);

        linlay_nature_6am = view.findViewById(R.id.registration_linlay_nature_6am);
        linlay_nature_9am = view.findViewById(R.id.registration_linlay_nature_9am);
        linlay_nature_12pm = view.findViewById(R.id.registration_linlay_nature_12pm);
        linlay_nature_3pm = view.findViewById(R.id.registration_linlay_nature_3pm);
        linlay_nature_6pm = view.findViewById(R.id.registration_linlay_nature_6pm);

        vendorName = view.findViewById(R.id.registration_vendor_name);
        homeHouseNo = view.findViewById(R.id.registration_home_houseno);
        homeLocality = view.findViewById(R.id.registration_home_locality);
        homeCity = view.findViewById(R.id.registration_home_city);
        homePincode = view.findViewById(R.id.registration_home_pincode);
        homeState = view.findViewById(R.id.registration_home_state);
        vendingHouseNo = view.findViewById(R.id.registration_houseno);
        vendingCity = view.findViewById(R.id.registration_city);
        vendingLocality = view.findViewById(R.id.registration_locality);
        vendingPincode = view.findViewById(R.id.registration_pincode);
        vendingState = view.findViewById(R.id.registration_state);
        mobileno = view.findViewById(R.id.registration_vendor_number);
        ward = view.findViewById(R.id.registration_home_wardzone);
        vendingWard = view.findViewById(R.id.registration_wardzone);
        aadharno = view.findViewById(R.id.registration_adhar_Card);

        locationOfVendingGps = view.findViewById(R.id.registration_locationOfVendingGps);
        imageView = view.findViewById(R.id.registeration_vendor_stall_pic);

        this.cash = (ImageView) view.findViewById(R.id.frag4_iv_cash);
        this.card = (ImageView) view.findViewById(R.id.frag4_iv_card);
        this.wallet = (ImageView) view.findViewById(R.id.frag4_iv_wallet);

        gridView = view.findViewById(R.id.registration_activitySp_commodity);
        adapter = new CommodityImageAdapter(getActivity());
        gridView.setAdapter(new CommodityImageAdapter(getActivity()));
        setGridViewHeightBasedOnChildren(gridView,4);


        uisp_nature6am.setItems(getResources().getStringArray(R.array.natureVendingOptionsVendor));
        uisp_nature6am.hasNoneOption(true);
        uisp_nature6am.setSelection(new int[]{0});

        uisp_nature3pm.setItems(getResources().getStringArray(R.array.natureVendingOptionsVendor));
        uisp_nature3pm.hasNoneOption(true);
        uisp_nature3pm.setSelection(new int[]{0});

        uisp_nature6pm.setItems(getResources().getStringArray(R.array.natureVendingOptionsVendor));
        uisp_nature6pm.hasNoneOption(true);
        uisp_nature6pm.setSelection(new int[]{0});

        uisp_nature9am.setItems(getResources().getStringArray(R.array.natureVendingOptionsVendor));
        uisp_nature9am.hasNoneOption(true);
        uisp_nature9am.setSelection(new int[]{0});

        uisp_nature12pm.setItems(getResources().getStringArray(R.array.natureVendingOptionsVendor));
        uisp_nature12pm.hasNoneOption(true);
        uisp_nature12pm.setSelection(new int[]{0});


        age = view.findViewById(R.id.registration_age);
        next = view.findViewById(R.id.form_bt_next);
        next.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                surveyFormData = null;
                surveyFormData = new SurveyFormData();
                String str = "";
                String str2 = "select one";
                if (!vendorName.getText().toString().equalsIgnoreCase(str)  && !age.getText().toString().equalsIgnoreCase(str) && !mobileno.getText().toString().equalsIgnoreCase(str)
                         ) {
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
                    surveyFormData.setTypeCommodity(selectedCommodities.toString());

                    surveyFormData.setTiming(timing.toString());
                    surveyFormData.setWardZone(ward.getText().toString());
                    surveyFormData.setVendorWardZone(vendingWard.getText().toString());
                    surveyFormData.setMobileNumber(mobileno.getText().toString());
                    surveyFormData.setPaymentOption(listPayment.toString());
                    AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson((Object) surveyFormData));
                    VendorRegisteration.getInstance().callSubmitApi();

                } else {
                    Toast.makeText(view.getContext(), "Please fill data", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* uisp_commodity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerServiceType = uisp_commodity.getItemAtPosition(i).toString();
                if (spinnerServiceType.equalsIgnoreCase("Other")) {
                    please_mention.setVisibility(View.VISIBLE);
                } else {
                    please_mention.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

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
        this.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showImageDialog(VendorRegisteration.getInstance(), FileEnum.file_stall.toString());
            }
        });

        locationOfVendingGps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VendorRegisteration.getInstance().callForLocation();
            }
        });


        this.cash.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!isCash.booleanValue()) {
                    cash.setBackground(getResources().getDrawable(R.drawable.green_background));
                    isCash = Boolean.valueOf(true);
                    listPayment.add("cash");
                    return;
                }
                cash.setBackground(getResources().getDrawable(R.drawable.black_background));
                isCash = Boolean.valueOf(false);
                listPayment.remove("cash");

            }
        });
        wallet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!isWallet.booleanValue()) {
                    wallet.setBackground(getResources().getDrawable(R.drawable.green_background));
                    isWallet = Boolean.valueOf(true);
                    listPayment.add("wallet");
                    return;
                }
                wallet.setBackground(getResources().getDrawable(R.drawable.black_background));
                isWallet = Boolean.valueOf(false);
                listPayment.remove("wallet");
            }
        });
        card.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!isCard.booleanValue()) {
                    card.setBackground(getResources().getDrawable(R.drawable.green_background));
                    isCard = Boolean.valueOf(true);
                    listPayment.add("card");
                    return;
                }
                card.setBackground(getResources().getDrawable(R.drawable.black_background));
                isCard = Boolean.valueOf(false);
                listPayment.remove("card");
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                int selectedIndex = adapter.selectedPositions.indexOf(position);
                GridViewItems = (LinearLayout) view;


                BackSelectedItem = (LinearLayout) gridView.getChildAt(position);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    BackSelectedItem.setSelected(false);
                    selectedCommodities.remove(mTexts[position]);
                    BackSelectedItem.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.white_background));

                } else {
                    adapter.selectedPositions.add(position);
                    selectedCommodities.add((mTexts[position]));
                    GridViewItems.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.background_custom_radio_buttons_selected_state));
                }


              //  commodity = mTexts[selectedIndex];
                Log.d("commodities",selectedCommodities.toString()) ;

            }
        });

        uisp_nature6am.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }
            @Override
            public void selectedStrings(List<String> strings) {
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

    public void showImageDialog(AppCompatActivity activity, String file) {
        VendorRegisteration.getInstance().konsiPic = file;
        this.selectImageDialog = new SelectImageDialog(activity, file);
        if (!this.selectImageDialog.isShowing()) {
            this.selectImageDialog.show();
        }
    }

    public void updateView(int position2) {
    }

    public void updateSelectedImage(String file, Uri bitmap, String url) {
        SurveyFormData surveyFormData = new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);
        if (surveyFormData == null) {
            surveyFormData = new SurveyFormData();
        }
        if (file.equalsIgnoreCase(FileEnum.file_stall.toString())) {
            imageView.setImageURI(bitmap);
            AppSharedPrefernce.getInstance().setPersistenceKeyVendorStallFile(url);
            surveyFormData.setFile_stall(url);
        }

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
            Toast.makeText(getContext(),"check your internet connection! ",Toast.LENGTH_LONG).show();
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
                        Type type = new TypeToken<ArrayList<State>>() {
                        }.getType();
                        stateList = (new Gson()).fromJson(jsonArray.toString(), type);
                        stateArrayAdapter = new ArrayAdapter<State>(VendorRegisteration.getInstance(), R.layout.support_simple_spinner_dropdown_item, stateList);
                        VendorRegisteration.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                homeState.setAdapter(stateArrayAdapter);
                                vendingState.setAdapter(stateArrayAdapter);
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

    public void generateCityList(int id,String citytype) {
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
                        Type type = new TypeToken<ArrayList<City>>() {
                        }.getType();
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

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight()-20;

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

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