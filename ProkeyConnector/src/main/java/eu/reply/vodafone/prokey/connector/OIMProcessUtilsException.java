package eu.reply.vodafone.prokey.connector;

public class OIMProcessUtilsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1819790261471156224L;

	public OIMProcessUtilsException(Throwable throwable) {
        super(throwable);
    }

    public OIMProcessUtilsException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public OIMProcessUtilsException(String string) {
        super(string);
    }

    public OIMProcessUtilsException() {
        super();
    }
}
