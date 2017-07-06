package com.dapperapps.ciandroid;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Calendar;

import network.AppUtil;
import network.LoginResponse2;
import network.RestCallback;
import network.ServerCodes;
import network.ServerConnectListener;
import network.ServerResponse;
import retrofit2.Call;

public class SplashActivity extends Activity implements ServerConnectListener {

    RelativeLayout mBtnToday;
    RelativeLayout mBtnWeekly;
    RelativeLayout mBtnSetting;
    RelativeLayout mBtnPhoneNumber;

    ImageView mIvToday;
    ImageView mIvWeekly;
    ImageView mIvSetting;
    ImageView mIvPhoneNumber;


    private MultiplePermissionsListener allPermissionsListener;
    private PermissionRequestErrorListener errorListener;
    ViewGroup rootView;
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        rootView = (ViewGroup) this.findViewById(android.R.id.content);
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new SampleMultiplePermissionListener(this);
        errorListener = new SampleErrorListener();
        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
                                R.string.all_permissions_denied_feedback)
                                .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                                .build());
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(allPermissionsListener)
                .withErrorListener(errorListener)
                .check();
        mBtnToday=(RelativeLayout) findViewById(R.id.btn_today);
        mBtnWeekly=(RelativeLayout) findViewById(R.id.btn_weekly);
        mBtnSetting=(RelativeLayout) findViewById(R.id.btn_setting);
        mBtnPhoneNumber=(RelativeLayout) findViewById(R.id.btn_phone_number);

        mIvToday=(ImageView) findViewById(R.id.audio_daily);
        mIvWeekly=(ImageView) findViewById(R.id.audio_weekly);
        mIvSetting=(ImageView) findViewById(R.id.audio_setting);
        mIvPhoneNumber=(ImageView) findViewById(R.id.audio_phone_number);




        mBtnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
        mBtnWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, ScreenSlidePagerActivity.class);
                startActivity(i);

            }
        });
        mBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, SettingActivity.class);
                startActivity(i);

            }
        });
        mBtnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, PhoneNumberActivity.class);
                startActivity(i);

            }
        });
        mIvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "daily_weather.mp4");

            }
        });
        mIvWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "weeky_weather_info.mp4");

            }
        });
        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "settings_screen_options.mp4");

            }
        });
        mIvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "weather_mobile_phone.mp4");

            }
        });
        invokeService();


    }
    private void invokeService() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);
        Intent intent = new Intent(this, UpdateLiveFeedCountService.class);
        PendingIntent pintent = PendingIntent.getService(this, 1000,
                intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AppUtil.HOUR * 24, pintent);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(this).setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    public void showPermissionGranted(String permission) {

    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {

    }

    @Override
    public void onSuccess(ServerResponse response) {
        if (response.getRequestCode() == ServerCodes.ServerRequestCodes.LOGIN_REQUEST_CODE) {
            LoginResponse2 loginResponse = (LoginResponse2) response;
        }
    }

    @Override
    public void onFailure(ServerResponse response) {
    }

}
