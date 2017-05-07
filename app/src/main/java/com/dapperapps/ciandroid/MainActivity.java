package com.dapperapps.ciandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.dapperapps.receiver.MessageEvent;

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

import static com.dapperapps.receiver.WeatherConditions.mHmWeatherInfo;

public class MainActivity extends AppCompatActivity {

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
    VideoView mVvHomeBg;
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

        mIvWeatherInfo.setImageResource(R.drawable.blizzard);

        mVvHomeBg = (VideoView) findViewById(R.id.vv_home_bg);



        String str = "Sent from your Twillio trial account[ \"Dry:1:0:0:33:0:0:0.19:7.62:42\", \"Drizzle in the morning.:2:0.0021:40.0:0:40:26:0.17:5.23:39\", \"Mostly cloudy until afternoon and dry throughout the day.:8:0.0005:9.0:0:44:27:0.21:8.75:36\", \"Mostly cloudy until evening.:8:0.0029:16.0:0:43:28:0.28:10.23:43\", \"Mostly cloudy in the morning.:8:0.0022:20.0:0:41:25:0.31:5.47:61\", \"Drizzle in the morning.:2:0.0025:31.0:0:39:23:0.34:0.74:221\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0.0014:13.0:0:37:23:0.29:1.24:178\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0:0:0:37:22:0.25:5.35:24\", \"Dry throughout the day and partly cloudy in the afternoon.:8:0:0:0:38:23:0.2:2.77:51\" ]";
        str = str.replaceAll("Sent from your Twillio trial account", "");
        AppPreference.saveValue(mContext, str, AppKeys.KEY_WEATHER_INFO);

        if (AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO) != null) {
            str = AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO);
            List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
            String[] parts = items.get(0).split(":");

            mTvPrecipIntensity.setText(parts[2]+ " inch/h");
            mTvPrecipProbability.setText((int) (Float.parseFloat(parts[3])*1) +"%");
            mTvTemperature.setText(parts[4]);
            mTvApparentTemperatureMax.setText(" زیادہ سے زیادہ "+ parts[5]);
            mTvApparentTemperatureMin.setText(" کم از کم "+parts[6]);
            mTvWindSpeed.setText(parts[8]);
            mTvWindSpeed.setText(parts[9].substring(0, parts[9].length()-1));

            mTvWeatherSummary.setText(parts[0].substring(3));
            mTvTemperature.setText(parts[4]);
            mTvHumidityValue.setText((int) (Float.parseFloat(parts[7])*100) +"%");
            mTvWindValue.setText(parts[8]+" MPH");

            if(Integer.parseInt(parts[4])<3) {
                //blue
                mRlWeather.setBackgroundColor(Color.parseColor("#80668cff"));
            }
            else if(Integer.parseInt(parts[4])>3 && Integer.parseInt(parts[4])<10) {
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
//        summary
//                icon
//        precipIntensity
//                precipProbability
//        temperature
//                apparentTemperatureMax
//        apparentTemperatureMin
//                humidity
//        windSpeed
//                windBearing
        AppPreference.saveValue(mContext, event.getMessage(), AppKeys.KEY_WEATHER_INFO);
        //String str = event.getMessage();//"[ \"Dry:1:0:0:33:0:0:0.19:7.62:42\", \"Drizzle in the morning.:2:0.0021:40.0:0:40:26:0.17:5.23:39\", \"Mostly cloudy until afternoon and dry throughout the day.:8:0.0005:9.0:0:44:27:0.21:8.75:36\", \"Mostly cloudy until evening.:8:0.0029:16.0:0:43:28:0.28:10.23:43\", \"Mostly cloudy in the morning.:8:0.0022:20.0:0:41:25:0.31:5.47:61\", \"Drizzle in the morning.:2:0.0025:31.0:0:39:23:0.34:0.74:221\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0.0014:13.0:0:37:23:0.29:1.24:178\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0:0:0:37:22:0.25:5.35:24\", \"Dry throughout the day and partly cloudy in the afternoon.:8:0:0:0:38:23:0.2:2.77:51\" ]";
        String str = "[ \"Dry:1:0:0:33:0:0:0.19:7.62:42\", \"Drizzle in the morning.:2:0.0021:40.0:0:40:26:0.17:5.23:39\", \"Mostly cloudy until afternoon and dry throughout the day.:8:0.0005:9.0:0:44:27:0.21:8.75:36\", \"Mostly cloudy until evening.:8:0.0029:16.0:0:43:28:0.28:10.23:43\", \"Mostly cloudy in the morning.:8:0.0022:20.0:0:41:25:0.31:5.47:61\", \"Drizzle in the morning.:2:0.0025:31.0:0:39:23:0.34:0.74:221\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0.0014:13.0:0:37:23:0.29:1.24:178\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0:0:0:37:22:0.25:5.35:24\", \"Dry throughout the day and partly cloudy in the afternoon.:8:0:0:0:38:23:0.2:2.77:51\" ]";
        str = str.replaceAll("Sent from your Twillio trial account", "");
        List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
        String[] parts = items.get(0).split(":");
        mTvPrecipIntensity.setText(parts[2]+ " inch/h");
        mTvPrecipProbability.setText((int) (Float.parseFloat(parts[3])*100) +"%");
        mTvTemperature.setText(parts[4]);
        mTvApparentTemperatureMax.setText(" زیادہ سے زیادہ"+ parts[5]);
        mTvApparentTemperatureMin.setText(" کم از کم"+parts[6]);
        mTvWindSpeed.setText(parts[8]);
        mTvWindSpeed.setText(parts[9].substring(0, parts[9].length()-1));

        mTvWeatherSummary.setText(parts[0].substring(3));
        mTvTemperature.setText(parts[4]);
        mTvHumidityValue.setText((int) (Float.parseFloat(parts[7])*100) +"%");
        mTvWindValue.setText(parts[8]+" MPH");

        if(Integer.parseInt(parts[4])<3) {
            //blue
            mRlWeather.setBackgroundColor(Color.parseColor("#668cff"));
        }
        else if(Integer.parseInt(parts[4])>3 && Integer.parseInt(parts[4])<10) {
            //dark blue
            mRlWeather.setBackgroundColor(Color.parseColor("#3333ff"));
        } else if (Integer.parseInt(parts[4])>10&&Integer.parseInt(parts[4])<30) {
            //orange
            mRlWeather.setBackgroundColor(Color.parseColor("#ffa500"));
        } else {
            //red
            mRlWeather.setBackgroundColor(Color.parseColor("#CCff0000"));
        }
        if(Integer.parseInt(parts[1])==0) {
            mIvWeatherInfo.setImageResource(R.drawable.clear_day);
        } else if(Integer.parseInt(parts[1])==1) {
            mIvWeatherInfo.setImageResource(R.drawable.clear_night);
        } else if(Integer.parseInt(parts[1])==2) {
            mIvWeatherInfo.setImageResource(R.drawable.rain);
        } else if(Integer.parseInt(parts[1])==3) {
            mIvWeatherInfo.setImageResource(R.drawable.snow);
        } else if(Integer.parseInt(parts[1])==4) {
            mIvWeatherInfo.setImageResource(R.drawable.sleet);
        } else if(Integer.parseInt(parts[1])==5) {
            mIvWeatherInfo.setImageResource(R.drawable.wind);
        } else if(Integer.parseInt(parts[1])==6) {
            mIvWeatherInfo.setImageResource(R.drawable.fog);
        } else if(Integer.parseInt(parts[1])==7) {
            mIvWeatherInfo.setImageResource(R.drawable.cloudy);
        } else if(Integer.parseInt(parts[1])==8) {
            mIvWeatherInfo.setImageResource(R.drawable.partly_cloudy_day);
        } else if(Integer.parseInt(parts[1])==9) {
            mIvWeatherInfo.setImageResource(R.drawable.partly_cloudy_night);
        } else if(Integer.parseInt(parts[1])==10) {
            mIvWeatherInfo.setImageResource(R.drawable.hail);
        } else if(Integer.parseInt(parts[1])==11) {
            mIvWeatherInfo.setImageResource(R.drawable.thunderstorm);
        } else if(Integer.parseInt(parts[1])==12) {
            mIvWeatherInfo.setImageResource(R.drawable.tornado);
        }
    }
}
