// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;


public interface ImsReferenceListener {

    /**
     * Notifies the application that the reference was successfully started
     * @param reference The related ImsReference.
     */
    public void referenceDelivered(ImsReference reference);

    /**
     * Notifies the application that the reference was not successfully started
     * @param reference The related ImsReference.
     */
    public void referenceDeliveredFailed(ImsReference reference);
    
    
    /**
     * Notifies the application with status reports regarding the Reference.
     * @param reference The related ImsReference.
     * @param notify status messages of this notification.
     */
    public void referenceNotify(ImsReference reference, String notify);
    
}
