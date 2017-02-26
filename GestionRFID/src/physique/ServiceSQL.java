package physique;

import java.sql.Connection;
import java.sql.DriverManager;

public class ServiceSQL {
    
    private Connection cnx;

    private final String dbHost;
    private final int dbPort;
    private final String dbName;
    private final String dbUser;
    private final String dbPasswd;
    private final String dbDriver;
    private final String dbURL;

    protected ServiceSQL() {
        dbHost = "localhost";
        dbPort = 3306;
        dbName = "controle_acces";
        dbUser = "user";
        dbPasswd = "user";
        dbDriver = "com.mysql.jdbc.Driver";
        dbURL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
    }

    public Connection connect() throws Exception {
        if(this.cnx == null){
            Class.forName(dbDriver);
            this.cnx = DriverManager.getConnection(this.dbURL, this.dbUser, this.dbPasswd);
        }
        return this.cnx;
    }
    
    public void disconnect() throws Exception {
        if(this.cnx != null){
            this.cnx.close();// Sous java les instructions sont en mode auto-commit
            this.cnx = null;
        }
    }
}