package com.izv.android.proyectojuego;

import android.graphics.Rect;

/**
 * Created by Ivan on 24/02/2015.
 */
public class Bola extends Figuras implements MoverFiguras {

    public static final int da = 1;
    public static final int ia = 2;
    public static final int ib = 3;
    public static final int db = 4;

    private int direccion;

    public Bola(Coordenadas origen, int ancho, int alto) {
        super(origen, ancho, alto);
        direccion = 1;
    }

    private boolean colision(int x, int y, Rect pared) {
        if(pared.contains(origen.getX()+x, origen.getY()+y))
            return true;
        if(pared.contains(origen.getX()+ancho+x, origen.getY()+y))
            return true;
        if(pared.contains(origen.getX()+x, origen.getY()+alto+y))
            return true;
        if(pared.contains(origen.getX()+ancho+x, origen.getY()+alto+y))
            return true;

        return false;
    }

    public boolean mover(int x, int y, Rect pantalla, Rect pared2, Rect pared1) {
        if(!mover(x,y,pantalla))
            return false;
        if(colision(x,y,pared2))
            return false;
        if(colision(x,y,pared1))
            return false;

        return true;
    }

    public void rebote(int x, int y, Rect pantalla, Rect pared2, Rect pared1) {
        if(!mover(x,y,pantalla)) {
            switch(direccion) {
                case da:
                    direccion = (origen.getY() - y <= pantalla.top) ?
                            db : ia;
                    break;
                case ia:
                    direccion = (origen.getY() - y <= pantalla.top) ?
                            ib : da;
                    break;
                case ib:
                    direccion = (origen.getY() + alto + y >= pantalla.bottom) ?
                            ia : db;
                    break;
                case db:
                    direccion = (origen.getY() + alto + y >= pantalla.bottom) ?
                            da : ib;
                    break;
            }
        }

        Rect pared = null;
        if(pared2.contains(origen.getX()+x, origen.getY()+y, origen.getX()+ancho+x, origen.getY()+alto+y))
            pared = pared2;
        if(pared1.contains(origen.getX()+x, origen.getY()+y, origen.getX()+ancho+x, origen.getY()+alto+y))
            pared = pared1;
        if(pared != null) {
            switch(direccion) {
                case da:
                    direccion = (origen.getX()+ancho < pared.left) ?
                            ia : db;
                    break;
                case ia:
                    direccion = (origen.getX()+ancho > pared.right) ?
                            ib : da;
                    break;
                case ib:
                    direccion = (origen.getX()+ancho > pared.right) ?
                            ia : db;
                    break;
                case db:
                    direccion = (origen.getX()+ancho < pared.left) ?
                            da : ib;
                    break;
            }
        }
    }

    @Override
    public void move(int x, int y) {
        switch(direccion) {
            case da:
                origen.setX(origen.getX() + x);
                origen.setY(origen.getY() - y);
                break;
            case ia:
                origen.setX(origen.getX() - x);
                origen.setY(origen.getY() - y);
                break;
            case ib:
                origen.setX(origen.getX() - x);
                origen.setY(origen.getY() + y);
                break;
            case db:
                origen.setX(origen.getX() + x);
                origen.setY(origen.getY() + y);
                break;
        }
    }
}
