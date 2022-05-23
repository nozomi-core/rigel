package app.cloudcoffee.rigel.signal;

public class NoTimeoutSignalStrategy implements SignalStrategy {
    private static final long VERY_LONG_TIMEOUT = 1000 * 60 * 60 * 12;
    @Override
    public void stopSignal() throws Exception {
        synchronized (this) {
            this.wait(VERY_LONG_TIMEOUT);
        }
    }

    @Override
    public void continueSignal() {
        synchronized (this) {
            this.notify();
        }
    }
}
