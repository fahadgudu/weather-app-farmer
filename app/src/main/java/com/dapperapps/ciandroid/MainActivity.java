package com.dapperapps.ciandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.dapperapps.receiver.MessageEvent;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import network.AppConstants;
import network.AppUtil;
import network.RestCallbackObject;
import network.ServerCodes;
import network.ServerConnectListenerObject;
import network.SharedPrefUtility;
import network.WeatherRequest;
import retrofit2.Call;

import static com.dapperapps.receiver.WeatherConditions.mHmWeatherInfo;

public class MainActivity extends AppCompatActivity implements ServerConnectListenerObject {

    Context mContext;
    TextView mTvWeatherInfo;
    ImageView mIvWeatherInfo;

    TextView mTvPrecipIntensity;
    TextView mTvPrecipProbability;
    TextView mTvTemperature;
    TextView mTvApparentTemperatureMax;
    TextView mTvApparentTemperatureMin;
    TextView mTvWindSpeed;

    RelativeLayout mRlWeather;
    TextView mTvWeatherSummary;
    TextView mTvWindValue;
    TextView mTvHumidityValue;

    TextView mTvAudio;
    ImageView mIvWeather;

    VideoView mVvHomeBg;

    int audioFileNu;
    String textName;
    NiftyDialogBuilder dialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext = this;
        mTvWeatherInfo = (TextView) findViewById(R.id.tv_weather_info);



        //GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.clear_day );
        mRlWeather = (RelativeLayout) findViewById(R.id.rl_weather);
        mTvWeatherSummary = (TextView) findViewById(R.id.tv_weather_summary);
        mIvWeatherInfo = (ImageView) findViewById(R.id.iv_weather_info);
        mTvTemperature = (TextView) findViewById(R.id.tv_temperature);
        mTvWindValue = (TextView) findViewById(R.id.tv_wind_value);
        mTvHumidityValue = (TextView) findViewById(R.id.tv_humidity_value);

        mTvApparentTemperatureMin = (TextView) findViewById(R.id.tv_temperature_min);
        mTvApparentTemperatureMax = (TextView) findViewById(R.id.tv_temperature_max);
        mTvWindSpeed = (TextView) findViewById(R.id.tv_wind_speed_value);
        mTvPrecipProbability = (TextView) findViewById(R.id.tv_rain_probility_value);
        mTvPrecipIntensity = (TextView) findViewById(R.id.tv_rain_intensity_value);
        mTvAudio = (TextView) findViewById(R.id.tv_audio);
        mIvWeather = (ImageView) findViewById(R.id.iv_enter_phone);



        mIvWeatherInfo.setImageResource(R.drawable.blizzard);

        mVvHomeBg = (VideoView) findViewById(R.id.vv_home_bg);
        mIvWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "detailed_weather_info.mp4");
            }
        });
        mTvAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder=NiftyDialogBuilder.getInstance(mContext);
                dialogBuilder
                        .withTitle(null)                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage("\n\n\n\n\n\n\n\n"+textName+"\n\n\n\n\n\n\n\n")                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#808080")                               //def  | withDialogColor(int resid)
                        //.withIcon(getResources().getDrawable(R.drawable.icon))
                        .withDuration(700)                                          //def
                        .withEffect(Effectstype.SlideBottom)                                         //def Effectstype.Slidetop
//                        .withButton1Text("OK")                                      //def gone
                        .withButton2Text("بند کریں")                                  //def gone
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
//                        .setButton1Click(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
//                            }
//                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
                AppUtil.playAudio(mContext, audioFileNu+".mp4");
            }
        });

        showProgressDialog("...", 100);
        WeatherRequest obj = new WeatherRequest();
        WeatherRequest.User user = obj.new User();
//        user.setLatitude("" + mLocation.getLatitude());
//        user.setLongitude("" + mLocation.getLongitude());
        user.setUser_id(SharedPrefUtility.getInstance(mContext).getIntValue(AppConstants.USER_ID));
        user.setDevice_token(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        obj.setUser(user);

        Call<Object> call = SampleApplication.getRestClient().getApiService().getWeathers(obj);
        call.enqueue(new RestCallbackObject<Object>(this, ServerCodes.ServerRequestCodes.LOGIN_REQUEST_CODE,
                SampleApplication.getAppContext()));
        showData("[ \"1:1:0.0235:6.0:10:10:8:0.44:5.55:172\", \"2:2:0.0083:38.0:0:38:29:0.5:3.9:104\",\"3:3:0.0021:16.0:0:39:27:0.48:4.03:111\",\"4:4:0.0067:22.0:0:12:27:0.5:3.67:130\",\"5:5:0.0003:1.0:0:50:29:0.48:3.49:108\",\"6:6:0.0043:9.0:0:43:31:0.43:2.11:140\",\"7:7:0.0186:41.0:0:20:28:0.63:6.83:124\",\"8:8:0.0291:38.0:0:25:27:0.76:8.01:113\",\"9:9:0.0791:63.0:0:0:24:0.82:8.3:109\" ]");


    }
    private ProgressDialog mProgressDialog;
    private void showProgressDialog(String text, int progress) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(text);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(progress);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    private void showData(String str) {
        //String str = "[ \"0:1:0:0:33:0:0:0.19:7.62:42\", \"1:2:0.0021:40.0:0:40:26:0.17:5.23:39\", \"2:8:0.0005:9.0:0:44:27:0.21:8.75:36\", \"3:8:0.0029:16.0:0:43:28:0.28:10.23:43\", \"4:8:0.0022:20.0:0:41:25:0.31:5.47:61\", \"5:2:0.0025:31.0:0:39:23:0.34:0.74:221\", \"6:9:0.0014:13.0:0:37:23:0.29:1.24:178\", \"12:9:0:0:0:37:22:0.25:5.35:24\", \"11:8:0:0:0:38:23:0.2:2.77:51\" ]";

        str = str.replaceAll("Sent from your Twillio trial account", "");
        AppPreference.saveValue(mContext, str, AppKeys.KEY_WEATHER_INFO);

        if (AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO) != null) {
            str = AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO);
            List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
            String[] parts = items.get(0).split(":");

            mTvPrecipIntensity.setText(parts[2]+ " inch/h");
            mTvPrecipProbability.setText((int) (Float.parseFloat(parts[3])*1) +"%");
            mTvTemperature.setText(parts[4]);
            mTvApparentTemperatureMax.setText(" زیادہ درجہ حرارت "+(char) 0x00B0 + parts[5]);
            mTvApparentTemperatureMin.setText(" کم درجہ حرارت "+(char) 0x00B0+parts[6]);
            mTvWindSpeed.setText(parts[8]);
            mTvWindSpeed.setText(parts[9].substring(0, parts[9].length()-1));
            parts[0] = parts[0].replaceAll("\"", "");
            parts[0] = parts[0].replaceAll("\\[", "");
            parts[0] = parts[0].replaceAll(" ", "");
            audioFileNu = Integer.parseInt(parts[0]);
            textName=AppUtil.getWeatherSummary(Integer.parseInt(parts[0]));
            mTvWeatherSummary.setText(AppUtil.getWeatherSummary(Integer.parseInt(parts[0])));
            mTvTemperature.setText(parts[4]);
            mTvHumidityValue.setText((int) (Float.parseFloat(parts[7])*100) +"%");
            mTvWindValue.setText(parts[8]+" MPH");

            if(Integer.parseInt(parts[4])<3) {
                //blue
                mRlWeather.setBackgroundColor(Color.parseColor("#80668cff"));
            }
            else if(Integer.parseInt(parts[4])>3 && Integer.parseInt(parts[4])<=10) {
                //dark blue
                mRlWeather.setBackgroundColor(Color.parseColor("#803333ff"));
            } else if (Integer.parseInt(parts[4])>10&&Integer.parseInt(parts[4])<30) {
                //orange
                mRlWeather.setBackgroundColor(Color.parseColor("#80ffa500"));
            } else {
                //red
                mRlWeather.setBackgroundColor(Color.parseColor("#99ff0000"));
            }
            String uri = "android.resource://" + getPackageName() + "/"
                    + R.raw.sleet;
            if(Integer.parseInt(parts[1])==0) {
                mIvWeatherInfo.setImageResource(R.drawable.clear_day);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.clear_day;
            } else if(Integer.parseInt(parts[1])==1) {
                mIvWeatherInfo.setImageResource(R.drawable.clear_night);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.clear_night;
            } else if(Integer.parseInt(parts[1])==2) {
                mIvWeatherInfo.setImageResource(R.drawable.rain);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.rain;
            } else if(Integer.parseInt(parts[1])==3) {
                mIvWeatherInfo.setImageResource(R.drawable.snow);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.snow;
            } else if(Integer.parseInt(parts[1])==4) {
                mIvWeatherInfo.setImageResource(R.drawable.sleet);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.sleet;
            } else if(Integer.parseInt(parts[1])==5) {
                mIvWeatherInfo.setImageResource(R.drawable.wind);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.wind;
            } else if(Integer.parseInt(parts[1])==6) {
                mIvWeatherInfo.setImageResource(R.drawable.fog);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.fog;
            } else if(Integer.parseInt(parts[1])==7) {
                mIvWeatherInfo.setImageResource(R.drawable.cloudy);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.cloudy;
            } else if(Integer.parseInt(parts[1])==8) {
                mIvWeatherInfo.setImageResource(R.drawable.partly_cloudy_day);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.partly_cloudy_day;
            } else if(Integer.parseInt(parts[1])==9) {
                mIvWeatherInfo.setImageResource(R.drawable.partly_cloudy_night);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.partly_cloudy_night;
            } else if(Integer.parseInt(parts[1])==10) {
                mIvWeatherInfo.setImageResource(R.drawable.hail);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.hail;
            } else if(Integer.parseInt(parts[1])==11) {
                mIvWeatherInfo.setImageResource(R.drawable.thunderstorm);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.thunderstorm;
            } else if(Integer.parseInt(parts[1])==12) {
                mIvWeatherInfo.setImageResource(R.drawable.tornado);
                uri = "android.resource://" + getPackageName() + "/"
                        + R.raw.tornado;
            }
            VideoPlayController.getInstance().playVideo(mContext, mVvHomeBg, uri);



        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        AppPreference.saveValue(mContext, event.getMessage(), AppKeys.KEY_WEATHER_INFO);
        showData(AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO));
    }

    @Override
    public void onSuccess(Object response) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
        showData(response.toString());
    }
    @Override
    public void onFailure(Throwable response) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
        showData(AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO));
    }
}
