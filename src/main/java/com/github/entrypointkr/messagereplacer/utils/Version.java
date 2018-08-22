package com.github.entrypointkr.messagereplacer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JunHyeong on 2018-08-22
 */
public class Version {
    private static final Pattern PATTERN = Pattern.compile("(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?");

    private int major = 0;
    private int minor = 0;
    private int maintenance = 0;

    public Version(int major, int minor, int maintenance) {
        this.major = major;
        this.minor = minor;
        this.maintenance = maintenance;
    }

    public Version(String version) {
        Matcher matcher = PATTERN.matcher(version);
        if (matcher.find()) {
            String majorStr = matcher.group(1);
            String minorStr = matcher.group(2);
            String maintenance = matcher.group(3);

            if (majorStr != null)
                this.major = Integer.parseInt(majorStr);
            if (minorStr != null)
                this.minor = Integer.parseInt(minorStr);
            if (maintenance != null)
                this.maintenance = Integer.parseInt(maintenance);
        }
    }

    public boolean after(Version version) {
        return version != null && (major > version.major || minor > version.minor || maintenance > version.maintenance);
    }

    public boolean afterEquals(Version version) {
        return version != null && (major == version.major ? minor == version.minor ?
                maintenance >= version.maintenance : minor >= version.minor : major >= version.major);
    }

    public boolean before(Version version) {
        return version != null && (major < version.major || minor < version.minor || maintenance < version.maintenance);
    }

    public boolean beforeEquals(Version version) {
        return version != null && (major == version.major ? minor == version.minor ?
                maintenance <= version.maintenance : minor <= version.minor : major <= version.minor);
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getMaintenance() {
        return maintenance;
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s", major, minor, maintenance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (major != version.major) return false;
        if (minor != version.minor) return false;
        return maintenance == version.maintenance;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + maintenance;
        return result;
    }
}

