package physique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import metier.Batiment;
import metier.Zone;

public class BatimentDataServiceImpl implements BatimentDataService {

    private final ServiceSQL sql;
    private Connection cnx;
    private PreparedStatement statement;
    private ResultSet result;
    private final ZoneDataService zoneDataSrv;

    BatimentDataServiceImpl() {
        sql = new ServiceSQL();
        zoneDataSrv = PhysiqueFactory.getZoneDataService();
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

    private Batiment createBatiment() throws Exception {
        long idBatiment = result.getLong("idBatiment");
        String nomBatiment = result.getString("nomBatiment");
        List<Zone> zones = new ArrayList<>();
        PreparedStatement statementCreate = null;
        ResultSet resultCreate = null;
        try {
            String query = "SELECT idZone FROM zone WHERE idBatiment = ? ORDER BY nomZone";
            statementCreate = cnx.prepareStatement(query);
            statementCreate.setLong(1, idBatiment);
            resultCreate = statementCreate.executeQuery();
            while (resultCreate.next()) {
                zones.add(zoneDataSrv.getByIdZone(resultCreate.getLong("idZone")));
            }
        } finally {
            if (resultCreate != null) {
                resultCreate.close();
            }
            if (statementCreate != null) {
                statementCreate.close();
            }
        }
        Batiment batiment = new Batiment(nomBatiment, zones);
        batiment.setIdBatiment(idBatiment);
        return batiment;
    }

    @Override
    public Batiment add(Batiment batiment) throws Exception {
        try {
            cnx = sql.connect();
            String query = "INSERT INTO batiment (nomBatiment) VALUES (?)";
            statement = cnx.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, batiment.getNomBatiment());
            if (statement.executeUpdate() > 0) {
                result = statement.getGeneratedKeys();
                if (result.next()) {
                    batiment.setIdBatiment(result.getLong(1));
                }
            }
            return batiment;
        } finally {
            close();
        }
    }

    @Override
    public boolean remove(Batiment batiment) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM batiment WHERE idBatiment = ?";
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
    public boolean update(Batiment batiment) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "UPDATE batiment SET nomBatiment = ? WHERE idBatiment = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, batiment.getNomBatiment());
            statement.setLong(2, batiment.getIdBatiment());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public Batiment getByIdBatiment(long idBatiment) throws Exception {
        try {
            cnx = sql.connect();
            Batiment batiment = null;
            String query = "SELECT * FROM batiment WHERE idBatiment = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, idBatiment);
            result = statement.executeQuery();
            if (result.next()) {
                batiment = createBatiment();
            }
            return batiment;
        } finally {
            close();
        }
    }

    @Override
    public Batiment getByNomBatiment(String nomBatiment) throws Exception {
        try {
            cnx = sql.connect();
            Batiment batiment = null;
            String query = "SELECT * FROM batiment WHERE nomBatiment = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, nomBatiment);
            result = statement.executeQuery();
            if (result.next()) {
                batiment = createBatiment();
            }
            return batiment;
        } finally {
            close();
        }
    }

    @Override
    public List<String> getListNomBatiment() throws Exception {
        try {
            cnx = sql.connect();
            List<String> nomBatiments = new ArrayList<>();
            String query = "SELECT nomBatiment FROM batiment ORDER BY nomBatiment ASC";
            statement = cnx.prepareStatement(query);
            result = statement.executeQuery();
            while (result.next()) {
                nomBatiments.add(result.getString("nomBatiment"));
            }
            return nomBatiments;
        } finally {
            close();
        }
    }
}
