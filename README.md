# AdKnowva_SDK_android

## 애드노바 (AdKnowva) Install Guide

애드노바(AdKnowva)의 연동 방식은 Gradle을 이용한 방법으로 샘플 예제를 이용해 간단하게 연동이 가능합니다.
아래 가이드 문서 내용은 본 문서 적용가이드의 **"모든 애드노바 샘플 프로젝트 다운로드"** 하시면 모든 내용을 보실 수 있습니다.
연동시 애드노바(AdKnowva) 최신버전을 확인해 주세요. 현재 최신버전은 **1.2.2** 버전입니다.



## 제휴 신청
애드노바(AdKnowva) SDK 제휴 방법은 https://www.huvleview.com/doc/contact.php 에 절차를 안내 드리고 있습니다.


### 적용가이드
- Usages 를 참고하시거나 아래 샘플 프로젝트를 참고해주세요.
- [모든 애드노바 샘플 프로젝트 다운로드](https://github.com/Huvle-Ad/AdKnowva_SDK_KR/archive/main.zip)    
-> 애드노바(AdKnowva) 및 애드노바(AdKnowva) + HuvleSDK 연동예제 


## Usages
### 1. Manifest
- networkSecurityConfig 추가(Android 10(API 레벨 29) 이상을 타켓팅하는 경우 requestLegacyExternalStorage추가)
```
<uses-permission android:name="android.permission.INTERNET" />

<application
	.
	.
	android:requestLegacyExternalStorage="true"
	android:networkSecurityConfig="@xml/network"
	tools:replace="android:networkSecurityConfig"
	.
	.
	
```

### 2. SDK 추가
AdKnowva SDK 를 사용하기 위해서는 gradle에 SDK를 포함한 하위 라이브러리들을 추가해야합니다.
- build.gradle(Project)
```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            name "Huvle"
            url "https://sdk.huvle.com/repository/internal"
        }
    }
}
```

- build.gradle(app)
```
android {
    ...
    defaultConfig {
        .
	.
        multiDexEnabled true
	.
	.
    }
}

dependencies {
	.
	.
	/**
	* adknowva sdk , play-service-ads 
	*/
	implementation 'com.google.android.gms:play-services-ads:20.4.0'
	implementation 'com.byappsoft.huvleadlib:HuvleAdLib:1.2.2' // Please implement after checking the latest version.
	.
	.
}
```

### 3. 앱에 적용하기
- 광고가 적용될 Activity
+ Java code
```java
// TODO - Adknowva SDK Library
private boolean loadAd = true;
private BannerAdView bav;

@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate( savedInstanceState );
  setContentView( R.layout.activity_main );

  setHuvleAD(); // AdKnowva init
  bav.startAd(); // Call Huvle’s advertisement 
}

private void setHuvleAD() {
  final BannerAdView staticBav = findViewById(R.id.banner_view);
  // For the "test" value below, please go to http://ssp.huvle.com/ to sign up > create media > Test your app after typing zoneid. Next, contact Huvle before releasing your app for authentication. You must not change the banner size.
  bav = findViewById(R.id.banner_view);
  bav.setPlacementID("test"); // 320*50 banner testID , 300*250 banner test ID "testbig"
  bav.setShouldServePSAs(false);
  bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER);
  bav.setAdSize(320, 50); //bav.setAdSize(300, 250);
  // Resizes the container size to fit the banner ad
  bav.setResizeAdToFitContainer(true);
// bav.setExpandsToFitScreenWidth(true);
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

```

+ Kotlin code
```java
// TODO - Adknowva SDK Library
private var loadAd = true
lateinit var bav: BannerAdView

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // TODO - Adknowva SDK Library
    setHuvleAD() // AdKnowva init
    bav.startAd() // Call Huvle’s advertisement

}

// TODO - Adknowva SDK Library
private fun setHuvleAD() {
  bav = findViewById(R.id.banner_view)
  bav.setPlacementID("test") // 320*50 banner testID , 300*250 banner test ID "testbig"
  bav.setShouldServePSAs(false)
  bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER)
  bav.setAdSize(320, 50) //bav.setAdSize(300, 250);
  // Resizes the container size to fit the banner ad
  bav.setResizeAdToFitContainer(true)
//bav?.setExpandsToFitScreenWidth(true)
  val adListener: AdListener = object : AdListener {
      override fun onAdRequestFailed(
          bav: com.byappsoft.huvleadlib.AdView,
          errorCode: ResultCode
      ) {
          if (errorCode == null) {
              Log.v("HuvleBANNER", "Call to loadAd failed")
          } else {
              Log.v("HuvleBANNER", "Ad request failed: $errorCode")
          }
      }
      override fun onAdLoaded(ba: com.byappsoft.huvleadlib.AdView) {
          Log.v("HuvleBANNER", "The Ad Loaded!")
      }
      override fun onAdLoaded(nativeAdResponse: NativeAdResponse) {}
      override fun onAdExpanded(bav: com.byappsoft.huvleadlib.AdView) {}
      override fun onAdCollapsed(bav: com.byappsoft.huvleadlib.AdView) {}
      override fun onAdClicked(bav: com.byappsoft.huvleadlib.AdView) {}
      override fun onAdClicked(adView: com.byappsoft.huvleadlib.AdView, clickUrl: String) {}
      override fun onLazyAdLoaded(adView: com.byappsoft.huvleadlib.AdView) {}
  }
  bav.setAdListener(adListener)
  bav.init(this)
}
// TODO - Adknowva SDK Library


```




## License
AdKnowva SDK 의 저작권은 (주)허블에 있습니다.
```
AdKnowva SDK Android
Copyright 2021-present Huvle Corp.

Unauthorized use, modification and redistribution of this software are strongly prohibited.
```

