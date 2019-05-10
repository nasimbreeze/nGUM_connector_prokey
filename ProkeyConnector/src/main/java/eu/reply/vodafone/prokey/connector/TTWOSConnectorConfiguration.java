package eu.reply.vodafone.prokey.connector;

public class TTWOSConnectorConfiguration {
	private String environment;
	private String attrMapLookup;

	private String serviceWsdlUrl;
	private String serviceUser;
	private String servicePwd;

	private String basicAuthEnabled;
	private String serviceHttpUser;
	private String serviceHttpPwd;

	private String dbDataSource;

	private String subjectCategory1;
	private String subjectCategory2;
	private String subjectCategory3;

	private String shortDescrCreation;
	private String shortDescrGrantAr;
	private String shortDescrRevokeAr;
	private String shortDescrUpdate;
	private String shortDescrSuspension;
	private String shortDescrReJoin;
	private String shortDescrTermination;

	private String appInstanceName;
	

	public TTWOSConnectorConfiguration() {
		super();
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getServiceWsdlUrl() {
		return serviceWsdlUrl;
	}

	public void setServiceWsdlUrl(String serviceWsdlUrl) {
		this.serviceWsdlUrl = serviceWsdlUrl;
	}

	public String getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(String serviceUser) {
		this.serviceUser = serviceUser;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getServiceHttpUser() {
		return serviceHttpUser;
	}

	public void setServiceHttpUser(String serviceHttpUser) {
		this.serviceHttpUser = serviceHttpUser;
	}

	public String getServiceHttpPwd() {
		return serviceHttpPwd;
	}

	public void setServiceHttpPwd(String serviceHttpPwd) {
		this.serviceHttpPwd = serviceHttpPwd;
	}

	public String getDbDataSource() {
		return dbDataSource;
	}

	public void setDbDataSource(String dbDataSource) {
		this.dbDataSource = dbDataSource;
	}

	public String getAttrMapLookup() {
		return attrMapLookup;
	}

	public void setAttrMapLookup(String attrMapLookup) {
		this.attrMapLookup = attrMapLookup;
	}

	public String getBasicAuthEnabled() {
		return basicAuthEnabled;
	}

	public void setBasicAuthEnabled(String basicAuthEnabled) {
		this.basicAuthEnabled = basicAuthEnabled;
	}

	public String getSubjectCategory1() {
		return subjectCategory1;
	}

	public void setSubjectCategory1(String subjectCategory1) {
		this.subjectCategory1 = subjectCategory1;
	}

	public String getSubjectCategory2() {
		return subjectCategory2;
	}

	public void setSubjectCategory2(String subjectCategory2) {
		this.subjectCategory2 = subjectCategory2;
	}

	public String getSubjectCategory3() {
		return subjectCategory3;
	}

	public void setSubjectCategory3(String subjectCategory3) {
		this.subjectCategory3 = subjectCategory3;
	}

	public String getAppInstanceName() {
		return appInstanceName;
	}

	public void setAppInstanceName(String appInstanceName) {
		this.appInstanceName = appInstanceName;
	}

	public String getShortDescrCreation() {
		return shortDescrCreation;
	}

	public void setShortDescrCreation(String shortDescrCreation) {
		this.shortDescrCreation = shortDescrCreation;
	}

	public String getShortDescrGrantAr() {
		return shortDescrGrantAr;
	}

	public void setShortDescrGrantAr(String shortDescrGrantAr) {
		this.shortDescrGrantAr = shortDescrGrantAr;
	}

	public String getShortDescrRevokeAr() {
		return shortDescrRevokeAr;
	}

	public void setShortDescrRevokeAr(String shortDescrRevokeAr) {
		this.shortDescrRevokeAr = shortDescrRevokeAr;
	}

	public String getShortDescrUpdate() {
		return shortDescrUpdate;
	}

	public void setShortDescrUpdate(String shortDescrUpdate) {
		this.shortDescrUpdate = shortDescrUpdate;
	}

	public String getShortDescrSuspension() {
		return shortDescrSuspension;
	}

	public void setShortDescrSuspension(String shortDescrSuspension) {
		this.shortDescrSuspension = shortDescrSuspension;
	}

	public String getShortDescrReJoin() {
		return shortDescrReJoin;
	}

	public void setShortDescrReJoin(String shortDescrReJoin) {
		this.shortDescrReJoin = shortDescrReJoin;
	}

	public String getShortDescrTermination() {
		return shortDescrTermination;
	}

	public void setShortDescrTermination(String shortDescrTermination) {
		this.shortDescrTermination = shortDescrTermination;
	}

}
