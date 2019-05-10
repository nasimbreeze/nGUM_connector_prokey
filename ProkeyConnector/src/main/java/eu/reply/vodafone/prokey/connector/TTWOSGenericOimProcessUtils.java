package eu.reply.vodafone.prokey.connector;

import java.util.HashMap;
import java.util.Map;

import com.thortech.util.logging.Logger;

import Thor.API.tcResultSet;
import Thor.API.Exceptions.tcAPIException;
import Thor.API.Exceptions.tcColumnNotFoundException;
import Thor.API.Exceptions.tcFormNotFoundException;
import Thor.API.Exceptions.tcInvalidLookupException;
import Thor.API.Exceptions.tcNotAtomicProcessException;
import Thor.API.Exceptions.tcProcessNotFoundException;
import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import Thor.API.Operations.tcLookupOperationsIntf;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.ApplicationInstanceService;
import oracle.iam.provisioning.exception.GenericAppInstanceServiceException;
import oracle.iam.provisioning.vo.ApplicationInstance;

public class TTWOSGenericOimProcessUtils {
	private InterfaceManager intfMgr;
	private static Logger logger = Logger.getLogger(CustomConstants.CONNECTOR_LOGGER);
	private tcFormInstanceOperationsIntf tcFormInOpsIntf;
	private tcLookupOperationsIntf tcLookOpsIntf;
	private tcFormDefinitionOperationsIntf tcFormDefOpsIntf;
	private ApplicationInstanceService appInstanceService;
	private String attrMapLookupName;

	public TTWOSGenericOimProcessUtils() {
		super();
	}

	public TTWOSGenericOimProcessUtils(InterfaceManager intfMgr) throws InterfaceManagerException {
		this.intfMgr = intfMgr;
		this.tcFormInOpsIntf = this.intfMgr.getLegacyFormInstanceOpsIntf();
		this.tcFormDefOpsIntf = this.intfMgr.getLegacyFormDefinitionOpsIntf();
		this.tcLookOpsIntf = this.intfMgr.getLegacyLookupOpsIntf();
		this.appInstanceService = this.intfMgr.getApplicationInstanceService();
	}

	public Map<String, String> getAttributesMap(String processFormKey, String lookupName) throws OIMLookupException, OIMProcessUtilsException {
		Map attributes = null;
		Map lookupAtMapTPS2 = getLookup(lookupName);
		logger.debug("lookupName: "+lookupName);
		attributes = getProcessFormData(processFormKey, lookupAtMapTPS2);
		return attributes;
	}

	private Map<String, String> getProcessFormData(String processFormKey, Map<String, String> lookup) throws OIMProcessUtilsException {
		tcResultSet resultSet = null;
		Map processFormData = new HashMap<>();
		long processFormKey_long=new Long(processFormKey).longValue();
		try {
			logger.debug("processFormKey_long: "+processFormKey_long);
			resultSet = this.tcFormInOpsIntf.getProcessFormData(processFormKey_long);
			logger.debug("resultSet done");
			for (String colname : resultSet.getColumnNames())
				logger.debug("getProcessFormData:::::colname=" + colname);

			for (Map.Entry entry : lookup.entrySet()) {
				logger.debug("getProcessFormData:::::entry=" + entry.getKey() + "::" + entry.getValue());
				logger.debug("getProcessFormData:::::resultset.value" + resultSet.getStringValue((String) entry.getKey()));
				processFormData.put(entry.getValue(), resultSet.getStringValue((String) entry.getKey()));
			}

			// aggiungo la objGUID
			// processFormData.put(CustomConstants.SPML_ATTR_NAME_OBJECTGUID,
			// resultSet.getStringValue(CustomConstants.FORM_FIELD_OBJGUID));

		} catch (NumberFormatException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcAPIException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcNotAtomicProcessException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcFormNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcProcessNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcColumnNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		}

		return processFormData;
	}

	public Map<String, String> getLookup(String lookupName) throws OIMLookupException {
		Map lookup = new HashMap();
		try {
			tcResultSet resultSet = this.tcLookOpsIntf.getLookupValues(lookupName);

			for (int i = 0; i < resultSet.getTotalRowCount(); i++) {
				resultSet.goToRow(i);
				lookup.put(resultSet.getStringValue("Lookup Definition.Lookup Code Information.Code Key"),
						resultSet.getStringValue("Lookup Definition.Lookup Code Information.Decode"));
			}
		} catch (tcAPIException e) {
			throw new OIMLookupException(e);
		} catch (tcInvalidLookupException e) {
			throw new OIMLookupException("Can't find specified lookup", e);
		} catch (tcColumnNotFoundException e) {
			throw new OIMLookupException(e);
		}

		return lookup;
	}

	public String findInLookUp(String key, String lookupName, String itResource) throws OIMLookupException {
		return getLookup(lookupName).get(key);
	}

	public Long getAppInstanceIdFromProcessInstanceKey(String processFormKey, String itResourceIn) throws OIMProcessUtilsException {
		Long result = null;
		String itResource = null;
		tcResultSet resultSet = null;

		// get it resouce key from process instance key
		try {
			resultSet = this.tcFormInOpsIntf.getProcessFormData(new Long(processFormKey).longValue());
			itResource = resultSet.getStringValue(itResourceIn);

		} catch (NumberFormatException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcAPIException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcNotAtomicProcessException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcFormNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcProcessNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcColumnNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		}

		// get app instance id from it resource key
		SearchCriteria criteria = new SearchCriteria(ApplicationInstance.ITRES_KEY, itResource, SearchCriteria.Operator.EQUAL);

		try {
			result = this.appInstanceService.findApplicationInstance(criteria, new HashMap<String, Object>()).get(0).getApplicationInstanceKey();
		} catch (GenericAppInstanceServiceException e) {
			throw new OIMProcessUtilsException(e);
		}

		return result;
	}

	public String getAppInstanceNameFromProcessInstanceKey(String processFormKey, String itResourceIn) throws OIMProcessUtilsException {
		String result = null;
		String itResource = null;
		tcResultSet resultSet = null;

		// get it resouce key from process instance key
		try {
			resultSet = this.tcFormInOpsIntf.getProcessFormData(new Long(processFormKey).longValue());
			itResource = resultSet.getStringValue(itResourceIn);

		} catch (NumberFormatException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcAPIException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcNotAtomicProcessException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcFormNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcProcessNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		} catch (tcColumnNotFoundException e) {
			throw new OIMProcessUtilsException(e);
		}

		// get app instance id from it resource key
		SearchCriteria criteria = new SearchCriteria(ApplicationInstance.ITRES_KEY, itResource, SearchCriteria.Operator.EQUAL);

		try {
			result = this.appInstanceService.findApplicationInstance(criteria, new HashMap<String, Object>()).get(0).getDisplayName();
		} catch (GenericAppInstanceServiceException e) {
			throw new OIMProcessUtilsException(e);
		}

		return result;
	}
}