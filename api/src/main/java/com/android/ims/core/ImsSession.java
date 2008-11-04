// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------
package com.android.ims.core;

import java.util.List;

import com.android.ims.ImsException;
import com.android.ims.core.media.Media;
import com.android.ims.core.media.Media.Direction;

/**
 * A representation of a media exchange between two IMS endpoints.
 * <p>
 * A Session is able to carry media of the type {@link Media}. Media objects
 * represent a media connection and not the media/content itself.
 * <p>
 * The state machine for ImsSession is illustrated in the following diagram:
 * <p><img src="./doc-files/session-state.jpg"/> </p> 
 * Example usage of starting a new session:
 * 
 * <pre>
 * ImsSession session = coreService.createSession(fromUid, toUid);
 * session.setListener(sessionListener);
 * session.start();
 * </pre>
 * When the destination accepts the call, the session listener will receive
 * <pre>
 * public void sessionStarted(ImsSession session) {
 *     .. 
 *     // do something
 *     session.ack();
 *     // communication established..
 * }
 * </pre> 
 * <p>
 * Once the session is established, one of the end points (both application itself or
 * remote entity) may decide to change/renegotiate the characteristics of the session (from only audio 
 * to audio/video media communication). The following states diagram depicts the sequence
 * of states/methods involved during renegotiation:  
 * <p><img src="./doc-files/session-state2.jpg"/> </p>    
 * 
 * @see ImsServiceMethod 
 * @see ImsSessionListener
 * @see State
 */
public interface ImsSession extends ImsServiceMethod {

    /** The state of the session with the remote endpoint. */
    public enum State {
        /** Session establishment is not started. */
        INITIATED,
        /** Session establishment has been started. */
        PROCEEDING,
        /** Session established. */
        ESTABLISHED,
        /** Session is negotiating updates. */
        NEGOTIATING,
        /** The Session is negotiating updates to the session */
        RENEGOTIATING,
        /** Session terminate request has been sent. */
        TERMINATING,
        /** Session is terminated. */
        TERMINATED
    }

    /**
     * This method can be used to accept a session invitation or a session
     * update depending on the context.
     * 
     * During session establishment, this is used to accept an incoming session
     * During a reliable communication, this can be also used to accept reliable
     * provisional responses received from the remote end point. 
     * After the session has been established this method can be used to
     * accept() a re-negotiation.
     * 
     * @see ImsCoreServiceListener.sessionInviationReceived()
     * @see ImsSessionListener.sessionProvisionalResponse()
     * @see ImsSessionListener.sessionUpdateRequest()
     * @see ImsSessionListener.sessionUpdateRequest()
     * 
     * @throws ImsException if acceptance message can not be created.
     * @throws IllegalStateException
     *             if current state not allows to accept. 
     */
    public void accept() throws IllegalStateException, ImsException;

    /**
     * Transfers an ongoing session to another PUI.
     * 
     * @param publicUserId
     *                A PUI to transfer the session to.
     * @throws IllegalStateException
     */
    public void transfer(String publicUserId) throws IllegalStateException;

    /**
     * Rejects this session. A session may be reject if the invitation is
     * received and found unacceptable to the application or if the session is
     * update in such a way that it is no longer wanted by the application.
     * 
     * @throws IllegalStateException
     */
    public void reject() throws IllegalStateException;

    /**
     * Start session. When this method is called the remote party is invited to
     * the session.
     * 
     * @throws IllegalStateException if the Session is not in INITIATED state. 
     * @throws ImsException If the Session can not be initiated correctly.
     */
    public void start() throws IllegalStateException, ImsException;

    /**
     * Terminate or cancel session. A session that has been started should
     * always be terminated using this method. By calling this function a
     * negotiated session is terminated.
     */
    public void terminate();

    /**
     * Synchronizes the session modifications with the remote endpoint that an
     * application has done.
     * @throws IllegalStateException if the session is not a updatable state.
     * @throws ImsException if the session can not be updated correctly.
     */
    public void update() throws ImsException;

    /**
     * Hold session. All media flows in the session are put on hold. Resources
     * for the session are still reserved. The local device stops sending data
     * on a media, and Hold message is sent to the remote to inform it that the
     * session is on Hold.
     * 
     * @throws IllegalStateException
     */
    public void hold() throws IllegalStateException;

    /**
     * Resume session. If this session has been put on hold on the local device,
     * then a call to this method resumes the session. A Resume message is sent
     * to the remote to inform that sending data on the media is continued. Then
     * the local device resumes sending data.
     * 
     * @throws IllegalStateException
     */
    public void resume() throws IllegalStateException;

    /**
     * Returns the current state of this session.
     * 
     * @return
     */
    public State getState();

    /**
     * Sets a listener for events and state change notifications, replacing any
     * previous ImsSessionListener. A null reference is allowed and has the
     * effect of removing any existing listener.
     * 
     * @param listener
     */
    public void setListener(ImsSessionListener listener);

    /** Creates a Media object with a media type name and adds it to the Session. */
    public Media createMedia(String type, Direction direction)
            throws ImsException;

    /** 
     * Creates Media objects corresponding to the received SDP proposal.
     * If the Session represents an incomming session, the SDP offer sent by the remote
     * endpoint can be readed using..
     * <pre>
     * public void processSessionInvitation(ImsCoreService service, ImsSession session){
     *   ...
     *   sdpOffer = imsSession.getRequest().getBodyParty("application/sdp");
     * </pre>
     * @param sdpOffer this is the body part received from the remote endpoint. 
     **/
    ImsMessageBody createMedia(ImsMessageBody sdpOffer) throws ImsException;

    /**
     * Return the list of medias associated to this session
     * @return an array of all Media in the Session 
     * @throws IllegalStateException - if the Session is in TERMINATED state.
     */
    public List<Media> getMedia();

    /**
     * This method is used for referring the remote endpoint to a third party
     * user or service.
     * @throws ImsException 
     */
    public ImsReference createReference(String referToUserId, Method referMethod) throws ImsException;

    /**
     * This method is used for creating Mid-session signaling information
     * towards the remote endpoint. 
     * Mid-session information is applicable in several use cases: Charging 
     * Information, PSTN-Gateways messaging, Gaming notes. etc.
     * 
     * @return an ImsInformation instance.
     * @see ImsInformation.
     * @throws ImsException
     *             if the mid-session information can not be created.
     * @throws IllegalStateException
     *             if the Session is not in ESTABLISHED state
     */
    public ImsInformation createInformation() throws ImsException;
    


    /**
     * Sends an progress indication about this session. It can be used to
     * indicate the remote party about, for instance, ringing, session progress,
     * etc.
     * 
     * @param responseCode
     *            a value between 101 and 199
     * @throws ImsException
     *          if provisional response can not be created. 
     * @throws IllegalStateException
     *             if state is not PROCEEDING.
     * @throws IllegalArgumentException
     *             if value is not between 101 and 199.
     */
    void provisionalResponse(int responseCode) throws ImsException;

    /**
     * It is used to indicated the remote party about error processing this
     * session.
     * 
     * @param errorCode
     *            a value between 400 and 699
     * @throws IllegalStateException
     *             If the state is not PROCEEDING.
     * @throws IllegalArgumentException
     *             if value is not between 400 and 699.
     */
    void errorResponse(int errorCode);

}
