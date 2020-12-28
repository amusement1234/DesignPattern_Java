package text;

public class HandlerA extends Handler {
    @Override
    public void handle() {
        boolean handled = false;
        //... 
        if (!handled && successor != null) {
            successor.handle();
        }
    }
}
