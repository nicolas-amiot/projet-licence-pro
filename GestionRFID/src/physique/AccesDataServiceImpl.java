package physique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.Acces;
import metier.Badge;
import metier.Zone;

public class AccesDataServiceImpl implements AccesDataService {

    private final ServiceSQL sql;
    private Connection cnx;
    private PreparedStatement statement;
    private ResultSet result;
    private final BadgeDataService badgeDataSrv;
    private final ZoneDataService zoneDataSrv;

    AccesDataServiceImpl() {
        sql = new ServiceSQL();
        badgeDataSrv = PhysiqueFactory.getBadgeDataService();
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

    private Acces createAcces() throws Exception {
        Badge badge = badgeDataSrv.getByIdBadge(result.getLong("idBadge"));
        Zone zone = zoneDataSrv.getByIdZone(result.getLong("idZone"));
        List<Date> datePassages = new ArrayList<>();
        PreparedStatement statementCreate = null;
        ResultSet resultCreate = null;
        try {
            String query = "SELECT datePassage FROM historique WHERE idBadge = ? AND idZone = ?";
            statementCreate = cnx.prepareStatement(query);
            statementCreate.setLong(1, badge.getIdBadge());
            statementCreate.setLong(2, zone.getIdZone());
            resultCreate = statementCreate.executeQuery();
            while (resultCreate.next()) {
                datePassages.add(new Date(resultCreate.getTimestamp("datePassage").getTime()));
            }
        } finally {
            if (resultCreate != null) {
                resultCreate.close();
            }
            if (statementCreate != null) {
                statementCreate.close();
            }
        }
        Acces acces = new Acces(badge, zone, datePassages);
        return acces;
    }

    @Override
    public List<Acces> addAll(List<Acces> acces) throws Exception {
        try {
            cnx = sql.connect();
            String query = "INSERT INTO acces (idBadge, idZone) VALUES (?, ?)";
            statement = cnx.prepareStatement(query);
            for (Acces a : acces) {
                statement.setLong(1, a.getBadge().getIdBadge());
                statement.setLong(2, a.getZone().getIdZone());
                statement.executeUpdate();
            }
            return acces;
        } finally {
            close();
        }
    }

    @Override
    public boolean removeAll(List<Acces> acces) throws Exception {
        PreparedStatement statementHistorique = null;
        try {
            cnx = sql.connect();
            boolean execute = true;
            String query = "DELETE FROM acces WHERE idBadge = ? AND idZone = ?";
            String queryHistorique = "DELETE FROM historique WHERE idBadge = ? AND idZone = ?";
            statement = cnx.prepareStatement(query);
            statementHistorique = cnx.prepareStatement(queryHistorique);
            for (Acces a : acces) {
                statement.setLong(1, a.getBadge().getIdBadge());
                statement.setLong(2, a.getZone().getIdZone());
                statementHistorique.setLong(1, a.getBadge().getIdBadge());
                statementHistorique.setLong(2, a.getZone().getIdZone());
                if (statement.executeUpdate() == 0) {
                    execute = false;
                } else {
                    statementHistorique.executeUpdate();
                }
            }
            return execute;
        } finally {
            if(statementHistorique != null){
                statementHistorique.close();
            }
            close();
        }
    }

    @Override
    public Acces getByIdAcces(long idBadge, long idZone) throws Exception {
        try {
            cnx = sql.connect();
            Acces acces = null;
            String query = "SELECT * FROM acces WHERE idBadge = ? AND idZone = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, idBadge);
            statement.setLong(2, idZone);
            result = statement.executeQuery();
            if (result.next()) {
                acces = createAcces();
            }
            return acces;
        } finally {
            close();
        }
    }

    @Override
    public List<Acces> getByBadge(Badge badge) throws Exception {
        try {
            cnx = sql.connect();
            List<Acces> acces = new ArrayList<>();
            String query = "SELECT * FROM acces WHERE idBadge = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, badge.getIdBadge());
            result = statement.executeQuery();
            while (result.next()) {
                acces.add(createAcces());
            }
            return acces;
        } finally {
            close();
        }
    }

    @Override
    public List<Acces> getByZone(Zone zone) throws Exception {
        try {
            cnx = sql.connect();
            List<Acces> acces = new ArrayList<>();
            String query = "SELECT * FROM acces WHERE idZone = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, zone.getIdZone());
            result = statement.executeQuery();
            while (result.next()) {
                acces.add(createAcces());
            }
            return acces;
        } finally {
            close();
        }
    }

}
