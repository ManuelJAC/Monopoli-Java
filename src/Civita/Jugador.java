package Civita;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador>{
    protected static int CasasMax = 4;
    protected  static int CasasPorHotel = 4;
    protected  boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida = 1000;
    protected float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial = 7500;
    private Sorpresa salvoconducto;
    private ArrayList<TituloPropiedad> propiedades;

    Jugador(String nombre){
        this.nombre = nombre;
        propiedades = new ArrayList<>();
        this.puedeComprar = false;
        saldo = 7500;
    }
    protected Jugador(Jugador otro){
        this.nombre = otro.nombre;
        this.saldo = otro.saldo;
        this.numCasillaActual = otro.numCasillaActual;
        this.encarcelado = otro.encarcelado;
        this.puedeComprar = otro.puedeComprar;
        this.salvoconducto = otro.salvoconducto;
        this.propiedades = new ArrayList<TituloPropiedad>(otro.propiedades);
    }

    public int compareTo(Jugador otro){
        if(this.getSaldo() == otro.getSaldo()){
            return 0;
        }
        else if(this.getSaldo() < otro.getSaldo()){
            return 1;
        }
        else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre  +
                ", encarcelado=" + encarcelado +
                ", numCasillaActual=" + numCasillaActual +
                ", PrecioLibertad=" + PrecioLibertad +
                ", puedeComprar=" + puedeComprar +
                ", saldo=" + saldo +
                ", salvoconducto=" + salvoconducto +
                ", propiedades=" + getNomPropiedades() +
                '}';
    }

    int cantidadCasasHoteles(){
        int sumaT=0;
        int suma;
        for(int i=0; i<this.propiedades.size(); i++){
            suma = this.propiedades.get(i).getNumCasas() + this.propiedades.get(i).getNumHoteles();
            sumaT += suma;
        }
        return sumaT;
    }

    public Boolean isEncarcelado(){
        if(encarcelado){
            return true;
        }
        else{
            return false;
        }
    }
    Boolean tieneSalvoconducto(){
        if(this.salvoconducto == null){
            return false;
        }
        else{
            return true;
        }
    }
    void perderSalvoconducto(){
        this.salvoconducto = null;
    }
    protected Boolean debeSerEncarcelado(){
        if(this.isEncarcelado()){
            return false;
        }
        else if(!this.tieneSalvoconducto()){
            return true;
        }
        else{
            this.perderSalvoconducto();
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("El jugador"  + this.nombre + " ha evitado la carcel con la carta sorpresa salir de carcel");
            return false;
        }
    }
    Boolean obtenerSalvoconducto(Sorpresa s){
        if(this.isEncarcelado()){
            return false;
        }
        else{
            this.salvoconducto = s;
            return true;
        }
    }

    Boolean modificarSaldo(float cantidad){
        this.saldo += cantidad;
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("saldo modificado del jugador " + this.nombre);
        return true;
    }

    Boolean moverACasilla(int numCasilla){
        if(this.isEncarcelado()){
            return false;
        }
        else{
            this.numCasillaActual = numCasilla;
            this.puedeComprar = false;
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("el jugador " + this.nombre + " se ha movido a la posicion " + this.numCasillaActual);
            return true;
        }
    }

    Boolean paga(float cantidad){
        cantidad *= -1;
        modificarSaldo(cantidad);
        return true;
    }

    Boolean pagaImpuesto(float cantidad){
        if(this.isEncarcelado()){
            return false;
        }
        else{
            paga(cantidad);
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" paga los impuestos por una cantidad igual a "+cantidad);
        }
            return true;

    }

    Boolean pagaAlquiler(float cantidad){
        if(this.isEncarcelado()){
            return false;
        }
        else{
            paga(cantidad);
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" paga el alquiler por una cantidad igual a "+cantidad);
            return true;
        }
    }

    Boolean recibe(float cantidad){
        if(this.isEncarcelado()){
            return false;
        }
        else{
            modificarSaldo(cantidad);
            return true;
        }
    }

    Boolean enBancarrota(){
        if(this.saldo<0){
            return true;
        }
        else{
            return false;
        }
    }

    Boolean encarcelar(int numCasillaCarcel){
        if(this.debeSerEncarcelado()){
            this.moverACasilla(numCasillaCarcel);
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("el jugador" + this.nombre + "ha sido encarcelado");
            encarcelado = true;
        }
        return encarcelado;
    }

    Boolean existeLaPropiedad(int ip){
        if(propiedades.get(ip)!=null){
            return true;
        }
        else{
            return false;
        }
    }


    Boolean pasaPorSalida(){
        this.modificarSaldo(this.getPremioPasoPorSalida());
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("el jugador" + this.nombre + "ha recibido la cantidad por pasar la salida");
        return true;
    }

    Boolean puedeComprarCasilla(){
        if(this.isEncarcelado()){
            this.puedeComprar = false;
        }
        else{
            this.puedeComprar = true;
        }
        return this.puedeComprar;
    }

    private Boolean PuedeSalirCarcelPagando(){return this.getSaldo() >= this.getPrecioLibertad();}

    private Boolean puedoEdificarCasa(TituloPropiedad propiedad){
        for(int i=0; i<propiedades.size(); i++){
            if(this.propiedades.get(i).getNombre().equals(propiedad.getNombre()) && this.getSaldo()>=this.propiedades.get(i).getPrecioEdificar()){
                return this.propiedades.get(i).getNumCasas() < 4;
            }
        }
        return false;
    }

    private Boolean puedoEdificarHotel(TituloPropiedad propiedad){
        for(int i=0; i<propiedades.size(); i++){
            if(this.propiedades.get(i).getNombre().equals(propiedad.getNombre()) && this.getSaldo()>=this.propiedades.get(i).getPrecioEdificar()){
                return this.propiedades.get(i).getNumCasas() == 4;
            }
        }
        return false;
    }

    private Boolean puedoGastar(float precio){return this.getSaldo() >= precio;}

    Boolean salirCarcelPagando(){
        if(this.isEncarcelado()){
            if(this.getSaldo()>=this.getPrecioLibertad()){
                Diario diario = Diario.getInstance();
                diario.ocurreEvento("el jugador" + this.nombre + "ha salido de la carcel pagando la tasa de libertad");
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    Boolean salirCarcelTirando(){
        Boolean aux;
        Dado dado;
        dado = Dado.getInstance();
        aux = dado.salgoCarcel();
        if(aux){
            this.encarcelado = false;
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("el jugador" + this.nombre + "ha salido de la carcel tirando el dado");
        }
        return aux;
    }

    Boolean tieneAlgoQueGestionar(){return this.propiedades.size()>0;}

    Boolean vender(int ip){
        Boolean resultado = false;
        if(this.isEncarcelado()){
            return resultado;
        }
        else if(this.existeLaPropiedad(ip)){
            resultado = propiedades.get(ip).vender(this);
            if(resultado){
                Diario.getInstance().ocurreEvento("El jugador "+nombre+" ha vendido la propiedad "+ propiedades.get(ip).getNombre());
                propiedades.remove(ip);
            }

        }
        return false;
    }

    /*METODOS GETS DE LA CLASE Jugador*/

    int getCasasMax(){return this.CasasMax;}
    int getCasasPorHotel(){return this.CasasPorHotel;}
    int getHotelesMax(){return this.HotelesMax;}
    protected String getNombre(){return this.nombre;}
    int getNumCasillaActual(){return this.numCasillaActual;}
    private float getPrecioLibertad(){return this.PrecioLibertad;}
    private float getPremioPasoPorSalida(){return this.PasoPorSalida;}
    public ArrayList<TituloPropiedad> getPropiedades(){return (propiedades);}
    Boolean getPuedeComprar(){return this.puedeComprar;}
    protected float getSaldo(){return this.saldo;}

    /*Definición de metodos que se implementarán en la siguiente practica*/

    Boolean cancelarHipoteca(int ip){
        boolean result = false;
        if(this.encarcelado){
            return result;
        }
        else{
            if(this.existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                boolean puedoGastar = this.puedoGastar(cantidad);
                if(puedoGastar){
                    result = propiedad.cancelarHipoteca(this);
                    if(result){
                        Diario.getInstance().ocurreEvento("el jugador "+ nombre+" cancela la hipoteca de la propiedad "+ ip);
                    }
                }
            }
        }
        return result;
    }
    Boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        if(this.encarcelado){
            return result;
        }
        else if(!titulo.tienePropietario()){
            if(puedeComprar){
                float precio = titulo.getPrecioCompra();
                if(puedoGastar(precio)){
                    result = titulo.comprar(this);
                    if(result){
                        propiedades.add(titulo);
                        Diario.getInstance().ocurreEvento("El jugador "+ this + " compra la propiedad "+ titulo.toString());
                    }
                    puedeComprar = false;
                }

            }
        }

        return result;
    }
    Boolean construirHotel(int ip){
        boolean result = false;

        if(encarcelado){
            return result;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
            if(puedoEdificarHotel){
                result = propiedad.construirHotel(this);
                int casasPorHotel = getCasasPorHotel();
                propiedad.derruirCasas(casasPorHotel, this);
                Diario.getInstance().ocurreEvento("El jugador "+ nombre+ " construye hotel en la propiedad "+ip);
            }
        }
        return result;
    }

    Boolean construirCasa(int ip){
        boolean result = false;

        if(encarcelado){
            return result;
        }
        else{
            boolean existe = existeLaPropiedad(ip);
            if(existe){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(puedoEdificarCasa){
                    result = propiedad.contruirCasa(this);
                    if(result){
                        Diario.getInstance().ocurreEvento("El jugador "+nombre+" construye casa en la propiedad "+ip);
                    }
                }

            }
        }
        return result;
    }

    Boolean hipotecar(int ip){
        boolean result = false;

        if(encarcelado){
            return result;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }
        if(result){
            Diario.getInstance().ocurreEvento("El jugador "+ nombre + " hipoteca la propiedad "+ ip);
        }
        return result;
    }
    void setPuedeComprar(boolean result){
        puedeComprar = result;
    }
    String getNomPropiedades(){
        String mensaje = "";
        for(int i=0; i<propiedades.size(); i++){
            mensaje = propiedades.get(i).getNombre() + "|";
        }
        return mensaje;
    }
}
