package com.isport_starsoccer.data;

import com.isport_starsoccer.BuildConfig;

public class DataURL {

    public static final String playstore = "market://details?id=com.isport_starsoccer";

    public static final String liveScore = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=livescore";
    public static final String ScoreResult = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=result";
    public static final String sportNews = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=getnews";
    public static final String liveScoreDetail = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=scoredetail";
    public static final String program = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=program";
    public static final String matchAnalyse = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=analysis";
    public static final String listMenu = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=menulist";
    public static final String table = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=leaguetable";
    public static final String clip = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=clip";
    public static final String smsservice = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=smsservice";
    public static final String hot = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=hot";
    public static final String event = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=event";
    public static final String notification = "http://wap.isport.co.th/isportws/starsoccer.aspx?ap=StarSoccer&pn=notification";

    // Active App
    public static final String GetOTP = "http://wap.isport.co.th/isportws/isportSub.aspx?ap=StarSoccer&pn=otp";
    public static final String SubmitOTP = "http://wap.isport.co.th/isportws/isportSub.aspx?ap=StarSoccer&pn=smo";
    // GCM
    public static final String SubmitToken = "http://wap.isport.co.th/isportws/isportgcm.aspx?ap=StarSoccer&pn=gcm&vc=" + String.valueOf(BuildConfig.VERSION_CODE) + "&vn=" + String.valueOf(BuildConfig.VERSION_NAME);
}
