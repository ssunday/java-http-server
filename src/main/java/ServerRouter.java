import Routes.Cobspec.CobspecDelivererFactory;
import Routes.DelivererFactoryBase;
import Routes.Wiki.DelivererSupport.Postgres;
import Routes.Wiki.WikiDelivererFactory;

public class ServerRouter {

    public static DelivererFactoryBase getDelivererFactory(String mode, int port, String baseDirectory){
        DelivererFactoryBase delivererFactory = new CobspecDelivererFactory(port, baseDirectory);

        if (mode.equals(ServerRoutes.COBSPEC_ROUTE)){
            delivererFactory = new CobspecDelivererFactory(port, baseDirectory);
        }
        else if (mode.equals(ServerRoutes.WIKI_ROUTE)){
            delivererFactory = new WikiDelivererFactory(new Postgres("wiki"));
        }

        return delivererFactory;
    }

}
