package com.ydnm4528.clientapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class GoogleAds {

    private InterstitialAd mInterstitialAd;
    public InterstitialAd loadInterstitialAds(Context context)
    {
        MobileAds.initialize(context, context.getString(R.string.App_ID));
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        return mInterstitialAd;
    }


    AdView adView1, adView2;
    FrameLayout container1, container2;


    public void loadAdVerticalAds(View myview, Context context, Activity activity)
    {
        MobileAds.initialize(context, context.getString(R.string.App_ID));

        adView1 = new AdView(context);
        adView2 = new AdView(context);

        container1 = myview.findViewById(R.id.container1);
        container2 = myview.findViewById(R.id.container2);

        adView1.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView2.setAdUnitId(context.getString(R.string.banner_unit_id));

        adView1.setAdSize(getAdSize(activity, context));
        adView2.setAdSize(getAdSize(activity, context));

        adView1.loadAd(new AdRequest.Builder().build());
        adView2.loadAd(new AdRequest.Builder().build());

        container1.addView(adView1);
        container2.addView(adView2);


    }

    private AdSize getAdSize(Activity activity, Context context) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    private RewardedVideoAd mRewardedVideoAd;

    private void loadRewardedVideoAd(Context context) {
        mRewardedVideoAd.loadAd(context.getString(R.string.rewarded_id),
                new AdRequest.Builder().build());
    }

    public RewardedVideoAd loadRewardedVideoAds(final Context context)
    {
        MobileAds.initialize(context, context.getString(R.string.App_ID));
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        loadRewardedVideoAd(context);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

                loadRewardedVideoAd(context);

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        return mRewardedVideoAd;
    }

    FrameLayout adContainer, adContainer2, adContainer3;
    AdView adView3;
    AdRequest request1, request2, request3;

    public void loadThreeVerticalAds(View myview, Context context, Activity activity)
    {
        MobileAds.initialize(context, context.getString(R.string.App_ID));

        adView1 = new AdView(context);
        adView2 = new AdView(context);
        adView3 = new AdView(context);

        adContainer = myview.findViewById(R.id.adcontainer);
        adContainer2 = myview.findViewById(R.id.adcontainer2);
        adContainer3 = myview.findViewById(R.id.adcontainer3);

        request1 = new AdRequest.Builder().build();
        adView1.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView1.setAdSize(getAdSize(activity, context));
        adView1.loadAd(request1);
        adContainer.addView(adView1);

        request2 = new AdRequest.Builder().build();
        adView2.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView2.setAdSize(getAdSize(activity, context));
        adView2.loadAd(request2);
        adContainer2.addView(adView2);

        request3 = new AdRequest.Builder().build();
        adView3.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView3.setAdSize(getAdSize(activity, context));
        adView3.loadAd(request3);
        adContainer3.addView(adView3);


    }

    TemplateView largeTemplate, smallTemplate;



    public void loadNativeAds(final View myview , Context context)
    {
        MobileAds.initialize(context, context.getString(R.string.App_ID));

        AdLoader adLoader = new AdLoader.Builder(context, context.getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        ColorDrawable background = new ColorDrawable(Color.WHITE);

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        smallTemplate = myview.findViewById(R.id.smalltemplate);
                        smallTemplate.setStyles(styles);
                        smallTemplate.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());



        AdLoader adLoader2 = new AdLoader.Builder(context, context.getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        ColorDrawable background = new ColorDrawable(Color.WHITE);

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        largeTemplate = myview.findViewById(R.id.largetemplate);
                        largeTemplate.setStyles(styles);
                        largeTemplate.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader2.loadAd(new AdRequest.Builder().build());



    }
}
