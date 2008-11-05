//--------------------------------------------------------------------------
//COPYRIGHT Kike
//--------------------------------------------------------------------------
package stack.com.android.ims.core.inet;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {

	public static String getLocalAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "127.0.0.1";
		}
	}

	public static int getRandomPort() {
		return new java.util.Random().nextInt(8975) + 1024;

	}
}
