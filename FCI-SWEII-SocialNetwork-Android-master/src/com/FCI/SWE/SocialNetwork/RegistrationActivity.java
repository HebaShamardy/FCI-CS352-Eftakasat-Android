package com.FCI.SWE.SocialNetwork;

import com.FCI.SWE.Controllers.Application;
import com.FCI.SWE.Controllers.UserController;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity implements OnClickListener {

	EditText userNameEditText;
	EditText userEmailEditText;
	EditText passwordEditText;
	Button registrationButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		userNameEditText = (EditText) findViewById(R.id.username_registration);
		userEmailEditText = (EditText) findViewById(R.id.email_registration);
		passwordEditText = (EditText) findViewById(R.id.password_registration);
		registrationButton = (Button) findViewById(R.id.RegistrationButton);
		registrationButton.setOnClickListener(this);
	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		UserController controller = Application.getUserController();
		controller.signUp(userNameEditText.getText().toString(), userEmailEditText.getText().toString(), passwordEditText
						.getText().toString());
		
	}


}
