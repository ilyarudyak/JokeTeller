package com.ilyarudyak.android.joketeller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JokeGenerator {

    private List<String> mJokes;
    private Random mRandom;

    public JokeGenerator() {
        mJokes = new ArrayList<>(Arrays.asList("Einstein walks into a bar and says to the bartender,\n" +
                "\"I'll take a beer, and a beer for my friend, Heisenberg.\"\n" +
                "\n" +
                "The bartender looks around and asks, \"Is your friend here?\"\n" +
                "\"Well,\" says Einstein, \"he is and he isn't.\"",
                "Einstein's second greatest contribution: he said that when\n" +
                        "he was cooking soup and also wanted a soft-boiled egg, he would\n" +
                        "add the egg to the soup and thereby have one less pot to wash.",
                "A student recognizes Einstein in a train and asks, “Excuse me, professor,\n" +
                        "but does New York stop by this train?” There also a similar story in which\n" +
                        "Einstein himself asks the conductor, \"Excuse me, does New York stop\n" +
                        "by this train?\""));
        mRandom = new Random();
        mRandom.setSeed(0);
    }

    public String getJoke() {
        return mJokes.get(mRandom.nextInt(mJokes.size()));
    }

    // helper methods
    private void readFile(String fileName) {

        BufferedReader br = null;
        try {

            StringBuilder sb = new StringBuilder();
            String line;

            br = new BufferedReader(new FileReader(getFile(fileName)));

            while ((line = br.readLine()) != null) {
                if (!line.contains("#")) {
                    sb.append(line + "\n");
                } else {
                    if (sb.length() > 0) {
                        mJokes.add(sb.toString());
                        sb = new StringBuilder();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private File getFile(String fileName) {

        //Get file from resources folder
        URL url = getClass().getResource(fileName);
        File file = new File(url.getPath());
        return file;
    }


    public static void main(String[] args) {

        JokeGenerator jg = new JokeGenerator();
        System.out.println(jg.getJoke());
    }
}
