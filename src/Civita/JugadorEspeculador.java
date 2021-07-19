package Civita;

public class JugadorEspeculador extends Jugador {
    private static int factorEspeculador = 2;
    private float fianza;
    private Boolean especulador;

    JugadorEspeculador(Jugador jugador, float fianza){
        super(jugador);
        this.fianza = fianza;
        especulador = true;
        for(int i=0; i<super.getPropiedades().size(); i++){
            super.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }

    @Override
    Boolean encarcelar(int numCasillaCarcel) {
        Boolean resultado = false;
        if(!super.encarcelado){
            if(!tieneSalvoconducto()){
                if(fianza>=super.getSaldo()){
                    resultado = true;
                }
                else{
                    super.paga(fianza);
                    Diario.getInstance().ocurreEvento("El jugador "+super.getNombre()+" no entra en la carcel pagando la fianza");
                }
            }
            else{
                resultado = false;
                super.perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador "+super.getNombre()+" no entra en la carcel utilizando el salvoconducto");

            }
        }
        return resultado;
    }

    @Override
    int getHotelesMax() {
        return (super.getHotelesMax()*factorEspeculador);
    }

    @Override
    int getCasasMax() {
        return (super.getCasasMax()*factorEspeculador);
    }

    @Override
    Boolean pagaImpuesto(float cantidad) {
        Boolean resultado = false;

        if(!encarcelado){
            float impuesto = cantidad / 2;
            resultado = paga(impuesto);
            Diario.getInstance().ocurreEvento("El jugador "+super.getNombre()+" paga los impuestos por una cantidad igual a "+impuesto);
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "JugadorEspeculador{" +
                super.toString() +
                "fianza=" + fianza +
                ", especulador=" + especulador +
                '}';
    }
}
