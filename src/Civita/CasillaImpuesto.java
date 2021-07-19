package Civita;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla {
    private float importe;

    CasillaImpuesto(float cantidad, String nombre){
        super(nombre);
        importe = cantidad;
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            todos.get(actual).pagaImpuesto(this.importe);
        }
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre=" + super.getNombre() +
                ", importe=" + importe +
                '}';
    }
}
