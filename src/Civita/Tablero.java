package Civita;

import java.util.ArrayList;

public class Tablero {
    private int numCasillaCarcel;
    private int porSalida;
    private ArrayList<Casilla> casillas;
    private Boolean tieneJuez;

    Tablero(int numCasillaCarcel){
        if(numCasillaCarcel>=1){
            this.numCasillaCarcel=numCasillaCarcel;
        }
        else{
            this.numCasillaCarcel=1;
        }
        this.porSalida=0;
        this.tieneJuez=false;

        this.casillas = new ArrayList<Casilla>();
        Casilla salida;
        salida= new Casilla("salida");
        casillas.add(salida);
    }
    private Boolean correcto(){
        if(this.casillas.size()>this.numCasillaCarcel && tieneJuez){
            return true;
        }
        else{
            return false;
        }
    }

    private Boolean correcto(int numCasilla){
        if(correcto() && numCasilla<=this.casillas.size()){
            return true;
        }
        else{
            return false;
        }
    }
    int getCarcel(){
        return this.numCasillaCarcel;
    }

    int getPorSalida(){
        if(this.porSalida>0){
            int aux=this.porSalida;
            this.porSalida=this.porSalida-1;
            return aux;
        }
        else{
            return this.porSalida;
        }

    }
    void aniadeCasilla(Casilla casilla){
        if(this.casillas.size()==this.numCasillaCarcel){
            Casilla carcel;
            carcel = new Casilla("carcel");
            casillas.add(carcel);
            casillas.add(casilla);
        }
        if(casilla instanceof CasillaJuez){
            aniadeJuez();
        }
        else{
            casillas.add(casilla);
        }
        if(this.casillas.size()==this.numCasillaCarcel){
            Casilla carcel;
            carcel = new Casilla("carcel");
            casillas.add(carcel);
            }
        }



    void aniadeJuez(){
        if(!tieneJuez){
            CasillaJuez juez = new CasillaJuez(numCasillaCarcel,"juez");;
            casillas.add(juez);
            tieneJuez=true;
        }
    }
    Casilla getCasilla(int numCasilla){
        if(correcto(numCasilla)){
            return this.casillas.get(numCasilla);
        }
        else{
            return null;
        }
    }
    int nuevaPosicion(int actual, int tirada){
        if(!correcto()){
            return -1;
        }
        else{
            int nueva = actual + tirada;

            if(nueva>=this.casillas.size()){
                nueva = nueva % this.casillas.size();
                this.porSalida = this.porSalida + 1;
                return nueva;
            }
            else{
                return nueva;
            }

        }
    }

    int calcularTirada(int origen, int destino){
        int valor = destino - origen;

        if(valor<0){
            valor = valor + this.casillas.size();
        }
        return valor;
    }
}
