package Civita;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa {
    private int valor;

    SorpresaPagarCobrar(int valor, String texto){
        super(texto);
        this.valor = valor;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            this.informe(actual, todos);
            todos.get(actual).modificarSaldo(this.valor);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPagarCobrar{" +
                "texto= " + super.getTexto() +
                ", valor=" + valor +
                '}';
    }
}
