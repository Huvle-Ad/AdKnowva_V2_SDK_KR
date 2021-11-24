package com.huvle.huvleadlibsamplegoogle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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

public class MainActivity extends Activity {

    private com.google.android.gms.ads.AdView mAdView;
    // TODO - Adknowva SDK Library
    private boolean loadAd = true;
    private BannerAdView bav;
    // TODO - Adknowva SDK Library

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // TODO - Adknowva SDK Library
//        setGoogleAD(true); // 구글 광고 로딩후 없으면 허블 광고 로딩, 구글만 로딩할경우 false(Loading Google ads first and if there is no google ads, load Huvle ads. If you want to load only Google Ads, change true into false)
        setHuvleAD(true); // 허블 광고 로딩후 없으면 구글 광고 로딩, 허블만 로딩할경우 false(Loading Huvle ads first and if there is no Huvle ads, load Google ads. If you want to load only Huvle Ads, change true into false)
        // TODO - Adknowva SDK Library
    }

//    public void setGoogleAD(final boolean opt) {
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//        mAdView = findViewById(R.id.gadView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
//            @Override public void onAdLoaded() {
//                // TODO - Adknowva SDK Library
//                if (loadAd) {
//                    loadAd = false;
//                    bav.stopAd();
//                }
//                // TODO - Adknowva SDK Library
//            }
//            @Override public void onAdFailedToLoad(LoadAdError adError) {
//                // TODO - Adknowva SDK Library
//                if (opt) {
//                    setHuvleAD(false);
//                }
//                // TODO - Adknowva SDK Library
//            }
//            @Override public void onAdOpened() {}
//            @Override public void onAdClicked() {}
//            @Override public void onAdClosed() {}
//        });
//    }


    // TODO - Adknowva SDK Library
    private void setHuvleAD(final boolean opt) {
        loadAd = true;
        /*
            정적 구현부와 동적구현부는 참고하시어 하나만 적용하시기 바랍니다.(With checking the implementation guide below, please apply Implementation either only Dynamic or Static)
            initBannerView 아이디 "test" 값은 http://ssp.huvle.com/ 에서 가입 > 매체생성 > zoneid 입력후 테스트 하시고, release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.(For the “test” value below, please go to http://ssp.huvle.com/ to sign up > create media > Test your app after typing zoneid. Next, contact Huvle before releasing your app for authentication. You must not change the banner size.)
        */

        // 정적으로 구현시(When if apply Dynamic Implementation) BannerAdView Start
        bav = findViewById(R.id.banner_view);
        initBannerView(bav, "test",320,50, opt); // 300 * 250 배너 테스트시(Example for testing 300 * 250 banner) initBannerView(staticBav, "testbig",300,250, opt);

        // 동적으로 구현시(When if apply Static Implementation) BannerAdView Start
        /*
        bav = new BannerAdView(this);
        initBannerView(bav, "test",320,50, opt); // 300 * 250 배너 테스트시(Example for testing 300 * 250 banner) initBannerView(staticBav, "testbig",300,250, opt);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_content);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bav.setLayoutParams(layoutParams);
        layout.addView(bav);
        */
    }
    private void initBannerView(final BannerAdView bav, String id, int w , int h, boolean opt) {
        bav.setPlacementID(id);
        bav.setAdSize(w, h);
        bav.setShouldServePSAs(false);
        bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER); // 광고 클릭시 브라우저를 기본브라우저로 Open(Open the browser as the default browser when clicking on an advertisement)
        // bav.setClickThroughAction(ANClickThroughAction.OPEN_HUVLE_BROWSER); // 광고 클릭시 브라우저를 허블로 Open - 허블 SDK 연동한 업체인경우만(Open the browser as the Huvle browser when clicking on an advertisement(When if Huvle SDK is already integrated))
        bav.setResizeAdToFitContainer(true);
        AdListener adListener = new AdListener() {
            @Override public void onAdRequestFailed(AdView bav, ResultCode errorCode) {}
            @Override public void onAdLoaded(AdView bav) {Log.v("Huvle_Banner", "The Ad Loaded!");}
            @Override public void onAdLoaded(NativeAdResponse nativeAdResponse) {Log.v("Huvle_Banner", "Ad onAdLoaded NativeAdResponse");}
            @Override public void onAdExpanded(AdView bav) {Log.v("Huvle_Banner", "Ad expanded");}
            @Override public void onAdCollapsed(AdView bav) {Log.v("Huvle_Banner", "Ad collapsed");}
            @Override public void onAdClicked(AdView bav) {Log.v("Huvle_Banner", "Ad clicked; opening browser");}
            @Override public void onAdClicked(AdView adView, String clickUrl) {Log.v("Huvle_Banner", "onAdClicked with click URL");}
            @Override public void onLazyAdLoaded(AdView adView) {}
        };
        bav.setAdListener(adListener);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                bav.loadAd();
            }
        }, 0);
    }
    // TODO - Adknowva SDK Library


    @Override
    public void onResume() {
        super.onResume();
        // TODO - Huvle SDK Library
        Sap_Func.setNotiBarLockScreen(this, false);
        Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
        // TODO - Huvle SDK Library
    }


    @Override
    protected void onDestroy() {
        // TODO - Adknowva SDK Library
        bav.destroy();
        // TODO - Adknowva SDK Library
        super.onDestroy();
    }
}

