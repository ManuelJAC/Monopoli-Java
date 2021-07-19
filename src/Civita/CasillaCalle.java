package Civita;

import javax.swing.*;
import java.util.ArrayList;

public class CasillaCalle extends Casilla {
    private float importe;
    private TituloPropiedad tituloPropiedad;

    CasillaCalle(TituloPropiedad tituloPropiedad){
        super(tituloPropiedad.getNombre());
        this.tituloPropiedad = tituloPropiedad;
        importe = tituloPropiedad.getPrecioCompra();
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            Jugador jugador = todos.get(actual);
            if(!tituloPropiedad.tienePropietario()){
                jugador.puedeComprarCasilla();
            }
            else{
                tituloPropiedad.tramitarAlquiler(jugador);
            }
        }
    }

    TituloPropiedad getTituloPropiedad(){return tituloPropiedad;}

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre=" + super.getNombre() +
                ", importe=" + importe +
                ", numero de casas= " + getTituloPropiedad().getNumCasas() +
                ", numero de hoteles= "+ getTituloPropiedad().getNumHoteles() +
                '}';
    }
}
