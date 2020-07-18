package com.example.tbc.utils;

import com.google.gson.JsonObject;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
    @POST("upload_img.php")
    @Multipart
    Call<JsonObject> UploadFile(@Part MultipartBody.Part part,@Part("surveyor") int id);

    @FormUrlEncoded
    @POST("add_vendor.php")
    Call<JsonObject> addSurvey(@FieldMap Map<String, String> map);

    @GET("vendor_api.php")
    Call<JsonObject> getVendorDetails(@Query("surveyor_id") int i);

    @FormUrlEncoded
    @POST("login_api.php")
    Call<JsonObject> surveyorLogin(@Field("access_token") String str, @Field("name") String str2, @Field("password") String str3);

    @FormUrlEncoded
    @POST("vendor_login.php")
    Call<JsonObject> vendorLogin(@Field("registration_no") String str, @Field("password") String str2);

    @GET("cities_api.php")
    Call<JsonObject> getCityList(@Query("state_id") int i);

    @GET("state_api.php?all_states")
    Call<JsonObject> getStateList();

    @GET("view_training_api.php")
    Call<JsonObject> getVendorTrainingDetails(@Query("all_training") int i);

    @GET("view_alert_api.php")
    Call<JsonObject> getVendorAlertDetails(@Query("all_alert") int i);

    @FormUrlEncoded
    @POST("user_login_api.php")
    Call<JsonObject> userLogin(@Field("name") String str, @Field("email") String str2, @Field("mobile_no") String str3,@Field("otp") String str4);

    @GET("cal_dis_api.php")
    Call<JsonObject> getnearby(@Query("type_commodity")String commodity);

    @FormUrlEncoded
    @POST("add_rating.php")
    Call<JsonObject> submitRating(@FieldMap Map<String, String> map);

    @GET
    Call<String> sendSms(@Url String url);

}
//    https://jungleetechnologies.com/tbc/admin/webservice/cal_dis_api.php?=Home Utility
//https://www.jungleetechnologies.com/tbc/admin/webservice/view_training_api.php?all_training
//https://www.jungleetechnologies.com/tbc/admin/webservice/view_alert_api.php?all_alert
