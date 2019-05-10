package eu.reply.vodafone.prokey.connector;

public class OIMLookupException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6014092278887224981L;

	public OIMLookupException(Throwable throwable) {
        super(throwable);
    }

    public OIMLookupException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public OIMLookupException(String string) {
        super(string);
    }

    public OIMLookupException() {
        super();
    }
}
