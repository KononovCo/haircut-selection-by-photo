package com.kononovco.haircutselectionbyphoto;

import android.app.Activity;
import androidx.annotation.*;
import com.yandex.mobile.ads.common.*;
import com.yandex.mobile.ads.interstitial.*;

public class Interstitial implements Ads {

    private final Activity activity;

    private static InterstitialAd ad;
    private InterstitialAdLoader loader;

    public Interstitial(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String getAdUnitId() {
        return "R-M-14079071-2";
    }

    @Override
    public void close() {
        if (ad != null) {
            ad.setAdEventListener(null);
            ad = null;
        }

        if (loader != null) {
            loader.setAdLoadListener(null);
            loader = null;
        }
    }

    @Override
    public void load() {
        loader = new InterstitialAdLoader(activity);

        loader.setAdLoadListener(new InterstitialAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                ad = interstitialAd;
                addListener();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                close();
            }
        });

        loader.loadAd(new AdRequestConfiguration.Builder(getAdUnitId()).build());
    }

    @Override
    public void addListener() {
        if (ad != null) {
            ad.setAdEventListener(new InterstitialAdEventListener() {
                @Override
                public void onAdShown() {}

                @Override
                public void onAdFailedToShow(@NonNull AdError adError) {
                    close();
                }

                @Override
                public void onAdDismissed() {
                    close();
                    load();
                }

                @Override
                public void onAdClicked() {}

                @Override
                public void onAdImpression(@Nullable ImpressionData impressionData) {}
            });
        }
    }

    public void show() {
        if (ad != null) {
            ad.show(activity);
        }
    }
}