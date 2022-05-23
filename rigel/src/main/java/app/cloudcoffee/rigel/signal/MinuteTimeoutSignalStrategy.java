package app.cloudcoffee.rigel.signal;

public class MinuteTimeoutSignalStrategy implements SignalStrategy {
    private final int awaitMillis;

    public MinuteTimeoutSignalStrategy(int awaitMinues) {
        this.awaitMillis = awaitMinues * 60 * 1000;
    }
    @Override
    public void stopSignal() throws Exception {
        synchronized (this) {
            this.wait(awaitMillis);
        }
    }

    @Override
    public void continueSignal() {
        synchronized (this) {
            this.notify();
        }
    }
}
