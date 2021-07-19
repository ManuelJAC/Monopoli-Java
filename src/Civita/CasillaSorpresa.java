package Civita;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;

    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        this.mazo = mazo;
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            Sorpresa sorpresa = mazo.siguiente();
            informe(actual, todos);
            sorpresa.aplicarAjugador(actual, todos);
        }
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre= " + super.getNombre() +
                ", sorpresa=" + sorpresa +
                '}';
    }
}
