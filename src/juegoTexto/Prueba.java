package juegoTexto;

import Civita.CivitasJuego;

import java.util.ArrayList;

public class Prueba {
    public static void main(String args[]){
        VistaTextual vista = new VistaTextual();

        ArrayList<String> nombres = new ArrayList<>();

        nombres.add("Jugador1");
        nombres.add("Jugador2");

        CivitasJuego juego = new CivitasJuego(nombres);

        Controlador controlador = new Controlador(juego, vista);
        controlador.juega();
    }
}
