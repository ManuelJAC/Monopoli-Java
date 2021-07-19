package Civita;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa {
    private int valor;

    SorpresaPorCasaHotel(int valor, String texto){
        super(texto);
        this.valor = valor;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            int casasHoteles;
            this.informe(actual, todos);
            casasHoteles = todos.get(actual).cantidadCasasHoteles();
            todos.get(actual).modificarSaldo(this.valor*casasHoteles);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPorCasaHotel{" +
                "texto= " + super.getTexto() +
                ", valor=" + valor +
                '}';
    }
}
