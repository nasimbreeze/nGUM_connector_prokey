package eu.reply.vodafone.prokey.connector;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import mmo_it_service_request_management.AuthenticationInfo;
import mmo_it_service_request_management.MMOITServiceRequestManagementPortTypePortType;
import mmo_it_service_request_management.MMOITServiceRequestManagementService;
import ttwos.StatusType;
import ttwos.UseCaseITCreateType;

public class TTWOSGenericServiceAdapter {

	private TTWOSConnectorConfiguration configuration;

	public TTWOSGenericServiceAdapter(TTWOSConnectorConfiguration configuration) {
		this.configuration = configuration;
	}

	private MMOITServiceRequestManagementPortTypePortType getService() {
		URL wsdlLocation;
		try {
			wsdlLocation = new URL(this.configuration.getServiceWsdlUrl());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String username = this.configuration.getServiceHttpUser();
		String password = this.configuration.getServiceHttpPwd();
		MMOITServiceRequestManagementService service = new MMOITServiceRequestManagementService(wsdlLocation,
				new QName("urn:MMO_IT_Service_Request_Management", "MMO_IT_Service_Request_ManagementService"));
		MMOITServiceRequestManagementPortTypePortType portType = service.getMMOITServiceRequestManagementPortTypeSoap();
		if ("true".equalsIgnoreCase(this.configuration.getBasicAuthEnabled())) {
			Map<String, Object> requestContext = ((BindingProvider) portType).getRequestContext();
			requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
			requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		}
		return portType;
	}

	public String opCreate(String shortDescr, String description) {
		MMOITServiceRequestManagementPortTypePortType srv = getService();
		AuthenticationInfo authInfo = new AuthenticationInfo();
		authInfo.setUserName(this.configuration.getServiceUser());
		authInfo.setPassword(this.configuration.getServicePwd());
		String creator = CustomConstants.REQUEST_CREATOR;
		String subject = CustomConstants.REQUEST_SUBJECT;
		UseCaseITCreateType it = new UseCaseITCreateType();
		it.setSubject(subject);
		it.setKurzbeschreibung(shortDescr); // Kurzbeschreibung= summary
		Holder<String> ticketNumber = new Holder<>();
		Holder<String> ticketURL = new Holder<>();
		Holder<StatusType> status = new Holder<>();
		Holder<String> ttwosAssigneeSection = new Holder<>();
		srv.opCreate(creator, null, null, null, null, description, null, null, null, null, it, null, null, ticketNumber, ticketURL, status,
				ttwosAssigneeSection, authInfo);
		return ticketNumber.value;
	}

}
