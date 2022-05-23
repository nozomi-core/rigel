package app.cloudcoffee.rigel.signal;

public interface AwaitSignal<T> {
    T waitfor() throws Exception;
    void postResult(T result);
    void postException(Exception e);
    void postExceptionString(String exception);
}
