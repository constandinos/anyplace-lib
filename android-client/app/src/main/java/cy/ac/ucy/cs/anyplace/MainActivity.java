package cy.ac.ucy.cs.anyplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cy.ac.ucy.anyplace.AnyplacePost;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MyApplication.TAG + "_" + MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        SimpleWifiManager.SetContext(getApplicationContext());
        SimpleWifiManager wifiManager = SimpleWifiManager.getInstance();
        wifiManager.startScan();
       wifiManager.registerScan();
        Log.d(TAG,"TEST: " + wifiManager.getIsScanning());
        Log.d(TAG, "RESULTS: " + wifiManager.getScanResults().size());


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

                            //((MyApplication)getApplication()).anyplace =  new AnyplacePost("ap.cs.ucy.ac.cy", "443");


                            AnyplacePost client = new AnyplacePost("ap.cs.ucy.ac.cy", "443");
                            // this can also be put in constructor..
                            //client.setCacheDir(getApplicationContext().getFilesDir()); // save where to store stuff..
                            response = client.allBuildingFloors(str);
                            tvOutput.setText(response);

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
                Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
