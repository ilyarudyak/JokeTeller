package com.ilyarudyak.android.joketeller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private ProgressBar mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinner = (ProgressBar)findViewById(R.id.progress_spinner);
        mSpinner.setVisibility(View.GONE);

        // add interstitial ad
        if (BuildConfig.FLAVOR.equals(MainActivityFragment.FREE)) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    fetchJokes();
                }
            });

            requestNewInterstitial();
        }
    }

    public void tellJoke(View view){

        if (BuildConfig.FLAVOR.equals(MainActivityFragment.FREE)) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                fetchJokes();
            }
        } else {
            fetchJokes();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // when we return from interstitial ad we use onResume()
        // but when we return from JokeShowActivity we use onStart()
        // so we can stop spinner only onStart()
        mSpinner.setVisibility(View.GONE);
    }

    // helper methods
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    private void fetchJokes() {
        mSpinner.setVisibility(View.VISIBLE);
        new JokeAsyncTask(this).execute();
    }



}
