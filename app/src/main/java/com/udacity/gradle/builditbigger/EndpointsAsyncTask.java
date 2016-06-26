package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.kathleenbenavides.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by kathleenbenavides on 6/25/16.
 * Created following instructions from the following:
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private EndpointsTaskListener taskListener;

    public interface EndpointsTaskListener {
        public void onFinished(String joke);
    }

    public EndpointsAsyncTask(EndpointsTaskListener listener) {
        this.taskListener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                    request.setDisableGZipContent(true);
                }
            });
            myApiService = builder.build();
        }

        try {
            return myApiService.getMyJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(this.taskListener != null) {
            //Let the listener know it finished and pass string
            this.taskListener.onFinished(s);
        }
    }
}
