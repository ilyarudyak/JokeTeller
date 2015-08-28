package com.ilyarudyak.android.joketeller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.ilyarudyak.android.jokeshow.JokeShowActivity;
import com.ilyarudyak.android.jokeshow.JokeShowFragment;
import com.ilyarudyak.joketeller.jokeapi.jokeApi.JokeApi;

import java.io.IOException;

public class JokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static JokeApi mJokeApiService = null;
    private Context context;

    JokeAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(mJokeApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            mJokeApiService = builder.build();
        }

        try {
            return mJokeApiService.getJoke(1L).execute().get("joke").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no jokes for Today";
    }

    @Override
    protected void onPostExecute(String jokeStr) {

        // start library activity and send a joke via intent
        Intent jokeIntent = new Intent(context, JokeShowActivity.class);
        jokeIntent.putExtra(JokeShowFragment.JOKE, jokeStr);
        context.startActivity(jokeIntent);

//        Toast.makeText(context, jokeStr, Toast.LENGTH_LONG).show();
    }
}