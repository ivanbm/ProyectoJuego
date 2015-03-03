package com.izv.android.proyectojuego;


/**
 * Created by Ivan on 24/02/2015.
 */
public class Pared extends Figuras implements MoverFiguras {

    public Pared(Coordenadas origen, int ancho, int alto) {
        super(origen, ancho, alto);
    }

    @Override
    public void move(int x, int y) {
        origen.setY(origen.getY() + y);
    }

}
