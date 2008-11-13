// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------

package stack.com.android.ims;

import java.util.HashMap;
import java.util.Vector;

import com.android.ims.core.ImsServiceMethod;

/**
 * Shared constants.
 */
public class Constants {

    /**
     * The Attribute name for the Service
     */
    public static final String ATTR_SERVICE = "service";

    public static final String ATTR_METHOD = "method";

    public static final String CONTENT_TYPE = "Content-Type";
    
    public static final String CONTENT_TYPE_SDP = "application/sdp";

    public static final String ROUTE_HEADER = "S-CSCF";
    public static final String P_ASSERTED_IDENTITY = "P-Asserted-Identity";
 
    
    public static Vector<String> splitInLines(String sdp) {
        Vector<String> result = new Vector<String>();
        int lineStart = 0;
        while (lineStart < sdp.length()) {
            int eol = sdp.indexOf("\n", lineStart);
            if (eol == -1) {
                eol = sdp.length();
            }
            int nextLineStart = eol + 1;

            if (eol > 0 && sdp.charAt(eol - 1) == '\r') {
                --eol;
            }

            if (eol > lineStart) {
                String line = sdp.substring(lineStart, eol);
                result.addElement(line);
            }

            lineStart = nextLineStart;
        }

        return result;
    }
    
    
    public static final HashMap<ImsServiceMethod.Method, String> MethodToString = new HashMap();
    
    static {
        MethodToString.put(ImsServiceMethod.Method.ACK,"ACK");
        MethodToString.put(ImsServiceMethod.Method.BYE,"BYE");
        MethodToString.put(ImsServiceMethod.Method.CANCEL,"CANCEL");
        MethodToString.put(ImsServiceMethod.Method.INFO,"INFO");
        MethodToString.put(ImsServiceMethod.Method.INVITE,"INVITE");
        MethodToString.put(ImsServiceMethod.Method.MESSAGE,"MESSAGE");
        MethodToString.put(ImsServiceMethod.Method.NOTIFY,"NOTIFY");
        MethodToString.put(ImsServiceMethod.Method.OPTIONS,"OPTIONS");
        MethodToString.put(ImsServiceMethod.Method.PRACK,"PRACK");
        MethodToString.put(ImsServiceMethod.Method.PUBLISH,"PUBLISH");
        MethodToString.put(ImsServiceMethod.Method.REFER,"REFER");
        MethodToString.put(ImsServiceMethod.Method.REGISTER,"REGISTER");
        MethodToString.put(ImsServiceMethod.Method.SUBSCRIBE,"SUBSCRIBE");
        MethodToString.put(ImsServiceMethod.Method.UPDATE,"UPDATE");
    }
        

}
