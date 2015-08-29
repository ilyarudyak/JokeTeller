package com.ilyarudyak.android.joketeller;

import android.test.AndroidTestCase;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * This AsyncTask test is from here:
 * http://marksunghunpark.blogspot.ru/2015/05/how-to-test-asynctask-in-android.html
 * */
public class JokeAsyncTaskTest extends AndroidTestCase {

    private static final String TAG = JokeAsyncTaskTest.class.getSimpleName();

    private String mJokeStr;
    private Exception mError;
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testJokeAsyncTask() throws Throwable {

        JokeAsyncTask jokeTask = new JokeAsyncTask(mContext);
        jokeTask.setListener(new JokeAsyncTask.JsonGetTaskListener() {
            @Override
            public void onComplete(String jokeStr, Exception e) {
                mJokeStr = jokeStr;
                mError = e;
                signal.countDown();
            }
        }).execute();
        signal.await();

        Log.d(TAG, mJokeStr);
        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJokeStr));

    }

}