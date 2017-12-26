package com.example.aiyang.allhttp_networkrequestdemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by aiyang on 2017/5/4.
 */

public class ToastUtil {

    public static void Show(Context content,String text){
        Toast.makeText(content,text,Toast.LENGTH_SHORT).show();
    }
}
