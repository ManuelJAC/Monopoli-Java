package Civita;

public class TituloPropiedad {
    private float alquilerBase;
    private static float factorinteresesHipoteca = (float) 1.1;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;

    TituloPropiedad(String nombre, float ab, float fr, float hb, float pc, float pe){
        this.nombre = nombre;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.precioCompra = pc;
        this.precioEdificar = pe;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.hipotecado = false;
        this.propietario = null;
    }
    void actualizaPropietarioPorConversion(Jugador jugador){
        this.propietario = jugador;
    }

    boolean cancelarHipoteca(Jugador jugador){
        boolean resultado = false;
        if(hipotecado && esEsteElPropietario(jugador)){
            propietario.paga(getImporteCancelarHipoteca());
            hipotecado = false;
            resultado = true;

        }
        return resultado;
    }

    int cantidadCasasHoteles(){
        return this.numCasas + this.numHoteles;
    }

    boolean comprar(Jugador jugador){
        Boolean resultado = false;
        if(!tienePropietario()){
            actualizaPropietarioPorConversion(jugador);
            propietario.paga(getPrecioCompra());
            resultado = true;
        }
        return resultado;
    }
    boolean contruirCasa(Jugador jugador){
        boolean comp = false;
        if(esEsteElPropietario(jugador)){
            propietario.paga(getPrecioEdificar());
            numCasas += 1;
            comp = true;
        }
        return comp;
    }
    boolean construirHotel(Jugador jugador){
        boolean comp = false;
        if(esEsteElPropietario(jugador)){
            jugador.paga(getPrecioEdificar());
            numHoteles += 1;
            comp = true;
        }
        return comp;
    }

    boolean derruirCasas(int n, Jugador jugador){
        Boolean resultado = false;
        if(esEsteElPropietario(jugador) && this.numCasas>=n){
            this.numCasas = this.numCasas - n;
            resultado = true;
        }
        return resultado;
    }

    boolean esEsteElPropietario(Jugador jugador){
        if(this.propietario == jugador){
            return true;
        }
        else{
            return false;
        }
    }


    private Boolean propietarioEncarcelado(){
        Boolean result = false;
        if(tienePropietario()){
            result = this.propietario.isEncarcelado();
        }
        return result;
    }

    Boolean tienePropietario(){
        if(propietario == null){
            return false;
        }
        else{
            return true;
        }
    }

    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && !esEsteElPropietario(jugador)){
            jugador.pagaAlquiler(getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler());
        }
    }

    Boolean vender(Jugador jugador){
        Boolean resultado = false;
        if(esEsteElPropietario(jugador)){
            propietario.recibe(getPrecioVenta());
            numCasas = 0;
            numHoteles = 0;
            propietario = null;
            resultado = true;
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" +
                "nombre= " + nombre +
                ", factorRevalorizacion= " + factorRevalorizacion +
                ", hipotecaBase= " + hipotecaBase +
                ", hipotecado= " + hipotecado +
                ", alquilerBase= " + alquilerBase  +
                ", numCasas= " + numCasas +
                ", numHoteles= " + numHoteles +
                ", precioCompra= " + precioCompra +
                ", precioEdificar= " + precioEdificar +
                '}';
    }

    Boolean hipotecar(Jugador jugador){
        Boolean result = false;
        if(!hipotecado && this.esEsteElPropietario(jugador)){
            jugador.recibe(this.getImporteHipoteca());
            hipotecado = true;
            result = true;
        }
        return result;
    }

    /*METODOS GETS DE LA CLASE TituloPropiedad*/
    public boolean getHipotecado(){return this.hipotecado;}
    float getImporteCancelarHipoteca(){return this.hipotecaBase * this.factorRevalorizacion;}
    private float getImporteHipoteca(){return this.hipotecaBase;}
    public String getNombre(){return this.nombre;}
    int getNumCasas(){return this.numCasas;}
    int getNumHoteles(){return this.numHoteles;}
    float getPrecioEdificar(){return this.precioEdificar;}
    Jugador getPropietario(){return this.propietario;}
    float getPrecioCompra(){return this.precioCompra;}
    private float getPrecioVenta(){
        float total;
        total = this.precioCompra + (this.numCasas + 5*this.numHoteles) * this.precioEdificar*this.factorRevalorizacion;
        return total;
    }

    private float getPrecioAlquiler(){
        if(hipotecado || this.propietario.isEncarcelado()){
            return 0;
        }
        else{
            return (float) (this.alquilerBase+(1+(this.numCasas*0.5)+(this.numHoteles*2.5)));
        }
    }


}
