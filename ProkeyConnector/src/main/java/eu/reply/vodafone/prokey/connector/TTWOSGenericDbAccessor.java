package eu.reply.vodafone.prokey.connector;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TTWOSGenericDbAccessor implements Closeable {
	private Connection conn;
	
	private static final String STMT =
			"insert into COMSAFE_TICKET_STATUS (request_id, ticket_id, req_date, user_login, action, acc_right, app_instance_id) " +
			"values (? , ? , ? , ? , ? , ?, ?)";
	
	public TTWOSGenericDbAccessor(TTWOSConnectorConfiguration cfg) {
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup(cfg.getDbDataSource());
			conn = ds.getConnection();
			//conn.setAutoCommit(true);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}		
	}	

	
	private PreparedStatement createStatement() throws Exception {
        return conn.prepareStatement(STMT);
    }
	
	public void registerCreate(String requestId, String ticketId, Date requestDate, String userLogin, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "CREATE");
			stmt.setNull(i++, Types.VARCHAR);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void registerUpdate(String requestId, String ticketId, Date requestDate, String userLogin, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "UPDATE");
			stmt.setNull(i++, Types.VARCHAR);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void registerEnable(String requestId, String ticketId, Date requestDate, String userLogin, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "ENABLE");
			stmt.setNull(i++, Types.VARCHAR);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void registerDisable(String requestId, String ticketId, Date requestDate, String userLogin, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "DISABLE");
			stmt.setNull(i++, Types.VARCHAR);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void registerGrant(String requestId, String ticketId, Date requestDate, String userLogin, String accessRight, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "GRANT");
			stmt.setString(i++, accessRight);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void registerRevoke(String requestId, String ticketId, Date requestDate, String userLogin, String accessRight, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "REVOKE");
			stmt.setString(i++, accessRight);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void registerDelete(String requestId, String ticketId, Date requestDate, String userLogin, Long appId) {
		try(PreparedStatement stmt = createStatement()) {
			int i = 1;
			stmt.setString(i++, requestId);
			stmt.setString(i++, ticketId);
			stmt.setTimestamp(i++, new Timestamp(requestDate.getTime()));
			stmt.setString(i++, userLogin);
			stmt.setString(i++, "DELETE");
			stmt.setNull(i++, Types.VARCHAR);
			stmt.setLong(i++, appId);
			stmt.execute();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void close() {
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
