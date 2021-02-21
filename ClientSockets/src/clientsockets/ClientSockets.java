package clientsockets;

import Connection.Client;
import Connection.ejemploObserver;
import java.util.Scanner;

public class ClientSockets {

    public static void main(String[] args) {
        Client cliente = new Client("localhost", 8080);

        Scanner sc = new Scanner(System.in);
        System.out.println("enter para conectar con el server"); 
        sc.nextLine();
        
        try {
            cliente.Init();
            System.out.println("Connected");
            
            // Para recibir algo del server usamos el patron observer para hacerlo mas facil
            // Cada vez que el cliente recibe algo de server lo notifica a sus observadores
            ejemploObserver observador = new ejemploObserver();
            cliente.addObserver(observador);
            
            // Para enviar algo al server
            sc.nextLine();
            cliente.SendString("test");
            System.out.println("Enviado");
            
            
            
        } catch (Exception e) {
            System.out.println("error"+e.getMessage());
        }

    }

}
