package com.vincent.mopubvungle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

public class BannerActivity extends AppCompatActivity implements View.OnClickListener {

    private MoPubView bannerContainer, shortBannerContainer, longBannerContainer, mrecContainer;
    private String mrecPlacementId,bannerPlacementId, shortBannerPlacementId, longBannerPlacementId;
    private String TAG = "MoPubVungle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();

        mrecPlacementId = this.getString(R.string.mrec_placement_id);
        bannerPlacementId = this.getString(R.string.banner_placement_id);
        shortBannerPlacementId = this.getString(R.string.short_banner_placement_id);
        longBannerPlacementId = this.getString(R.string.long_banner_placement_id);
    }

    private void initView() {

        Button loadBannerBtn = findViewById(R.id.load_banner_btn);
        Button loadShortBannerBtn = findViewById(R.id.load_short_banner_btn);
        Button loadLongBannerBtn = findViewById(R.id.load_long_banner_btn);
        Button loadMrecBtn = findViewById(R.id.load_mrec_btn);

        loadBannerBtn.setOnClickListener(this);
        loadShortBannerBtn.setOnClickListener(this);
        loadLongBannerBtn.setOnClickListener(this);
        loadMrecBtn.setOnClickListener(this);

        bannerContainer = findViewById(R.id.banner_container);
        mrecContainer = findViewById(R.id.mrec_container);
        shortBannerContainer = findViewById(R.id.short_banner_container);
        longBannerContainer = findViewById(R.id.long_banner_container);

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
        shortBannerContainer.setBannerAdListener(bannerAdListener);
        longBannerContainer.setBannerAdListener(bannerAdListener);
    }

    public void loadBannerAd(String placementId, MoPubView.MoPubAdSize size, MoPubView container) {
        container.setAdUnitId(placementId);
        container.setAdSize(size);
        container.loadAd();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_mrec_btn:
                loadBannerAd(mrecPlacementId, MoPubView.MoPubAdSize.HEIGHT_250, mrecContainer);
                break;
            case R.id.load_banner_btn:
                loadBannerAd(bannerPlacementId, MoPubView.MoPubAdSize.HEIGHT_50, bannerContainer);
                break;
            case R.id.load_short_banner_btn:
                loadBannerAd(shortBannerPlacementId, MoPubView.MoPubAdSize.HEIGHT_50, shortBannerContainer);
                break;
            case R.id.load_long_banner_btn:
                loadBannerAd(longBannerPlacementId, MoPubView.MoPubAdSize.HEIGHT_90, longBannerContainer);
                break;
        }
    }
}
