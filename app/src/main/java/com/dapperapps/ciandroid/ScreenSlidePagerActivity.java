package com.dapperapps.ciandroid;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import network.AppConstants;
import network.RestCallbackObject;
import network.ServerCodes;
import network.ServerConnectListenerObject;
import network.SharedPrefUtility;
import network.WeatherRequest;
import retrofit2.Call;

/**
 * Created by usman on 4/24/17.
 */

public class ScreenSlidePagerActivity extends FragmentActivity implements ServerConnectListenerObject {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int NUM_PAGES = 7;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private CirclePageIndicator mIndicator;

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);


        showProgressDialog("...", 100);
        WeatherRequest obj = new WeatherRequest();
        WeatherRequest.User user = obj.new User();
//        user.setLatitude("" + mLocation.getLatitude());
//        user.setLongitude("" + mLocation.getLongitude());
        user.setUser_id(SharedPrefUtility.getInstance(this).getIntValue(AppConstants.USER_ID));
        user.setDevice_token(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        obj.setUser(user);

        Call<Object> call = SampleApplication.getRestClient().getApiService().getWeathers(obj);
        call.enqueue(new RestCallbackObject<Object>(this, ServerCodes.ServerRequestCodes.LOGIN_REQUEST_CODE,
                SampleApplication.getAppContext()));
    }
    private ProgressDialog mProgressDialog;
    private void showProgressDialog(String text, int progress) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(text);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(progress);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onSuccess(Object response) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
        AppPreference.saveValue(this, response.toString(), AppKeys.KEY_WEATHER_INFO);


        String str = AppPreference.getValue(this, AppKeys.KEY_WEATHER_INFO);
        List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
        NUM_PAGES = items.size() - 1;
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator) findViewById(R.id.viewpager_indicator);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mPager);

        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mTv6 = (TextView) findViewById(R.id.tv_6);
        mTv7 = (TextView) findViewById(R.id.tv_7);


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int index=0;
        for (int i = 0; i < 7; i++) {
            String dayName="";

            switch (day) {
                case Calendar.SUNDAY:
                    dayName="اتوار";
                    break;
                case Calendar.MONDAY:
                    dayName="پیر";
                    break;

                case Calendar.TUESDAY:
                    dayName="منگل";
                    break;
                case Calendar.WEDNESDAY:
                    dayName="بدھ";
                    break;
                case Calendar.THURSDAY:
                    dayName="جمعرات";
                    index=i;
                    break;
                case Calendar.FRIDAY:
                    dayName="جمعہ";
                    break;
                case Calendar.SATURDAY:
                    dayName="ہفتہ";
                    break;
            }

            if(i==0) {
                mTv1.setText(dayName);
            } else if(i==1) {
                mTv2.setText(dayName);
            } else if(i==2) {
                mTv3.setText(dayName);
            } else if(i==3) {
                mTv4.setText(dayName);
            } else if(i==4) {
                mTv5.setText(dayName);
            } else if(i==5) {
                mTv6.setText(dayName);
            } else if(i==6) {
                mTv7.setText(dayName);
            }
            day = day + 1;
            day=(day)%7;
            if(day==0) {
                day=7;
            }
        }
        for (int i = 0; i < 7; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 0.9f;

            mTv1.setLayoutParams(params);
            mTv2.setLayoutParams(params);
            mTv3.setLayoutParams(params);
            mTv4.setLayoutParams(params);
            mTv5.setLayoutParams(params);
            mTv6.setLayoutParams(params);
            mTv7.setLayoutParams(params);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params2.weight = 1.1f;
            if(index==0) {
                mTv1.setLayoutParams(params2);
            } else if(index==1) {
                mTv2.setLayoutParams(params2);
            } else if(index==2) {
                mTv3.setLayoutParams(params2);
            } else if(index==3) {
                mTv4.setLayoutParams(params2);
            } else if(index==4) {
                mTv5.setLayoutParams(params2);
            } else if(index==5) {
                mTv6.setLayoutParams(params2);
            } else if(index==6) {
                mTv7.setLayoutParams(params2);
            }
        }
        mTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(0);
            }
        });
        mTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(1);
            }
        });
        mTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(2);
            }
        });
        mTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(3);
            }
        });
        mTv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(4);
            }
        });
        mTv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(5);
            }
        });
        mTv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(6);
            }
        });

    }
    @Override
    public void onFailure(Throwable response) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }

    }

}
