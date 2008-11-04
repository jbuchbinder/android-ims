// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims;


/**
 * ImsService is the base interface for IMS Services and their are built following
 * the standard way provided by the ImsManager.
 * 
 * @see ImsCoreService
 */
public interface ImsService {
    
    
    /**
     * Service Type Core (see {@link ImsCoreService}) 
     */
    public static final int SERVICE_TYPE_CORE = 0;

    /**
     * Service Type OMA Presence (see {@link ImsPresenceService})
     */
    public static final int SERVICE_TYPE_PRESENCE = 1;
    
    /**
     * Service Type OMA-IM (see {@link ImsIMService})
     */
    public static final int SERVICE_TYPE_MESSAGING = 2;

    /**
     * Service Type XDM (see {@link XDMService})
     */
    public static final int SERVICE_TYPE_XDM = 3;

    /**
     * Returns the Service Type..
     * @return a integer representation of the type of service.
     */
    public int getServiceType();

    /**
     * Returns the service id string that this Service was created with.
     * @return the service ID.
     */
    public String getServiceId();
    
    
    /**
     * Initialize service. 
     */
    public void init();
}
