package com.vincent.mopubvungle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.VungleAdapterConfiguration;
import com.mopub.mobileads.VungleMediationConfiguration;
import com.vungle.warren.AdConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private MoPubView bannerContainer,mrecContainer;
    private MoPubInterstitial mInterstitial;
    private String adUnitId,interstitialPlacementId,bannerPlacementId,rewardPlacementId,mrecPlacementId;
    private VungleMediationConfiguration vungleMediationConfiguration;
    private static String TAG = "MoPubVungle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        MoPub.onCreate(this);
        initView();
    }

    public void initView() {
        Button initBtn = findViewById(R.id.init_btn);
        Button loadInterstitialBtn = findViewById(R.id.load_interstitial_btn);
        Button playInterstitialBtn = findViewById(R.id.play_interstitial_btn);
        Button loadRewardBtn = findViewById(R.id.load_reward_btn);
        Button playRewardBtn = findViewById(R.id.play_reward_btn);
        Button goBannerBtn = findViewById(R.id.go_banner_btn);

        bannerContainer = findViewById(R.id.banner_container);
        mrecContainer = findViewById(R.id.mrec_container);

        initBtn.setOnClickListener(this);
        loadInterstitialBtn.setOnClickListener(this);
        playInterstitialBtn.setOnClickListener(this);
        loadRewardBtn.setOnClickListener(this);
        playRewardBtn.setOnClickListener(this);
        goBannerBtn.setOnClickListener(this);

        adUnitId = context.getString(R.string.ad_unit_id);
        interstitialPlacementId = context.getString(R.string.interstitial_placement_id);
        rewardPlacementId = context.getString(R.string.reward_placement_id);
        mrecPlacementId = context.getString(R.string.mrec_placement_id);
        bannerPlacementId = context.getString(R.string.banner_placement_id);
    }

    public void init() {
        Map<String, String> vungleSettings = new HashMap<>();
//        vungleSettings.put("VNG_DEVICE_ID_OPT_OUT", "true");
        vungleSettings.put("appId", "56e0df40945f181a3c000012");
//        vungleSettings.put("VNG_MIN_SPACE_INIT", "99999999999");
//        vungleSettings.put("VNG_MIN_SPACE_LOAD_AD", "99999999999");

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(rewardPlacementId)
                .withMediatedNetworkConfiguration(VungleAdapterConfiguration.class.getName(), vungleSettings)
                .withLogLevel(MoPubLog.LogLevel.DEBUG)
                .withLegitimateInterestAllowed(false)
                .build();

        MoPub.initializeSdk(this, sdkConfiguration, new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                Log.d(TAG,"onInitializationFinished");
            }
        });

        mInterstitial = new MoPubInterstitial(MainActivity.this, interstitialPlacementId);
        vungleMediationConfiguration = new VungleMediationConfiguration.Builder()
        .withAutoRotate(AdConfig.LANDSCAPE)
        .withStartMuted(true)
        .withOrdinalViewCount(10)
        .withUserId("IE-Vincent")
        .withCancelDialogBody("CUSTOM_BODY")
        .withCancelDialogCloseButton("CUSTOM_CLOSE")
        .withCancelDialogKeepWatchingButton("CUSTOM_KEEPWATCHING")
        .withCancelDialogTitle("CUSTOM_TITLE")
        .build();
        mInterstitial.setLocalExtras(vungleMediationConfiguration.getExtrasMap());

        mInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                Log.d(TAG,"onInterstitialLoaded");
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                Log.d(TAG,"onInterstitialFailed");
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {
                Log.d(TAG,"onInterstitialShown");
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {
                Log.d(TAG,"onInterstitialClicked");
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                Log.d(TAG,"onInterstitialDismissed");
            }
        });

        MoPubRewardedVideos.setRewardedVideoListener(new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
                Log.d(TAG,"onRewardedVideoLoadSuccess");
            }

            @Override
            public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                Log.d(TAG,"onRewardedVideoLoadFailure");
            }

            @Override
            public void onRewardedVideoStarted(@NonNull String adUnitId) {
                Log.d(TAG,"onRewardedVideoStarted");
            }

            @Override
            public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                Log.d(TAG,"onRewardedVideoPlaybackError");
            }

            @Override
            public void onRewardedVideoClicked(@NonNull String adUnitId) {
                Log.d(TAG,"onRewardedVideoClicked");
            }

            @Override
            public void onRewardedVideoClosed(@NonNull String adUnitId) {
                Log.d(TAG,"onRewardedVideoClosed");
            }

            @Override
            public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
                Log.d(TAG,"onRewardedVideoCompleted");
            }
        });

        MoPubView.BannerAdListener bannerAdListener = new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(MoPubView banner) {
                Log.d(TAG,"onBannerLoaded" + banner.getAdUnitId());
            }

            @Override
            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                Log.d(TAG,"onBannerFailed" + banner.getAdUnitId() + "errorCode" + errorCode.getIntCode());
            }

            @Override
            public void onBannerClicked(MoPubView banner) {
                Log.d(TAG,"onBannerClicked" + banner.getAdUnitId());
            }

            @Override
            public void onBannerExpanded(MoPubView banner) {
                Log.d(TAG,"onBannerExpanded" + banner.getAdUnitId());
            }

            @Override
            public void onBannerCollapsed(MoPubView banner) {
                Log.d(TAG,"onBannerCollapsed" + banner.getAdUnitId());
            }
        };

        mrecContainer.setBannerAdListener(bannerAdListener);
        bannerContainer.setBannerAdListener(bannerAdListener);
    }

    public void loadInterstitial() {
        mInterstitial.load();
    }

    public void playInterstitial() {
        if (mInterstitial.isReady()) {
            mInterstitial.show();
        } else {
            Toast.makeText(context,"Interstitial is not ready",Toast.LENGTH_SHORT ).show();
        }
    }

    public void loadReward() {
        MoPubRewardedVideos.loadRewardedVideo(rewardPlacementId,vungleMediationConfiguration);
    }

    public void playReward() {
//        if (MoPubRewardedVideos.hasRewardedVideo(rewardPlacementId)) {
//            MoPubRewardedVideos.showRewardedVideo(rewardPlacementId);
//        } else {
//            Toast.makeText(context,"Reward is not ready",Toast.LENGTH_SHORT ).show();
//        }
        MoPubRewardedVideos.showRewardedVideo(rewardPlacementId);
    }

    public void goBanner() {
        Intent i = new Intent(MainActivity.this, BannerActivity.class);
        startActivity(i);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.init_btn:
                init();
                break;
            case R.id.load_interstitial_btn:
                loadInterstitial();
                break;
            case R.id.play_interstitial_btn:
                playInterstitial();
            case R.id.go_banner_btn:
                goBanner();
                break;
            case R.id.load_reward_btn:
                loadReward();
                break;
            case R.id.play_reward_btn:
                playReward();
                break;
        }
    }
}
