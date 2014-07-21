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
import byu.zappala.cityissuetracker.model.ServiceRequest;
import byu.zappala.cityissuetracker.utils.ServiceRequestXMLParser;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class ViewIssuesActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_issues);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		new RequestTask().execute("http://311.zappala.org/requests.xml", "GET_SERVICE_LIST");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_issues, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_view_issues,
					container, false);
			return rootView;
		}
	}
	
	class RequestTask extends AsyncTask<String, String, String>{

		List<ServiceRequest> serviceRequests = null;
		
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        ServiceRequestXMLParser parser = new ServiceRequestXMLParser();
	        
	        if(params[1] == "GET_SERVICE_LIST") {
	        	try {
	        		
	        		
	        		
	        		response = httpclient.execute(new HttpGet(params[0]));
	        		StatusLine statusLine = response.getStatusLine();
	        		
        			serviceRequests = parser.parseServiceRequestList(response.getEntity().getContent());
					
	        		if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        		} else{
	        		}
	        	} catch (ClientProtocolException e) {
	        		e.printStackTrace();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	} catch (XmlPullParserException e) {
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
	        tv.setText(serviceRequests.get(0).toString());
	        
	    }
	}
	
	

}
