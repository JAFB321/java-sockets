

package Connection;

import java.util.Observable;
import java.util.Observer;


public class ejemploObserver implements Observer{

    // Observer de ejemplo para imprimir los objetos recibidos por el socket
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
    
}
