package physique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.Badge;

public class BadgeDataServiceImpl implements BadgeDataService {

    private final ServiceSQL sql;
    private Connection cnx;
    private PreparedStatement statement;
    private ResultSet result;

    BadgeDataServiceImpl() {
        sql = new ServiceSQL();
    }

    private void close() throws Exception {
        if (result != null) {
            result.close();
            result = null;
        }
        if (statement != null) {
            statement.close();
            statement = null;
        }
        sql.disconnect();
    }

    private Badge createBadge() throws Exception {
        long idBadge = result.getLong("idBadge");
        String numero = result.getString("numero");
        String password = result.getString("password");
        Date dateCreation = result.getDate("dateCreation");
        Badge badge = new Badge(numero, password, dateCreation);
        badge.setIdBadge(idBadge);
        return badge;
    }

    @Override
    public Badge add(Badge badge, long idPersonne) throws Exception {
        try {
            cnx = sql.connect();
            String query = "INSERT INTO badge (numero, password, dateCreation, idPersonne) VALUES (?, ?, ?, ?)";
            statement = cnx.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, badge.getNumero());
            statement.setString(2, badge.getPassword());
            statement.setDate(3, new java.sql.Date(badge.getDateCreation().getTime()));
            statement.setLong(4, idPersonne);
            System.err.println("");
            if (statement.executeUpdate() > 0) {
                result = statement.getGeneratedKeys();
                if (result.next()) {
                    badge.setIdBadge(result.getLong(1));
                }
            }
            return badge;
        } finally {
            close();
        }
    }

    @Override
    public boolean remove(Badge badge) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM badge WHERE idBadge = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, badge.getIdBadge());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public boolean update(Badge badge) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "UPDATE badge SET numero = ?, password = ? WHERE idBadge = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, badge.getNumero());
            statement.setString(2, badge.getPassword());
            statement.setLong(3, badge.getIdBadge());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public Badge getByIdBadge(long idBadge) throws Exception {
        try {
            cnx = sql.connect();
            Badge badge = null;
            String query = "SELECT * FROM badge WHERE idBadge = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, idBadge);
            result = statement.executeQuery();
            if (result.next()) {
                badge = createBadge();
            }
            return badge;
        } finally {
            close();
        }
    }

    @Override
    public Badge getByNumero(String numero) throws Exception {
        try {
            cnx = sql.connect();
            Badge badge = null;
            String query = "SELECT * FROM badge WHERE numero = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, numero);
            result = statement.executeQuery();
            if (result.next()) {
                badge = createBadge();
            }
            return badge;
        } finally {
            close();
        }
    }

    @Override
    public List<String> getListNumero() throws Exception {
        try {
            cnx = sql.connect();
            List<String> numeors = new ArrayList<>();
            String query = "SELECT numero FROM badge ORDER BY numero ASC";
            statement = cnx.prepareStatement(query);
            result = statement.executeQuery();
            while (result.next()) {
                numeors.add(result.getString("numero"));
            }
            return numeors;
        } finally {
            close();
        }
    }
}
