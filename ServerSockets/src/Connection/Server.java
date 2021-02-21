package Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends CustomObservable implements Runnable {

    // Componentes
    ServerSocket server;
    ArrayList<ClientConnection> clientes = new ArrayList<ClientConnection>();

    int PORT;
    int nClientes;
    int currentID = 0;

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void Init() throws IOException {
        server = new ServerSocket(PORT);
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public int getNexID() {
        currentID++;
        return currentID;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket cliente;
                cliente = server.accept();
                AddClient(cliente);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddClient(Socket socket) {
        ClientConnection cliente = new ClientConnection(socket, "Cliente" + getNexID());
        clientes.add(cliente);
        cliente.initCliente();

        notifyObservers(cliente);
    }

    public ClientConnection[] getClientes() {
        ArrayList<ClientConnection> conectados = new ArrayList<ClientConnection>();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).Socket().isConnected()) {
                conectados.add(clientes.get(i));
            }
        }

        return (ClientConnection[]) (conectados.toArray(new ClientConnection[0]));
    }

    public void sendToClients(String json) {
        for (ClientConnection cliente : getClientes()) {
            cliente.sendObject(json);
        }
    }

    public void sendToClients(String json, String ID) {
        for (ClientConnection cliente : getClientes()) {
            if (cliente.ID.equals(ID)) {
                cliente.sendObject(json);
            }
        }
    }

    public void sendToClients(String json, String[] IDs) {
        for (ClientConnection cliente : getClientes()) {

            boolean hasID = false;
            for (String ID : IDs) {
                if (cliente.ID.equals(ID)) {
                    hasID = true;
                }
            }

            if (hasID) {
                cliente.sendObject(json);
            }
        }
    }

}
