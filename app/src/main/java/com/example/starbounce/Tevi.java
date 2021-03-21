package com.example.starbounce;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Tevi {
    private Bitmap imagine1, imagine2;
    public int xX, yY;
    private int inaltimeEcran= Resources.getSystem().getDisplayMetrics().heightPixels;

    public Tevi(Bitmap bmp1, Bitmap bmp2, int x, int y){
        imagine1=bmp1;
        imagine2=bmp2;
        yY=y;
        xX=x;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(imagine1,xX,-(Joc.distantaTevi/2)+yY, null);
        canvas.drawBitmap(imagine2,xX,((inaltimeEcran/2)+(Joc.distantaTevi/2))+yY,null);
    }

    public void update(){
        xX-= Joc.vitezaJoc;
    }
}
