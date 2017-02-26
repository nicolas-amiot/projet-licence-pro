package physique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import metier.Badge;
import metier.Personne;

public class PersonneDataServiceImpl implements PersonneDataService {

    private final ServiceSQL sql;
    private Connection cnx;
    private PreparedStatement statement;
    private ResultSet result;
    private final BadgeDataService badgeDataSrv;

    PersonneDataServiceImpl() {
        sql = new ServiceSQL();
        badgeDataSrv = PhysiqueFactory.getBadgeDataService();
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

    private Personne createPersonne() throws Exception {
        long idPersonne = result.getLong("idPersonne");
        String nom = result.getString("nom");
        String prenom = result.getString("prenom");
        Badge badge = null;
        PreparedStatement statementCreate = null;
        ResultSet resultCreate = null;
        try {
            String query = "SELECT idBadge FROM badge WHERE idPersonne = ?";
            statementCreate = cnx.prepareStatement(query);
            statementCreate.setLong(1, idPersonne);
            resultCreate = statementCreate.executeQuery();
            if (resultCreate.next()) {
                badge = badgeDataSrv.getByIdBadge(resultCreate.getLong("idBadge"));
            }
        } finally {
            if (resultCreate != null) {
                resultCreate.close();
            }
            if (statementCreate != null) {
                statementCreate.close();
            }
        }
        Personne personne = new Personne(nom, prenom, badge);
        personne.setIdPersonne(idPersonne);
        return personne;
    }

    @Override
    public Personne add(Personne personne) throws Exception {
        try {
            cnx = sql.connect();
            String query = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
            statement = cnx.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, personne.getNom());
            statement.setString(2, personne.getPrenom());
            if (statement.executeUpdate() > 0) {
                result = statement.getGeneratedKeys();
                if (result.next()) {
                    personne.setIdPersonne(result.getLong(1));
                }
            }
            return personne;
        } finally {
            close();
        }
    }

    @Override
    public boolean remove(Personne personne) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM personne WHERE idPersonne = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, personne.getIdPersonne());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public boolean update(Personne personne) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "UPDATE personne SET nom = ?, prenom = ? WHERE idPersonne = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, personne.getNom());
            statement.setString(2, personne.getPrenom());
            statement.setLong(3, personne.getIdPersonne());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public Personne getByIdPersonne(long idPersonne) throws Exception {
        try {
            cnx = sql.connect();
            Personne metrologue = null;
            String query = "SELECT * FROM personne WHERE idPersonne = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, idPersonne);
            result = statement.executeQuery();
            if (result.next()) {
                metrologue = createPersonne();
            }
            return metrologue;
        } finally {
            close();
        }
    }

    @Override
    public Personne getByBadge(Badge badge) throws Exception {
        try {
            cnx = sql.connect();
            Personne personne = null;
            String query = "SELECT * FROM personne WHERE idPersonne = (SELECT idPersonne FROM badge WHERE idBadge = ?)";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, badge.getIdBadge());
            result = statement.executeQuery();
            if (result.next()) {
                personne = createPersonne();
            }
            return personne;
        } finally {
            close();
        }
    }

    @Override
    public Personne getByNomPrenom(String nom, String prenom) throws Exception  {
        try {
            cnx = sql.connect();
            Personne personne = null;
            String query = "SELECT * FROM personne WHERE nom = ? AND prenom = ? ORDER BY nom, prenom ASC";
            statement = cnx.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            result = statement.executeQuery();
            if (result.next()) {
                personne = createPersonne();
            }
            return personne;
        } finally {
            close();
        }
    }

}
