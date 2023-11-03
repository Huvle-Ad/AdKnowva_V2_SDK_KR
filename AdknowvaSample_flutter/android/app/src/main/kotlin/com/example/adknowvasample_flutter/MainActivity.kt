package com.example.adknowvasample_flutter

import android.util.Log
import android.widget.RelativeLayout
import com.adknowva.adlib.ANClickThroughAction
import com.adknowva.adlib.AdListener
import com.adknowva.adlib.BannerAdView
import com.adknowva.adlib.InterstitialAdView
import com.adknowva.adlib.NativeAdResponse
import com.adknowva.adlib.ResultCode
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val CHANNEL = "com.example.adknowva/ads"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL
        ).setMethodCallHandler { call, result ->
            when(call.method) {
                "loadAd" -> bannerAd()
                "interstitialAd" -> interstitialAd()
                else ->result.notImplemented()
            }
//            if (call.method == "loadAd") {
//                bannerAd()
//            } else {
//                result.notImplemented()
//            }
        }
    }

    private fun bannerAd() {
        val bav = BannerAdView(context)
        val layout = RelativeLayout(context)
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_VERTICAL)

        layoutParams.setMargins(50,0,0,100)

        bav.layoutParams = layoutParams
        layout.addView(bav)
        this.addContentView(layout,layoutParams)


        bav.placementID = "test" // 320*50 banner testID , 300*250 banner test ID "testbig"
        bav.shouldServePSAs = false
        bav.clickThroughAction = ANClickThroughAction.OPEN_DEVICE_BROWSER
        bav.setAdSize(320, 50) //bav.setAdSize(300, 250);
//                bav.resizeAdToFitContainer = true
//                bav.expandsToFitScreenWidth = true
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


    private fun interstitialAd() {
        val iadv = InterstitialAdView(context)
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

}
