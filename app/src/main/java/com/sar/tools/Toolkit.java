package com.sar.tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sar.tools.interfaces.AlertListener;
import com.sar.tools.interfaces.RvListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public abstract class Toolkit {

    public static String generateRandomString() {

        String random_character = "";
        Random rnd = new Random();
        String str;

//        String randomLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String randomLetterSmall = "abcdefghijklmnopqrstuvwxyz";
        String randomNumber = "123456789";

        for (int n = 0; n < 1; n++) {
//            str = String.valueOf(randomLetters.charAt(rnd.nextInt(randomLetters.length())));
//            str += String.valueOf(randomNumber.charAt(rnd.nextInt(randomNumber.length())));
//            str += String.valueOf(randomLetterSmall.charAt(rnd.nextInt(randomLetters.length())));
            str = String.valueOf(randomNumber.charAt(rnd.nextInt(randomNumber.length())));

            //Copy above line to increase character of the String
            random_character = str;
        }

        return random_character;
    }

    public static String today(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss.ZZZZ", Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return df.format(new Date());
    }

    public static String currentDate(){
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String onlyDateNow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    public static void alertDialog(Context mContext, String title, String msg, String positiveBtnCaption, String negativeBtnCaption, boolean isCancelable, final AlertListener target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(title).setMessage(msg).setCancelable(false).setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                target.PositiveMethod(dialog, id);
            }
        }).setNegativeButton(negativeBtnCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                target.NegativeMethod(dialog, id);
            }
        });

        AlertDialog alert = builder.create();
        alert.setCancelable(isCancelable);
        alert.show();
        if (isCancelable) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    target.NegativeMethod(null, 0);
                }
            });
        }
    }

    public static void e (String className, String fnction, String message){
        Log.e(className,fnction + " => " + message);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private RvListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RvListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }
        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        }
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}
