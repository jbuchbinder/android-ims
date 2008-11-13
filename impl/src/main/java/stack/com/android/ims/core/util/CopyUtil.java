// --------------------------------------------------------------------------
// COPYRIGHT Kike.
// --------------------------------------------------------------------------

package stack.com.android.ims.core.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Provides some useful utilities for copying various data structures.
 */
public final class CopyUtil {

    private CopyUtil() {
        // Utility class, construction not allowed (private constructor).
    }

    /**
     * Copies a byte array.
     * 
     * @param original the original
     * @return a copy
     */
    public static byte[] copy(byte[] original) {
        byte[] copy;
        if (original != null) {
            copy = new byte[original.length];
            System.arraycopy(original, 0, copy, 0, original.length);
        } else {
            copy = null;
        }
        return copy;
    }

    /**
     * Copies a string array.
     * 
     * @param original the original
     * @return a copy
     */
    public static String[] copy(String[] original) {
        String[] copy;
        if (original != null) {
            copy = new String[original.length];
            System.arraycopy(original, 0, copy, 0, original.length);
        } else {
            copy = null;
        }
        return copy;
    }

    /**
     * Creates a shallow copy of a <code>Vector</code>.
     * <p>
     * Shallow copy means that the copy will contain references to the same
     * objects as the original.
     * 
     * @param original the vector
     * @return the copy
     */
    public static Vector copy(Vector original) {
        if (original != null) {
            Vector copy = new Vector(original.size());
            for (int i = 0; i < original.size(); ++i) {
                copy.addElement(original.elementAt(i));
            }
            return copy;
        } else {
            return null;
        }
    }

    /**
     * Copies a {@link Hashtable}.
     * 
     * @param original the original
     * @return a copy
     */
    public static Hashtable copy(Hashtable original) {
        Hashtable copy;
        if (original != null) {
            copy = new Hashtable(original.size());
            Enumeration keys = original.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                copy.put(key, original.get(key));
            }
        } else {
            copy = null;
        }
        return copy;
    }
}
