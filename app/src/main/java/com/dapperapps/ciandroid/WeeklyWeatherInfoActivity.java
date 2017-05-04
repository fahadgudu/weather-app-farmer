package com.dapperapps.ciandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.dapperapps.receiver.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class WeeklyWeatherInfoActivity extends AppCompatActivity {

    Context mContext;
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
        setContentView(R.layout.activity_main);
        mContext = this;
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

        if (AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO) != null) {
            String str = AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO);
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

}
