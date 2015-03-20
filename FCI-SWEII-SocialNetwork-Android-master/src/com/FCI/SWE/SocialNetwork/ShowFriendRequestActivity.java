package com.FCI.SWE.SocialNetwork;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.FCI.SWE.Controllers.UserController;
import com.FCI.SWE.Controllers.Application;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ShowFriendRequestActivity extends Activity {

	Button accept,decline;
	TextView requests;
	EditText friendEmail;
	Spinner requestsListSpinner;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_friend_request);
        
        accept=(Button)findViewById(R.id.accept);
        decline=(Button)findViewById(R.id.deny);
        requests=(TextView)findViewById(R.id.requests);
        //friendEmail=(EditText)findViewById(R.id.friendEmail);
        requestsListSpinner=(Spinner)findViewById(R.id.requestsList);
        
        Bundle extras = getIntent().getExtras();
		String status = extras.getString("status");
		String service = extras.getString("Service");
		String array = extras.getString("array");
		JSONArray requestArray;
		ArrayList<String> requestList=new ArrayList<String>();
		try {
			requestArray = new JSONArray(array);
			for(int i=0;i<requestArray.length();i++){
				JSONObject object=new JSONObject();
				object = (JSONObject)requestArray.get(i);
				String email = object.getString("email");
				requestList.add(email);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        requests.setText(status);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, requestList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        requestsListSpinner.setAdapter(adapter);
        

        accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				UserController user = Application.getUserController();
				user.acceptFriend(requestsListSpinner.getSelectedItem().toString());
				
			}
		});
        
        decline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				UserController user = Application.getUserController();
				user.declineFriend(requestsListSpinner.getSelectedItem().toString());
			}
		});
        
    
    
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
