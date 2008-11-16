package com.android.ims.samples.hello;

import java.util.Properties;

import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.ims.ImsPlatformListener;
import com.android.ims.ImsService;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloIMS extends Activity {
	private TextView tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		tv.setText("Hello, Android");
		setContentView(tv);

		Properties prop = new Properties();
		prop.put("imsapi.userId", "sip:alice@domain.com");
		prop.put("imsapi.proxy", "192.168.2.155:5081");
		prop.put("imsapi.listeningPort", "5078");
		prop.put("imsapi.listeningIP", "127.0.0.1");
		prop.put("imsapi.protocol", "TCP");
		ImsManager manager = ImsManager.getInstance(prop);
		manager.setListener(new ImsManagerListener());
		
		try {
			manager.init();
		} catch (ImsException e) {
			e.printStackTrace();
		}
	}
	
	class ImsManagerListener implements ImsPlatformListener {

		public void processRegistered(ImsManager imsManager) {
			tv.setText("Registered.");
			System.out.println(" processRegistered ");
		}

		public void processRegistrationFailed(ImsManager imsManager) {
		}

		public void processServiceRegistered(ImsService service) {
			
		}
		
	}
}