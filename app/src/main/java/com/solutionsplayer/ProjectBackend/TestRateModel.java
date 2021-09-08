package com.solutionsplayer.ProjectBackend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "testRate")
public class TestRateModel {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("test_id")
    @ColumnInfo(name="test_id")
    private String test_id;

    @SerializedName("test_name")
    @ColumnInfo(name="test_name")
    private String test_name;

    @SerializedName("test_desc")
    @ColumnInfo(name="test_desc")
    private String test_desc;

    @SerializedName("test_fee")
    @ColumnInfo(name="test_fee")
    private String test_fee;

    public TestRateModel(String test_id, String test_name, String test_desc, String test_fee) {
        this.test_id = test_id;
        this.test_name = test_name;
        this.test_desc = test_desc;
        this.test_fee = test_fee;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_desc() {
        return test_desc;
    }

    public void setTest_desc(String test_desc) {
        this.test_desc = test_desc;
    }

    public String getTest_fee() {
        return test_fee;
    }

    public void setTest_fee(String test_fee) {
        this.test_fee = test_fee;
    }

    @Override
    public String toString() {
        return "TestRateModel{" +
                "id=" + id +
                ", test_id='" + test_id + '\'' +
                ", test_name='" + test_name + '\'' +
                ", test_desc='" + test_desc + '\'' +
                ", test_fee='" + test_fee + '\'' +
                '}';
    }
}
