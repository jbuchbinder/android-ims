package stack.com.android.ims.core.sip;

import java.util.TooManyListenersException;

import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipListener;
import javax.sip.TimeoutEvent;
import javax.sip.Transaction;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.message.Request;
import javax.sip.message.Response;

import java.util.logging.Logger;

public class SipMessageListener implements SipListener {
    /**
     * Logger
     */
    private static final Logger theirLogger = Logger
            .getLogger(SipMessageListener.class.toString());

    private SipMessageManager itsMessageManager;

    public SipMessageListener(SipMessageManager manager) {
        itsMessageManager = manager;
        init();
    }

    private void init() {
        try {
            itsMessageManager.getSipUtil().getSipProvider()
                    .addSipListener(this);
        } catch (TooManyListenersException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void processDialogTerminated(DialogTerminatedEvent arg0) {
    }

    public void processIOException(IOExceptionEvent arg0) {
        // TODO Auto-generated method stub

    }

    /* 
     * @see javax.sip.SipListener#processRequest(javax.sip.RequestEvent)
     */
    public void processRequest(RequestEvent requestEvent) {
        String method = requestEvent.getRequest().getMethod();

        theirLogger.info("Request received..." + method);

        itsMessageManager.handleRequestEvent(requestEvent);
    }

    /* 
     * @see javax.sip.SipListener#processResponse(javax.sip.ResponseEvent)
     */
    public void processResponse(ResponseEvent responseEvent) {
        int responseCode = responseEvent.getResponse().getStatusCode();

        theirLogger.info("Response received..." + responseCode);

        itsMessageManager.handleResponseEvent(responseEvent);
    }

    /** 
     * @see javax.sip.SipListener#processTimeout(javax.sip.TimeoutEvent)
     */
    public void processTimeout(TimeoutEvent timeOutEvent) {
        theirLogger.info("Time out received...");

        Transaction transaction;

        if (timeOutEvent.isServerTransaction())
            transaction = timeOutEvent.getServerTransaction();
        else
            transaction = timeOutEvent.getClientTransaction();

        String method = transaction.getRequest().getMethod();

        theirLogger.info("Time out method: " + method);

        itsMessageManager.handleTimeOut(timeOutEvent);

    }

    /* 
     * @see javax.sip.SipListener#processTransactionTerminated(javax.sip.TransactionTerminatedEvent)
     */
    public void processTransactionTerminated(TransactionTerminatedEvent arg0) {
        // Do Nothing
    }

}
