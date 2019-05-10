package eu.reply.vodafone.prokey.connector;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import oracle.iam.platform.Platform;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.EntitlementInstance;

public class AdapterUtilities {

	private Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());

	public boolean isGroupEmpty(String usrKey, String childFormName) throws Exception {
		boolean ret = false;
		if (getProfileCount(usrKey, childFormName) < 1) {
			ret = true;
		}
		return ret;
	}

	private int getProfileCount(String usrKey, String childFormName) throws Exception {
		int nrec = 0;
		List<EntitlementInstance> list = Platform.getService(ProvisioningService.class).getEntitlementsForUser(usrKey);
		for (EntitlementInstance userEntitlement : list) {

			if (userEntitlement.getEntitlement().getFormName().equalsIgnoreCase(childFormName)) {
				nrec++;
			}
		}
		return nrec;
	}

	public boolean isEnable(String usrKey, String processInstance) throws Exception {
		return !isToRenable(usrKey, Long.parseLong(processInstance));
	}

	private boolean isToRenable(String usrKey, Long processInstance) throws Exception {
		LOGGER.info("isToRenable start");
		LOGGER.info("usrKey:" + usrKey);
		LOGGER.info("processInstance:" + processInstance);

		List<Account> list = Platform.getService(ProvisioningService.class).getAccountsProvisionedToUser(usrKey);
		for (Account account : list) {
			LOGGER.info("account" + account);
			LOGGER.info("getProcessInstanceKey" + account.getProcessInstanceKey());
			LOGGER.info("getAccountStatus" + account.getAccountStatus());
			if (account.getProcessInstanceKey().equals(Long.toString(processInstance))) {
				if (account.getAccountStatus().equals("Disabled")) {
					LOGGER.info("isEnable return false");
					return true;
				}
			}
		}
		LOGGER.info("isEnable return true");
		return false;
	}

	public String removeAllEntitlements(String userKey, Long processKey, String childFormName) throws Exception {
		String ret = "SUCCESS";
		LOGGER.info("userKey=" + userKey + ":::REVOKE ALL ROLES START");
		List<EntitlementInstance> list = Platform.getService(ProvisioningService.class).getEntitlementsForUser(userKey);
		LOGGER.info("userKey=" + userKey + "::REMOVE ALL ROLES::roles to be revoked=" + list.size());
		for (EntitlementInstance userEntitlement : list) {
			LOGGER.info("userKey=" + userKey + "::REMOVE ALL ROLES::revoking entitlement instance id=" + userEntitlement.getEntitlementInstanceKey());
			if (userEntitlement.getEntitlement().getFormName().equalsIgnoreCase(childFormName) && processKey.equals(userEntitlement.getProcessInstanceKey())) {
				Platform.getService(ProvisioningService.class).revokeEntitlement(userEntitlement);
				LOGGER.info("userKey=" + userKey + "::REMOVE ALL ROLES::revoking entitlement instance id=" + userEntitlement.getEntitlementInstanceKey());
			}
		}
		LOGGER.finer("userKey=" + userKey + ":::REVOKE ALL ROLES END");
		return ret;
	}

	public String prepopulateOrgLocationCode(String orgLocationCode) throws OIMLookupException, InterfaceManagerException {
		LOGGER.finer("prepopulateOrgLocationCode start");
		LOGGER.finer("orgLocationCode:" + orgLocationCode);
		String ret;

		TTWOSGenericOimProcessUtils oimp = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
		Map<String, String> map = oimp.getLookup(CustomConstants.LOCATION_CODE_LOOKUP);
		ret = map.get(orgLocationCode);

		LOGGER.finer("prepopulateOrgLocationCode end");
		return ret;
	}
}
