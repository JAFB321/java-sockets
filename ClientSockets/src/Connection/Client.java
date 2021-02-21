package Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends CustomObservable implements Runnable {

    // Params
    String Host;
    int puerto;

    // Componentes
    private Socket cliente;

    private DataInputStream in;
    private DataOutputStream out;

    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    public Client(String Host, int puerto) {
        this.Host = Host;
        this.puerto = puerto;
    }

    public void Init() throws IOException {
        cliente = new Socket(Host, puerto);

        in = new DataInputStream(cliente.getInputStream());
        out = new DataOutputStream(cliente.getOutputStream());
        //objOut = new ObjectOutputStream(out);
        //objIn = new ObjectInputStream(in);

        //objOut.flush();
        //out.flush();
        
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String obj = in.readUTF();
                ObjectRecived(obj);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void ObjectRecived(String obj) {
        notifyObservers(obj);
    }

    public boolean SendString(String json) {
        try {
            out.writeUTF(json);
            out.flush(); // send the message

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void Close() throws IOException {
        try {
            if (cliente != null) {
                cliente.close();
                out.close();
                in.close();
            }
        } catch (IOException e) {
        }
    }

}
