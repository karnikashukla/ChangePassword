package com.changepassword;

public class CustomerListModel {
    public static Boolean isChecked;
    private String title;

    public CustomerListModel(String title, Boolean isChecked) {
        this.title = title;
        CustomerListModel.isChecked = isChecked;
    }

    public CustomerListModel() {
    }

    public CustomerListModel(String s) {
        this.title = s;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
