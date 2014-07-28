package byu.zappala.cityissuetracker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParserException;

import byu.zappala.cityissuetracker.MainActivity.RequestTask;
import byu.zappala.cityissuetracker.utils.ServiceRequestXMLParser;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class ReportIssueActivity extends ActionBarActivity {
	
	double latitude = 0;
	double longitude = 0;
	
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_issue);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		// Acquire a reference to the system Location Manager
	    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    
		// Define a listener that responds to location updates
	    LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	         	latitude = location.getLatitude();
	         	longitude = location.getLongitude();
	         	TextView latView = (TextView) findViewById(R.id.textView2);
	        	TextView longView = (TextView) findViewById(R.id.textView3);
	        	latView.setText("Latitude: " + Double.toString(latitude));
	        	longView.setText("Longitude: " + Double.toString(longitude));
	        }

	        public void onStatusChanged(String provider, int status, Bundle extras) {}

	        public void onProviderEnabled(String provider) {}

	        public void onProviderDisabled(String provider) {}

	    };
	    
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
		//new RequestTask(this).execute("http://311.zappala.org/requests.xml", "POST_SERVICE_REQUEST");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_issue, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_report_issue,
					container, false);
			return rootView;
		}
	}
	
	class RequestTask extends AsyncTask<String, String, String>{
		private Context context;
		
	    public RequestTask (Context context){
	         this.context = context;
	    }
		
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	     
	        if(params[1] == "POST_SERVICE_REQUEST") {
	        	try {
	        		HttpPost httppost = new HttpPost(params[0]);
	        		
	        		// Request parameters and other properties.
	        		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	        	
	        		parameters.add(new BasicNameValuePair("api_key", ConfigConstants.API_KEY));
	        		parameters.add(new BasicNameValuePair("service_code", "001"));
	        		parameters.add(new BasicNameValuePair("attribute[external_id]", "1111"));
	        		parameters.add(new BasicNameValuePair("lat", Double.toString(latitude)));
	        		parameters.add(new BasicNameValuePair("long", Double.toString(longitude)));
	        		parameters.add(new BasicNameValuePair("description", "Testing Post request"));
	        		httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
	        		
	        		response = httpclient.execute(httppost);
	        		StatusLine statusLine = response.getStatusLine();
	        		ByteArrayOutputStream out = new ByteArrayOutputStream();
	        		response.getEntity().writeTo(out);
	        		out.close();
	        		responseString = out.toString();
	        		
	        		if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        		
	        		} else{ 
	        			
	        		}
	        	} catch (ClientProtocolException e) {
	        		e.printStackTrace();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	}
	        }
	        return responseString;
		}
	
		@Override
	    protected void onPostExecute(String result) {
			 super.onPostExecute(result);
		        //Do anything with response..
		        TextView tv = (TextView)findViewById(R.id.textView1);
		        tv.setText(result);
	        
	    }
	}

}
