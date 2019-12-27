import java.io.*;
import java.net.*;

public class ServidorHilo extends Thread{

    private Socket conexion;

    private DataOutputStream salida;
    private DataInputStream entrada;

    private int idSesion;

    public ServidorHilo(Socket socket, int id) {

        this.conexion = socket;
        this.idSesion = id;

        try {

            salida = new DataOutputStream(conexion.getOutputStream());
            entrada = new DataInputStream(conexion.getInputStream());

        } catch (IOException ex) {

            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void desconectar() {
        try {

            conexion.close();

        } catch (IOException ex) {

            System.out.println("Error: "+ex.getMessage());
        }
    }

    @Override
    public void run() {

        String parametros = "";

        try {

            parametros = entrada.readUTF();
        
            //Aqui veriamos el id de la estacion para realizar la consulta en la base de datos y devolver el JSON
            if(parametros.startsWith("Refresh")){

            }
            //Aqui pondriamos más posibles acciones que puede ejecutar el servidor por ejemplo actualización de tablas...

        } catch (IOException ex) {

            System.out.println("Error: "+ex.getMessage());
        }

        desconectar();
    }
}
