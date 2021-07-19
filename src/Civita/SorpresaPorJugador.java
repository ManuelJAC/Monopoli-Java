package Civita;

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa {
    private int valor;

    SorpresaPorJugador(int valor, String texto){
        super(texto);
        this.valor = valor;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            int nuevoValor = this.valor * -1;
            int valorCobrar = this.valor * (todos.size()-1);
            SorpresaPagarCobrar pagar = new SorpresaPagarCobrar(nuevoValor, "sorpresa tipo pagarCobrar" );
            for(int i = 0; i<todos.size(); i++){
                if(i != actual){
                    todos.get(i).modificarSaldo(nuevoValor);
                }
            }
            SorpresaPagarCobrar cobrar = new SorpresaPagarCobrar(valorCobrar, "sorpresa tipo pagarCobrar");
            todos.get(actual).modificarSaldo(valorCobrar);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPorJugador{" +
                "texto= " + super.getTexto() +
                ", valor=" + valor +
                '}';
    }
}
