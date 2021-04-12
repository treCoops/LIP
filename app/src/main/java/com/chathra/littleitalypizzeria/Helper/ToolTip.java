package com.chathra.littleitalypizzeria.Helper;

import android.content.Context;
import android.widget.EditText;

import com.chathra.littleitalypizzeria.R;

import cl.jesualex.stooltip.Position;
import cl.jesualex.stooltip.Tooltip;



public class ToolTip {


    public static void show(Context context, EditText editText, String message){
        Tooltip.on(editText)
                .text(message)
                .color(context.getResources().getColor(R.color.toolTipBackground))
                .textSize(10)
                .clickToHide(true)
                .corner(20)
                .animation(R.anim.animate_fade_enter, R.anim.animate_fade_exit)
                .position(Position.BOTTOM)
                .show(1500);
    }


}
