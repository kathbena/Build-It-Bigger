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

import gradle.kathleenbenavides.com.jokeLibrary.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String theJoke;
    ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button jokeButton = (Button) root.findViewById(R.id.jokeButton);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        //Instantiate java joke library and get joke
        Jokester joke = new Jokester();
        theJoke = joke.getJoke();

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show progress spinner then kick off async task
                progressBar.setVisibility(View.VISIBLE);
                kickOffTask();
            }
        });

        return root;
    }

    public void kickOffTask(){
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
