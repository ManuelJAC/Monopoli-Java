package Civita;

import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private Boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa UltimaSorpresa;

    private void init(){
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }

    MazoSorpresas(boolean d){
        this.debug = d;
        init();
        if(debug) {
            //informar de que debug esta activado en diario
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("modo debug activado en el mazo Cartas Sorpresas");
        }
    }
    MazoSorpresas(){
        init();
        this.debug=false;
    }

    void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }

    Sorpresa siguiente(){
        if(!barajada || usadas== sorpresas.size()){
            if(!debug){
                Collections.shuffle(sorpresas);
            }
            usadas=0;
            barajada=true;
            usadas+=1;
            UltimaSorpresa = sorpresas.get(0);
            sorpresas.remove(0);
            sorpresas.add(UltimaSorpresa);
        }
        return UltimaSorpresa;
    }

    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        for(int i=0; i<sorpresas.size(); i++){
            if(sorpresas.get(i).equals(sorpresa)){
                cartasEspeciales.add(sorpresas.get(i));
                sorpresas.remove(i);
                //añadir operacion al diario
                Diario diario = Diario.getInstance();
                diario.ocurreEvento("Carta especial extraida del mazo Sopresas");
            }
        }

    }

    void habilitarCartaEspecial(Sorpresa sorpresa){
        for(int i=0; i<cartasEspeciales.size(); i++){
            if(cartasEspeciales.get(i).equals(sorpresa)){
                sorpresas.add(cartasEspeciales.get(i));
                cartasEspeciales.remove(i);
                //añadir operacion al diario
                Diario diario = Diario.getInstance();
                diario.ocurreEvento("Carta especial extraida del mazo cartas Especiales");
            }
        }
    }
    Sorpresa getSorpresa(int i){
        return sorpresas.get(i);
    }

    int getTamSorpresas(){
        return sorpresas.size();
    }


}


