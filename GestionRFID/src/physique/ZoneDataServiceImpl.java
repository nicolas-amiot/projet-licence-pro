package physique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import metier.Batiment;
import metier.Lecteur;
import metier.Zone;

public class ZoneDataServiceImpl implements ZoneDataService {

    private final ServiceSQL sql;
    private Connection cnx;
    private PreparedStatement statement;
    private ResultSet result;
    private final LecteurDataService lecteurDataSrv;

    ZoneDataServiceImpl() {
        sql = new ServiceSQL();
        lecteurDataSrv = PhysiqueFactory.getLecteurDataService();
    }

    private void close() throws Exception {
        if (result != null) {
            result.close();
        }
        if (statement != null) {
            statement.close();
            statement = null;
        }
        sql.disconnect();
    }

    private Zone createZone() throws Exception {
        long idZone = result.getLong("idZone");
        String nomZone = result.getString("nomZone");
        boolean sensible = result.getBoolean("sensible");
        String horaireOuverture = result.getString("horaireOuverture");
        String horaireFermeture = result.getString("horaireFermeture");
        List<Lecteur> lecteurs = new ArrayList<>();
        PreparedStatement statementCreate = null;
        ResultSet resultCreate = null;
        try {
            String query = "SELECT idLecteur FROM lecteur WHERE idZone = ? ORDER BY ip";
            statementCreate = cnx.prepareStatement(query);
            statementCreate.setLong(1, idZone);
            resultCreate = statementCreate.executeQuery();
            while (resultCreate.next()) {
                lecteurs.add(lecteurDataSrv.getByIdLecteur(resultCreate.getLong("idLecteur")));
            }
        } finally {
            if (resultCreate != null) {
                resultCreate.close();
            }
            if (statementCreate != null) {
                statementCreate.close();
            }
        }
        Zone zone = new Zone(nomZone, sensible, horaireOuverture, horaireFermeture, lecteurs);
        zone.setIdZone(idZone);
        return zone;
    }

    @Override
    public Zone add(Zone zone, long idBatiment) throws Exception {
        try {
            cnx = sql.connect();
            String query = "INSERT INTO zone (nomZone, sensible, horaireOuverture, horaireFermeture, idBatiment) VALUES (?, ?, ?, ?, ?)";
            statement = cnx.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, zone.getNomZone());
            statement.setBoolean(2, zone.getSensible());
            statement.setString(3, zone.getHoraireOuverture());
            statement.setString(4, zone.getHoraireFermeture());
            statement.setLong(5, idBatiment);
            if (statement.executeUpdate() > 0) {
                result = statement.getGeneratedKeys();
                if (result.next()) {
                    zone.setIdZone(result.getLong(1));
                }
            }
            return zone;
        } finally {
            close();
        }
    }

    @Override
    public boolean remove(Zone zone) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM zone WHERE idZone = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, zone.getIdZone());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }
    
    @Override
    public boolean removeByBatiment(Batiment batiment) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM zone WHERE idBatiment = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, batiment.getIdBatiment());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public boolean update(Zone zone) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "UPDATE zone SET nomZone = ?, sensible = ?, horaireOuverture = ?, horaireFermeture = ? WHERE idZone = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, zone.getNomZone());
            statement.setBoolean(2, zone.getSensible());
            statement.setString(3, zone.getHoraireOuverture());
            statement.setString(4, zone.getHoraireFermeture());
            statement.setLong(5, zone.getIdZone());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public Zone getByIdZone(long idZone) throws Exception {
        try {
            cnx = sql.connect();
            Zone zone = null;
            String query = "SELECT * FROM zone WHERE idZone = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, idZone);
            result = statement.executeQuery();
            if (result.next()) {
                zone = createZone();
            }
            return zone;
        } finally {
            close();
        }
    }

    @Override
    public Zone getByNomZone(String nomZone) throws Exception {
        try {
            cnx = sql.connect();
            Zone zone = null;
            String query = "SELECT * FROM zone WHERE nomZone = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, nomZone);
            result = statement.executeQuery();
            if (result.next()) {
                zone = createZone();
            }
            return zone;
        } finally {
            close();
        }
    }

    @Override
    public List<String> getListNomZone() throws Exception {
        try {
            cnx = sql.connect();
            List<String> nomZones = new ArrayList<>();
            String query = "SELECT nomZone FROM zone ORDER BY nomZone ASC";
            statement = cnx.prepareStatement(query);
            result = statement.executeQuery();
            while (result.next()) {
                nomZones.add(result.getString("nomZone"));
            }
            return nomZones;
        } finally {
            close();
        }
    }

}
