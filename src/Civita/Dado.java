package Civita;

import java.util.Random;

public class Dado {
    static final private Dado instance = new Dado();

    private Random random;
    private int ultimoResultado;
    private Boolean debug;

    private static int salidaCarcel = 5;

    private Dado(){
        this.random = new Random();
        this.ultimoResultado=0;
        this.debug=false;
    }

    static public Dado getInstance() { return instance; }

    int tirar(){
        if(!debug){
            int valorDado = random.nextInt(6)+1;
            this.ultimoResultado = valorDado;
            return this.ultimoResultado;
        }
        else {
            //¿hace falta registrar el ultimo resultado si el debug esta activado?
            this.ultimoResultado = 1;
            return 1;
        }
    }

    Boolean salgoCarcel(){
        //¿utilizar la variable ultimoResultado o llamar al metodo tirar()?
        int valor = tirar();

        if(valor>=5){
            return true;
        }
        else{
            return false;
        }
    }

    int quienEmpieza(int n) {
        return this.random.nextInt(n);
    }

    void setDebug(boolean d){
        this.debug=d;
        // informar de que debug esta activado en diario
        Diario diario = Diario.getInstance();
        if(debug){
            diario.ocurreEvento("modo debug activado en dado");
        }
        else{
            diario.ocurreEvento("modo debug desactivado en dado");
        }

    }

    int getUltimoResultado(){ return this.ultimoResultado; }

}
