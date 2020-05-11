
package com.midooabdaim.bloodbank.Data.Model.GenralReqest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenralReqestData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("governorate_id")
    @Expose
    private String governorateId;
    @SerializedName("governorate")
    @Expose
    private GenralReqestData governorate;

    public GenralReqestData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenralReqestData() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(String governorateId) {
        this.governorateId = governorateId;
    }

    public GenralReqestData getGovernorate() {
        return governorate;
    }

    public void GenralReqestData(GenralReqestData governorate) {
        this.governorate = governorate;
    }
}
