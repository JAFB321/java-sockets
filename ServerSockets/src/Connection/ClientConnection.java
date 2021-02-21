package Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection extends CustomObservable implements Runnable {

    public final String ID;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    //private ObjectInputStream objIn;
    //private ObjectOutputStream objOut;

    public ClientConnection(Socket socket, String ID) {
        this.ID = ID;
        this.socket = socket;
        initCliente();

        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void initCliente() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            //objOut = new ObjectOutputStream(out);
            //objIn = new ObjectInputStream(in);

            //objOut.flush();
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Socket Socket() {
        return socket;
    }

    public boolean sendObject(Object obj) {
        try {
          //  objOut.writeObject(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendString(String json) {
        try {
            out.writeUTF(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String obj = in.readUTF();
                ObjectRecived(obj);
            }
        } catch (SocketException e) {
            Disconected(e);
            System.out.println("Socket exception");
        } catch (IOException ex) {
            //Logger.getLogg(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        catch (NullPointerException e) {
            System.out.println("NULL POINTER");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void listenObject() {

    }

    public void ObjectRecived(String obj) {
        notifyObservers(obj);
        System.out.println(obj);
    }

    public void Disconected(SocketException e) {
        notifyObservers(e);
    }

}
