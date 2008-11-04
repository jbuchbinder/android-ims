// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * It is the single point of entry to the API. It is used to initialized the
 * IMS stack and provide methods for creating IMS services. The object is
 * implemented as a singleton.
 * <p>
 * Example code of how to create an ImsServiceManager instance:
 * 
 * <pre>
 * Properties prop = new Properties();
 * prop.put(&quot;imsapi.userId&quot;, &quot;sip:alice@ericsson.com&quot;);
 * prop.put(&quot;imsapi.proxy&quot;, &quot;164.48.48.57:6666&quot;);
 * ImsManager manager = ImsManager.getInstance(prop);
 * manager.init();
 * </pre>
 * Or, if the ImsManager has been already initialized, use:
 * <pre>
 * ImsManager manager = ImsManager.getInstance();
 * </pre>
 */
public class ImsManager {
    
    private static final Logger logger = Logger.getLogger(ImsManager.class.getName());
    
    private static final String DEFAULT_PATH_NAME = "impl.com.ericsson.imsapi.ImsManagerImpl";
    private static ImsManager singleInstance;

    private static String pathName = null;
    protected Properties properties;

    protected ImsManager(Properties properties) {
        this.properties = properties;
    }

    /**
     * Creates an IMS service.
     * @param serviceType
     *            The serviceName must be defined in the Configuration.
     * @param serviceId
     *            The serviceId must be defined in the Configuration.
     */
    public ImsService createService(int serviceType, String serviceId)
            throws ImsException {
        assert singleInstance != null;
        return singleInstance.createService(serviceType, serviceId);
    }

    /**
     * Returns the Single Instance of the ImsManager.
     * 
     * @param properties
     *            the Properties containing the IMS configuration.
     * @return The ImsServiceManager instance.
     */
    public synchronized static ImsManager getInstance(Properties properties) {
        if (singleInstance == null) {
            singleInstance = createImsManager(properties);

        }
        return singleInstance;
    }
    
    /**
     * Sets the pathname that identifies the location of a particular vendor's
     * implementation of this specification.
     * 
     * @param pathName the path name to the vendor's implementation.
     */
    public synchronized static void setPathName(String aPathName){
        pathName = aPathName;
    }

    /**
     * Returns the Single Instance of the ImsManager. In order to call this
     * method, the ImsManager must be initialized first (Refer to
     * ImsManager.getInstance(Properties)).
     * @return The ImsServiceManager instance.
     */
    public synchronized static ImsManager getInstance() {
        return singleInstance;
    }

    /**
     * Set the Listener for the ImsManager. The listener is needed to notified
     * the client application about ImsManager initialization status.
     * @param listener
     *            the platform listener.
     */
    public void setListener(ImsPlatformListener listener) {
        singleInstance.setListener(listener);
    }

    /** 
     * Initialize service manager 
     * @throws ImsException If IMS Manager can not be initialized. 
     **/
    public void init() throws ImsException {
        singleInstance.init();
    }

    /** terminate service manager */
    public void terminate() {
        singleInstance.terminate();
    }

    // PRIVATE-PROTECTED METHODS
    protected static ImsManager createImsManager(Properties properties) {
        Class<?> aclass[] = new Class[1];
        try {
            aclass[0] = Class.forName("java.util.Properties");
            Constructor<?> constructor = Class.forName(getPathName())
                    .getConstructor(aclass);

            Object aobj[] = new Object[1];
            aobj[0] = properties;
            ImsManager imsManager = (ImsManager) constructor.newInstance(aobj);

            return imsManager;

        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        } catch (SecurityException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        } catch (NoSuchMethodException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        } catch (InstantiationException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        } catch (IllegalAccessException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        } catch (InvocationTargetException e) {
            logger.log(Level.WARNING, "Error on Create IMS Manager",e);
        }
        return null;
    }

    /**
     * This is still to do. Path Name must be received from properties or
     * configuration.
     */
    private static String getPathName() {
        if (pathName == null)
            return DEFAULT_PATH_NAME;
        return pathName;
    }

    public Properties getProperties() {
        return properties;
    }

}
