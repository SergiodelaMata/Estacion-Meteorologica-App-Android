import java.io.*;
import java.net.*;


class ClienteID extends Thread{

    Socket cliente;

    DataInputStream entrada;
    DataOutputStream salida;

    String mensaje, respuesta;

    private String idConexion;

    public ClienteID(String id){
        this.idConexion = id;
    }

    @Override
    public void run(){

        try{

            cliente = new Socket("weatherubicuastation.duckdns.org", 8080);
            
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());

            mensaje = "Refresh-"+this.idConexion; 

            salida.writeUTF(mensaje);

            respuesta = entrada.readUTF(); //Seria el archivo JSON enviado por el server

            entrada.close();
            salida.close();
            cliente.close();
            
        }catch(IOException e){

            System.out.println("Error: "+e.getMessage());
        }
    }
}

public class Cliente{
    public static void main(String[] args) {
        
        ClienteID cliente = new ClienteID("Alcala De Henares"); //El parametro de construccion de ClienteID seria el texto del spinner
                                                                //ya sea latitud-longitud o el id de la estacion
        cliente.start();
    }
}
