
package com.midooabdaim.bloodbank.Data.Model.User.ResetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPass {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ResetPassData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResetPassData getData() {
        return data;
    }

    public void setData(ResetPassData data) {
        this.data = data;
    }

}
