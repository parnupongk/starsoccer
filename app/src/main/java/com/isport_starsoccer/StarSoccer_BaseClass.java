package com.isport_starsoccer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.isport_starsoccer.data.DataElementEvent;
import com.isport_starsoccer.data.DataElementGroupListMenu;
import com.isport_starsoccer.data.DataElementLeague;
import com.isport_starsoccer.data.DataElementScore;
import com.isport_starsoccer.data.DataElementSportNews;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.service.ImageUtil;
import com.isport_starsoccer.service.StartUp;

public class StarSoccer_BaseClass extends Activity {

    public static Vector<DataElementSportNews> vDataNews = null;
    public static Vector<DataElementEvent> vdataEvent = null;
    public static ArrayList<DataElementGroupListMenu> vGroupListMenu = null;
    public static Vector<DataElementScore> vLiveScoreData = null;
    public static Hashtable<String, DataElementLeague> hLeagueLiveScore = null;
    protected RelativeLayout layoutFooter = null;
    protected ImageView main_img_close = null;
    public static String SERVERVERSION = "";
    public static String ISACTIVE = "";
    public static String ISAdView = "";
    public static String ACTIVE_HEADER = "";
    public static String ACTIVE_DETAIL = "";
    public static String ACTIVE_FOOTER = "";
    public static String ACTIVE_FOOTER1 = "";
    public static String ACTIVE_OTPWAIT = "";
    public static String URLICON = "";
    public static String URLBANNER = "";
    public static String IVRNO = "";
    public static String OTPCODE = "";

    public static Hashtable<String, DataElementLeague> hLeagueProgram = null;

    public static Vector<DataElementScore> vResultData = null;
    public static Hashtable<String, DataElementLeague> hLeagueResult = null;

    protected ImageUtil imgUtil = null;

    protected RelativeLayout layoutTop = null;
    protected RelativeLayout layoutSport = null;
    protected RelativeLayout layoutCenter = null;
    protected RelativeLayout layoutMenu = null;
    protected RelativeLayout layoutButtom = null;
    protected StarSoccer_RelativeHeader relativeHeader = null;
    protected LinearLayout main_layout_webview = null;
    protected LinearLayout main_layout_adview = null;

    //protected WebView main_webview_banner = null;
    protected AdView main_adView = null;
    protected AdView main_adView_isp = null;
    private PublisherInterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);

        imgUtil = new ImageUtil(this);
        DataSetting.IMEI = StartUp.getImei(this);
        DataSetting.IMSI = StartUp.getImsi(this);
        StartUp.getModel(this);
        StartUp.getSetting(this);
    }


    protected void init() {
        try {

            main_layout_webview = (LinearLayout) findViewById(R.id.main_layout_webview);
            main_layout_adview = (LinearLayout) findViewById(R.id.main_layout_adview);
            layoutTop = (RelativeLayout) findViewById(R.id.main_layout_top);
            //main_webview_banner = (WebView) findViewById(R.id.main_webview_banner);
            main_adView = (AdView) findViewById(R.id.main_adView);
            main_adView_isp = (AdView) findViewById(R.id.main_adView_isp);
            relativeHeader = new StarSoccer_RelativeHeader(this, this, URLICON );
            layoutTop.addView(relativeHeader);
            layoutFooter = (RelativeLayout) findViewById(R.id.main_layout_footer);
            main_img_close = (ImageView) findViewById(R.id.main_img_close);
            main_img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == main_img_close) {
                        AnimatorSet set = new AnimatorSet();
                        set.addListener(new Animator.AnimatorListener() {

                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                layoutFooter.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }

                        });
                        float  dest = 1;
                        if (layoutFooter.getAlpha() >0) {
                            //System.out.println(layoutFooter.getAlpha());
                            dest = 0;
                        }
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(layoutFooter,
                                "alpha", dest);
                        animation1.setDuration(2000);
                        set.play(animation1);
                        set.start();
                    }
                }
            });
            main_img_close.setVisibility(View.GONE);


            if (ISAdView != null && ISAdView.equals("true")) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice(DataSetting.IMEI)
                        .build();

                Random rad = new Random();
                int ads = rad.nextInt(3);
                if (ads == 1 || ads == 2) {
                    main_layout_webview.setVisibility(View.GONE);
                    main_layout_adview.setVisibility(View.VISIBLE);

                    main_adView_isp.setVisibility(View.GONE);
                    main_adView.setVisibility(View.VISIBLE);

                    main_adView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            main_img_close.setVisibility(View.VISIBLE);
                        }
                    });
                    main_adView.loadAd(adRequest);
                } /* else if (ads == 2) {
                    main_layout_webview.setVisibility(View.GONE);
                    main_layout_adview.setVisibility(View.VISIBLE);

                    main_adView.setVisibility(View.GONE);
                    main_adView_isp.setVisibility(View.VISIBLE);

                    main_adView_isp.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            main_img_close.setVisibility(View.VISIBLE);
                        }
                    });
                    main_adView_isp.loadAd(adRequest);
                } */ else {
                    setAdsIsport();
                }


            } else {
                setAdsIsport();
            }

            SetInterstitialAd();
        } catch (Exception ex) {
            Log.e("StarSoccer", ex.getMessage());
        }

    }

    private void setAdsIsport() {
        try {
            main_adView_isp.setVisibility(View.GONE);
            main_adView.setVisibility(View.GONE);
            main_img_close.setVisibility(View.VISIBLE);
            main_layout_webview.setVisibility(View.VISIBLE);
            main_layout_webview.removeAllViews();
            WebView wView = new WebView(this);
            wView.setWebViewClient(new MyWebViewClient());
            wView.getSettings().setJavaScriptEnabled(true);
            wView.loadUrl(URLBANNER);

            main_layout_webview.addView(wView);
        } catch (Exception ex) {
            Log.e("Sportpool", ex.getMessage());
        }
    }

    @Override
    protected void onStop() {
        if(main_adView_isp != null)main_adView_isp.pause();
        if(main_adView != null)main_adView.pause();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        GetInterstitialAd();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void GetInterstitialAd() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    public void SetInterstitialAd() {
        interstitialAd = new PublisherInterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.admobFullId));

        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
    }
}



