package org.cerion.apidemos;

import java.util.ArrayList;
import java.util.List;


public class ListData {

    public int mInt;
    public String mText;

    public ListData(int i)
    {
        mInt = i;
        mText = "Number " + i;
    }

    public static List<ListData> getList(int size) {
        List<ListData> result = new ArrayList<>(size);
        for(int i = 1; i <= size; i++) {
            ListData listData = new ListData(i);
            result.add(listData);
        }

        return result;
    }

    public static List<String> getStrings(int size) {
        List<String> result = new ArrayList<>(size);
        for(int i = 1; i <= size; i++) {
            result.add("Number " + i);
        }

        return result;
    }

    @Override
    public String toString() {
        return mText;
    }
}
