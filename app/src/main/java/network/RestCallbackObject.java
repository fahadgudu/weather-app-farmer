package network;

import android.content.Context;
import org.json.JSONObject;

import java.lang.Exception;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("rawtypes")
public class RestCallbackObject<T> implements Callback {
    private boolean isCanceled;
    private ServerConnectListenerObject listener;
    private int requestCode;

    private Context mContext;

    public RestCallbackObject(ServerConnectListenerObject listener,
                              int requestCode, Context context) {
        this.listener = listener;
        this.requestCode = requestCode;
        this.mContext = context;
    }

    public void cancel() {
        isCanceled = true;
    }

    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (isCancelled()) {
            return;
        }
        if (response.body() != null) {
            try {
                String s = response.body().toString();
                s = s.replaceAll("\n", "");
                s = s.replaceAll("\r", "");
                JSONObject obj = new JSONObject(s);
                listener.onSuccess(response.body());
            } catch (Exception e) {
                listener.onSuccess(response.body().toString());
            }
        } else {
            listener.onFailure(null);
        }
    }

    @Override
    public void onFailure(Call call, Throwable throwable) {
        listener.onFailure(throwable);
    }
}
