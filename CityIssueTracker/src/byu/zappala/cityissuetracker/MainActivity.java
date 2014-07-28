 package byu.zappala.cityissuetracker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	/** Called when the user touches the button */
	public void handleViewIssues(View view) {
		Intent intent = new Intent(this, ViewIssuesActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user touches the button */
	public void handleReportIssue(View view) {
		Intent intent = new Intent(this, ReportIssueActivity.class);
		startActivity(intent);
	}
	
	
	/** Called when the user touches the button */
	public void handleGetServiceList(View view) {
		new RequestTask().execute("http://311.zappala.org/requests.xml", "GET_SERVICE_LIST");
	}
	
	/** Called when the user touches the button */
	public void handleGetServiceRequest(View view) {
		new RequestTask().execute("http://311.zappala.org/requests/1011.xml", "GET_SERVICE_REQUEST");
	}
	
	/** Called when the user touches the button */
	public void handlePostServiceRequest(View view) {
		new RequestTask().execute("http://311.zappala.org/requests.xml", "POST_SERVICE_REQUEST");
	}
	
	class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        
	        if(params[1] == "GET_SERVICE_LIST") {
	        	try {
	        		
	        		response = httpclient.execute(new HttpGet(params[0]));
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
	        else if(params[1] == "GET_SERVICE_REQUEST") {
	        	try {
	        		
	        		response = httpclient.execute(new HttpGet(params[0]));
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
	        else if(params[1] == "POST_SERVICE_REQUEST") {
	        	try {
	        		HttpPost httppost = new HttpPost(params[0]);
	        		
	        		// Request parameters and other properties.
	        		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	        	
	        		parameters.add(new BasicNameValuePair("api_key", "0CN&oSBXngiGRCo!&u$4"));
	        		parameters.add(new BasicNameValuePair("service_code", "001"));
	        		parameters.add(new BasicNameValuePair("attribute[external_id]", "1111"));
	        		parameters.add(new BasicNameValuePair("lat", "39.756954"));
	        		parameters.add(new BasicNameValuePair("long", "-125.40473"));
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
	}

}
