// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims;

public interface ImsPlatformListener {
    public void processServiceRegistered(ImsService service);

    public void processRegistered(ImsManager imsManager);

    public void processRegistrationFailed(ImsManager imsManager);
}
