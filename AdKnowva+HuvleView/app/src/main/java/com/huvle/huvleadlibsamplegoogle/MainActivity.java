package com.huvle.huvleadlibsamplegoogle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.byappsoft.huvleadlib.ANClickThroughAction;
import com.byappsoft.huvleadlib.AdListener;
import com.byappsoft.huvleadlib.AdView;
import com.byappsoft.huvleadlib.BannerAdView;
import com.byappsoft.huvleadlib.NativeAdResponse;
import com.byappsoft.huvleadlib.ResultCode;
import com.byappsoft.sap.launcher.Sap_act_main_launcher;
import com.byappsoft.sap.utils.Sap_Func;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    // TODO - Adknowva SDK Library
    private boolean loadAd = true;
    private BannerAdView bav;
    // TODO - Adknowva SDK Library

    private RelativeLayout layout; //정적뷰
    private com.google.android.gms.ads.AdView mAdView; //구글 Admob

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO - Adknowva SDK Library
        setHuvleAD(); // 애드노바를 호출하는 Activity onCreate 부분에 적용해준다.
        bav.startAd(); // 애드노바 단독 또는 호출 우선시 사용 아니라면 주석처리 후 우선 호출되는 AdSDK 에서 처리해준다.
        // TODO - Adknowva SDK Library

//        setGoogleAD();
    }

//    private void setGoogleAD(){
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//        mAdView = findViewById(R.id.gadView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
//            @Override public void onAdLoaded() {
//                // TODO - Adknowva SDK Library
//                // startAd() , stopAd() 메소드를 통해 애드노바의 호출을 결정하면 된다.
//                if (loadAd) {
//                    loadAd = false;
//                    bav.stopAd();
//                }
//                // TODO - Adknowva SDK Library
//                Log.v("GoogleAD", "The Ad Loaded!");
//            }
//            @Override public void onAdFailedToLoad(LoadAdError adError) {
//                // TODO - Adknowva SDK Library
//                // startAd() , stopAd() 메소드를 통해 애드노바의 호출을 결정하면 된다.
//                if (loadAd) {
//                    loadAd = true;
//                    bav.startAd();
//                }
//                // TODO - Adknowva SDK Library
//                Log.v("GoogleAD", "The Ad failed!");
//            }
//            @Override public void onAdOpened() {}
//            @Override public void onAdClicked() {}
//            @Override public void onAdClosed() {}
//        });
//    }



    private void setHuvleAD(){

 /*
    정적 구현부와 동적구현부는 참고하시어 하나만 적용하시기 바랍니다.(With checking the implementation guide below, please apply Implementation either only Dynamic or Static.)
    initBannerView 아이디 "test" 값은 http://ssp.huvle.com/ 에서 가입 > 매체생성 > zoneid 입력후 테스트 하시고, release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.(For the “test” value below, please go to http://ssp.huvle.com/ to sign up > create media > Test your app after typing zoneid. Next, contact Huvle before releasing your app for authentication. You must not change the banner size.)
*/

        // 동적으로 구현시(When if apply Static Implementation) BannerAdView Start
//        bav = new BannerAdView(this);
//        layout = (RelativeLayout) findViewById(R.id.adview_container);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        bav.setLayoutParams(layoutParams);
//        layout.addView(bav);

        // 정적으로 구현시(When if apply Dynamic Implementation) BannerAdView Start
        bav = findViewById(R.id.banner_view);

        bav.setPlacementID("test"); // 320*50 banner testID , 300*250 banner test ID "testbig"
        bav.setShouldServePSAs(false);
        bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER);
        bav.setAdSize(320, 50); //bav.setAdSize(300, 250);
        // Resizes the container size to fit the banner ad
        bav.setResizeAdToFitContainer(true);
//        bav.setExpandsToFitScreenWidth(true);
        AdListener adListener = new AdListener() {
            @Override
            public void onAdRequestFailed(AdView bav, ResultCode errorCode) {
                if (errorCode == null) {
                    Log.v("HuvleBANNER", "Call to loadAd failed");
                } else {
                    Log.v("HuvleBANNER", "Ad request failed: " + errorCode);
                }
            }

            @Override
            public void onAdLoaded(AdView ba) {Log.v("HuvleBANNER", "The Ad Loaded!");}
            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {}
            @Override
            public void onAdExpanded(AdView bav) {}
            @Override
            public void onAdCollapsed(AdView bav) {}
            @Override
            public void onAdClicked(AdView bav) {}
            @Override
            public void onAdClicked(AdView adView, String clickUrl) {}
            @Override
            public void onLazyAdLoaded(AdView adView) {}
        };
        bav.setAdListener(adListener);
        bav.init(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // huvleView apply
        Sap_Func.setNotiBarLockScreen(this, false);
        Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

