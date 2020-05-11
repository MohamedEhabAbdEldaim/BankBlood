package com.midooabdaim.bloodbank.Data.Api;


import com.midooabdaim.bloodbank.Data.Model.GenralReqest.GenralReqest;
import com.midooabdaim.bloodbank.Data.Model.Notification.Notification;
import com.midooabdaim.bloodbank.Data.Model.NotificationStting.NotificationStting;
import com.midooabdaim.bloodbank.Data.Model.User.NewPassword.NewPass;
import com.midooabdaim.bloodbank.Data.Model.User.ResetPassword.ResetPass;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.RegisterAndLoginAndEdit;
import com.midooabdaim.bloodbank.Data.Model.contactUs.ContactUs;
import com.midooabdaim.bloodbank.Data.Model.donationDetails.DonationDetailsAndRequest;
import com.midooabdaim.bloodbank.Data.Model.donationRequests.AllDonation;
import com.midooabdaim.bloodbank.Data.Model.postToggleFavourite.PostToggleFavourite;
import com.midooabdaim.bloodbank.Data.Model.posts.Posts;
import com.midooabdaim.bloodbank.Data.Model.setting.Setting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("blood-types")
    Call<GenralReqest> getBloodTypes();

    @GET("governorates")
    Call<GenralReqest> getGovernorate();

    @GET("cities")
    Call<GenralReqest> getCities(@Query("governorate_id") int governorateId);

    @POST("login")
    @FormUrlEncoded
    Call<RegisterAndLoginAndEdit> getUser(@Field("phone") String phone,
                                          @Field("password") String password);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPass> getResetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPass> getNewPassword(@Field("password") String password,
                                 @Field("password_confirmation") String password_confirmation,
                                 @Field("pin_code") String pin_code,
                                 @Field("phone") String phone);


    @POST("signup")
    @FormUrlEncoded
    Call<RegisterAndLoginAndEdit> getRegister(@Field("name") String name,
                                              @Field("email") String email,
                                              @Field("birth_date") String birth_date,
                                              @Field("city_id") int city_id,
                                              @Field("phone") String phone,
                                              @Field("donation_last_date") String donation_last_date,
                                              @Field("password") String password,
                                              @Field("password_confirmation") String password_confirmation,
                                              @Field("blood_type_id") int blood_type_id);

    @POST("profile")
    @FormUrlEncoded
    Call<RegisterAndLoginAndEdit> getProfile(@Field("name") String name,
                                             @Field("email") String email,
                                             @Field("birth_date") String birth_date,
                                             @Field("city_id") int city_id,
                                             @Field("phone") String phone,
                                             @Field("donation_last_date") String donation_last_date,
                                             @Field("blood_type_id") int blood_type_id,
                                             @Field("password") String password,
                                             @Field("password_confirmation") String password_confirmation,
                                             @Field("api_token") String api_token);


    @GET("categories")
    Call<GenralReqest> getCategories();

    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token,
                         @Query("page") int page);

    @GET("posts")
    Call<Posts> getPostFilter(@Query("api_token") String api_token,
                              @Query("keyword") String keyword,
                              @Query("page") int page,
                              @Query("category_id") int category_id);


    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavourite> getPostToggleFavourite(@Field("post_id") int post_id,
                                                     @Field("api_token") String api_token);

    @GET("my-favourites")
    Call<Posts> getMyFavourites(@Query("api_token") String api_token,
                                @Query("page") int page);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationDetailsAndRequest> getDonationRequestCreate(@Field("api_token") String api_token,
                                                             @Field("patient_name") String patient_name,
                                                             @Field("patient_age") String patient_age,
                                                             @Field("blood_type_id") int blood_type_id,
                                                             @Field("bags_num") String bags_num,
                                                             @Field("hospital_name") String hospital_name,
                                                             @Field("hospital_address") String hospital_address,
                                                             @Field("city_id") int city_id,
                                                             @Field("phone") String phone,
                                                             @Field("notes") String notes,
                                                             @Field("latitude") double latitude,
                                                             @Field("longitude") double longitude);


    @GET("donation-requests")
    Call<AllDonation> getDonationRequests(@Query("api_token") String api_token,
                                          @Query("page") int page);

    @GET("donation-requests")
    Call<AllDonation> getDonationRequestsFilter(@Query("api_token") String api_token,
                                                @Query("blood_type_id") int blood_type_id,
                                                @Query("governorate_id") int governorate_id,
                                                @Query("page") int page);

    @GET("donation-request")
    Call<DonationDetailsAndRequest> getDonationDetails(@Query("api_token") String api_token,
                                                       @Query("donation_id") String donation_id);


    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> getContactUs(@Field("title") String title,
                                 @Field("message") String message,
                                 @Field("api_token") String api_token);

    @GET("settings")
    Call<Setting> getSettings(@Query("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationStting> getNotificationSetting(@Field("api_token") String api_token);


    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationStting> changeNotificationSetting(@Field("api_token") String api_token,
                                                       @Field("governorates[]") List<Integer> governorates,
                                                       @Field("blood_types[]") List<Integer> blood_types);


    @GET("notifications")
    Call<Notification> getListNotification(@Query("api_token") String api_token,
                                           @Query("page") int page);

}



