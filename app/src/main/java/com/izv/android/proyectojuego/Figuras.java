package com.izv.android.proyectojuego;

import android.graphics.Rect;

/**
 * Created by Ivan on 24/02/2015.
 */
public abstract class Figuras {
    protected Coordenadas origen;
    protected int ancho;
    protected int alto;

    public Figuras(Coordenadas origen, int ancho, int alto) {
        this.origen = origen;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getOrigenX() {
        return origen.getX();
    }

    public int getOrigenY() {
        return origen.getY();
    }

    public void setOrigenX(int newX) {
        origen.setX(newX);
    }

    public void setOrigenY(int newY) {
        origen.setY(newY);
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Rect getFigura() {
        return new Rect(getOrigenX(), getOrigenY(), getOrigenX()+ancho, getOrigenY()+alto);
    }

    public boolean mover(int x, int y, Rect pantalla) {
        return pantalla.contains(origen.getX() + x, origen.getY() + y, origen.getX() + ancho + x, origen.getY() + alto + y);
    }
}
