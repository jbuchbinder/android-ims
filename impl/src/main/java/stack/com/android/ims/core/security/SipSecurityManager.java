// --------------------------------------------------------------------------
// COPYRIGHT Kike
// --------------------------------------------------------------------------

package stack.com.android.ims.core.security;

import java.text.ParseException;
import java.util.ListIterator;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sip.ClientTransaction;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.SipProvider;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.CSeqHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.ViaHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

import stack.com.android.ims.core.sip.SipUtil;

import com.android.ims.ImsException;

/**
 * The class handles authentication challenges, caches user credentials and
 * takes care (through the SecurityAuthority interface) about retrieving
 * passwords.
 **/
public class SipSecurityManager {

	private static final Logger logger = Logger
			.getLogger(SipSecurityManager.class.getName());

	/**
	 * Credentials cached so far.
	 */
	private CredentialsCache cachedCredentials = new CredentialsCache();

	public SipSecurityManager(Properties properties) throws ImsException {
		// initialize credentials:
		String count = properties.getProperty("imsapi.credentials.count");
		if (count == null) {
			logger.info("Credentials not available");
			return;
		}

		int countInt = Integer.valueOf(count).intValue();
		for (int i = 0; i < countInt; i++) {
			String realm = properties.getProperty("imsapi.credential" + i
					+ ".realm");
			String userName = properties.getProperty("imsapi.credential" + i
					+ ".username");
			String password = properties.getProperty("imsapi.credential" + i
					+ ".password");

			if (realm == null || userName == null || password == null) {
				throw new ImsException(
						"IMS Credential parameters missing. Credential No. "
								+ i);
			}

			UserCredentials uc = new UserCredentials();
			uc.setPassword(password);
			uc.setUserName(userName);

			cacheCredentials(realm, uc);

		}

	}

	/**
	 * Caches <tt>realm</tt> and <tt>credentials</tt> for later usage.
	 *
	 * @param realm the
	 * @param credentials UserCredentials
	 */
	public void cacheCredentials(String realm, UserCredentials credentials) {
		CredentialsCacheEntry ccEntry = new CredentialsCacheEntry();
		ccEntry.userCredentials = credentials;

		this.cachedCredentials.cacheEntry(realm, ccEntry);
	}

	/**
	 * Uses securityAuthority to determine a set of valid user credentials
	 * for the specified Response (Challenge) and appends it to the challenged
	 * request so that it could be retransmitted.
	 *
	 * @param challenge the 401/407 challenge response
	 * @param challengedTransaction the transaction established by the challenged
	 * request
	 * @param transactionCreator the JAIN SipProvider that we should use to
	 * create the new transaction.
	 *
	 * @return a transaction containing a reoriginated request with the
	 *         necessary authorization header.
	 * @throws SipException if we get an exception white creating the
	 * new transaction
	 * @throws InvalidArgumentException if we fail to create a new header
	 * containing user credentials.
	 * @throws ParseException if we fail to create a new header containing user
	 * credentials.
	 * @throws NullPointerException if an argument or a header is null.
	 * our security authority.
	 */
	public ClientTransaction handleChallenge(Response challenge,
			ClientTransaction challengedTransaction,
			SipProvider transactionCreator) throws SipException,
			InvalidArgumentException, ParseException {

		Request challengedRequest = challengedTransaction.getRequest();
		Request reoriginatedRequest = (Request) challengedRequest.clone();

		removeBranchID(reoriginatedRequest);

		if (challenge == null || reoriginatedRequest == null)
			throw new NullPointerException(
					"A null argument was passed to handle challenge.");
		ListIterator authHeaders = null;
		if (challenge.getStatusCode() == 401)
			authHeaders = challenge.getHeaders("WWW-Authenticate");
		else if (challenge.getStatusCode() == 407)
			authHeaders = challenge.getHeaders("Proxy-Authenticate");
		else
			throw new IllegalArgumentException("Unexpected status code ");
		if (authHeaders == null)
			throw new IllegalArgumentException(
					"Could not find WWWAuthenticate or ProxyAuthenticate headers");
		reoriginatedRequest.removeHeader("Authorization");
		reoriginatedRequest.removeHeader("Proxy-Authorization");
		CSeqHeader cSeq = (CSeqHeader) reoriginatedRequest.getHeader("CSeq");
		cSeq.setSeqNumber(cSeq.getSeqNumber() + 1L);
		ClientTransaction retryTran = transactionCreator
				.getNewClientTransaction(reoriginatedRequest);
		WWWAuthenticateHeader authHeader = null;
		AuthorizationHeader authorization;
		for (; authHeaders.hasNext(); reoriginatedRequest
				.addHeader(authorization)) {
			authHeader = (WWWAuthenticateHeader) authHeaders.next();
			String realm = authHeader.getRealm();
			CredentialsCacheEntry ccEntry = cachedCredentials.get(realm);
			UserCredentials userCreds = ccEntry.userCredentials;

			if (userCreds == null)
				throw new SipException(
						"Cannot find user creds for the given user name and realm");

			authorization = getAuthorization(reoriginatedRequest.getMethod(),
					reoriginatedRequest.getRequestURI().toString(),
					reoriginatedRequest.getContent() != null ? new String(
							reoriginatedRequest.getRawContent()) : "",
					authHeader, userCreds);
			logger.info((new StringBuilder()).append(
					"Created authorization header: ").append(
					authorization.toString()).toString());

			//TODO
			// cachedCredentials.cacheAuthorizationHeader(userCreds.getSipDomain(), authorization);
		}

		logger.info("Returning authorization transaction.");
		return retryTran;

	}

	/**
	 * Makes sure that the password that was used for this forbidden response,
	 * is removed from the local cache and is not stored for future use.
	 *
	 * @param forbidden the 401/407 challenge response
	 * @param endedTransaction the transaction established by the challenged
	 * request
	 * @param transactionCreator the JAIN SipProvider that we should use to
	 * create the new transaction.
	 */
	public void handleForbiddenResponse(Response forbidden,
			ClientTransaction endedTransaction, SipProvider transactionCreator) {

	}

	private AuthorizationHeader getAuthorization(String method, String uri,
			String requestBody, WWWAuthenticateHeader authHeader,
			UserCredentials userCredentials) {
		String response = null;
		String qopList = authHeader.getQop();
		String qop = qopList == null ? null : "auth";
		String nc_value = "00000001";
		String cnonce = "xyz";

		HeaderFactory headerFactory = SipUtil.getSingleInstance()
				.getHeaderFactory();

		response = MessageDigestAlgorithm.calculateResponse(authHeader
				.getAlgorithm(), userCredentials.getUserName(), authHeader
				.getRealm(), userCredentials.getPassword(), authHeader
				.getNonce(), nc_value, cnonce, method, uri, requestBody, qop);

		AuthorizationHeader authorization = null;
		try {
			if (authHeader instanceof ProxyAuthenticateHeader)
				authorization = headerFactory
						.createProxyAuthorizationHeader(authHeader.getScheme());
			else
				authorization = headerFactory
						.createAuthorizationHeader(authHeader.getScheme());
			authorization.setUsername(userCredentials.getUserName());
			authorization.setRealm(authHeader.getRealm());
			authorization.setNonce(authHeader.getNonce());
			authorization.setParameter("uri", uri);
			authorization.setResponse(response);
			if (authHeader.getAlgorithm() != null)
				authorization.setAlgorithm(authHeader.getAlgorithm());
			if (authHeader.getOpaque() != null)
				authorization.setOpaque(authHeader.getOpaque());
			if (qop != null) {
				authorization.setQop(qop);
				authorization.setCNonce(cnonce);
				authorization.setNonceCount(Integer.parseInt(nc_value));
			}
			authorization.setResponse(response);
		} catch (ParseException ex) {
			throw new RuntimeException(
					"Failed to create an authorization header!");
		}
		return authorization;
	}

	/**
	 * Removes all via headers from <tt>request</tt> and replaces them with a
	 * new one, equal to the one that was top most.
	 *
	 * @param request the Request whose branchID we'd like to remove.
	 *
	 * @throws ParseException in case the host port or transport in the original
	 * request were malformed
	 * @throws InvalidArgumentException if the port in the original via header
	 * was invalid.
	 */
	private void removeBranchID(Request request) throws ParseException,
			InvalidArgumentException {
		ViaHeader viaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);

		request.removeHeader(ViaHeader.NAME);

		ViaHeader newViaHeader = SipUtil.getSingleInstance().getHeaderFactory()
				.createViaHeader(viaHeader.getHost(), viaHeader.getPort(),
						viaHeader.getTransport(), null);

		request.setHeader(newViaHeader);
	}
}
