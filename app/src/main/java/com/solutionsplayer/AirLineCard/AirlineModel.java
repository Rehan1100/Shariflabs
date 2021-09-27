package com.solutionsplayer.AirLineCard;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "AirlineModel")
public class AirlineModel {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id1")
    private int id1;

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    @SerializedName("name")
    @ColumnInfo(name="name")
    String name;
    @SerializedName("group")
    @ColumnInfo(name="group")
    String group;
    @SerializedName("id")
    @ColumnInfo(name="id")
    String id;

    public AirlineModel(String name, String group, String id) {
        this.name = name;
        this.group = group;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AirlineModel{" +
                "id1=" + id1 +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

