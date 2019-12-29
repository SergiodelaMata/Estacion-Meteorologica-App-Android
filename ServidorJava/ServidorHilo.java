import java.io.*;
import java.net.*;
import java.sql.*;

public class ServidorHilo extends Thread{

    private Socket conexion;

    private DataOutputStream salida;
    private DataInputStream entrada;
    
    private Connection conexionBD = null;
    
    private String dominio, usuario, password;

    public ServidorHilo(Socket socket) {

        this.conexion = socket;
        this.dominio = "jdbc:mysql://localhost/prueba"; //Cambiar el dominio en funcion del nombre de la base de datos
        this.usuario = "root";
        this.password = "WeatherStationUbicua2019"; 

        try {

            salida = new DataOutputStream(conexion.getOutputStream());
            entrada = new DataInputStream(conexion.getInputStream());

        } catch (IOException ex) {

            System.out.println("ErrorIO: "+ex.getMessage());
        }
    }

    public void desconectar() {
        try {

            conexion.close();

        } catch (IOException ex) {

            System.out.println("ErrorIO: "+ex.getMessage());
        }
    }

    @Override
    public void run() {

        String parametros = "";

        try {

            parametros = entrada.readUTF();
            
            String[] tokens = parametros.split("-");
            
            System.out.println(tokens[0]+tokens[1]);
        
            //Aqui veriamos el id de la estacion para realizar la consulta en la base de datos y devolver el JSON
            if(tokens[0].equals("Refresh")){
                
                try{
                    conectarBD();
                    salida.writeUTF(refreshDatos(tokens[1]));
                    desconectarBD();
                }catch(SQLException ex){                  
                    System.out.println("ErrorSQL: "+ ex.getMessage());
                }
            }
            //Aqui pondriamos más posibles acciones que puede ejecutar el servidor por ejemplo actualización de tablas...

        } catch (IOException ex) {

            System.out.println("ErrorIO: "+ex.getMessage());
        }

        desconectar();
    }
    
    public void conectarBD() throws SQLException{
            
        conexionBD = DriverManager.getConnection(dominio, usuario, password);
    }    
    
    public void desconectarBD() throws SQLException{
        
        conexionBD.close();
    }
    
    public String refreshDatos(String nombreEstacion) throws SQLException{
        
        Statement estado = conexionBD.createStatement();
        ResultSet consulta = estado.executeQuery("select * from tabla where id = "+nombreEstacion); //Cambiar segun la base de datos
        
        String resultado = "";
        while(consulta.next()){
            
            if(consulta.getString(1)!=null){
                resultado += consulta.getString(1) + "\n";
            }else{
                resultado += "NULL \n";
            }
        }
        
        return resultado;
    }
}

