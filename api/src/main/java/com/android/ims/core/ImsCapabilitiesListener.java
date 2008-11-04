package com.android.ims.core;

//--------------------------------------------------------------------------
//COPYRIGHT Kike
//--------------------------------------------------------------------------
public interface ImsCapabilitiesListener {

    /**
     * Notifies the application that the capability query response from the
     * remote endpoint was successfully received.
     * 
     * @param capabilities
     *            the related ImsCapabilities
     */
    void capabilityQueryDelivered(ImsCapabilities capabilities);

    /**
     * Notifies the application that the capability query response from the
     * remote endpoint was not successfully received.
     * 
     * @param capabilities
     *            the related ImsCapabilities
     */
    void capabilityQueryDeliveryFailed(ImsCapabilities capabilities);
}
