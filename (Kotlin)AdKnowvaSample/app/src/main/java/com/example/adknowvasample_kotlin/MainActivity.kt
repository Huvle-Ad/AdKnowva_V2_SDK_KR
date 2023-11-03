package com.example.adknowvasample_kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.adknowva.adlib.ANClickThroughAction
import com.adknowva.adlib.AdListener
import com.adknowva.adlib.BackAdListener
import com.adknowva.adlib.BannerAdView
import com.adknowva.adlib.InterstitialAdView
import com.adknowva.adlib.NativeAdResponse
import com.adknowva.adlib.ResultCode

class MainActivity : AppCompatActivity() {

    // TODO - Adknowva SDK Library
    lateinit var bav: BannerAdView
    // TODO - Adknowva SDK Library

    private var layout : RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO - Adknowva SDK Library
        setAdknowvaAD() // 애드노바 sdk init - Activity onCreate 부분에 적용해준다.
        // TODO - Adknowva SDK Library


        findViewById<View>(R.id.load_iad_btn).setOnClickListener {
            // 전면광고 샘플
            launchInterstitialAd()
        }

    }


    // TODO - Adknowva SDK Library
    private fun setAdknowvaAD() {
        /*
            정적 구현부와 동적구현부는 참고하시어 하나만 적용하시기 바랍니다.(With checking the implementation guide below, please apply Implementation either only Dynamic or Static.)
            initBannerView 아이디 "test" 값은 http://ssp.huvle.com/ 에서 가입 > 매체생성 > zoneid 입력후 테스트 하시고, release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.(For the “test” value below, please go to http://ssp.huvle.com/ to sign up > create media > Test your app after typing zoneid. Next, contact Huvle before releasing your app for authentication. You must not change the banner size.)
        */

//        // 동적으로 구현시(When if apply Dynamic Implementation) BannerAdView Start
//        bav = BannerAdView(this)
//        layout = findViewById<View>(R.id.adview_container) as RelativeLayout
//        val layoutParams = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
//            RelativeLayout.LayoutParams.WRAP_CONTENT
//        )
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
//        bav!!.layoutParams = layoutParams
//        layout!!.addView(bav)

        // 정적으로 구현시(When if apply Static Implementation) BannerAdView Start
        bav = findViewById(R.id.banner_view)
        bav.placementID = "test" // 320*50 banner testID , 300*250 banner test ID "testbig"
        bav.shouldServePSAs = false
        bav.clickThroughAction = ANClickThroughAction.OPEN_DEVICE_BROWSER
        bav.setAdSize(320, 50) //bav.setAdSize(300, 250);
        // Resizes the container size to fit the banner ad
        bav.resizeAdToFitContainer = true
//        bav.setExpandsToFitScreenWidth(true)
        val adListener: AdListener = object : AdListener {
            override fun onAdRequestFailed(
                bav: com.adknowva.adlib.AdView,
                errorCode: ResultCode
            ) {
                if (errorCode == null) {
                    Log.v("HuvleBANNER", "Call to loadAd failed")
                } else {
                    Log.v("HuvleBANNER", "Ad request failed: $errorCode")
                }
            }
            override fun onAdLoaded(ba: com.adknowva.adlib.AdView) {
                Log.v("HuvleBANNER", "The Ad Loaded!")
            }
            override fun onAdLoaded(nativeAdResponse: NativeAdResponse) {}
            override fun onAdExpanded(bav: com.adknowva.adlib.AdView) {}
            override fun onAdCollapsed(bav: com.adknowva.adlib.AdView) {}
            override fun onAdClicked(bav: com.adknowva.adlib.AdView) {}
            override fun onAdClicked(adView: com.adknowva.adlib.AdView, clickUrl: String) {}
            override fun onLazyAdLoaded(adView: com.adknowva.adlib.AdView) {}
        }
        bav.adListener = adListener
        bav.loadAd()

    }

    //InterstitialAd
    private fun launchInterstitialAd() {
        val iadv = InterstitialAdView(this)
        //bav.setBackgroundColor(0xffffffff); // 배경 color
        iadv.closeButtonDelay = 3 * 1000 // 3초뒤 X 버튼 활성화
//        iadv.closeButtonDelay = 0         // X 버튼 즉시 활성화
//        iadv.closeButtonDelay = -1        // X 버튼 비활성화

        // 아이디 "testfull" 값은 https://ssp.huvle.com/ 에서 가입 > 매체생성 > fullscreen 체크한 zoneid 입력 후 테스트 하시고,
        // release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.
        iadv.placementID = "testfull" // zoneId
        iadv.shouldServePSAs = false
        iadv.clickThroughAction = ANClickThroughAction.OPEN_DEVICE_BROWSER
        val adListener: AdListener = object : AdListener {
            override fun onAdRequestFailed(
                bav: com.adknowva.adlib.AdView,
                errorCode: ResultCode
            ) {
                if (errorCode == null) {
                    Log.v("HuvleInterstitialAd", "Call to loadAd failed")
                } else {
                    Log.v("HuvleInterstitialAd", "Ad request failed: $errorCode")
                }
            }

            override fun onAdLoaded(ba: com.adknowva.adlib.AdView) {
                Log.v("HuvleInterstitialAd", "The Ad Loaded!")
                iadv.show()
            }

            override fun onAdLoaded(nativeAdResponse: NativeAdResponse) {
                Log.v("HuvleInterstitialAd", "Ad onAdLoaded NativeAdResponse")
            }

            override fun onAdExpanded(bav: com.adknowva.adlib.AdView) {
                Log.v("HuvleInterstitialAd", "Ad expanded")
            }

            override fun onAdCollapsed(bav: com.adknowva.adlib.AdView) {
                Log.v("HuvleInterstitialAd", "Ad collapsed")
            }

            override fun onAdClicked(bav: com.adknowva.adlib.AdView) {
                Log.v("HuvleInterstitialAd", "Ad clicked; opening browser")
            }

            override fun onAdClicked(adView: com.adknowva.adlib.AdView, clickUrl: String) {
                Log.v("HuvleInterstitialAd", "onAdClicked with click URL")
            }

            override fun onLazyAdLoaded(adView: com.adknowva.adlib.AdView) {
                Log.v("HuvleInterstitialAd", "onLazyAdLoaded")
            }
        }
        iadv.adListener = adListener
        iadv.loadAd()
    }

    // backPressed InterstitialAd load
    override fun onBackPressed() {
        launchBackButtonAd()
    }

    private fun launchBackButtonAd() {
        val badv = InterstitialAdView(this)
        // bav.setBackgroundColor(-0x1)
        // badv.setCloseButtonDelay = 10 * 1000 // 10초뒤 [X] 버튼 활성화
        // badv.setCloseButtonDelay = 0         // 0 이면 [X] 버튼 즉시 노출
        badv.closeButtonDelay = 0              // 0 보다 작으면 [X] 버튼 비 노출
        badv.placementID = "testfull" // zoneId
        badv.shouldServePSAs = false
        badv.clickThroughAction = ANClickThroughAction.OPEN_DEVICE_BROWSER

        val adListener: AdListener = object : BackAdListener {
            override fun onBackPressed() {
                Log.v("backIAD", "BackAdListener.onBackPressed()!")
            }

            override fun onAdLoaded(adView: com.adknowva.adlib.AdView) {
                Log.v("backIAD", "The Ad Loaded!")
                badv.show()
            }

            override fun onAdLoaded(nativeAdResponse: NativeAdResponse) {
                Log.v("backIAD", "Ad onAdLoaded NativeAdResponse")
            }

            override fun onAdRequestFailed(
                adView: com.adknowva.adlib.AdView,
                errorCode: ResultCode
            ) {
                if (errorCode == null) {
                    Log.v("backIAD", "Call to loadAd failed")
                } else {
                    Log.v("backIAD", "Ad request failed: $errorCode")
                }
                // 백버튼 광고 실패시 앱종료
                finish()
            }

            override fun onAdExpanded(adView: com.adknowva.adlib.AdView) {
                Log.v("backIAD", "Ad expanded")
            }

            override fun onAdCollapsed(adView: com.adknowva.adlib.AdView) {
                // 닫기 버튼 클릭시 앱종료
                finish()
            }

            override fun onAdClicked(adView: com.adknowva.adlib.AdView) {
                Log.v("backIAD", "Ad clicked; opening browser")
            }

            override fun onAdClicked(adView: com.adknowva.adlib.AdView, s: String) {
                Log.v("backIAD", "onAdClicked with click URL")
            }

            override fun onLazyAdLoaded(adView: com.adknowva.adlib.AdView) {
                Log.v("backIAD", "onLazyAdLoaded")
            }
        }

        badv.adListener = adListener
        badv.loadAd()

    }
    // TODO - Adknowva SDK Library


    override fun onResume() {
        bav.activityOnResume()
        super.onResume()
    }

    override fun onPause() {
        bav.activityOnPause()
        super.onPause()
    }

    override fun onDestroy() {
        // TODO - Adknowva SDK Library
        bav.destroy()
        // TODO - Adknowva SDK Library
        super.onDestroy()

    }

}