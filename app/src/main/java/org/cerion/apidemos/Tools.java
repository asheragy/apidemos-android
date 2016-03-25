package org.cerion.apidemos;

import java.util.Random;

public class Tools {


    public static void sleep(int seconds)
    {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final Random mRand = new Random();
    public static int getRandomInteger() {
        return mRand.nextInt(100);
    }


}
