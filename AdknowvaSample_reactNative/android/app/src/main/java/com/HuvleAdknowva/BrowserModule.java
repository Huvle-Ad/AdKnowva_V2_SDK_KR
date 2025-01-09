package com.HuvleAdknowva;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import com.byappsoft.sap.browser.Sap_BrowserActivity;
import com.byappsoft.sap.browser.Sap_MainActivity;
import com.byappsoft.sap.utils.Sap_Func;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import java.util.Locale;

@ReactModule(name = "BrowserModule")
public class BrowserModule extends ReactContextBaseJavaModule {
    public BrowserModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void openNotificationSettings() {
        if (checkPermission()) {
            if (!NotificationManagerCompat.from(getReactApplicationContext()).areNotificationsEnabled()) {
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getReactApplicationContext().getPackageName());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    intent.putExtra("app_package", getReactApplicationContext().getPackageName());
                    intent.putExtra("app_uid", getReactApplicationContext().getApplicationInfo().uid);
                } else {
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + getReactApplicationContext().getPackageName()));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getReactApplicationContext().startActivity(intent);
            } else {
                getReactApplicationContext().runOnUiQueueThread(new Runnable() {
                    @Override
                    public void run() {
                        // WebView 초기화가 포함된 UI 관련 작업 실행
                        Sap_Func.notiUpdate(getReactApplicationContext());
                    }
                });

            }
        }
    }

    @ReactMethod
    public void turnOffNotification() {
        getReactApplicationContext().runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                // WebView 초기화가 포함된 UI 관련 작업 실행
                Sap_Func.notiCancel(getReactApplicationContext());
            }
        });
    }

    @ReactMethod
    public void openSapMainActivity() {
        String url = "https://www.huvle.com";
        Intent intent = new Intent(getReactApplicationContext(), Sap_MainActivity.class);
        intent.putExtra(Sap_BrowserActivity.PARAM_OPEN_URL, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(intent);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return getReactApplicationContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public String getName() {
        return "BrowserModule";
    }
}
