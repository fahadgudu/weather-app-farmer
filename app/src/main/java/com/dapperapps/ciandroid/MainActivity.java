package com.dapperapps.ciandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.dapperapps.receiver.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

import static com.dapperapps.receiver.WeatherConditions.mHmWeatherInfo;

public class MainActivity extends AppCompatActivity {

    TextView mTvWeatherInfo;
    ImageView mIvWeatherInfo;

    TextView mTvSummary;
    TextView mTvIcon;
    TextView mTvPrecipIntensity;
    TextView mTvPrecipProbability;
    TextView mTvTemperature;
    TextView mTvApparentTemperatureMax;
    TextView mTvApparentTemperatureMin;
    TextView mTvHumidity;
    TextView mTvWindSpeed;
    TextView mTvwindBearing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit);
        setContentView(R.layout.activity_main);
        mTvWeatherInfo = (TextView) findViewById(R.id.tv_weather_info);
        mIvWeatherInfo = (ImageView) findViewById(R.id.iv_weather_info);

        mTvSummary = (TextView) findViewById(R.id.summary);
        mTvIcon = (TextView) findViewById(R.id.icon);
        mTvPrecipIntensity = (TextView) findViewById(R.id.precipIntensity);
        mTvPrecipProbability = (TextView) findViewById(R.id.precipProbability);
        mTvTemperature = (TextView) findViewById(R.id.temperature);
        mTvApparentTemperatureMax = (TextView) findViewById(R.id.apparentTemperatureMax);
        mTvApparentTemperatureMin = (TextView) findViewById(R.id.apparentTemperatureMin);
        mTvHumidity = (TextView) findViewById(R.id.humidity);
        mTvWindSpeed = (TextView) findViewById(R.id.windSpeed);
        mTvwindBearing = (TextView) findViewById(R.id.windBearing);

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
        String str = event.getMessage();//"[ \"Dry:1:0:0:33:0:0:0.19:7.62:42\", \"Drizzle in the morning.:2:0.0021:40.0:0:40:26:0.17:5.23:39\", \"Mostly cloudy until afternoon and dry throughout the day.:8:0.0005:9.0:0:44:27:0.21:8.75:36\", \"Mostly cloudy until evening.:8:0.0029:16.0:0:43:28:0.28:10.23:43\", \"Mostly cloudy in the morning.:8:0.0022:20.0:0:41:25:0.31:5.47:61\", \"Drizzle in the morning.:2:0.0025:31.0:0:39:23:0.34:0.74:221\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0.0014:13.0:0:37:23:0.29:1.24:178\", \"Partly cloudy starting in the evening and dry throughout the day.:9:0:0:0:37:22:0.25:5.35:24\", \"Dry throughout the day and partly cloudy in the afternoon.:8:0:0:0:38:23:0.2:2.77:51\" ]";
        str=str.replaceAll("Sent from your Twillio trial account", "");
        List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
        String[] parts = items.get(0).split(":");
        mTvSummary.setText(parts[0]);
        mTvIcon.setText(parts[1]);
        mTvPrecipIntensity.setText(parts[2]);
        mTvPrecipProbability.setText(parts[3]);
        mTvTemperature.setText(parts[4]);
        mTvApparentTemperatureMax.setText(parts[5]);
        mTvApparentTemperatureMin.setText(parts[6]);
        mTvHumidity.setText(parts[7]);
        mTvWindSpeed.setText(parts[8]);
        mTvwindBearing.setText(parts[9]);
    }
}
