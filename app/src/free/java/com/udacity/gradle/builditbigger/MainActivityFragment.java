package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.Jokester;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import gradle.kathleenbenavides.com.jokeLibrary.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String theJoke;
    InterstitialAd interstitialAd;
    ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        Button jokeButton = (Button) root.findViewById(R.id.jokeButton);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //Full page interstitial ad
        //Created add following instructions at
        //https://developers.google.com/mobile-ads-sdk/docs/admob/android/interstitial
        interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //Show progress spinner then kick off async task
                progressBar.setVisibility(View.VISIBLE);
                kickOffJokeTask();
            }
        });
        //Call to load add
        loadInterstitial();
        //Instantiate java joke library and get joke
        Jokester joke = new Jokester();
        theJoke = joke.getJoke();

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interstitialAd.isLoaded()) {
                    //If ad is loaded - show ad
                    interstitialAd.show();
                } else {
                    //Ad was not loaded -- skip and kick off async task
                    progressBar.setVisibility(View.VISIBLE);
                    kickOffJokeTask();
                }
            }
        });

        return root;
    }

    private void loadInterstitial() {
        //Load full page interstitial ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
    }

    public void kickOffJokeTask(){
        //Start async task -- when completed launch joke intent
        EndpointsAsyncTask jokeTask = new EndpointsAsyncTask(new EndpointsAsyncTask.EndpointsTaskListener() {
            @Override
            public void onFinished(String joke) {
                launchJoke(joke);
            }
        });
        jokeTask.execute();
    }


    public void launchJoke(String joke){
        if(joke != null){
            Intent intent = new Intent(getActivity(), JokeActivity.class);
            intent.putExtra(getString(R.string.jokeId), joke);
            startActivity(intent);
            //Activity loaded - remove progress spinner
            progressBar.setVisibility(View.GONE);
        }
    }
}
