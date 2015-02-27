package com.FCI.SWE.Controllers;

import android.content.Context;

public class Application extends android.app.Application {

	private static Context context;
	private static UserController userController;

    public void onCreate(){
        super.onCreate();
        Application.context = getApplicationContext();
        Application.userController = UserController.getInstance();
    }

    public static Context getAppContext() {
        return Application.context;
    }
    
    public static UserController getUserController(){
    	return Application.userController;
    }
}
