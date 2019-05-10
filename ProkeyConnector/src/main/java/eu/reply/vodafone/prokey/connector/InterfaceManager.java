package eu.reply.vodafone.prokey.connector;

import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.Operations.tcUserOperationsIntf;

import com.thortech.util.logging.Logger;

import java.util.Hashtable;

import javax.security.auth.login.LoginException;

import oracle.iam.identity.orgmgmt.api.OrganizationManager;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.Platform;
import oracle.iam.provisioning.api.ApplicationInstanceService;

public class InterfaceManager {
	private OIMClient oimClient;
	private boolean isLocal;
	private static InterfaceManager intfMgr;
	private static Logger logger = Logger.getLogger(CustomConstants.CONNECTOR_LOGGER);

	private InterfaceManager(boolean local) throws InterfaceManagerException, LoginException {
		String methodName = "InterfaceManager()::";
		logger.debug(methodName + "START::" + local);
		this.isLocal = local;

		if (local) {
			Hashtable env = new Hashtable();
			env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
			try {
				// String providerURL = ProvisioningUtils.getConfigProperty("PROVIDER_URL");
				String providerURL = "t3://deng01vr.dc-ratingen.de:14000";

				logger.debug(methodName + "providerURL=" + providerURL);

				env.put("java.naming.provider.url", providerURL);
				// String username = ProvisioningUtils.getConfigProperty("OIM_USERNAME");
				String username = "xelsysadm";

				logger.debug(methodName + "username=" + username);

				// String password = ProvisioningUtils.getConfigProperty("OIM_PASSWORD");
				String password = "Technology0";

				logger.debug(methodName + "password=" + password);
				System.setProperty("java.security.auth.login.config",
						"C:\\JDeveloper\\mywork\\OIM11gR2PS1_client\\oimclient\\conf\\authwl.conf");

				System.setProperty("OIM.AppServerType", "wls");
				System.setProperty("APPSERVER_TYPE", "wls");
				this.oimClient = new OIMClient(env);
				this.oimClient.login(username, password.toCharArray());
			} catch (Exception e) {
				throw new InterfaceManagerException(e);
			}
		}
		logger.info(getClass().getName() + ".new():END");
	}

	public static InterfaceManager getInstance(boolean local) throws InterfaceManagerException {
		try {
			if (intfMgr == null)
				intfMgr = new InterfaceManager(local);
		} catch (LoginException e) {
			throw new InterfaceManagerException(e);
		}
		return intfMgr;
	}

	public UserManager getUserMgmtIntf() throws InterfaceManagerException {
		UserManager userMgmtIntf = null;
		try {
			if (this.isLocal)
				userMgmtIntf = (UserManager) this.oimClient.getService(UserManager.class);
			else
				userMgmtIntf = (UserManager) Platform.getService(UserManager.class);
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return userMgmtIntf;
	}

	public OrganizationManager getOrgMgmtIntf() throws InterfaceManagerException {
		OrganizationManager orgMgmtIntf = null;
		try {
			if (this.isLocal) {
				orgMgmtIntf = (OrganizationManager) this.oimClient.getService(OrganizationManager.class);
			} else
				orgMgmtIntf = (OrganizationManager) Platform.getService(OrganizationManager.class);
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return orgMgmtIntf;
	}

	public tcUserOperationsIntf getLegacyUserOpsIntf() throws InterfaceManagerException {
		tcUserOperationsIntf userOpIntf = null;
		try {
			if (this.isLocal) {
				userOpIntf = (tcUserOperationsIntf) this.oimClient.getService(tcUserOperationsIntf.class);
			} else
				userOpIntf = (tcUserOperationsIntf) Platform.getService(tcUserOperationsIntf.class);
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return userOpIntf;
	}

	public tcFormInstanceOperationsIntf getLegacyFormInstanceOpsIntf() throws InterfaceManagerException {
		tcFormInstanceOperationsIntf intf = null;
		try {
			if (this.isLocal) {
				intf = (tcFormInstanceOperationsIntf) this.oimClient.getService(tcFormInstanceOperationsIntf.class);
			} else
				intf = (tcFormInstanceOperationsIntf) Platform.getService(tcFormInstanceOperationsIntf.class);
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return intf;
	}

	public tcFormDefinitionOperationsIntf getLegacyFormDefinitionOpsIntf() throws InterfaceManagerException {
		tcFormDefinitionOperationsIntf intf = null;
		try {
			if (this.isLocal) {
				intf = (tcFormDefinitionOperationsIntf) this.oimClient.getService(tcFormDefinitionOperationsIntf.class);
			} else
				intf = (tcFormDefinitionOperationsIntf) Platform.getService(tcFormDefinitionOperationsIntf.class);
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return intf;
	}

	public tcLookupOperationsIntf getLegacyLookupOpsIntf() throws InterfaceManagerException {
		tcLookupOperationsIntf intf = null;
		try {
			if (this.isLocal) {
				intf = (tcLookupOperationsIntf) this.oimClient.getService(tcLookupOperationsIntf.class);
			} else
				intf = (tcLookupOperationsIntf) Platform.getService(tcLookupOperationsIntf.class);
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return intf;
	}

	public ApplicationInstanceService getApplicationInstanceService() throws InterfaceManagerException {
		ApplicationInstanceService intf = null;

		try {
			if (this.isLocal) {
				intf = (ApplicationInstanceService) this.oimClient.getService(ApplicationInstanceService.class);
			} else
				intf = (ApplicationInstanceService) Platform.getService(ApplicationInstanceService.class);

		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
		return intf;

	}

	public void logout() throws InterfaceManagerException {
		try {
			this.oimClient.logout();
		} catch (Exception e) {
			throw new InterfaceManagerException(e);
		}
	}
	/*
	 * public static void main(String[] args) { try { InterfaceManager i =
	 * getInstance(true); UserManager um = i.getUserMgmtIntf(); //asd =
	 * i.getLegacyUserOpsIntf(); } catch (InterfaceManagerException e) {
	 * tcUserOperationsIntf asd; e.printStackTrace(); } }
	 */
}
