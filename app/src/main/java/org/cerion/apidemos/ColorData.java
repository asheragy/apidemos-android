package org.cerion.apidemos;


import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

public class ColorData {

    private int r;
    private int g;
    private int b;

    private static final Random mRand = new Random();

    public ColorData() {
        r = mRand.nextInt(256);
        g = mRand.nextInt(256);
        b = mRand.nextInt(256);
    }

    public int getColor() {
        return Color.rgb(r,g,b);
    }

    @Override
    public String toString() {
        return String.format("#%02x%02x%02x", r, g, b).toUpperCase();
    }

    public Bitmap getBitmap(int width, int height) {
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        image.eraseColor(getColor());
        return image;
    }
}
