package com.FCI.SWE.SocialNetwork;

import com.FCI.SWE.Controllers.Application;
import com.FCI.SWE.Controllers.UserController;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {

	TextView helloTextView;
	Button sendRequest,back,showRequests,signOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bundle extras = getIntent().getExtras();
		String status = extras.getString("status");
		String service = extras.getString("Service");
		String changedData = "",changedText="";
		
		sendRequest= (Button) findViewById(R.id.sendFriendRequest);
		helloTextView = (TextView) findViewById(R.id.helloText);
		back=(Button)findViewById(R.id.backButton);
		showRequests=(Button)findViewById(R.id.showRequests);
		signOut=(Button)findViewById(R.id.signout);
		if(service.equals("Login")){
			changedData = extras.getString("name");
				changedText = "Welcome " + changedData;
		}else if(service.equals("signup")){
			changedText="Hello";
			back.setVisibility(View.VISIBLE);
			sendRequest.setVisibility(View.INVISIBLE);
			showRequests.setVisibility(View.INVISIBLE);
			signOut.setVisibility(View.INVISIBLE);
			back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent MainIntent = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(MainIntent);
					
				}
			});
			
		}else if(service.equals("sendFriendRequest")){
			changedData = extras.getString("email");
			changedText = "request is sent to "+changedData;
		}
		
		
		String text = status + " ... " + changedText;
		helloTextView.setText(text);
		sendRequest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent sendFriendRequestIntent = new Intent(getApplicationContext(),FriendRequestActivity.class);
				startActivity(sendFriendRequestIntent);
			}
		});
		
		showRequests.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserController user = Application.getUserController();
				user.showFriendRequest();
				
			}
		});
		
		signOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserController user=Application.getUserController();
				user.signOut();
				Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(mainIntent);
				
			}
		});
	}

}
