package com.izv.android.proyectojuego;

import android.graphics.Rect;

/**
 * Created by Ivan on 25/02/2015.
 */
public class HebraBola extends Thread {
    private Bola bola;
    private Pared pared1, pared2;
    private boolean run;
    private int velocidad;
    private Rect pantalla;

    public HebraBola(Bola bola, Pared pared1, Pared pared2, Rect pantalla) {
        this.bola = bola;
        this.pared1 = pared1;
        this.pared2 = pared2;
        this.pantalla = pantalla;
        this.run = false;
        this.velocidad = 2;
    }

    public void setFuncionando(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        while(run) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!bola.mover(velocidad, velocidad, pantalla, pared1.getFigura(), pared2.getFigura()))
                bola.rebote(velocidad, velocidad, pantalla, pared1.getFigura(), pared2.getFigura());
            bola.move(velocidad, velocidad);
        }
    }

}
