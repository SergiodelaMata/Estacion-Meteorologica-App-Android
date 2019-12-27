import java.io.*;
import java.net.*;

public class Servidor{

    public static void main(String[] args) {
        
        ServerSocket servidor;

        Socket conexion;

        System.out.print("Inicializando servidor... ");

        try {

            servidor = new ServerSocket(8080);

            int idSesion = 0;

            while (true) {
                conexion = servidor.accept();
                System.out.println("\t[OK]");

                System.out.println("Nueva conexión entrante: "+conexion);

                ((ServidorHilo) new ServidorHilo(conexion, idSesion)).start();

                idSesion ++;

            }
        } catch (IOException ex) {

            System.out.println("Error: "+ex.getMessage());
        }
    }
}