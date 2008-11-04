// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

public interface ImsPageMessageListener {
    public void processMessageDelivered(ImsPageMessage message);

    public void processMessageDeliveryFailed(ImsPageMessage message);
}
