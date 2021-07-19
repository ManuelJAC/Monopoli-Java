package Civita;

public class OperacionInmobiliaria {
    private int numPropiedad;
    GestionesInmobiliarias gestion;

    public GestionesInmobiliarias getGestion() {return gestion;}
    public int getNumPropiedad() {return numPropiedad;}
    public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip){
        numPropiedad = ip;
        gestion = gest;
    }
}
