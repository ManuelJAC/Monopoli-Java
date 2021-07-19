package juegoTexto;

import Civita.*;

public class Controlador {
    CivitasJuego juego;
    VistaTextual vista;

    Controlador(CivitasJuego juego, VistaTextual vista){
        this.juego = juego;
        this.vista = vista;
    }

    void juega() {
        vista.setCivitasJuego(juego);
        OperacionesJuego siguiente;

        while (!vista.juegoModel.finalDelJuego()) {
            vista.actualizarVista();
            vista.pausa();
            siguiente = vista.juegoModel.siguientePaso();

            vista.mostrarSiguienteOperacion(siguiente);

            if (siguiente != OperacionesJuego.PASAR_TURNO) {
                vista.mostrarEventos();
            }
            boolean fin = vista.juegoModel.finalDelJuego();

            if (!fin) {
                switch (siguiente){
                    case COMPRAR:
                        Respuestas respuesta = vista.comprar();
                        if (respuesta == Respuestas.SI) {
                            vista.juegoModel.comprar();
                        }
                        vista.juegoModel.siguientePasoCompletado(siguiente);
                        break;
                    case GESTIONAR:
                        vista.gestionar();
                        OperacionInmobiliaria operacion = new OperacionInmobiliaria(GestionesInmobiliarias.values()[vista.getGestion()], vista.getPropiedad());
                        System.out.println(operacion.getNumPropiedad()+ " es la propiedad");
                        switch (operacion.getGestion()) {
                            case CANCELAR_HIPOTECA:
                                vista.juegoModel.cancelarHipoteca(operacion.getNumPropiedad());
                                break;
                            case CONSTRUIR_CASA:
                                vista.juegoModel.construirCasa(operacion.getNumPropiedad());
                                break;
                            case CONSTRUIR_HOTEL:
                                vista.juegoModel.construirHotel(operacion.getNumPropiedad());
                                break;
                            case HIPOTECAR:
                                vista.juegoModel.hipotecar(operacion.getNumPropiedad());
                                break;
                            case VENDER:
                                vista.juegoModel.vender(operacion.getNumPropiedad());
                                break;
                            case TERMINAR:
                                vista.juegoModel.siguientePasoCompletado(OperacionesJuego.GESTIONAR);
                                break;

                        }
                        break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida = vista.salirCarcel();

                        switch (salida) {
                            case PAGANDO:
                                vista.juegoModel.salirCarcelPagando();
                                break;
                            case TIRANDO:
                                vista.juegoModel.salirCarcelTirando();
                                break;
                        }
                        vista.juegoModel.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);
                        break;
                }


            }
        }
        vista.juegoModel.mostrarRanking();
    }
}

