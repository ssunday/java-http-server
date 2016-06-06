package Server;

import Server.Deliverer.DelivererBase;

public interface DelivererFactoryBase {
    DelivererBase getDeliverer(String request);
}
