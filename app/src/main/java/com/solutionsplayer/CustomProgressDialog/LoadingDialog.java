package com.solutionsplayer.CustomProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.solutionsplayer.shariflabs.R;


public class LoadingDialog {

    Activity activity;
    public  AlertDialog dialog;
    TextView title;
    public LoadingDialog(Activity myActivity)
    {
        activity=myActivity;

    }
    public  void startLoadingDialog(String title)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setTitle(title);
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.show();

    }
   public void dismiss()
    {
        dialog.dismiss();
    }
}
