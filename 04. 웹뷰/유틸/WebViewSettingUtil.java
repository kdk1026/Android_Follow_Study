package com.example.testapp.util;

import android.webkit.WebSettings;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 8. 25. 김대광	최초 작성
 * </pre>
 *
 * <pre>
 * 안드로이드 웹뷰 세팅 모음
 *     - 워낙 많다 보니 한 곳에 몰빵
 *     - 포기할까 고민했는데... 훗~ 이게 바로 인간 승리지
 * </pre>
 *
 * @author 김대광
 * @Description	: minSdkVersion 26 / targetSdkVersion 30
 */
public class WebViewSettingUtil {

    public static void setSettingTotal(WebSettings webSettings) {
        // 자바스크립트 사용 여부 (Default : false)
        webSettings.setJavaScriptEnabled(true);

        /*
            파일 액세스 허용 여부
                - 번역기 돌려도 뭔말인지 이해가 안감... Default 무시하고 무조건 true
         */
        webSettings.setAllowFileAccess(true);

        /*
            파일 URL 로 파일 접근 허용 여부
                - 역시 뭔말인지 이해가 안감... Default 무시하고 무조건 true
         */
        webSettings.setAllowFileAccessFromFileURLs(true);

        // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정 (Default : false)
        webSettings.setBuiltInZoomControls(true);

        /*
            브라우저 캐시 허용 여부
                - LOAD_CACHE_ELSE_NETWORK 기간이 만료돼 캐시를 사용할 수 없을 경우 네트워크를 사용합니다.
				- LOAD_CACHE_ONLY 네트워크를 사용하지 않고 캐시를 불러옵니다.
				- LOAD_DEFAULT 기본적인 모드로 캐시를 사용하고 만료된 경우 네트워크를 사용해 로드합니다. (Default)
				- LOAD_NORMAL 기본적인 모드로 캐시를 사용합니다.
				- LOAD_NO_CACHE 캐시모드를 사용하지 않고 네트워크를 통해서만 호출합니다.
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 줌 사용시 하단에 뜨는 +,- 아이콘 보여주기 여부 (Default : true)
        webSettings.setDisplayZoomControls(false);

        // 로컬 스토리지 사용여부 (Default : false)
        webSettings.setDomStorageEnabled(true);
        
        // window.open() 허용 여부 (Default : false)
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 컨텐츠가 웹뷰보다 클때 스크린 크기에 맞추기 (Default : false)
        webSettings.setLoadWithOverviewMode(true);

        // HTTPS HTTP의 연동, 서로 호출 가능하도록 (Default : MIXED_CONTENT_NEVER_ALLOW)
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // 여러개의 윈도우 사용 여부 (Default : false)
        webSettings.setSupportMultipleWindows(true);

		/*
			UserAgent에 특정 문자열 추가
				- 일반적으로 모바일 웹, WebView 의 UserAgent는 동일
				- 문자열 추가로 애플리케이션 접속 판별 구분
		*/
        String sUserAgent = webSettings.getUserAgentString() + "InApp";
        webSettings.setUserAgentString(sUserAgent);
    }

    /*
        //---------------------
        // Default 그대로 사용
        //---------------------

        // 웹뷰를 통해 Content URL 에 접근할지 여부
        webSettings.setAllowContentAccess(true);

        // 웹뷰내의 위치 정보 사용 여부
        webSettings.setGeolocationEnabled(true);

       // 앱내부의 캐시 사용 여부
        webSettings.setAppCacheEnabled(false);

        // 네트워크를 통해 이미지리소스 받을지 여부
        webSettings.setBlockNetworkImage(false);

        // database storage API 사용 여부
        webSettings.setDatabaseEnabled(false);

        // 기본 고정 폰트 사이즈
        webSettings.setDefaultFixedFontSize(16);

        // 기본 폰트 사이즈
        webSettings.setDefaultFontSize(16);

        // 인코딩 설정
        webSettings.setDefaultTextEncodingName("UTF-8");

        // 폰트
        webSettings.setFixedFontFamily("monospace");

        // 현재 위치 불러오기 설정
        webSettings.setGeolocationEnabled(true);

        // 이미지 자동 로드
        webSettings.setLoadsImagesAutomatically(true);

        // 동영상 자동 재생
        webSettings.setMediaPlaybackRequiresUserGesture(true);

        webSettings.setMinimumFontSize(8);
        webSettings.setMinimumLogicalFontSize(8);
        webSettings.setSafeBrowsingEnabled(true);

        // 줌 사용 여부
        webSettings.setSupportZoom(true);
     */

    /*
        //---------------------
        // 안건드리는게 좋을 듯
        //---------------------
        
        webSettings.setAppCachePath
        webSettings.setBlockNetworkLoads
        webSettings.setForceDark
        webSettings.setGeolocationDatabasePath
        webSettings.setLayoutAlgorithm
        webSettings.setNeedInitialFocus
        webSettings.setTextZoom
        webSettings.setUseWideViewPort
     */

    /*
        //----------------------------
        // 국내에서 사용하는 사람 없는듯
        //----------------------------

        setCursiveFontFamily
        setDisabledActionModeMenuItems
        setFantasyFontFamily
        setOffscreenPreRaster
        setSansSerifFontFamily
        setSerifFontFamily
        setStandardFontFamily
        setStandardFontFamily
     */

}
