package eu.reply.vodafone.prokey.connector;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thortech.util.logging.Logger;

import oracle.iam.platform.Platform;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.EntitlementInstance;

public class ProkeyConnector {

	private boolean simulationEnabled = false;
	private TTWOSConnectorConfiguration configuration;
	private TTWOSGenericAttributesFormat attributesFormat;
	private static final Logger logger = Logger.getLogger(CustomConstants.CONNECTOR_LOGGER);
	
	public ProkeyConnector(String attrMapLookupName, String environment,
						   String serviceWsdlUrl, String serviceUser, String servicePwd,
						   String httpUser, String httpPwd,
						   String dbDataSource) {
					logger.debug("initializing connector");
					this.configuration = new TTWOSConnectorConfiguration();
					logger.debug("environment: "+environment);
					this.configuration.setEnvironment(environment);
					logger.debug("serviceWsdlURL: "+serviceWsdlUrl);
					this.configuration.setServiceWsdlUrl(serviceWsdlUrl);
					logger.debug("serviceUser: "+serviceUser);
					this.configuration.setServiceUser(serviceUser);
					logger.debug("servicePwd: "+servicePwd);
					this.configuration.setServicePwd(servicePwd);
					logger.debug("serviceHTTPUser: "+httpUser);
					this.configuration.setServiceHttpUser(httpUser);
					logger.debug("serviceHTTPPwd: "+httpPwd);
					this.configuration.setServiceHttpPwd(httpPwd);
					logger.debug("dbDataSource: "+dbDataSource);
					this.configuration.setDbDataSource(dbDataSource);
					logger.debug("attrMapLookup: "+attrMapLookupName);
					this.configuration.setAttrMapLookup(attrMapLookupName);;
					this.attributesFormat = new TTWOSGenericAttributesFormat();

					try {
							simulationEnabled=Boolean.parseBoolean(ProvisioningUtils.getComponentConfigProperty(CustomConstants.COMPONENT_NAME, "simulationEnabled"));
					} catch (ProvisioningUtilsException e) {
							logger.error("Unable to fetch properties, falling back to simulation mode",e);
					}
	}

	
	

	public ProkeyConnector(String attrMapLookupName, String environment, String serviceWsdlUrl, String serviceUser, String servicePwd, String httpUser,
						   String httpPwd, String dbDataSource, String basicAuthEnabled, String subjectCategory1, String subjectCategory2, String subjectCategory3,
						   String shortDescrCreation, String shortDescrGrantAr, String shortDescrRevokeAr, String shortDescrUpdate, String shortDescrSuspension,
						   String shortDescrReJoin, String shortDescrTermination) {
		logger.debug("initializing connector");
		this.configuration = new TTWOSConnectorConfiguration();
		logger.debug("environment: " + environment);
		this.configuration.setEnvironment(environment.trim());
		logger.debug("serviceWsdlURL: " + serviceWsdlUrl);
		this.configuration.setServiceWsdlUrl("file://"+System.getProperty("user.dir") + serviceWsdlUrl);
		logger.debug("serviceUser: " + serviceUser);
		this.configuration.setServiceUser(serviceUser);
		logger.debug("servicePwd: " + servicePwd);
		this.configuration.setServicePwd(servicePwd);
		logger.debug("serviceHTTPUser: " + httpUser);
		this.configuration.setServiceHttpUser(httpUser);
		logger.debug("serviceHTTPPwd: " + httpPwd);
		this.configuration.setServiceHttpPwd(httpPwd);
		logger.debug("basicAuthEnabled: " + basicAuthEnabled);
		this.configuration.setBasicAuthEnabled(basicAuthEnabled);
		logger.debug("dbDataSource: " + dbDataSource);
		this.configuration.setDbDataSource(dbDataSource);
		logger.debug("subjectCategory1: " + subjectCategory1);
		this.configuration.setSubjectCategory1(subjectCategory1);
		logger.debug("subjectCategory2: " + subjectCategory2);
		this.configuration.setSubjectCategory2(subjectCategory2);
		logger.debug("subjectCategory3: " + subjectCategory3);
		this.configuration.setSubjectCategory3(subjectCategory3);
		logger.debug("shortDescrCreation: " + shortDescrCreation);
		this.configuration.setShortDescrCreation(shortDescrCreation);
		logger.debug("shortDescrGrantAr: " + shortDescrGrantAr);
		this.configuration.setShortDescrGrantAr(shortDescrGrantAr);
		logger.debug("shortDescrRevokeAr: " + shortDescrRevokeAr);
		this.configuration.setShortDescrRevokeAr(shortDescrRevokeAr);
		logger.debug("shortDescrUpdate: " + shortDescrUpdate);
		this.configuration.setShortDescrUpdate(shortDescrUpdate);
		logger.debug("shortDescrSuspension: " + shortDescrSuspension);
		this.configuration.setShortDescrSuspension(shortDescrSuspension);
		logger.debug("shortDescrReJoin: " + shortDescrReJoin);
		/*this.configuration.setShortDescrReJoin(shortDescrReJoin);
		logger.debug("shortDescrTermination: " + shortDescrTermination);*/ //TODO check to see if exists in Prokey like DWH
		this.configuration.setShortDescrTermination(shortDescrTermination);
		logger.debug("attrMapLookup: " + attrMapLookupName);
		this.configuration.setAttrMapLookup(attrMapLookupName);
		this.attributesFormat = new TTWOSGenericAttributesFormat();

//		try {
//			simulationEnabled = Boolean.parseBoolean(ProvisioningUtils.getComponentConfigProperty(CustomConstants.COMPONENT_NAME, "simulationEnabled"));
//		} catch (ProvisioningUtilsException e) {
//			logger.error("Unable to fetch properties, falling back to simulation mode", e);
//		}
	}

	private String translate(String attrMapLookupName) {
	
		/* commentato vecchio metodo del connettore generico, non serve per questa tipologia di connettore.
		String ret;
		switch (attrMapLookupName.toUpperCase()) {
		case "EMAIL":
			ret = CustomConstants.LOOKUPEMAIL;
			break;
		case "LDAPUID":
			ret = CustomConstants.LOOKUPLDAPUID;
			break;
		case "ADSAMACCOUNTNAME":
			ret = CustomConstants.LOOKUPADSAMACCONTNAME;
			break;
		default:
			ret = null;
			break;
		}
		logger.debug("translate:" + ret);
*/
		return attrMapLookupName;
	}

	private String translateItResource(String attrMapLookupName) {
		//semplificato il metodo generico di translate inserendo una costante in risposta.
		logger.debug("transalteItResource" + CustomConstants.UD_ITRESORUCE);
		return CustomConstants.UD_ITRESORUCE;
	}

	public TTWOSConnectorConfiguration getConfiguration() {
		return configuration;
	}

	public String createUser(String username, String requestId, String processKey) {
		String status = CustomConstants.ADP_RETURN_KO;
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::CREATE START");
		Date requestDate = new Date();
		Map<String, String> attributes;

		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(CustomConstants.APP_INSTANCE_NAME);//opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			//inserito direttamente il nome dell'app instance
			logger.debug("username=" + username + "::CREATE::generating attribute map");
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::CREATE::" + attr.getKey() + "=" + attr.getValue());
			String ticketNumber;
			logger.debug("username=" + username + "::CREATE::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatCreateShortDescr(getConfiguration(), attributes);
			logger.debug("username=" + username + "::CREATE::shortDescription=" + shortDescr);
			String description = attributesFormat.formatCreateDescription(username, attributes);
			logger.debug("username=" + username + "::CREATE::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::CREATE::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::CREATE::ticketNumber=" + ticketNumber);
			logger.debug("username=" + username + "::CREATE::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::CREATE::inserting ticket details in DB");
				db.registerCreate(requestId, ticketNumber, requestDate, username, appId);
				logger.debug("username=" + username + "::CREATE::insert in DB completed");
			} catch (Exception e) {
				logger.error("username=" + username + "::CREATE::DB failure", e);
			}
			status = CustomConstants.ADP_RETURN_OK;
		} catch (Exception e) {
			logger.error("username=" + username + "::CREATE ERROR", e);
		}

		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::CREATE END");
		return status;
	}

	public String modifyUser(String username, String accountName, String requestId, String processKey) {
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::MODIFY START");
		String status = CustomConstants.ADP_RETURN_KO;

		Date requestDate = new Date();
		Map<String, String> attributes;
		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::MODIFY::" + attr.getKey() + "=" + attr.getValue());

			String ticketNumber;
			logger.debug("username=" + username + "::MODIFY::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatUpdateShortDescr(getConfiguration(), accountName);
			logger.debug("username=" + username + "::MODIFY::shortDescription=" + shortDescr);
			String description = attributesFormat.formatUpdateDescription(username, attributes);
			logger.debug("username=" + username + "::MODIFY::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::MODIFY::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::MODIFY::ticketNumber=" + ticketNumber);
			logger.debug("username=" + username + "::MODIFY::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::MODIFY::inserting ticket details in DB");
				db.registerUpdate(requestId, ticketNumber, requestDate, username, appId);
				logger.debug("username=" + username + "::MODIFY::insert in DB completed");
			} catch (Exception e) {
				logger.error("username=" + username + "::CREATE::DB failure", e);
			}

			status = CustomConstants.ADP_RETURN_OK;

		} catch (Exception e) {
			logger.error("username=" + username + "::MODIFY ERROR", e);
		}

		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::MODIFY END");
		return status;
	}

	public String enableUser(String username, String accountName, String requestId, String processKey) {
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::ENABLE START");
		String status = CustomConstants.ADP_RETURN_KO;

		Date requestDate = new Date();
		Map<String, String> attributes;
		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::ENABLE::" + attr.getKey() + "=" + attr.getValue());
			String ticketNumber;
			logger.debug("username=" + username + "::ENABLE::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatEnableShortDescr(getConfiguration(), accountName);
			logger.debug("username=" + username + "::ENABLE::shortDescription=" + shortDescr);
			String description = attributesFormat.formatEnableDescription(username, attributes);
			logger.debug("username=" + username + "::ENABLE::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::ENABLE::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::ENABLE::ticketNumber=" + ticketNumber);
			logger.debug("username=" + username + "::ENABLE::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::ENABLE::inserting ticket details in DB");
				db.registerEnable(requestId, ticketNumber, requestDate, username, appId);
				logger.debug("username=" + username + "::ENABLE::insert in DB completed");
			} catch (Exception e) {
				logger.error("username=" + username + "::ENABLE::DB failure", e);
			}
			status = CustomConstants.ADP_RETURN_OK;
		} catch (Exception e) {
			logger.error("username=" + username + "::ENABLE ERROR", e);
		}
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::ENABLE END");
		return status;
	}

	public String disableUser(String username, String accountName, String requestId, String processKey) {
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::DISABLE START");
		String status = CustomConstants.ADP_RETURN_KO;

		Date requestDate = new Date();
		Map<String, String> attributes;
		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::DISABLE::" + attr.getKey() + "=" + attr.getValue());

			String ticketNumber;
			logger.debug("username=" + username + "::DISABLE::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatDisableShortDescr(getConfiguration(), accountName);
			logger.debug("username=" + username + "::DISABLE::shortDescription=" + shortDescr);
			String description = attributesFormat.formatDisableDescription(username, attributes);
			logger.debug("username=" + username + "::DISABLE::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::DISABLE::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::DISABLE::ticketNumber=" + ticketNumber);
			logger.debug("username=" + username + "::DISABLE::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::DISABLE::inserting ticket details in DB");
				db.registerDisable(requestId, ticketNumber, requestDate, username, appId);
				logger.debug("username=" + username + "::DISABLE::insert in DB completed");

			} catch (Exception e) {
				logger.error("username=" + username + "::DISABLE::DB failure", e);
			}
			status = CustomConstants.ADP_RETURN_OK;

		} catch (Exception e) {
			logger.error("username=" + username + "::DISABLE ERROR", e);
		}
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::DISABLE END");
		return status;
	}

	public String grantRight(String username, String accountName, String accessRight, String requestId, String processKey) {
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + "::accessRight=" + accessRight + ":::GRANT START");
		String status = CustomConstants.ADP_RETURN_KO;
		Date requestDate = new Date();
		Map<String, String> attributes;
		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::GRANT::" + attr.getKey() + "=" + attr.getValue());
			String ticketNumber;
			logger.debug("username=" + username + "::GRANT::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatGrantShortDescr(getConfiguration(), accountName);
			logger.debug("username=" + username + "::GRANT::shortDescription=" + shortDescr);
			String description = attributesFormat.formatGrantDescription(username, accessRight, attributes);
			logger.debug("username=" + username + "::GRANT::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::GRANT::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::GRANT::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::GRANT::inserting ticket details in DB");
				db.registerGrant(requestId, ticketNumber, requestDate, username, accessRight, appId);
				logger.debug("username=" + username + "::GRANT::insert in DB completed");
			} catch (Exception e) {
				logger.error("username=" + username + "::GRANT::DB failure", e);
			}
			status = CustomConstants.ADP_RETURN_OK;
		} catch (Exception e) {
			logger.error("username=" + username + "::GRANT ERROR", e);
		}
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::GRANT END");
		return status;
	}

	public String revokeRight(String username, String accountName, String accessRight, String requestId, String processKey) {
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + "::accessRight=" + accessRight + ":::REVOKE START");
		String status = CustomConstants.ADP_RETURN_KO;
		Date requestDate = new Date();
		Map<String, String> attributes;
		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::REVOKE::" + attr.getKey() + "=" + attr.getValue());
			String ticketNumber;
			logger.debug("username=" + username + "::REVOKE::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatRevokeShortDescr(getConfiguration(), accountName);
			logger.debug("username=" + username + "::REVOKE::shortDescription=" + shortDescr);
			String description = attributesFormat.formatRevokeDescription(username, accessRight, attributes);
			logger.debug("username=" + username + "::REVOKE::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::REVOKE::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::REVOKE::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::REVOKE::inserting ticket details in DB");
				db.registerRevoke(requestId, ticketNumber, requestDate, username, accessRight, appId);
				logger.debug("username=" + username + "::REVOKE::insert in DB completed");
			} catch (Exception e) {
				logger.error("username=" + username + "::REVOKE::DB failure", e);
			}
			status = CustomConstants.ADP_RETURN_OK;
		} catch (Exception e) {
			logger.error("username=" + username + "::REVOKE ERROR", e);
		}
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::REVOKE END");
		return status;
	}

	public String delete(String username, String accountName, String requestId, String processKey) {
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::DELETE START");
		String status = CustomConstants.ADP_RETURN_KO;

		Date requestDate = new Date();
		Map<String, String> attributes;
		try {
			TTWOSGenericOimProcessUtils opu = new TTWOSGenericOimProcessUtils(InterfaceManager.getInstance(false));
			configuration.setAppInstanceName(opu.getAppInstanceNameFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup())));
			attributes = opu.getAttributesMap(processKey, this.configuration.getAttrMapLookup());
			for (Map.Entry<String, String> attr : attributes.entrySet())
				logger.debug("username=" + username + "::DELETE::" + attr.getKey() + "=" + attr.getValue());

			String ticketNumber;
			logger.debug("username=" + username + "::DELETE::creating TTWOSServiceAdapter");
			TTWOSGenericServiceAdapter srv = new TTWOSGenericServiceAdapter(configuration);
			String shortDescr = attributesFormat.formatDeleteShortDescr(getConfiguration(), accountName);
			logger.debug("username=" + username + "::DELETE::shortDescription=" + shortDescr);
			String description = attributesFormat.formatDeleteDescription(username, attributes);
			logger.debug("username=" + username + "::DELETE::description=" + description);
			Long appId = opu.getAppInstanceIdFromProcessInstanceKey(processKey, translateItResource(configuration.getAttrMapLookup()));
			logger.debug("username=" + username + "::CREATE::appId=" + appId);
			logger.debug("username=" + username + "::DELETE::invoking TTWOS opCreate...");
			if (!simulationEnabled)
				ticketNumber = srv.opCreate(shortDescr, description);
			else
				ticketNumber = "" + System.currentTimeMillis();
			logger.debug("username=" + username + "::DELETE::ticketNumber=" + ticketNumber);
			logger.debug("username=" + username + "::DELETE::creating DBAccessor");
			try (TTWOSGenericDbAccessor db = new TTWOSGenericDbAccessor(configuration)) {
				logger.debug("username=" + username + "::DELETE::inserting ticket details in DB");
				db.registerDelete(requestId, ticketNumber, requestDate, username, appId);
				logger.debug("username=" + username + "::DELETE::insert in DB completed");

			} catch (Exception e) {
				logger.error("username=" + username + "::DELETE::DB failure", e);
			}
			status = CustomConstants.ADP_RETURN_OK;

		} catch (Exception e) {
			logger.error("username=" + username + "::DELETE ERROR", e);
		}
		logger.debug("username=" + username + "::requestID=" + requestId + "::processKey=" + processKey + ":::DELETE END");
		return status;
	}

	public String removeAllEntitlements(String username, String userKey, String processKey, String childFormName) throws Exception {
		String ret = "SUCCESS";
		logger.debug("username=" + username + "::userKey=" + userKey + ":::REVOKE ALL ROLES START");
		List<EntitlementInstance> list = Platform.getService(ProvisioningService.class).getEntitlementsForUser(userKey);
		logger.debug("username=" + username + "::REMOVE ALL ROLES::roles to be revoked=" + list.size());
		for (EntitlementInstance userEntitlement : list) {
			logger.debug("username=" + username + "::REMOVE ALL ROLES::revoking entitlement instance id=" + userEntitlement.getEntitlementInstanceKey());
			if (userEntitlement.getEntitlement().getFormName().equalsIgnoreCase(childFormName)
					&& userEntitlement.getProcessInstanceKey() == Long.parseLong(processKey)) {
				Platform.getService(ProvisioningService.class).revokeEntitlement(userEntitlement);
				logger.debug("username=" + username + "::REMOVE ALL ROLES::revoking entitlement instance id=" + userEntitlement.getEntitlementInstanceKey());
			}
		}
		logger.debug("username=" + username + "::userKey=" + userKey + ":::REVOKE ALL ROLES END");
		return ret;
	}
}
