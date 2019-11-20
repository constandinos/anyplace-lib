package cy.ac.ucy.cs.anyplace;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cy.ac.ucy.anyplace.AnyplacePost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        final EditText text = findViewById(R.id.textArea);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            String str = "username_1373876832005";
                            String response;
                            AnyplacePost client = new AnyplacePost("ap.cs.ucy.ac.cy", "443");
                            response = client.allBuildingFloors(str);
                            text.setText(response);

                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
