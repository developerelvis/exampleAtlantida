package com.example.basicexample.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Connectivity {
    public static String isConnected(Context context) {
        ConnectivityManager conma = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = null;
        if (conma != null)
            activeInfo = conma.getActiveNetworkInfo();

        if (activeInfo != null && activeInfo.isConnected())
            return activeInfo.getTypeName();

        return null;
    }

    public static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = false;
                        if (sAddr != null) {
                            isIPv4 = sAddr.indexOf(':')<0;
                        }

                        if (isIPv4) {
                                return sAddr;
                        } else {
                            int delim = 0; // drop ip6 zone suffix
                            if (sAddr != null) {
                                delim = sAddr.indexOf('%');
                            }
                            if (sAddr != null) {
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

}