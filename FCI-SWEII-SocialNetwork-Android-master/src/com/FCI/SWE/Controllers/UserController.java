package com.FCI.SWE.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.SocialNetwork.HomeActivity;
import com.FCI.SWE.SocialNetwork.ShowFriendRequestActivity;

public class UserController {

	private static UserEntity currentActiveUser;
	private static UserController userController;

	public static UserController getInstance() {
		if (userController == null)
			userController = new UserController();
		return userController;
	}

	private UserController() {

	}

	public void login(String email, String password) {
		
		new Connection().execute(
				"http://eftakasat-socialnetwork.appspot.com/rest/LoginService", email,
				password, "LoginService");
	}

	public void signUp(String userName, String email, String password) {
		new Connection().execute(
				"http://eftakasat-socialnetwork.appspot.com/rest/RegistrationService", userName,
				email, password, "RegistrationService");
	}
	
	public void signOut() {
		currentActiveUser=null;
	}
	
	
	public void sendFriendRequest(String femail) {
		String uemail=currentActiveUser.getEmail();
		new Connection().execute(
				"http://eftakasat-socialnetwork.appspot.com/rest/sendFriendRequest", uemail,
				femail, "sendFriendRequest");
	}
	
	public void showFriendRequest() {
		String uemail=currentActiveUser.getEmail();
		new Connection().execute(
				"http://eftakasat-socialnetwork.appspot.com/rest/showFriendRequests",uemail,
				"showFriendRequests");
	}
	
	public void acceptFriend(String femail) {
		String uemail=currentActiveUser.getEmail();
		new Connection().execute(
				"http://eftakasat-socialnetwork.appspot.com/rest/AddFriendService", uemail,
				femail, "AddFriendService");
	}
	
	public void declineFriend(String femail) {
		String uemail=currentActiveUser.getEmail();
		new Connection().execute(
				"http://eftakasat-socialnetwork.appspot.com/rest/denyFriendService", uemail,
				femail, "denyFriendService");
	}
	

	static private class Connection extends AsyncTask<String, String, String> {

		String serviceType;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL url;
			serviceType = params[params.length - 1];
			String urlParameters="";
			if (serviceType.equals("LoginService"))
				urlParameters = "email=" + params[1] + "&password=" + params[2];
			else if(serviceType.equals("RegistrationService"))
				urlParameters = "uname=" + params[1] + "&email=" + params[2]
						+ "&password=" + params[3];
			else if(serviceType.equals("sendFriendRequest"))
				urlParameters = "uemail=" + params[1] + "&femail=" + params[2];
			else if(serviceType.equals("showFriendRequests"))
				urlParameters = "uemail=" + params[1];
			else if(serviceType.equals("AddFriendService"))
				urlParameters = "uemail=" + params[1] + "&femail=" + params[2];
			else if(serviceType.equals("denyFriendService"))
				urlParameters = "uemail=" + params[1] + "&femail=" + params[2];

			HttpURLConnection connection;
			try {
				url = new URL(params[0]);

				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(60000); // 60 Seconds
				connection.setReadTimeout(60000); // 60 Seconds

				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				writer.write(urlParameters);
				writer.flush();
				String line, retJson = "";
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					retJson += line;
				}
				return retJson;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				
				
				
				if (serviceType.equals("LoginService")) {
					JSONObject object = new JSONObject(result);
					
					if(!object.has("Status") || object.getString("Status").equals("Failed")){
						Toast.makeText(Application.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
						return;
					}
					currentActiveUser = UserEntity.createLoginUser(result);
					
					Intent homeIntent = new Intent(Application.getAppContext(),
							HomeActivity.class);
					System.out.println("--- " + serviceType + "IN LOGIN " + object.getString("Status"));
					
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					/* here you should initialize user entity */
					homeIntent.putExtra("Service", "Login");
					homeIntent.putExtra("status", object.getString("Status"));
					homeIntent.putExtra("name", object.getString("name"));
					
					Application.getAppContext().startActivity(homeIntent);
				}
				else if(serviceType.equals("RegistrationService")){
					JSONObject object = new JSONObject(result);
					
					if(!object.has("Status") || object.getString("Status").equals("Failed")){
						Toast.makeText(Application.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
						return;
					}
					Intent homeIntent = new Intent(Application.getAppContext(),
							HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					homeIntent.putExtra("Service", "signup");
					homeIntent.putExtra("status", "Registered successfully");
					Application.getAppContext().startActivity(homeIntent);
				}
				else if(serviceType.equals("sendFriendRequest")){
					JSONObject object = new JSONObject(result);
					
					if(!object.has("Status") || object.getString("Status").equals("Failed")){
						Toast.makeText(Application.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
						return;
					}
					Intent homeIntent = new Intent(Application.getAppContext(),
							HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					homeIntent.putExtra("Service", "sendFriendRequest");
					homeIntent.putExtra("status", "Friend request was sent successfully");
					homeIntent.putExtra("email", object.getString("friend email"));
					Application.getAppContext().startActivity(homeIntent);
				}
				else if(serviceType.equals("showFriendRequests")){
					//JSONString requestEmails = new JSONString(result);
					Intent showRequestIntent = new Intent(Application.getAppContext(),
							ShowFriendRequestActivity.class);
					showRequestIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					showRequestIntent.putExtra("Service", "showFriendRequest");
					showRequestIntent.putExtra("status", "Choose Email below to accept or deny request");
					showRequestIntent.putExtra("array",result);
					Application.getAppContext().startActivity(showRequestIntent);
				}
				else if(serviceType.equals("AddFriendService")){
					//JSONString requestEmails = new JSONString(result);
					Intent homeIntent = new Intent(Application.getAppContext(),
							HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					homeIntent.putExtra("Service", "AddFriendService");
					homeIntent.putExtra("array",result);
					Toast.makeText(Application.getAppContext(), "Request Accepted", Toast.LENGTH_LONG).show();
					Application.getAppContext().startActivity(homeIntent);
				}
				else if(serviceType.equals("denyFriendService")){
					//JSONString requestEmails = new JSONString(result);
					Intent homeIntent = new Intent(Application.getAppContext(),
							HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					homeIntent.putExtra("Service", "denyFriendService");
					homeIntent.putExtra("array",result);
					Toast.makeText(Application.getAppContext(), "Request Denied", Toast.LENGTH_LONG).show();
					Application.getAppContext().startActivity(homeIntent);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
