package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by kathleenbenavides on 6/26/16.
 */
public class GetJokeTest extends AndroidTestCase {

    public void testJokeRetrieved() {
        final CountDownLatch signal = new CountDownLatch(1);

        EndpointsAsyncTask testTask = new EndpointsAsyncTask(new EndpointsAsyncTask.EndpointsTaskListener() {
            @Override
            public void onFinished(String joke) {
                assertTrue(joke != null && joke.length() != 0);
                signal.countDown();
            }
        });
        testTask.execute();
        try {
            signal.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            fail(e.getMessage());
        }
    }

}
