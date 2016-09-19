package com.lxp.testdemo;

import java.util.List;

/**
 * Created by lenovo on 2016/9/17.
 */
public class Sandbox implements RectShape {
    private List<String> mGreetings;

    @Override
    public String toString() {
        return "Sandbox{" +
                "mGreetings=" + mGreetings +
                '}';
    }

    @Override
    public void hasAlpha() {
        int i = 3;
        i++;
    }

    public List<String> getGreetings() {
        return mGreetings;
    }

    public void setGreetings(List<String> greetings) {
        mGreetings = greetings;
    }

    public Sandbox(List<String> greetings) {

        mGreetings = greetings;
    }

    public Sandbox() {
    }
}
