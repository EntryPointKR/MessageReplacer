package com.github.entrypointkr.messagereplacer.utils;

import java.util.concurrent.Callable;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class LibUsageChart extends Metrics.SimplePie {
    public static void addTo(Metrics metrics, String libName) {
        metrics.addCustomChart(new LibUsageChart(() -> libName));
    }

    public LibUsageChart(Callable<String> callable) {
        super("lib_usage", callable);
    }
}
