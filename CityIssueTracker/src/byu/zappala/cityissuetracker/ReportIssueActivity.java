package byu.zappala.cityissuetracker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParserException;

import byu.zappala.cityissuetracker.MainActivity.RequestTask;
import byu.zappala.cityissuetracker.model.Service;
import byu.zappala.cityissuetracker.model.ServiceRequest;
import byu.zappala.cityissuetracker.utils.ServiceRequestXMLParser;
import byu.zappala.cityissuetracker.utils.ServiceXMLParser;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Build;
import android.provider.*;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;

public class ReportIssueActivity extends ActionBarActivity {
	
	double latitude = 0;
	double longitude = 0;
	EditText descriptionText;
	EditText firstNameText;
	EditText lastNameText;
	List<Service> services = null;
	Bitmap imageBitmap = null; 
	Uri imageUri = null;
	
	static final int REQUEST_IMAGE_CAPTURE = 1;

	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
	    savedInstanceState.putParcelable("image", imageBitmap);
	    savedInstanceState.putParcelable("imageUri", imageUri);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
	    imageBitmap = savedInstanceState.getParcelable("image");
	    imageUri = savedInstanceState.getParcelable("imageUri");
	    if(imageBitmap != null) {
			ImageView image = (ImageView)findViewById(R.id.imageView1);
		    image.setImageBitmap(imageBitmap);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_issue);
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);	    
	    
	    LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	    	    System.out.println("Reached Location Changer");
	         	latitude = location.getLatitude();
	         	longitude = location.getLongitude();
	         	TextView latView = (TextView) findViewById(R.id.textView2);
	        	TextView longView = (TextView) findViewById(R.id.textView3);
	        	try {
	        	latView.setText("Latitude: " + Double.toString(latitude));
	        	} catch(NullPointerException e) {
	        		e.printStackTrace();
	        	}
	        	try {
	        	longView.setText("Longitude: " + Double.toString(longitude));
	        	} catch(NullPointerException e) {
	        		e.printStackTrace();
	        	}
	        	
	        }

	        public void onStatusChanged(String provider, int status, Bundle extras) {}

	        public void onProviderEnabled(String provider) {}

	        public void onProviderDisabled(String provider) {}

	    };
	    
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
		new GetServicesTask(this).execute("http://311.zappala.org/services.xml", "GET_SERVICE_LIST");
		
	    
		
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        imageBitmap = (Bitmap) extras.get("data");
	        imageUri = data.getData();
	        ImageView image = (ImageView)findViewById(R.id.imageView1);
	        image.setImageBitmap(imageBitmap);
	    }
	}
	
	/** Called when the user touches the button */
	public void handleSubmitRequest(View view) {
		new PostRequestTask(this).execute("http://311.zappala.org/requests.xml", "POST_SERVICE_REQUEST");
	}
	
	/** Called when the user touches the button to take a picture*/
	public void handleTakePicture(View view) {
		takePicture();
	}

	/** Called when the user touches the button to take a picture*/
	public void handleSendPicture(View view) {
		sendPicture();
	}
	
	private void takePicture() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	private void sendPicture() {
		new PostImageTask(this).execute(getRealPathFromURI(this, imageUri));
	}
	
	public String getRealPathFromURI(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
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
			View rootView = inflater.inflate(R.layout.fragment_report_issue,
					container, false);
			return rootView;
		}
		
		
	}
	
	class PostRequestTask extends AsyncTask<String, String, String>{
		private Context context;
		
	    public PostRequestTask (Context context){
	         this.context = context;
	    }
		
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        ServiceXMLParser parser = new ServiceXMLParser();
	        if(params[1] == "POST_SERVICE_REQUEST") {
	        	try {
	        		HttpPost httppost = new HttpPost(params[0]);
	        		firstNameText = (EditText)findViewById(R.id.firstNameText);
	        		lastNameText = (EditText)findViewById(R.id.lastNameText);
	        		descriptionText = (EditText)findViewById(R.id.editText1);
	        		
	        		// Request parameters and other properties.
	        		List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	        		parameters.add(new BasicNameValuePair("api_key", ConfigConstants.API_KEY));
	        		
	        		Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
	        		Service selectedService = (Service) dropdown.getSelectedItem();
	        		parameters.add(new BasicNameValuePair("service_code", selectedService.getServiceCode()));
	        		
	        		parameters.add(new BasicNameValuePair("attribute[external_id]", "AN_" + UUID.randomUUID().toString()));
	        		parameters.add(new BasicNameValuePair("lat", Double.toString(latitude)));
	        		parameters.add(new BasicNameValuePair("long", Double.toString(longitude)));
	        		parameters.add(new BasicNameValuePair("first_name", firstNameText.getText().toString()));
	        		parameters.add(new BasicNameValuePair("last_name", lastNameText.getText().toString()));
	        		parameters.add(new BasicNameValuePair("description", descriptionText.getText().toString()));
	        		
	        		httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8")); 
	        		response = httpclient.execute(httppost);
	        		StatusLine statusLine = response.getStatusLine();
	        		ByteArrayOutputStream out = new ByteArrayOutputStream();
	        		response.getEntity().writeTo(out);
	        		out.close();
	        		responseString = out.toString();
	        		
	        		if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        			return "Successfully sent new Service Request";
	        		} else{ 
	        		    return "Error while Sending Service Request";
	        		}
	        	} catch (ClientProtocolException e) {
	        		e.printStackTrace();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	}
	        }
	        return "Error: not POST_SERVICE_REQUEST";
		}
	
		@Override
	    protected void onPostExecute(String result) {
			 super.onPostExecute(result);
			 AlertDialog.Builder builder = new AlertDialog.Builder(context);
			 builder.setMessage(result).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			 
	    }
	}
	
	class GetServicesTask extends AsyncTask<String, String, String>{
		private Context context;
		
	    public GetServicesTask (Context context){
	         this.context = context;
	    }
		
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        ServiceXMLParser parser = new ServiceXMLParser();
	
	        if(params[1] == "GET_SERVICE_LIST") {
	        	try {
	        			
	        		response = httpclient.execute(new HttpGet(params[0]));
	        		StatusLine statusLine = response.getStatusLine();
	      
        			services = parser.parseServiceList(response.getEntity().getContent());
	        		 
	        		if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        		} else{
	        		}
	        	} catch (ClientProtocolException e) {
	        		e.printStackTrace();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        return responseString;
		}
	
		@Override
	    protected void onPostExecute(String result) {
			 super.onPostExecute(result);
			 Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
 			 ArrayAdapter<Service> adapter = new ArrayAdapter<Service>(this.context, android.R.layout.simple_spinner_item, services);
 			 dropdown.setAdapter(adapter);
	    }
	}
	
	class PostImageTask extends AsyncTask<String, String, String>{
		private Context context;
		
	    public PostImageTask (Context context){
	         this.context = context;
	    }
		
		@Override
		protected String doInBackground(String... params) {
			final String imgurUrl = "https://api.imgur.com/3/upload.xml";
			final String API_KEY = "384c5f1c52b8894";
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        try {
	        	HttpPost httppost = new HttpPost(imgurUrl);
	        	httppost.setHeader("Authorization", "Client-ID " + API_KEY);
	        	
	        	//ImageView image = (ImageView)findViewById(R.id.imageView1);
	        	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	        	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	        	builder.addPart("image", new FileBody(new File(params[0])));
	            httppost.setEntity(builder.build());
	       
	        	
	        	response = httpclient.execute(httppost);
	        	StatusLine statusLine = response.getStatusLine();
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
	        	response.getEntity().writeTo(out);
	        	out.close();
	        	responseString = out.toString();
	        	if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        		return "Successfully uploaded picture";
	        	} else{ 
	        		return "Error: Picture could not upload";
	        	}
	        	} catch (ClientProtocolException e) {
	        		e.printStackTrace();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	}
	        
	        return "Error: exception thrown";
		}
	
		@Override
	    protected void onPostExecute(String result) {
			 super.onPostExecute(result);
			 AlertDialog.Builder builder = new AlertDialog.Builder(context);
			 builder.setMessage(result).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			 
	    }
	}
	
}
