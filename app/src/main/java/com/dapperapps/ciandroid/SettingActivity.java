package com.dapperapps.ciandroid;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;

import network.AppConstants;
import network.AppUtil;
import network.RegisteredRequest;
import network.RestCallbackObject;
import network.ServerCodes;
import network.ServerConnectListenerObject;
import network.SharedPrefUtility;
import network.WeatherRequest;
import retrofit2.Call;

public class SettingActivity extends Activity implements ServerConnectListenerObject {

    Button btn_save;
    Switch mSwitchThreeTimesDaily;
    Switch mSwitchDaily;
    Switch mSwitchWeekly;
    ImageView mIvEnterPhone;

    private FusedLocationProviderClient mFusedLocationClient;

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;
        btn_save = (Button) findViewById(R.id.btn_save);
        mIvEnterPhone = (ImageView) findViewById(R.id.iv_enter_phone);
        mSwitchThreeTimesDaily = (Switch) findViewById(R.id.switch_three_time_daily);

        mSwitchThreeTimesDaily.setChecked(SharedPrefUtility.getInstance(mContext).getBooleanValue(AppConstants.THREE_TIMES_DAILY));

        mSwitchThreeTimesDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefUtility.getInstance(mContext).savePrefrences(AppConstants.THREE_TIMES_DAILY, isChecked);
            }
        });

        mSwitchDaily = (Switch) findViewById(R.id.switch_daily);

        mSwitchDaily.setChecked(SharedPrefUtility.getInstance(mContext).getBooleanValue(AppConstants.DAILY));

        mSwitchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefUtility.getInstance(mContext).savePrefrences(AppConstants.DAILY, isChecked);
            }
        });

        mSwitchWeekly = (Switch) findViewById(R.id.switch_weekly);

        mSwitchWeekly.setChecked(SharedPrefUtility.getInstance(mContext).getBooleanValue(AppConstants.WEEKLY));

        mSwitchWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefUtility.getInstance(mContext).savePrefrences(AppConstants.WEEKLY, isChecked);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRegister();


            }
        });

        mIvEnterPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "menu_down_options.mp4");


            }
        });
    }


    private void requestRegister() {
        showProgressDialog("...", 100);
        WeatherRequest obj = new WeatherRequest();
        WeatherRequest.User user = obj.new User();
//        user.setLatitude("" + mLocation.getLatitude());
//        user.setLongitude("" + mLocation.getLongitude());
        user.setUser_id(SharedPrefUtility.getInstance(mContext).getIntValue(AppConstants.USER_ID));
        user.setDevice_token(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        user.setAfter_four_hours(mSwitchThreeTimesDaily.isChecked());
        user.setDaily(mSwitchDaily.isChecked());
        user.setWeekly(mSwitchWeekly.isChecked());
        obj.setUser(user);

        Call<Object> call = SampleApplication.getRestClient().getApiService().updateSetting(obj);
        call.enqueue(new RestCallbackObject<Object>(this, ServerCodes.ServerRequestCodes.LOGIN_REQUEST_CODE,
                SampleApplication.getAppContext()));
    }

    @Override
    public void onSuccess(Object response) {
        //NotificationsCountResponse notificationsCountObj = (NotificationsCountResponse) response;
        Log.e("hp-onSuccess", "UpdateLiveFeedCountService");
        // ShortcutBadger library added, Only this line added for showing badge on launcher.
//        ShortcutBadger.with(getApplicationContext()).count((notificationsCountObj.getData().getMyFeedsCount()));
//        Bundle bundle = new Bundle();
//        AppPreference.saveInt(FollowitApplication.getAppContext(), notificationsCountObj.getData().getMyFeedsCount(), AppBundles.MY_FEED_COUNT.getKey());
//        AppPreference.saveInt(FollowitApplication.getAppContext(), notificationsCountObj.getData().getMessagesCount(), AppBundles.MESSAGES_COUNT.getKey());
//        bundle.putInt(AppBundles.MY_FEED_COUNT.getKey(), notificationsCountObj.getData().getMyFeedsCount());
//        bundle.putInt(AppBundles.MESSAGES_COUNT.getKey(), notificationsCountObj.getData().getMessagesCount());
//        EventsListeners.getInstance().broadCastEvent(
//                ListinerCategory.HOME_MENU_NEW, ChangeEvents.NOTIFICATIONS_COUNT, bundle);
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                if (SettingActivity.this.mProgressDialog != null) {
                    SettingActivity.this.mProgressDialog.hide();
                }
                AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
//        alertDialog.setTitle("Alert");
                alertDialog.setMessage("آپ کا انتخاب محفوظ ہو گیا ہے");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بند کریں",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SettingActivity.this.finish();
                            }
                        });
                alertDialog.show();


            } // This is your code
        };
        mainHandler.postDelayed(myRunnable, 2000);
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
    @Override
    public void onFailure(Throwable response) {

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                if (SettingActivity.this.mProgressDialog != null) {
                    SettingActivity.this.mProgressDialog.hide();
                }
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SettingActivity.this).create();
//        alertDialog.setTitle("Alert");
                alertDialog.setMessage("آپ کا انتخاب پہلے ہی محفوظ ہے");
                alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "بند کریں",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SettingActivity.this.finish();
                            }
                        });
                alertDialog.show();


            } // This is your code
        };
        mainHandler.postDelayed(myRunnable, 2000);

    }

}
