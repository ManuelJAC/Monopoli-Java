package Civita;

import java.util.ArrayList;

public class SorpresaIrACasilla extends Sorpresa {
    private int valor;
    private Tablero tablero;

    SorpresaIrACasilla(Tablero tablero, int valor, String texto){
        super(texto);
        this.tablero = tablero;
        this.valor = valor;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            int casillaActual, nuevaPosicion;
            super.informe(actual, todos);
            casillaActual = todos.get(actual).getNumCasillaActual();
            nuevaPosicion = tablero.calcularTirada(casillaActual, this.valor);
            todos.get(actual).moverACasilla(nuevaPosicion);
            tablero.getCasilla(valor).recibeJugador(actual, todos);
        }
    }

    @Override
    public String toString() {
        return "SorpresaIrACasilla{" +
                "texto= " + super.getTexto() +
                ", valor= " + valor +
                '}';
    }
}
