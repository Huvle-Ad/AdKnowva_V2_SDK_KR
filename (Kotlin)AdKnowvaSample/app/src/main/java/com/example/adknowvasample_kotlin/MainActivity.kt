package com.example.adknowvasample_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.byappsoft.huvleadlib.*

class MainActivity : AppCompatActivity() {

    private var bav: BannerAdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO - Adknowva SDK Library
        setHuvleAD()

    }

    // TODO - Adknowva SDK Library
    private fun setHuvleAD() {
        /*
            정적 구현부와 동적구현부는 참고하시어 하나만 적용하시기 바랍니다.(With checking the implementation guide below, please apply Implementation either only Dynamic or Static.)
            initBannerView 아이디 "test" 값은 http://ssp.huvle.com/ 에서 가입 > 매체생성 > zoneid 입력후 테스트 하시고, release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.(For the “test” value below, please go to http://ssp.huvle.com/ to sign up > create media > Test your app after typing zoneid. Next, contact Huvle before releasing your app for authentication. You must not change the banner size.)
        */
        // 정적으로 구현시(When if apply Dynamic Implementation) BannerAdView Start
        bav = findViewById(R.id.banner_view)
        initBannerView(bav,"test",320,50) // 300 * 250 배너 테스트시(Example for testing 300 * 250 banner)  initBannerView(staticBav, "testbig",300,250);

        // 동적으로 구현시(When if apply Static Implementation) BannerAdView Start
//        val bav = BannerAdView(this)
//        initBannerView(bav,"test",320,50) // 300 * 250 배너 테스트시 initBannerView(staticBav, "testbig",300,250);
//        val layout = findViewById<View>(R.id.main_content) as RelativeLayout
//        val layoutParams = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
//            RelativeLayout.LayoutParams.WRAP_CONTENT
//        )
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
//        bav.layoutParams = layoutParams
//        layout.addView(bav)
    }

    private fun initBannerView(bav: BannerAdView?, id: String, w: Int, h: Int) {
        bav?.placementID = id
        bav?.setAdSize(w, h)
        bav?.shouldServePSAs = false
        bav?.clickThroughAction =
            ANClickThroughAction.OPEN_DEVICE_BROWSER // 광고 클릭시 브라우저를 기본브라우저로 Open(Open the browser as the default browser when clicking on an advertisement)
        //bav.setClickThroughAction(ANClickThroughAction.OPEN_HUVLE_BROWSER); // 광고 클릭시 브라우저를 허블로 Open - 허블 SDK 연동한 업체인경우만(Open the browser as the Huvle browser when clicking on an advertisement(When if Huvle SDK is already integrated))
        bav?.resizeAdToFitContainer = true
        val adListener: AdListener = object : AdListener {
            override fun onAdRequestFailed(
                bav: AdView,
                errorCode: ResultCode
            ) { /*광고가 없을때 처리(Handle when there is no advertiment)*/
            }

            override fun onAdLoaded(bav: AdView) {
                Log.v("Huvle_Banner", "The Ad Loaded!")
            }

            override fun onAdLoaded(nativeAdResponse: NativeAdResponse) {
                Log.v("Huvle_Banner", "Ad onAdLoaded NativeAdResponse")
            }

            override fun onAdExpanded(bav: AdView) {
                Log.v("Huvle_Banner", "Ad expanded")
            }

            override fun onAdCollapsed(bav: AdView) {
                Log.v("Huvle_Banner", "Ad collapsed")
            }

            override fun onAdClicked(bav: AdView) {
                Log.v("Huvle_Banner", "Ad clicked; opening browser")
            }

            override fun onAdClicked(adView: AdView, clickUrl: String) {
                Log.v("Huvle_Banner", "onAdClicked with click URL")
            }

            override fun onLazyAdLoaded(adView: AdView) {}
        }
        bav?.adListener = adListener
        Handler(Looper.getMainLooper()).postDelayed({ bav?.loadAd() }, 0)
    }

    // TODO - Adknowva SDK Library
    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        bav!!.destroy()
        super.onDestroy()
    }

}