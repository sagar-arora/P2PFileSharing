package com.github.arorasagar;

import java.util.Comparator;

public class DownloadRate implements Comparable<DownloadRate>{

    public int bytesDownload;
    public long timeTaken;
    double downloadRate;

    public DownloadRate(double downloadRate) {
        this.downloadRate = downloadRate;
    }
    public DownloadRate(int bytesDownload, long timeTakenInMillis) {
        this.bytesDownload = bytesDownload;
        this.timeTaken = timeTakenInMillis;
        this.downloadRate = (double) ((long)bytesDownload / timeTaken) * 1000;
    }

    public double getDownloadRate() {
        return downloadRate;
    }

    @Override
    public int compareTo(DownloadRate o) {
        return Double.compare(downloadRate, o.getDownloadRate());
    }

    public static int compare(DownloadRate o1, DownloadRate o2) {
        return Double.compare(o1.downloadRate, o2.downloadRate);
    }
}
