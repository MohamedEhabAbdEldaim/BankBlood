
package com.midooabdaim.bloodbank.Data.Model.donationDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.midooabdaim.bloodbank.Data.Model.GenralReqest.GenralReqestData;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;


public class DonationData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("patient_age")
    @Expose
    private String patientAge;
    @SerializedName("blood_type")
    @Expose
    private GenralReqestData bloodType;
    @SerializedName("bags_num")
    @Expose
    private String bagsNum;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("hospital_address")
    @Expose
    private String hospitalAddress;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city")
    @Expose
    private GenralReqestData city;
    @SerializedName("client")
    @Expose
    private UserData client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public GenralReqestData getBloodType() {
        return bloodType;
    }

    public void setBloodType(GenralReqestData bloodType) {
        this.bloodType = bloodType;
    }

    public String getBagsNum() {
        return bagsNum;
    }

    public void setBagsNum(String bagsNum) {
        this.bagsNum = bagsNum;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public GenralReqestData getCity() {
        return city;
    }

    public void setCity(GenralReqestData city) {
        this.city = city;
    }

    public UserData getClient() {
        return client;
    }

    public void setClient(UserData client) {
        this.client = client;
    }

}
