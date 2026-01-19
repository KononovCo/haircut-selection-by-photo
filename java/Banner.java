package com.kononovco.haircutselectionbyphoto;

import android.app.Activity;
import android.view.ViewGroup;
import android.util.DisplayMetrics;
import androidx.annotation.*;
import com.yandex.mobile.ads.common.*;
import com.yandex.mobile.ads.banner.*;

public class Banner implements Ads {

    private final Activity activity;
    private static BannerAdView ad;

    public Banner(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String getAdUnitId() {
        return "R-M-14079071-1";
    }

    @Override
    public void close() {
        if (ad != null && activity.isDestroyed()) {
            ad.destroy();
            ad = null;
        }
    }

    @Override
    public void load() {
        ad = new BannerAdView(activity);

        ad.setAdUnitId(getAdUnitId());
        ad.setAdSize(getAdSize());

        addListener();
        ad.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void addListener() {
        if (ad != null) {
            ad.setBannerAdEventListener(new BannerAdEventListener() {
                @Override
                public void onAdLoaded() {
                    close();
                }

                @Override
                public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                    close();
                }

                @Override
                public void onAdClicked() {}

                @Override
                public void onLeftApplication() {}

                @Override
                public void onReturnedToApplication() {}

                @Override
                public void onImpression(@Nullable ImpressionData impressionData) {}
            });
        }
    }

    private BannerAdSize getAdSize() {
        DisplayMetrics display = activity.getResources().getDisplayMetrics();
        int width = (int) (display.widthPixels / display.density);

        return BannerAdSize.stickySize(activity, width);
    }

    public void add(ViewGroup group) {
        if (ad != null) {
            ViewGroup parent = (ViewGroup) ad.getParent();

            if (parent != null) {
                parent.removeView(ad);
            }

            group.addView(ad);
        }
    }
}