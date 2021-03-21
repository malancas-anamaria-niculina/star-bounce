package com.example.starbounce;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class FirExecutie extends Thread{
    private Joc joc;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    public static Canvas canvas;
    private int jocFPS=60;
    private double averageFPS;

    public FirExecutie (SurfaceHolder surfaceHolder, Joc joc){
        super();
        this.surfaceHolder=surfaceHolder;
        this.joc=joc;
    }

    @Override
    public void run(){
        long start, timp, asteptare, timpTotal=0, targetTime=100/jocFPS;
        int cadre=0;
        while (running){
            start=System.nanoTime();
            canvas=null;
            try{
                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.joc.update();
                    this.joc.draw(canvas);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            finally {
                if (canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            timp=(System.nanoTime()-start)/1000000;
            asteptare=targetTime-timp;
            try{
                this.sleep(asteptare);
            }catch (Exception e){}

            timpTotal+=System.nanoTime()-start;
            cadre++;

            if (cadre==jocFPS){
                averageFPS=1000/((timpTotal/cadre)/1000000);
                cadre=0;
                timpTotal=0;
            }

        }
    }

    public void setRunning(boolean isRunning)
    {
        running=isRunning;
    }

}
