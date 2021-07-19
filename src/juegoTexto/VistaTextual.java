package juegoTexto;

import Civita.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
    int opcion = menu("¿Desea comprar esta casilla?", new ArrayList<>(Arrays.asList("SI", "NO")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar () {
    int opcion = menu("¿que numero de gestion inmobiliaria desea realizar?", new ArrayList<>(Arrays.asList("vender","hipotecar","cancelarHipoteca","construirCasa", "construirHotel","Terminar")));
    iGestion = opcion;
    ArrayList<String> calles = new ArrayList<>();

    for(int i=0; i<juegoModel.getJugadorActual().getPropiedades().size(); i++){
      calles.add(juegoModel.getJugadorActual().getPropiedades().get(i).getNombre());
    }
    iPropiedad = menu("¿sobre que casilla desea realizar la operacion?", calles);
  }
  
  public int getGestion(){return iGestion;}
  
  public int getPropiedad(){return iPropiedad;}
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
    switch (operacion){
      case AVANZAR:
        System.out.println("La siguiente operacion es avanzar");
        break;
      case COMPRAR:
        System.out.println("La siguiente operacion es comprar");
        break;
      case PASAR_TURNO:
        System.out.println("La siguiente operacion es pasar turno");
        break;
      case GESTIONAR:
        System.out.println("La siguiente operacion es gestionar");
        break;
      case SALIR_CARCEL:
        System.out.println("La siguiente operacion es salir de la carcel");



    }
  }


  void mostrarEventos() {
    while(Diario.getInstance().eventosPendientes()){
      System.out.println(Diario.getInstance().leerEvento());

    }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();

    }
  
  void actualizarVista(){
    System.out.println(juegoModel.infoJugadorTexto());

  }
}
