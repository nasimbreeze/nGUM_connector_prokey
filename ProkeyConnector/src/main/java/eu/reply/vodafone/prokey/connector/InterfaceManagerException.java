package eu.reply.vodafone.prokey.connector;

public class InterfaceManagerException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2635111949852567363L;

	public InterfaceManagerException(Throwable throwable) {
        super(throwable);
    }

    public InterfaceManagerException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InterfaceManagerException(String string) {
        super(string);
    }

    public InterfaceManagerException() {
        super();
    }
	    
}
