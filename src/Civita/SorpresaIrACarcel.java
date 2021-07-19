package Civita;

import java.util.ArrayList;

public class SorpresaIrACarcel extends Sorpresa {
    private Tablero tablero;

    SorpresaIrACarcel(Tablero tablero){
        super("Vas a la carcel");
        this.tablero = tablero;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            this.informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    @Override
    public String toString() {
        return "SorpresaIrACarcel{" +
                "texto=" + super.getTexto() +
                '}';
    }
}
