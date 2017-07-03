package network;


public interface ServerConnectListenerObject {
    /**
     * Called if server call was successful
     *
     * @param response
     */

    void onSuccess(Object response);

    /**
     * Called if server call was failed.
     *
     * @param response
     */
    void onFailure(Throwable response);
}
