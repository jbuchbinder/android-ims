package com.android.ims.samples.hello;

import java.util.Properties;

import com.android.ims.ImsException;
import com.android.ims.ImsManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloIMS extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Hello, Android");
		setContentView(tv);

		Properties prop = new Properties();
		prop.put("imsapi.userId", "sip:alice@domain.com");
		prop.put("imsapi.proxy", "127.0.0.1:5081");
		ImsManager manager = ImsManager.getInstance(prop);
		
		try {
			manager.init();
		} catch (ImsException e) {
			e.printStackTrace();
		}

	}
}