# AdKnowva_V2_SDK_android

## 애드노바_V2 (AdKnowva_V2) Install Guide

애드노바_V2(AdKnowva_V2)의 연동 방식은 Gradle을 이용한 방법으로 샘플 예제를 이용해 간단하게 연동이 가능합니다.
아래 가이드 문서 내용은 본 문서 적용가이드의 **"모든 애드노바_V2 샘플 프로젝트 다운로드"** 하시면 모든 내용을 보실 수 있습니다.
연동시 애드노바(AdKnowva) 최신버전을 확인해 주세요. 현재 최신버전은 **1.5.2** 버전입니다.



## 제휴 신청
애드노바_V2(AdKnowva_V2) SDK 제휴 방법은 https://www.huvleview.com/doc/contact.php 에 절차를 안내 드리고 있습니다.


### 적용가이드
- Usages 를 참고하시거나 아래 샘플 프로젝트를 참고해주세요.
- [모든 애드노바 샘플 프로젝트 다운로드](https://github.com/Huvle-Ad/AdKnowva_SDK_KR/archive/main.zip)    
-> 애드노바(AdKnowva) 및 애드노바(AdKnowva) + HuvleSDK , 플러터, 유니티(Unity3D) 연동예제 
- [유니티 Plugin 다운로드](https://github.com/Huvle-Ad/AdKnowva_SDK_KR/releases/tag/1.5.0)


## Usages
### 1. Manifest
- networkSecurityConfig 추가
```

<uses-permission android:name="com.google.android.gms.permission.AD_ID" /> 
<uses-permission android:name="android.permission.INTERNET" />

<application
	.
	.
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
	implementation 'com.google.android.gms:play-services-ads:20.6.0' // Please use that version or higher
	implementation 'com.byappsoft.huvleadlib:HuvleAdLib:1.5.2' // Please implement after checking the latest version.
	.
	.
}
```

### 3. 앱에 적용하기

>#### 배너광고

- 광고가 적용될 Activity
+ Java code
```java
// TODO - Adknowva SDK Library
private BannerAdView bav;
// TODO - Adknowva SDK Library

@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate( savedInstanceState );
  setContentView( R.layout.activity_main );

  // TODO - Adknowva SDK Library  
  setAdknowvaAD(); // AdKnowva SDK init -  Activivty onCreate 부분에 적용해준다.
  // TODO - Adknowva SDK Library
}
// TODO - Adknowva SDK Library
private void setAdknowvaAD() {
  // 정적 구현부와 동적구현부는 참고하시어 하나만 적용하시기 바랍니다.
  // initBannerView 
  
  // 아이디 "test" 값은 https://ssp.huvle.com/ 에서 가입 > 매체생성 > zoneid 입력 후 테스트 하시고, 
  // release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.
  
  bav = findViewById(R.id.banner_view);
  bav.setPlacementID("test"); // 320*50 banner testID , 300*250 banner test ID "testbig"
  bav.setShouldServePSAs(false);
  bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER);
  bav.setAdSize(320, 50); //bav.setAdSize(300, 250);
  // Resizes the container size to fit the banner ad
  bav.setResizeAdToFitContainer(true);
  // bav.setExpandsToFitScreenWidth(true)
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
protected void onResume() {
    // TODO - Adknowva SDK Library
    if (bav != null) {
        bav.activityonResume();
    }
    // TODO - Adknowva SDK Library
    super.onResume();
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


// TODO - Adknowva SDK Library
```

+ Kotlin code
```java
// TODO - Adknowva SDK Library
lateinit var bav: BannerAdView
// TODO - Adknowva SDK Library

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // TODO - Adknowva SDK Library
    setAdknowvaAD() // 애드노바 sdk init - Activity onCreate 부분에 적용해준다.
    // TODO - Adknowva SDK Library
}

// TODO - Adknowva SDK Library
private fun setAdknowvaAD() {
  bav = findViewById(R.id.banner_view)
  bav.setPlacementID("test") // 320*50 banner testID , 300*250 banner test ID "testbig"
  bav.setShouldServePSAs(false)
  bav.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER)
  bav.setAdSize(320, 50) //bav.setAdSize(300, 250);
  // Resizes the container size to fit the banner ad
  bav.setResizeAdToFitContainer(true)
//bav.setExpandsToFitScreenWidth(true)
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
  bav.loadAd()
}

override fun onPause() {
    bav.activityOnPause()
    super.onPause()
}

override fun onResume() {
    bav.activityOnResume()
    super.onResume()
}

override fun onDestroy() {
    bav.destroy()
    super.onDestroy()
    
}

// TODO - Adknowva SDK Library
```


>#### 전면 광고
+ 전체 적용 방법은 예제 샘플을 참고바랍니다.
+ 광고가 적용될 Activity


```java

private void launchInterstitialAd() {
        final InterstitialAdView iadv = new InterstitialAdView(this);
        //bav.setBackgroundColor(0xffffffff); // 배경 color
        iadv.setCloseButtonDelay(10 * 1000);  // 10초 뒤 닫기 버튼 활성화
        //badv.setCloseButtonDelay(0);        // 즉시 활성화
        //iadv.setCloseButtonDelay(-1);       // 닫기버튼 비활성화

        // 아이디 "testfull" 값은 https://ssp.huvle.com/ 에서 가입 > 매체생성 > fullscreen 체크한 zoneid 입력 후 테스트 하시고, 
        // release시점에 허블에 문의주시면 인증됩니다. 배너사이즈는 변경하지 마세요.
        iadv.setPlacementID("testfull"); // zoneId
        iadv.setShouldServePSAs(false);
        iadv.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER);


        AdListener adListener = new AdListener() {
            @Override
            public void onAdRequestFailed(AdView bav, ResultCode errorCode) {
                if (errorCode == null) {
                    Log.v(IAD_TAG, "Call to loadAd failed");
                } else {
                    Log.v(IAD_TAG, "Ad request failed: " + errorCode);
                }
            }

            @Override
            public void onAdLoaded(AdView ba) {
                Log.v(IAD_TAG, "The Ad Loaded!");
                iadv.show();
            }

            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                Log.v(IAD_TAG, "Ad onAdLoaded NativeAdResponse");
            }

            @Override
            public void onAdExpanded(AdView bav) {
                Log.v(IAD_TAG, "Ad expanded");
            }

            @Override
            public void onAdCollapsed(AdView bav) {
                Log.v(IAD_TAG, "Ad collapsed");
            }

            @Override
            public void onAdClicked(AdView bav) {
                Log.v(IAD_TAG, "Ad clicked; opening browser");
            }

            @Override
            public void onAdClicked(AdView adView, String clickUrl) {
                Log.v(IAD_TAG, "onAdClicked with click URL");
            }

            @Override
            public void onLazyAdLoaded(AdView adView) {
                Log.v(IAD_TAG, "onLazyAdLoaded");
            }
        };
        iadv.setAdListener(adListener);
        iadv.loadAd();
    }

```

>### 전면광고_BackButton 기능
+ 전체 적용 방법은 예제 샘플을 참고바랍니다.
+ 광고가 적용될 Activity
- BackAdListener 사용

```java

// backPressed InterstitialAd load
    @Override
    public void onBackPressed() {
        launchBackButtonAd();
    }

    private void launchBackButtonAd() {
        final InterstitialAdView badv = new InterstitialAdView(this);
//        bav.setBackgroundColor(0xffffffff);

//        badv.setCloseButtonDelay(10 * 1000); // 10초 뒤 닫기 버튼 활성화
        badv.setCloseButtonDelay(0);           // 즉시 활성화
//        badv.setCloseButtonDelay(-1);        // 닫기버튼 비활성화


        badv.setPlacementID("testfull"); // ZoneId
        badv.setShouldServePSAs(false);
        badv.setClickThroughAction(ANClickThroughAction.OPEN_DEVICE_BROWSER);

        AdListener adListener = new BackAdListener() {
            @Override
            public void onBackPressed() {
                Log.v("backIAD", "BackAdListener.onBackPressed()!");
            }

            @Override
            public void onAdLoaded(AdView adView) {
                Log.v("backIAD", "The Ad Loaded!");
                badv.show();

            }

            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                Log.v("backIAD", "Ad onAdLoaded NativeAdResponse");
            }

            @Override
            public void onAdRequestFailed(AdView adView, ResultCode errorCode) {
                if (errorCode == null) {
                    Log.v("backIAD", "Call to loadAd failed");
                } else {
                    Log.v("backIAD", "Ad request failed: " + errorCode);
                }
                // 백버튼 광고 실패시 앱종료
                finish();
            }

            @Override
            public void onAdExpanded(AdView adView) {
                Log.v("backIAD", "Ad expanded");
            }

            @Override
            public void onAdCollapsed(AdView adView) {
                // 닫기 버튼 클릭시 앱종료
                finish();
            }

            @Override
            public void onAdClicked(AdView adView) {
                Log.v("backIAD", "Ad clicked; opening browser");
            }

            @Override
            public void onAdClicked(AdView adView, String s) {
                Log.v("backIAD", "onAdClicked with click URL");
            }

            @Override
            public void onLazyAdLoaded(AdView adView) {
                Clog.v("backIAD", "onLazyAdLoaded");
            }
        };

        badv.setAdListener(adListener);
        badv.loadAd();

    }

```






## License
AdKnowva_V2 SDK 의 저작권은 (주)허블에 있습니다.
```
AdKnowva_V2 SDK Android
Copyright 2021-present Huvle Corp.

Unauthorized use, modification and redistribution of this software are strongly prohibited.
```

