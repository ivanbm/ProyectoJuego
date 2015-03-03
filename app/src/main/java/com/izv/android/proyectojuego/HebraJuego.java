package com.izv.android.proyectojuego;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

/**
 * Created by Ivan on 24/02/2015.
 */
public class HebraJuego  extends Thread {
    private SurfaceHolder sh;
    private VistaJuego view;
    private boolean run;

    public HebraJuego(SurfaceHolder sh, VistaJuego view) {
        this.sh = sh;
        this.view = view;
        run = false;
    }

    public void setFuncionando(boolean run) {
        this.run = run;
    }

    public void run() {
        Canvas canvas;
        while(run) {
            canvas = null;
            try {
                canvas = sh.lockCanvas(null);
                synchronized(sh) {
                    view.onDraw(canvas);
                }
            } finally {
                if(canvas != null)
                    sh.unlockCanvasAndPost(canvas);
            }
        }
    }


}
