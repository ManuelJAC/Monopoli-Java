package Civita;

import java.util.ArrayList;

public class Casilla {
    private String nombre;

    Casilla(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public Boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(actual>=0 && actual<todos.size()){
            return true;
        }
        else{
            return false;
        }
    }

    void informe(int actual, ArrayList<Jugador>todos){
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("el jugador " + todos.get(actual).getNombre() + " ha caido en la " + this.toString());

    }

    public String toString(){
        return "Casilla{" +
                "nombre=" + getNombre() +
                '}';
    }

    void recibeJugador(int actual, ArrayList<Jugador>todos){
        informe(actual, todos);
    }

}

