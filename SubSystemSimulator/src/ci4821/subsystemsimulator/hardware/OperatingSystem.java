package ci4821.subsystemsimulator.hardware;

public class OperatingSystem {

    private Processor processor;

    public OperatingSystem(){

    }
    public void startProcess(String nameProcess, int nPages){
        // OBTENER ESPACIO EN MEMORIA PARA LA PAGE TABLE PARA ESTE NUEVO PROCESO
        // OBTENER SU DIRECCIÓN EN MEMORIA
        // INICIALIZAR VACIA

        // OBTENER ESPACIO EN MEMORIA PARA LA disk TABLE PARA ESTE NUEVO PROCESO
        // OBTENER SU DIRECCIÓN EN MEMORIA
        // INICIALIZAR VACIA

        // RESETEAR LA PAGE TABLA DEL MMU CON LA DE ESTE PROCESO
        //processor.setMMUPageTableAddress();
    }

    public void setReferenceProcessor(Processor processor) {
        this.processor = processor;
    }
}
