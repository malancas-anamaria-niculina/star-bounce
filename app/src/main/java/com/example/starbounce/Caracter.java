package com.example.starbounce;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Caracter {
    private Bitmap imagine;
    public  int x,y, vitezaVerticala=10;
    public static int latimeEcran=Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int inaltimeEcran=Resources.getSystem().getDisplayMetrics().heightPixels;

    public Caracter(Bitmap bitmap){
        imagine=bitmap;
        x=100;
        y=100;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(imagine,x,y,null);
    }
    public void update(){
        y+=vitezaVerticala;
    }
}
