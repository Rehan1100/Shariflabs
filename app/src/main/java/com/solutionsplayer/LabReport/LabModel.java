package com.solutionsplayer.LabReport;

import java.util.ArrayList;

public class LabModel {

  private String test_name;
  private String test_id;
  private String test_result;
  private String test_details;
  private String ptn;
  private String patient_name;
  private String patient_dob;
  private String passport_no;
  private String ticket_pnr_no;
  private String sample_date;
  private String sample_time;
  private ArrayList<LabModel> list= new ArrayList<>();
  private String gender;

    public LabModel(String ptn, String patient_name, String patient_dob, String passport_no, String ticket_pnr_no, String sample_date, String sample_time, ArrayList<LabModel> list, String gender) {
        this.ptn = ptn;
        this.patient_name = patient_name;
        this.patient_dob = patient_dob;
        this.passport_no = passport_no;
        this.ticket_pnr_no = ticket_pnr_no;
        this.sample_date = sample_date;
        this.sample_time = sample_time;
        this.list = list;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPtn() {
        return ptn;
    }

    public void setPtn(String ptn) {
        this.ptn = ptn;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }


    public String getPatient_dob() {
        return patient_dob;
    }

    public void setPatient_dob(String patient_dob) {
        this.patient_dob = patient_dob;
    }

    public String getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(String passport_no) {
        this.passport_no = passport_no;
    }

    public String getTicket_pnr_no() {
        return ticket_pnr_no;
    }

    public void setTicket_pnr_no(String ticket_pnr_no) {
        this.ticket_pnr_no = ticket_pnr_no;
    }

    public String getSample_date() {
        return sample_date;
    }

    public void setSample_date(String sample_date) {
        this.sample_date = sample_date;
    }

    public String getSample_time() {
        return sample_time;
    }

    public void setSample_time(String sample_time) {
        this.sample_time = sample_time;
    }

    public ArrayList<LabModel> getList() {
        return list;
    }

    public void setList(ArrayList<LabModel> list) {
        this.list = list;
    }

    public LabModel(String test_name, String test_id, String test_result, String test_details) {
        this.test_name = test_name;
        this.test_id = test_id;
        this.test_result = test_result;
        this.test_details = test_details;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getTest_result() {
        return test_result;
    }

    public void setTest_result(String test_result) {
        this.test_result = test_result;
    }

    public String getTest_details() {
        return test_details;
    }

    public void setTest_details(String test_details) {
        this.test_details = test_details;
    }
}
