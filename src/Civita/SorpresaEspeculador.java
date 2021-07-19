package Civita;

import java.util.ArrayList;

public class SorpresaEspeculador extends Sorpresa {
    private int valor;

    SorpresaEspeculador(int valor){
        super("debes convertirte en especulador");
        this.valor = valor;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual,todos)){
            informe(actual, todos);
            JugadorEspeculador especulador = new JugadorEspeculador(todos.get(actual), valor);
            todos.set(actual, especulador);
        }
    }
}
