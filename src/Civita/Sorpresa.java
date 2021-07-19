package Civita;

import java.util.ArrayList;

public abstract class Sorpresa {
    private String texto;

    Sorpresa(String texto){
        this.texto = texto;
    }

    abstract void aplicarAjugador(int actual, ArrayList<Jugador>todos);


    public Boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(actual>=todos.size()){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString(){
        return "Sorpresa{" +
                "texto= " + texto +
                '}';
    }

    String getTexto(){return texto;}

    void informe(int actual, ArrayList<Jugador>todos){
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("el jugador" + todos.get(actual).getNombre() + "esta utilizando la " + toString());
    }


}
