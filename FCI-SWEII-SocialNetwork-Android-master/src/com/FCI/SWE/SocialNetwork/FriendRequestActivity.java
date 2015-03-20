package com.FCI.SWE.SocialNetwork;

import com.FCI.SWE.Controllers.UserController;
import com.FCI.SWE.Controllers.Application;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FriendRequestActivity extends Activity {

	Button send;
	EditText femail;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        send = (Button) findViewById(R.id.sendRequest);
        femail = (EditText) findViewById(R.id.femail);
        
        send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserController user= Application.getUserController();
				user.sendFriendRequest(femail.getText().toString());
				
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
