package com.dapperapps.ciandroid;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import network.AppUtil;

/**
 * Created by usman on 4/24/17.
 */

public class ScreenSlidePageFragment extends Fragment {

    private int mPageNumber;
    public static final String ARG_PAGE = "page";
    Context mContext;
    TextView mTvCurrentDay;
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

    VideoView mVvHomeBg;
    String uri;

    int audioFileNu;
    ImageView mIvWeather;
    String textName;
    NiftyDialogBuilder dialogBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        mTvCurrentDay = (TextView) rootView.findViewById(R.id.tv_current_day);
        mTvWeatherInfo = (TextView) rootView.findViewById(R.id.tv_weather_info);



        //GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.clear_day );
        mRlWeather = (RelativeLayout) rootView.findViewById(R.id.rl_weather);
        mTvWeatherSummary = (TextView) rootView.findViewById(R.id.tv_weather_summary);
        mIvWeatherInfo = (ImageView) rootView.findViewById(R.id.iv_weather_info);
        mTvTemperature = (TextView) rootView.findViewById(R.id.tv_temperature);
        mTvWindValue = (TextView) rootView.findViewById(R.id.tv_wind_value);
        mTvHumidityValue = (TextView) rootView.findViewById(R.id.tv_humidity_value);

        mTvApparentTemperatureMin = (TextView) rootView.findViewById(R.id.tv_temperature_min);
        mTvApparentTemperatureMax = (TextView) rootView.findViewById(R.id.tv_temperature_max);
        mTvWindSpeed = (TextView) rootView.findViewById(R.id.tv_wind_speed_value);
        mTvPrecipProbability = (TextView) rootView.findViewById(R.id.tv_rain_probility_value);
        mTvPrecipIntensity = (TextView) rootView.findViewById(R.id.tv_rain_intensity_value);

        mTvAudio = (TextView) rootView.findViewById(R.id.tv_audio);
        mIvWeather = (ImageView) rootView.findViewById(R.id.iv_enter_phone);
        mIvWeatherInfo.setImageResource(R.drawable.blizzard);

        mVvHomeBg = (VideoView) rootView.findViewById(R.id.vv_home_bg);


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day=(day+mPageNumber)%7;
        if(day==0) {
            day=7;
        }
        switch (day) {
            case Calendar.SUNDAY:
                mTvCurrentDay.setText("اتوار");
                break;
            case Calendar.MONDAY:
                mTvCurrentDay.setText("پیر");
                break;

            case Calendar.TUESDAY:
                mTvCurrentDay.setText("منگل");
                break;
            case Calendar.WEDNESDAY:
                mTvCurrentDay.setText("بدھ");
                break;
            case Calendar.THURSDAY:
                mTvCurrentDay.setText("جمعرات");
                break;
            case Calendar.FRIDAY:
                mTvCurrentDay.setText("جمعہ");
                break;
            case Calendar.SATURDAY:
                mTvCurrentDay.setText("ہفتہ");
                break;
        }

        showData();
        return rootView;
    }

    private void showData() {
        if (AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO) != null) {
            String str = AppPreference.getValue(mContext, AppKeys.KEY_WEATHER_INFO);
            List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
            String[] parts = items.get(mPageNumber+1).split(":");
            mTvPrecipIntensity.setText(parts[2]+ " inch/h");
            mTvPrecipProbability.setText((int) (Float.parseFloat(parts[3])*1) +"%");
            mTvTemperature.setText(parts[5]);
            mTvApparentTemperatureMax.setText(" زیادہ درجہ حرارت "+(char) 0x00B0 + parts[5]);
            mTvApparentTemperatureMin.setText(" کم درجہ حرارت "+(char) 0x00B0+parts[6]);
            mTvWindSpeed.setText(parts[8]);
            mTvWindSpeed.setText(parts[9].substring(0, parts[9].length()-1));
            parts[0] = parts[0].replaceAll("\"", "");
            parts[0] = parts[0].replaceAll("\\[", "");
            parts[0] = parts[0].replaceAll(" ", "");
            textName=AppUtil.getWeatherSummary(Integer.parseInt(parts[0]));
            mTvWeatherSummary.setText(AppUtil.getWeatherSummary(Integer.parseInt(parts[0])));
            audioFileNu = Integer.parseInt(parts[0]);
            if(parts[0].toString().length()>10) {
                mTvWeatherSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            }
            mTvHumidityValue.setText((int) (Float.parseFloat(parts[7])*100) +"%");
            mTvWindValue.setText(parts[8]+" MPH");

            if(Integer.parseInt(parts[5])<3) {
                //blue
                mRlWeather.setBackgroundColor(Color.parseColor("#80668cff"));
            }
            else if(Integer.parseInt(parts[5])>3 && Integer.parseInt(parts[5])<=10) {
                //dark blue
                mRlWeather.setBackgroundColor(Color.parseColor("#803333ff"));
            } else if (Integer.parseInt(parts[5])>10&&Integer.parseInt(parts[5])<30) {
                //orange
                mRlWeather.setBackgroundColor(Color.parseColor("#80ffa500"));
            } else {
                //red
                mRlWeather.setBackgroundColor(Color.parseColor("#99ff0000"));
            }
            uri = "android.resource://" + getActivity().getPackageName() + "/"
                    + R.raw.sleet;
            if(Integer.parseInt(parts[1])==0) {
                mIvWeatherInfo.setImageResource(R.drawable.clear_day);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.clear_day;
            } else if(Integer.parseInt(parts[1])==1) {
                mIvWeatherInfo.setImageResource(R.drawable.clear_night);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.clear_night;
            } else if(Integer.parseInt(parts[1])==2) {
                mIvWeatherInfo.setImageResource(R.drawable.rain);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.rain;
            } else if(Integer.parseInt(parts[1])==3) {
                mIvWeatherInfo.setImageResource(R.drawable.snow);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.snow;
            } else if(Integer.parseInt(parts[1])==4) {
                mIvWeatherInfo.setImageResource(R.drawable.sleet);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.sleet;
            } else if(Integer.parseInt(parts[1])==5) {
                mIvWeatherInfo.setImageResource(R.drawable.wind);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.wind;
            } else if(Integer.parseInt(parts[1])==6) {
                mIvWeatherInfo.setImageResource(R.drawable.fog);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.fog;
            } else if(Integer.parseInt(parts[1])==7) {
                mIvWeatherInfo.setImageResource(R.drawable.cloudy);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.cloudy;
            } else if(Integer.parseInt(parts[1])==8) {
                mIvWeatherInfo.setImageResource(R.drawable.partly_cloudy_day);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.partly_cloudy_day;
            } else if(Integer.parseInt(parts[1])==9) {
                mIvWeatherInfo.setImageResource(R.drawable.partly_cloudy_night);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.partly_cloudy_night;
            } else if(Integer.parseInt(parts[1])==10) {
                mIvWeatherInfo.setImageResource(R.drawable.hail);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.hail;
            } else if(Integer.parseInt(parts[1])==11) {
                mIvWeatherInfo.setImageResource(R.drawable.thunderstorm);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.thunderstorm;
            } else if(Integer.parseInt(parts[1])==12) {
                mIvWeatherInfo.setImageResource(R.drawable.tornado);
                uri = "android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.tornado;
            }

        }
        mIvWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "detailed_weather_info.mp4");
            }
        });
        mTvAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder= NiftyDialogBuilder.getInstance(mContext);
                dialogBuilder
                        .withTitle(null)                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage("\n\n\n\n\n"+textName+"\n\n\n\n\n")                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")
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
    }

    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public ScreenSlidePageFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoPlayController.getInstance().playVideo(mContext, mVvHomeBg, uri);
    }
}
