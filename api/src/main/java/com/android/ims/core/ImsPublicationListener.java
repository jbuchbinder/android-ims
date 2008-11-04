// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

public interface ImsPublicationListener {


    /**
     * Notifies the application that the publication request was successfully
     * delivered.
     * <p> 
     * The state of the ImsPublication has become ACTIVE.
     */
    void publicationDelivered(ImsPublication pub);

    /**
     * Notifies the application that the unpublish request was not successfully
     * delivered.
     * <p> 
     * The state of the ImsPublication has become eithe ACTIVE or INACTIVE.
     */
    void publicationhDeliveredFailed(ImsPublication pub);

    /**
     * Notifies the application that the unpublish request was successfully
     * delivered.
     * <p> 
     * The state of the ImsPublication has become INACTIVE.
     */
    void publicationTerminated(ImsPublication pub);

    /**
     * Notifies the application that the unpublish request was not successfully
     * delivered.
     * <p> 
     * The state of the ImsPublication has become ACTIVE.
     */
    void publicationTerminatedFailed(ImsPublication pub);
    

    /**
     * Notifies the application that a publication refresh has been
     * issued by the remote endpoint.
     * @param publication - the concerned publication.
     */    
    void publicationUpdated(ImsPublication publication);

}
