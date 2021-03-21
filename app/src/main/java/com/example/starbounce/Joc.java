package com.example.starbounce;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joc extends SurfaceView implements SurfaceHolder.Callback {
    private FirExecutie fir;
    private Caracter caracter;
    public Tevi teava1, teava2, teava3;
    public static int distantaTevi = 500, vitezaJoc = 10;
    private int latimeEcran = Caracter.latimeEcran, inaltimeEcran = Caracter.inaltimeEcran;
    Resources resurse = getResources();
    Bitmap bitmap = BitmapFactory.decodeResource(resurse, R.drawable.cer);
    Bitmap bmp = Bitmap.createScaledBitmap(bitmap, latimeEcran, inaltimeEcran, true);

    public Joc(Context context) {
        super(context);
        getHolder().addCallback(this);
        fir = new FirExecutie(getHolder(), this);
        setFocusable(true);
    }

    public Bitmap getResizedBitmap(Bitmap bmp, int inaltime, int latime) {
        int width = bmp.getWidth(), height = bmp.getHeight();
        float latimeScalare = ((float) latime) / width, inaltimeScalare = ((float) inaltime) / height;
        Matrix matrice = new Matrix();
        matrice.postScale(latimeScalare, inaltimeScalare);
        Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrice, false);
        bmp.recycle();
        return newBitmap;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        caracter.y = caracter.y - (caracter.vitezaVerticala * 18);
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        creeazaNivel();
        fir.setRunning(true);
        fir.start();

    }

    private void creeazaNivel() {
        caracter = new Caracter(getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.stea), 200, 200));
        Bitmap bmp, bmp2;
        int x, y;

        bmp = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.teava2), 500, Resources.getSystem().getDisplayMetrics().heightPixels / 4);
        bmp2 = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.teava1), 500, Resources.getSystem().getDisplayMetrics().heightPixels / 4);
        teava1 = new Tevi(bmp, bmp2, 2000, 100);
        teava2 = new Tevi(bmp, bmp2, 4500, 100);
        teava3 = new Tevi(bmp, bmp2, 3200, 100);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                fir.setRunning(false);
                fir.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            retry = false;
        }
    }

    public void update() {
        logica();
        caracter.update();
        teava1.update();
        teava2.update();
        teava3.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(bmp, 0, 0, null);
            caracter.draw(canvas);
            teava1.draw(canvas);
            teava2.draw(canvas);
            teava3.draw(canvas);
        }
    }

    public void logica() {
        List<Tevi> tevi = new ArrayList<>();
        tevi.add(teava1);
        tevi.add(teava2);
        tevi.add(teava3);


        for (int i = 0; i < tevi.size(); i++) {
            if (caracter.y < tevi.get(i).yY  + (inaltimeEcran / 2) - (distantaTevi / 2) && caracter.x + 200 > tevi.get(i).xX && caracter.x < tevi.get(i).xX + 200)
                resetJoc();

            else if (caracter.y + 200 > (inaltimeEcran / 2) + (distantaTevi / 2) + tevi.get(i).yY  && caracter.x + 200 > tevi.get(i).xX && caracter.x < tevi.get(i).xX + 200)
                resetJoc();

            if (tevi.get(i).xX + 500 < 0) {
                Random r = new Random();
                int value1 = r.nextInt(500), value2 = r.nextInt(500);
                tevi.get(i).xX = latimeEcran + value1 + 1000;
                tevi.get(i).yY = value2 - 250;
            }
        }

        if (caracter.y + 240 < 0)
            resetJoc();

        if (caracter.y > inaltimeEcran)
            resetJoc();
    }

    public  void resetJoc(){
        caracter.y=50;
        teava1.xX=2000;
        teava1.yY=0;
        teava2.xX=4500;
        teava2.yY=200;
        teava3.xX=3200;
        teava3.yY=250;
    }
}
