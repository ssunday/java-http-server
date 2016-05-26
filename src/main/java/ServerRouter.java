import Routes.Cobspec.CobspecDelivererFactory;
import Routes.DelivererFactoryBase;
import Routes.Wiki.DelivererSupport.Postgres;
import Routes.Wiki.WikiDelivererFactory;

public class ServerRouter {

    private static final String POSTGRES_TABLE_NAME = "posts";

    public static DelivererFactoryBase getDelivererFactory(String mode, int port, String baseDirectory){
        DelivererFactoryBase delivererFactory = new CobspecDelivererFactory(port, baseDirectory);
        if (mode.equals(ServerRoutes.COBSPEC_ROUTE)){
            delivererFactory = new CobspecDelivererFactory(port, baseDirectory);
        }
        else if (mode.equals(ServerRoutes.WIKI_ROUTE)){
            Postgres postgres = new Postgres(POSTGRES_TABLE_NAME);
            delivererFactory = new WikiDelivererFactory(postgres);
        }
        return delivererFactory;
    }

}
