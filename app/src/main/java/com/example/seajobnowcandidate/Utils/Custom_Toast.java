package com.example.seajobnowcandidate.Utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.seajobnowcandidate.R;

import es.dmoral.toasty.Toasty;


public class Custom_Toast {
    public static void warning(Context context, String message){
        Toasty.warning(context,message, Toast.LENGTH_SHORT,true).show();
    }

    public static void error(Context context, String message){
        Toasty.error(context, message, Toast.LENGTH_SHORT,true).show();
    }
    public static void info2(Context context, String message){
        Toasty.info(context, message, Toast.LENGTH_SHORT,true).show();
    }
    public static void custom_error(Context context, String message){
        Toast toast = Toasty.error(context, message, Toast.LENGTH_SHORT,true);
        toast.setGravity(Gravity.BOTTOM, 1, 1);
        toast.show();
    }

    public static void success(Context context, String message){
        Toasty.success(context,message, Toast.LENGTH_SHORT,true).show();
    }

    public static void custom_success(Context context, String message){
        Toast toast = Toasty.custom(context,message, R.drawable.circle, R.color.black,Toast.LENGTH_LONG,true,false);
        toast.setGravity(Gravity.CENTER, 1, 1);
        toast.show();
        //Toasty.success(context,message, android.widget.Custom_Toast.LENGTH_LONG,true).show();
    }

    public static void info(Context context, String message){
        Toast toast = Toasty.custom(context,message, R.drawable.ic_info_outline_white_24dp, R.color.yellow_400,Toast.LENGTH_LONG,true,true);
        toast.setGravity(Gravity.CENTER, 1, 1);
        toast.show();
    }
}
