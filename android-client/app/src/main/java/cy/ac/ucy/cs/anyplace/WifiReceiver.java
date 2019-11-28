package cy.ac.ucy.cs.anyplace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ListView;
import java.util.List;

class WifiReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    private ListView wifiDeviceList;
    private String fingerprints[];
    private boolean ready = false;

    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            List<ScanResult> wifiList = wifiManager.getScanResults();
            fingerprints = new String[wifiList.size()];
            int i = 0;
            for (ScanResult scanResult : wifiList) {
                StringBuilder result = new StringBuilder();
                result.append("{\"bssid\":\"").append(scanResult.BSSID).append("\",\"rss\":").append(scanResult.level).append("}");
                fingerprints[i] = result.toString();
                i++;
            }
            this.ready = true;
        }
    }

    public String[] getFingerprints(){
        return fingerprints;
    }

    public boolean isReady(){
        return ready;
    }
}