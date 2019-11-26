package cy.ac.ucy.cs.anyplace;

import android.app.Application;
import android.content.Context;

import cy.ac.ucy.anyplace.AnyplacePost;


public class MyApplication extends Application{
    private static Context context;

    public static final String TAG = "AP_LIB";

    AnyplacePost anyplace = null;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}