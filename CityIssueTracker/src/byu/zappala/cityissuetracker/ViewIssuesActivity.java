package byu.zappala.cityissuetracker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class ViewIssuesActivity extends ActionBarActivity {

	List<ServiceRequest> serviceRequests = null;
    Map<Integer, Bitmap> imagesToDisplay = new HashMap<Integer, Bitmap>(); 
	ProgressDialog dialog = null;
	ServiceRequestArrayAdapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_issues);
		this.setTitle("View Issues");
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		new RequestTask(this).execute("http://311.zappala.org/requests.xml", "GET_SERVICE_LIST");
		dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
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
		private Context context;
		
	    public RequestTask (Context context){
	         this.context = context;
	    }
		
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
	        final ListView lv = (ListView)findViewById(R.id.listView1);
	        ServiceRequestArrayAdapter adapter = new ServiceRequestArrayAdapter(this.context,
	                android.R.layout.simple_list_item_2, serviceRequests);
	        lv.setAdapter(adapter);
	        dialog.dismiss();
	    }
	}
	
	class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
		ImageView image;
		Integer position;
		List<ServiceRequest> values;
		DownloadImageTask(ImageView image, Integer position, List<ServiceRequest> values){
			this.image = image;
			this.position = position;
			this.values = values;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String imageURL = params[0];
			Bitmap imageBitmap = null;
			
			try {
				InputStream input = (InputStream) new URL(imageURL).getContent();
				imageBitmap = BitmapFactory.decodeStream(input);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return imageBitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(result != null) {
				//Matrix matrix = new Matrix();
				//matrix.postRotate(90);
				//Bitmap rotatedBitmap = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
				Bitmap scaled = Bitmap.createScaledBitmap(result, 64, 64, true);
				image.setImageBitmap(scaled); 
				imagesToDisplay.put(position, result);
				values.get(position).setImageDownloaded(true);
			}
		  }
	}
	
	  private class ServiceRequestArrayAdapter extends ArrayAdapter<ServiceRequest> {
		    private final Context context;
		    private final List<ServiceRequest> values;
		   
		    public ServiceRequestArrayAdapter(Context context, int textViewResourceId,
		        List<ServiceRequest> objects) {
		      super(context, textViewResourceId, objects);
		      this.context = context;
		      this.values = objects;
		    }
		    
		    /** Called when the user touches the button 
		     * @param serviceRequest2 */
			public void handleViewIssue(View view, ServiceRequest serviceRequest) {
				Intent intent = new Intent(context, ViewIssueActivity.class);
				intent.putExtra("service_request", serviceRequest);
				if(imagesToDisplay.get(serviceRequest.getPosition()) != null) {
					//intent.putExtra("service_request_image", imagesToDisplay.get(serviceRequest.getPosition()));
				}
				startActivity(intent);
			}
		    
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		      View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		  	  final ServiceRequest serviceRequest = values.get(position);
		  	  serviceRequest.setPosition(position);
		      rowView.setOnClickListener(new OnClickListener() {

		    	    @Override
		    	    public void onClick(View v) {
		    	    	handleViewIssue(v, serviceRequest);
		    	    }
		    	});
		      
		      LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.linearLayout1);
		      TextView textView1 = (TextView) linearLayout.findViewById(R.id.textView1);
		      TextView textView2 = (TextView) linearLayout.findViewById(R.id.textView2);
		      ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
  
		      String description = values.get(position).getDescription();
		      if(description == "") {
		    	  description = "No Description Available";
		      }
		     
		      String address = values.get(position).getAddress();
		      if(address == "") {
		    	  address = "No Address Available";
		      }
		      
		      String mediaURL = values.get(position).getMediaURL();
		      if(!mediaURL.equals("") && !values.get(position).isImageDownloaded()) {
		    	  new DownloadImageTask(imageView, position, values).execute(mediaURL);
		      } else if(!mediaURL.equals("")){
		    	 Bitmap scaled = Bitmap.createScaledBitmap(imagesToDisplay.get(position), 64, 64, true);
		    	 imageView.setImageBitmap(scaled);
		      }
		      
		      textView1.setText(description);
		      textView2.setText(address);
		      return rowView;
		    }
		    
		   

		  }
	  
	    
	

}
