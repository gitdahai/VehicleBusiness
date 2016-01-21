package cn.hollo.www.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by orson on 16/1/18.
 * 提醒对话框
 */
public class TipDialog {
    private AlertDialog.Builder builder;
    private Dialog dialog;

    public TipDialog(Context context) {
        builder = new AlertDialog.Builder(context);
    }

    public void setTitle(String title){
        builder.setTitle(title);
    }

    public void setMessage(String msg){
        builder.setMessage(msg);
    }

    public void setPositiveButton(String name, DialogInterface.OnClickListener lisnter){
        builder.setPositiveButton(name, lisnter);
    }

    public void setNegativeButton(String name, DialogInterface.OnClickListener listener){
        builder.setNeutralButton(name, listener);
    }

    public void show(){
        dialog = builder.create();
        dialog.show();
    }

    public void dismiss(){
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
