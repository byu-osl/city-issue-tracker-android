package byu.zappala.cityissuetracker;

import byu.zappala.cityissuetracker.model.ServiceRequest;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class ViewIssueActivity extends ActionBarActivity {

	static ServiceRequest serviceRequest = null;
	static Bitmap serviceRequestImage = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		serviceRequest = (ServiceRequest) getIntent().getSerializableExtra("service_request");
		serviceRequestImage = (Bitmap) getIntent().getParcelableExtra("service_request_image");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_issue);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		this.setTitle("Issue #" + serviceRequest.getServiceRequestID());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_issue, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_view_issue,
					container, false);
			
			TextView textView1 = (TextView) rootView.findViewById(R.id.textView1);
			textView1.setText(serviceRequest.toString());
			ImageView image = (ImageView) rootView.findViewById(R.id.imageView1);
			if(serviceRequestImage != null) {
				image.setImageBitmap(serviceRequestImage);
			}
			return rootView;
		}
	}

}
