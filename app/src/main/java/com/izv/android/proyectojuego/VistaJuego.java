package com.izv.android.proyectojuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ivan on 24/02/2015.
 */
public class VistaJuego extends SurfaceView implements SurfaceHolder.Callback {

    private HebraJuego hebraJuego;
    private HebraBola hebraBola;

    private Figuras pared1;
    private Figuras pared2;
    private Figuras bola;
    private Bitmap fondoAjus, pelota;

    private Figuras actual = null;
    private int inicioY;

    public VistaJuego(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Fondo del juego
        Bitmap fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        float scale = (float)fondo.getHeight()/(float)getHeight();
        int newWidth = Math.round(fondo.getWidth()/scale);
        int newHeight = Math.round(fondo.getHeight()/scale);
        fondoAjus = Bitmap.createScaledBitmap(fondo, newWidth, newHeight, true);
        pelota = BitmapFactory.decodeResource(getResources(), R.drawable.pelota);


        //Crear los objetos del juego
        pared1 = new Pared(new Coordenadas(50,getHeight()/2-50),30,150);
        pared2 = new Pared(new Coordenadas(getWidth()-70,getHeight()/2-50),30,150);
        bola = new Bola(new Coordenadas(getWidth()/2-5,getHeight()/2-5),15,15);

        //hebra para el juego
        hebraJuego = new HebraJuego(getHolder(), this);
        hebraJuego.setFuncionando(true);
        hebraJuego.start();

        //hebra para el movimiento de la bola
        hebraBola = new HebraBola((Bola)bola, (Pared)pared1, (Pared)pared2, new Rect(0,0,getWidth(),getHeight()));
        hebraBola.setFuncionando(true);
        hebraBola.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        hebraJuego.setFuncionando(false);
        hebraBola.setFuncionando(false);
        while (retry) {
            try {
                hebraJuego.join();
                hebraBola.join();
                retry = false;
            } catch (InterruptedException e) { }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(200,50,50));

        canvas.drawBitmap(pelota,null,bola.getFigura(),paint);
        canvas.drawBitmap(fondoAjus, 0, 0, null);
        canvas.drawRect(pared1.getFigura(), paint);
        canvas.drawRect(pared2.getFigura(), paint);
        canvas.drawRect(bola.getFigura(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(pared1.getFigura().contains(x, y)) {
                    actual = pared1;
                    inicioY = y;
                    break;
                }
                if(pared2.getFigura().contains(x, y)) {
                    actual = pared2;
                    inicioY = y;
                    break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(actual != null) {
                    Pared r = (Pared) actual;
                    if(r.mover(0, y - inicioY, new Rect(0, 0, getWidth(), getHeight())))
                        r.move(0, y - inicioY);
                }
                inicioY = y;
                break;
            case MotionEvent.ACTION_UP:
                actual = null;
                break;
        }

        return true;
    }
}
