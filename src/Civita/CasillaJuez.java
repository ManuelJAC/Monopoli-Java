package Civita;

import java.util.ArrayList;

public class CasillaJuez extends Casilla {
    private static int carcel;

    CasillaJuez(int numCarcel, String nombre){
        super(nombre);
        carcel = numCarcel;
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            todos.get(actual).encarcelar(this.carcel);
        }
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre=" + super.getNombre() +
                ", te mandara a la casilla de carcel en la casilla=" + carcel +
                '}';
    }
}
