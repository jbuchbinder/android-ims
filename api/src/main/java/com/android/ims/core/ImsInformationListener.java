// --------------------------------------------------------------------------
// COPYRIGHT Ericsson Telecommunicatie B.V., 2008
// All rights reserved
//
// The copyright to the computer program(s) herein is the property of
// Ericsson Telecommunicatie B.V.. The programs may be used and/or
// copied only with written permission from Ericsson Telecommunicatie
// B.V. or in accordance with the terms and conditions stipulated in the
// agreement/contract under which the program(s) have been supplied.
// --------------------------------------------------------------------------

package com.android.ims.core;


/**
 * This listener type is used to notify the application about the status of sent
 * information messages.
 */
public interface ImsInformationListener {

    /**
     * Notifies the application that the ImsInformation was successfully delivered.
     */    
    void informationDelivered(ImsInformation information);

    /**
     * Notifies the application that the ImsInformation was not successfully
     * delivered.
     */    
    void informationDeliveredFailed(ImsInformation information);

}
