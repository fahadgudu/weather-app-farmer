package network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServerResponse implements Serializable {

    //Request Code
    private int requestCode;
    private Exception Exception;
    private Throwable throwable;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public Exception getException() {
        return Exception;
    }

    public void setException(Exception exception) {
        Exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
