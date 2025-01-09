package com.HuvleAdknowva;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.adknowva.adlib.ANClickThroughAction;
import com.adknowva.adlib.AdListener;
import com.adknowva.adlib.AdView;
import com.adknowva.adlib.BannerAdView;
import com.adknowva.adlib.NativeAdResponse;
import com.adknowva.adlib.ResultCode;
import com.byappsoft.sap.launcher.Sap_act_main_launcher;
import com.byappsoft.sap.utils.Sap_Func;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.facebook.react.ReactInstanceManager;
import com.HuvleAdknowva.R;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  private ReactRootView mReactRootView;
  private ReactInstanceManager mReactInstanceManager;

  public final static int REQUEST_CODE = 10000;


  private BannerAdView bav;
  private Activity activity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // ReactRootView init
    mReactRootView = new ReactRootView(this);
    mReactInstanceManager = getReactNativeHost().getReactInstanceManager();

    // React Setting the Component Name
    mReactRootView.startReactApplication(
            mReactInstanceManager,
            "HuvleAdknowva",
            null
    );

    // Add ReactRootView to Framelayout
    FrameLayout reactRootViewContainer = findViewById(R.id.react_root_view);
    reactRootViewContainer.addView(mReactRootView);

    Log.e("HuvleAdknowva", "onCreate called in MainActivity");
    // Check notification permissions for Android 13 and above.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if(!checkPermission()){
        requestSapPermissions();
      } else {
        // 안드로이드 14 이상 알림 및 리마인드 권한 획득
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
          checkExactAlarm();
        }
      }
    }


    /////////////////////////////////////////////////////////////
    // 기본 배너광고
    /////////////////////////////////////////////////////////////
    bav = new BannerAdView(this);
    bav.setPlacementID("test"); // 320*50 banner test
    bav.setShouldServePSAs(false);
    bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER);
    bav.setAdSize(320, 50);
    bav.setExpandsToFitScreenWidth(true);

    // Set up a listener on this ad view that logs events.
    AdListener adListener = new AdListener() {
      @Override
      public void onAdRequestFailed(AdView bav, ResultCode errorCode) {
        if (errorCode == null) {
          Log.v("SIMPLEBANNER", "Call to loadAd failed");
        } else {
          Log.v("SIMPLEBANNER", "Ad request failed: " + errorCode);
        }
      }

      @Override
      public void onAdLoaded(AdView ba) {
        Log.v("SIMPLEBANNER", "The Ad Loaded!");
      }

      @Override
      public void onAdLoaded(NativeAdResponse nativeAdResponse) {
        Log.v("SIMPLEBANNER", "Ad onAdLoaded NativeAdResponse");
      }

      @Override
      public void onAdExpanded(AdView bav) {
        Log.v("SIMPLEBANNER", "Ad expanded");
      }

      @Override
      public void onAdCollapsed(AdView bav) {
        Log.v("SIMPLEBANNER", "Ad collapsed");
      }

      @Override
      public void onAdClicked(AdView bav) {
        Log.v("SIMPLEBANNER", "Ad clicked; opening browser");
      }

      @Override
      public void onAdClicked(AdView adView, String clickUrl) {
        Log.v("SIMPLEBANNER", "onAdClicked with click URL");
      }

      @Override
      public void onLazyAdLoaded(AdView adView) {

      }
    };

    bav.setAdListener(adListener);

    final RelativeLayout layout = (RelativeLayout) findViewById(R.id.adview_container);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    bav.setLayoutParams(layoutParams);
    layout.addView(bav);


    bav.loadAd();

  }

  @Override
  protected void onPause() {
    // TODO - Adknowva SDK Library
    if (bav != null) {
      bav.activityOnPause();
    }
    // TODO - Adknowva SDK Library
    super.onPause();
  }


  @Override
  protected void onDestroy() {

    // TODO - Adknowva SDK Library
    if (bav != null) {
      bav.destroy();
    }
    // TODO - Adknowva SDK Library
    super.onDestroy();
  }

  public void onResume() {

      // TODO - Adknowva SDK Library
      if (bav != null) {
        bav.activityOnResume();
      }
      // TODO - Adknowva SDK Library

    super.onResume();

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (checkPermission()) { // 안드로이드 13 이상 POST_NOTIFICATION 권한 확인 필수
        if (Build.VERSION.SDK_INT >= 34) {
          Sap_Func.setServiceState(this,true);
        }
        huvleView();
      }
    } else {
      huvleView();
    }
  }


  public void huvleView() {
    Sap_Func.setNotiBarLockScreen(this, false);
    Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true, new Sap_act_main_launcher.OnLauncher() {

      @Override
      public void onDialogOkClicked() {
        checkDrawOverlayPermission();
      }

      @Override
      public void onDialogCancelClicked() {
      }

      @Override
      public void onInitSapStartapp() {
      }

      @Override
      public void onUnknown() {
      }
    });
  }
  private boolean checkPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }
    return true;
  }

  private void requestSapPermissions() {
    try{
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
    }catch (Exception ignored){
    }
  }

  public boolean checkExactAlarm() {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
      return true;
    }

    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    boolean canScheduleExactAlarms = alarmManager.canScheduleExactAlarms();

    if (!canScheduleExactAlarms) {
      new AlertDialog.Builder(this)
              .setTitle("알림 및 리마인더 허용")
              .setMessage("알림 및 리마인더 권한을 허용해주세요.")
              .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                  intent.setData(Uri.parse("package:" + getPackageName()));
                  startActivity(intent);
                }
              })
              .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
                }
              })
              .create()
              .show();
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 0) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        checkExactAlarm();
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      if (requestCode == 0) {
      if (checkPermission()) {
        // Post notification 권한이 허용된 경우를 확인합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
          checkExactAlarm();
        }
      }
    }
  }


  public boolean checkDrawOverlayPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }
    if (!Settings.canDrawOverlays(this)) {
      new AlertDialog.Builder(this)
              .setTitle("다른앱 위에 그리기")
              .setMessage("다른 앱 위에 그리기 권한을 허용해주세요.")
              .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent();
                  intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                  Uri uri = Uri.parse("package:" + getPackageName());
                  intent.setData(uri);
                  startActivity(intent);

                }
              })
              .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
                }
              })
              .create()
              .show();
      return false;
    } else {
      return true;
    }
  }

  @Override
  protected String getMainComponentName() {
    Log.e("HuvleAdknowva","HuvleAdknowva");
    return "HuvleAdknowva";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }
}
