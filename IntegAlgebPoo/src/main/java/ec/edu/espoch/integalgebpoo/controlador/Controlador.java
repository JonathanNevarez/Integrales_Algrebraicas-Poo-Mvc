package ec.edu.espoch.integalgebpoo.controlador;

import ec.edu.espoch.integalgebpoo.modelo.Resolucion;
import ec.edu.espoch.integalgebpoo.vista.Interfaz;

public class Controlador {
    private  Interfaz vista;
    private Resolucion modelo;

    public Controlador(Interfaz vista) {
        this.vista = vista;
        this.modelo = new Resolucion();
    }

    public void procesaEventoResolverIntegral(){
        String valorFuncion = this.vista.getEntradaValores();
        modelo.setValorFuncion(valorFuncion);
        this.modelo.instanciarFuncion();
        this.modelo.obtenerValores();
        if(this.modelo.getResultado().contains("!")){
            this.vista.mostrarProcedimiendo1("");
            this.vista.mostrarProcedimiendo2(this.modelo.getResultado());
            this.vista.mostrarResolucion(""); 
        }else{
            this.vista.mostrarProcedimiendo1(this.modelo.procedimiento1());
            this.vista.mostrarProcedimiendo2(this.modelo.procedimiento2());
            this.vista.mostrarResolucion(this.modelo.getResultado() + "+C"); 
        }
    }   
}