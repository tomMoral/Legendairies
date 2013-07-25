package com.roadBlog.legendairies;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.w3c.dom.Text;

import android.R.string;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView t = null;
	
	public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      t =(TextView) findViewById(R.id.textView1);

      /* Use the LocationManager class to obtain GPS locations */
      LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

      LocationListener mlocListener = (new LocationListener(){
			public void onLocationChanged(Location location) {
				String display = "Lattitude: " + location.getLatitude();
				display += "\nLongitude: "+ location.getLongitude();
				display += "\nAccuracy: "+ location.getAccuracy();
				display += "\n" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
				t.setText(display);
				addLocation(location);
			}
			public void onProviderDisabled(String provider) {}
			public void onProviderEnabled(String provider) {}
			public void onStatusChanged(String provider, int status, Bundle extras) {} 
      });
      mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
      mlocManager.requestLocationUpdates( LocationManager.PASSIVE_PROVIDER, 0, 0, mlocListener);
      
      Button quit = (Button) findViewById(R.id.button2);
      
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }    
    
    public boolean isExternalStorageWritable() {
    	String state = Environment.getExternalStorageState();
    	if (Environment.MEDIA_MOUNTED.equals(state)) 
    		return true;
    	return false;
    }
    
    public File addLocation(Location loc){
    	File file = new File(Environment.getExternalStoragePublicDirectory(
    		Environment.DIRECTORY_DOWNLOADS) + File.separator +  
    		"Location.csv");
    	OutputStreamWriter out = null;
    	try {
    		 out = new OutputStreamWriter(new FileOutputStream(file));
    		 out.append(String.valueOf(loc.getLatitude()) + ',');
    		 out.append(String.valueOf(loc.getLongitude()) + ',');
    		 out.append(String.valueOf(loc.getAccuracy()) + '\n');
    		 out.close();
    	} catch( IOException e){
    		System.err.println("Caught IOException: " + e.getMessage());
    	}
    	return file;
    	
    	
    }
}
