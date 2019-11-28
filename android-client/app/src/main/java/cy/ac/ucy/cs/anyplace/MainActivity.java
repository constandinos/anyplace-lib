package cy.ac.ucy.cs.anyplace;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cy.ac.ucy.anyplace.AnyplacePost;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DEBUG";
    private ListView wifiList;
    private WifiManager wifiManager;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    private WifiReceiver receiverWifi;
    private final static String buid = "username_1373876832005";
    private final static String floor = "0";
    private final static String algorithm ="1";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "Turning WiFi ON...", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        final Button button = findViewById(R.id.button);
        final TextView tvOutput = findViewById(R.id.tvOutput);

        final Button buttonOpenMaps = findViewById(R.id.buttonOpenMaps);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            String str = "username_1373876832005";
                            String response;

                            //(MyApplication)getApplication()).anyplace =  new AnyplacePost("ap.cs.ucy.ac.cy", "443");
                           AnyplacePost client = new AnyplacePost("ap.cs.ucy.ac.cy", "443");
                            //client.setCacheDir(getApplicationContext().getFilesDir()); // save where to store stuff..
                           response = client.allBuildingFloors(str);
                            tvOutput.setText(response);

                            //new code




                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        buttonOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {

                            //REAL CODE

                            while (receiverWifi.isReady()==false){
                                //wait
                            }
                            String[] fingerprints = receiverWifi.getFingerprints();

                            AnyplacePost client = new AnyplacePost("ap-dev.cs.ucy.ac.cy", "443");
                            String responseString = client.estimatePosition(buid, floor, fingerprints, algorithm);
                            String[] response = responseString.split("[,:]");
                            for (int i=0; i<response.length; i++) {
                                if (response[i].equals("\"lat\"")) {
                                    MapsActivity.lan = Double.parseDouble(response[i+1].replace('"', ' '));
                                }
                                if (response[i].equals("\"long\"")) {
                                    MapsActivity.lon = Double.parseDouble(response[i+1].replace('"', ' '));
                                }
                            }

                            Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                            MainActivity.this.startActivity(myIntent);

/*
                            double latitude =  35.144413;
                            double longitude = 33.411476;

                            MapsActivity.lan = latitude;
                            MapsActivity.lon = longitude;

                            Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                            MainActivity.this.startActivity(myIntent);
*/

                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        receiverWifi = new WifiReceiver(wifiManager, wifiList);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiverWifi, intentFilter);
        getWifi();

    }
    private void getWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(MainActivity.this, "version> = marshmallow", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "location turned off", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
            } else {
                Toast.makeText(MainActivity.this, "location turned on", Toast.LENGTH_SHORT).show();
                wifiManager.startScan();
            }
        } else {
            Toast.makeText(MainActivity.this, "scanning", Toast.LENGTH_SHORT).show();
            wifiManager.startScan();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverWifi);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
                    wifiManager.startScan();
                } else {
                    Toast.makeText(MainActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }
}
