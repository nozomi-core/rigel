package app.cloudcoffee.rigel.signal;

public interface SignalStrategy {
    void stopSignal() throws Exception;
    void continueSignal();
}
