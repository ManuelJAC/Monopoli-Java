package Civita;

import java.util.ArrayList;
import java.util.Collections;

public class CivitasJuego {
    private int indiceJugador;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private Tablero tablero;
    private MazoSorpresas mazo;
    private ArrayList<Jugador> jugadores;

    public CivitasJuego(ArrayList<String> nombres){
        this.jugadores = new ArrayList<>();
        for(int i=0; i< nombres.size(); i++){
            Jugador jugador = new Jugador(nombres.get(i));
            jugadores.add(jugador);
        }
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        indiceJugador = Dado.getInstance().quienEmpieza(jugadores.size());
        mazo = new MazoSorpresas();
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);

    }

    public Boolean cancelarHipoteca(int ip){return jugadores.get(indiceJugador).cancelarHipoteca(ip);}
    public Boolean construirCasa(int ip){return jugadores.get(indiceJugador).construirCasa(ip);}
    public Boolean construirHotel(int ip){return jugadores.get(indiceJugador).construirHotel(ip);}
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(this.tablero.getPorSalida()>0){
            jugadorActual.pasaPorSalida();
        }
    }
    public Boolean finalDelJuego(){
        Boolean comp = false;
        for(int i=0; i<jugadores.size(); i++){
            if(jugadores.get(i).enBancarrota()){
                comp = true;
            }
        }
        return comp;
    }
    public Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugador).getNumCasillaActual());
    }
    public Jugador getJugadorActual(){return jugadores.get(indiceJugador);}
    public Boolean hipotecar(int ip){return jugadores.get(indiceJugador).hipotecar(ip);}
    public String infoJugadorTexto(){
        return jugadores.get(indiceJugador).toString();
    }

    private void pasarTurno(){
        indiceJugador = (indiceJugador+1)%jugadores.size();
    }

    public Boolean salirCarcelPagando(){return jugadores.get(indiceJugador).salirCarcelPagando();}
    public Boolean salirCarcelTirando(){return jugadores.get(indiceJugador).salirCarcelTirando();}

    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugador), estado, operacion);
    }
    public Boolean vender(int ip){return jugadores.get(indiceJugador).vender(ip);}

    private void inicializarMazoSorpresas(Tablero tablero){
        mazo.alMazo(new SorpresaIrACarcel(tablero));
        mazo.alMazo(new SorpresaIrACasilla(tablero, 6, "debes de ir a la casilla 6"));
        mazo.alMazo(new SorpresaIrACasilla(tablero, 16, "debes de ir a la casilla 16"));
        mazo.alMazo(new SorpresaSalirCarcel(mazo));
        mazo.alMazo(new SorpresaPagarCobrar(200, "Has ganado 200"));
        mazo.alMazo(new SorpresaPagarCobrar(-100, "Debes pagar 100"));
        mazo.alMazo(new SorpresaPorCasaHotel(100,"Ganas 100 por cada casa u hotel en posesion"));
        mazo.alMazo(new SorpresaPorCasaHotel(-50,"debes de pagar 50 por cada casa u hotel en posesion"));
        mazo.alMazo(new SorpresaPorJugador(100, "Cada jugador te da 100"));
        mazo.alMazo(new SorpresaPorJugador(-50, "Pagas 50 a cada jugador"));
        mazo.alMazo(new SorpresaEspeculador(300));
    }


    private void inicializarTablero(MazoSorpresas mazo){
        tablero = new Tablero(5);
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle1", 50, 10, 150, 500, 250))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle2", 50, 10, 150, 510, 270))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle3", 50, 10, 150, 520, 280))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle4", 50, 10, 150, 530, 290))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle5", 50, 10, 150, 540, 300))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle6", 50, 10, 150, 550, 330))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle7", 50, 10, 150, 560, 360))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle8", 50, 10, 150, 570, 390))));
        tablero.aniadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle9", 50, 10, 150, 580, 400))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle10", 50, 10, 150, 590, 450))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle11", 50, 10, 150, 600, 500))));
        tablero.aniadeCasilla(new CasillaSorpresa(mazo, "Parking"));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle12", 50, 10, 150, 610, 520))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle13", 50, 10, 150, 620, 570))));
        tablero.aniadeCasilla(new CasillaJuez(tablero.getCarcel(), "juez"));
        tablero.aniadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle15", 50, 10, 150, 630, 680))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle16", 50, 10, 150, 640, 700))));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle17", 50, 10, 150, 650, 800))));
        tablero.aniadeCasilla(new CasillaImpuesto(300, "Impuesto"));
        tablero.aniadeCasilla(new CasillaCalle((new TituloPropiedad("calle18", 50, 10, 150, 660, 900))));
    }

    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugador);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugador, jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    }

    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugador);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);

        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        return operacion;
    }
    public Boolean comprar(){
        boolean res = false;
        Jugador jugadorActual = jugadores.get(indiceJugador);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        CasillaCalle calle = (CasillaCalle)casilla;
        TituloPropiedad titulo = calle.getTituloPropiedad();
        if(titulo.tienePropietario()){
            System.out.println("Esta casilla ya tiene propietario");
        }
        else{
            res = jugadorActual.comprar(titulo);
        }

        return res;
    }

    private ArrayList<Jugador> ranking(){
        ArrayList<Jugador> ranking = new ArrayList<Jugador>();
        for(int i=0; i<jugadores.size(); i++){
            ranking.add(jugadores.get(i));
        }

        for(int i=0; i<ranking.size(); i++){
            for(int j=0; j< ranking.size(); j++){
                if(ranking.get(i).compareTo(ranking.get(j))<0){
                    Jugador aux = new Jugador(ranking.get(i));
                    ranking.set(i, ranking.get(j));
                    ranking.set(j, aux);
                }
            }
        }
        return ranking;
    }

    public void mostrarRanking(){
        ArrayList<Jugador> ranking = new ArrayList<Jugador>();
        ranking = ranking();

        for(int i=0; i< ranking.size(); i++){
            System.out.println((i+"- "+ranking.get(i)));
        }
    }

}
