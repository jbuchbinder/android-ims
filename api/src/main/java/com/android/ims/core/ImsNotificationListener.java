// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

public interface ImsNotificationListener {
    public void processNotificationDelivered(ImsNotification notification);

    public void processNotificationDeliveryFailed(ImsNotification notification);
}
