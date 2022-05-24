package app.cloudcoffee.rigel.signal;

public class SignalAwaiter<T> implements AwaitSignal<T> {
    private T result = null;
    private Exception error = null;

    private final SignalStrategy signalStrategy;

    public SignalAwaiter(SignalStrategy signalStrategy){
        this.signalStrategy = signalStrategy;
    }

    public T waitForResult() throws Exception {
        if(result != null)
            return result;
        signalStrategy.stopSignal();

        if(result == null) {
            //GlobalRigelKt.report("PROGRAM WILL TERMINATE TIMING OUT", "");
            if(error != null)
                throw error;
            else
                throw new RuntimeException("Result was not posted");
        }
        return result;
    }

    @Override
    public T waitfor() throws Exception {
        return waitForResult();
    }

    @Override
    public void postResult(T result) {
        synchronized (this){
            this.result = result;
            signalStrategy.continueSignal();
        }
    }

    @Override
    public void postException(Exception e) {
        synchronized (this){
            error = e;
            signalStrategy.continueSignal();
        }
    }

    @Override
    public void postExceptionString(String exception){
        postException(new RuntimeException(exception));
    }
}
