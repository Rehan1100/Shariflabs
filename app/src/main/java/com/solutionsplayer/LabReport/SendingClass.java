package com.solutionsplayer.LabReport;

import com.google.gson.annotations.SerializedName;

public class SendingClass {

    @SerializedName("id")
    String id;
    @SerializedName("test_id")
    String test_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    @Override
    public String toString() {
        return "SendingClass{" +
                "id='" + id + '\'' +
                ", test_id='" + test_id + '\'' +
                '}';
    }
}
