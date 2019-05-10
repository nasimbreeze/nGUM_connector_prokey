package eu.reply.vodafone.prokey.connector;

import java.util.Map;

public class TTWOSGenericAttributesFormat {

	private static final String USERLOGIN = "User Login";
	private static final String E_MAIL = "Mail address";
	private static final String LAST_NAME = "Last Name";
	private static final String FIRST_NAME = "First Name";
	private static final String NGUM_LDAP_UID = "D2LDAP UID";
	
	private static final String PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY = "nGUM / Prokey:";
	
	public String formatCreateDescription(String username, Map<String, String> attributes) {
		return "\n nGUM unique identifier: " + username + "\n" + "D2LDAP UID: " + attributes.get(NGUM_LDAP_UID) + "\n" + "First Name: " + attributes.get(FIRST_NAME) + "\n" + "Last Name: " + attributes.get(LAST_NAME)
				+ "\n" + "Mail Address: " + attributes.get(E_MAIL) + "\n";
	}

	public String formatCreateShortDescr(TTWOSConnectorConfiguration cfg, Map<String, String> attributes) {
		String descr = PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Create account";
		return descr;

	}

	public String formatUpdateDescription(String username, Map<String, String> attributes) {
		return formatCreateDescription(username, attributes);
	}

	public String formatUpdateShortDescr(TTWOSConnectorConfiguration cfg, String accountName) {
		String descr = PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Change account data for "+accountName;
		return descr;
	}

	public String formatEnableDescription(String username, Map<String, String> attributes) {
		return  formatCreateDescription(username, attributes);
	}

	public String formatEnableShortDescr(TTWOSConnectorConfiguration cfg, String accountName) {
		String descr = PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Enable (Re-Join) account "+accountName;
		return descr;	}

	public String formatDisableDescription(String username, Map<String, String> attributes) {
		return formatCreateDescription(username, attributes);
	}

	public String formatDisableShortDescr(TTWOSConnectorConfiguration cfg, String accountName) {
		String descr =  PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Disable (Suspend) account "+accountName;
		return descr;
		}

	public String formatGrantDescription(String username, String accessRight, Map<String, String> attributes) {
		String accessRightName = null;
		String[] splitted = accessRight.split("~");
		if (splitted.length == 2)
			accessRightName = splitted[1];
		else
			accessRightName = accessRight;
		return "Mail Address: " + attributes.get(E_MAIL) + "\n" + "D2LDAP UID: " + attributes.get(NGUM_LDAP_UID) + "\n" + "Access Right Name: " + accessRightName;
	}

	public String formatGrantShortDescr(TTWOSConnectorConfiguration cfg, String accountName) {
		String descr = PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Grant role for "+accountName;
		return descr;
	}

	public String formatRevokeDescription(String username, String accessRight, Map<String, String> attributes) {
		String accessRightName = null;
		String[] splitted = accessRight.split("~");
		if (splitted.length == 2)
			accessRightName = splitted[1];
		else
			accessRightName = accessRight;
		return "Mail Address: " + attributes.get(E_MAIL) + "\n" + "D2LDAP UID: " + attributes.get(NGUM_LDAP_UID) + "\n" + "Access Right Name: " + accessRightName;

	}

	public String formatRevokeShortDescr(TTWOSConnectorConfiguration cfg, String accountName) {
		String descr = PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Revoke role for "+accountName;
		return descr;
	}

	public String formatDeleteDescription(String username, Map<String, String> attributes) {
		return formatCreateDescription(username, attributes);
	}

	public String formatDeleteShortDescr(TTWOSConnectorConfiguration cfg, String accountName) {
		String descr = PLACEHOLDER_SHORT_DESCRIPTION_PER_PROKEY +" Delete (Terminate) account "+accountName;
		return descr;
	}

}
