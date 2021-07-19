package Civita;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa {
    private MazoSorpresas mazo;

    SorpresaSalirCarcel(MazoSorpresas mazo){
        super("Puedes salir de la carcel");
        this.mazo = mazo;
    }

    @Override
    void aplicarAjugador(int actual, ArrayList<Jugador> todos) {
        Boolean tiene = false;
        if(jugadorCorrecto(actual,todos)){
            informe(actual, todos);
            for(int i=0; i< todos.size(); i++){
                if(todos.get(i).tieneSalvoconducto()){
                    tiene = true;
                }
            }
            if(!tiene){
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }

    @Override
    public String toString() {
        return "SorpresaSalirCarcel{" +
                "texto= " + super.getTexto() +
                '}';
    }

    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }

    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
}
