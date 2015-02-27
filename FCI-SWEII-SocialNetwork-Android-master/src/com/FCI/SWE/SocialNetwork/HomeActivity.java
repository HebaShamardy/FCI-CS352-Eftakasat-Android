package com.FCI.SWE.SocialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {

	TextView helloTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bundle extras = getIntent().getExtras();
		String status = extras.getString("status");
		String name = "",welcome="Hello";
		
		if(extras.containsKey("name")){
			name = extras.getString("name");
			welcome = "Welcome " + name;
		}
		helloTextView = (TextView) findViewById(R.id.helloText);
		String text = status + " ... " + welcome;
		helloTextView.setText(text);
	}

}
