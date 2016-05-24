package Routes;

public abstract class DelivererFactoryBase {
    public abstract DelivererBase getDeliverer(String request);
    protected boolean isRoute(String path, String route){
        return path.contains(route);
    }

}
