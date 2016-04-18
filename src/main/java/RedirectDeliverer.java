public class RedirectDeliverer extends DelivererBase {

    @Override
    public int getHTTPCode(){
        return 302;
    }
}
