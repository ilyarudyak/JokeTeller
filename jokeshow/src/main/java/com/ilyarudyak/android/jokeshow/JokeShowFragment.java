package com.ilyarudyak.android.jokeshow;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokeShowFragment extends Fragment {

    public static final String JOKE = "com.ilyarudyak.android.jokeshow.JOKE";

    public JokeShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joke, container, false);

        TextView jokeTextView = (TextView) rootView.findViewById(R.id.joke_text_view);
        String jokeStr = getActivity().getIntent().getStringExtra(JOKE);
        jokeTextView.setText(jokeStr);

        return rootView;
    }
}
