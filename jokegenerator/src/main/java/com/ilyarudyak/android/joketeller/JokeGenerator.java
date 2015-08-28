package com.ilyarudyak.android.joketeller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JokeGenerator {

    private List<String> mJokes;

    public JokeGenerator() {
        mJokes = new ArrayList<>(Arrays.asList("it's a new joke"));
    }

    public String getJoke() {
        return mJokes.get(0);
    }
}
