package physique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import metier.Lecteur;
import metier.Zone;

public class LecteurDataServiceImpl implements LecteurDataService {

    private final ServiceSQL sql;
    private Connection cnx;
    private PreparedStatement statement;
    private ResultSet result;

    LecteurDataServiceImpl() {
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

    private Lecteur createLecteur() throws Exception {
        long idLecteur = result.getLong("idLecteur");
        String ip = result.getString("ip");
        Lecteur lecteur = new Lecteur(ip);
        lecteur.setIdLecteur(idLecteur);
        return lecteur;
    }

    @Override
    public Lecteur add(Lecteur lecteur, long idZone) throws Exception {
        try {
            cnx = sql.connect();
            String query = "INSERT INTO lecteur (ip, idZone) VALUES (?, ?)";
            statement = cnx.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, lecteur.getIp());
            statement.setLong(2, idZone);
            if (statement.executeUpdate() > 0) {
                result = statement.getGeneratedKeys();
                if (result.next()) {
                    lecteur.setIdLecteur(result.getLong(1));
                }
            }
            return lecteur;
        } finally {
            close();
        }
    }

    @Override
    public boolean remove(Lecteur lecteur) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM lecteur WHERE idLecteur = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, lecteur.getIdLecteur());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }
    
    @Override
    public boolean removeByZone(Zone zone) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "DELETE FROM lecteur WHERE idZone = ?";
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
    public boolean update(Lecteur lecteur) throws Exception {
        try {
            cnx = sql.connect();
            boolean execute = false;
            String query = "UPDATE lecteur SET ip = ? WHERE idLecteur = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, lecteur.getIp());
            statement.setLong(2, lecteur.getIdLecteur());
            if (statement.executeUpdate() > 0) {
                execute = true;
            }
            return execute;
        } finally {
            close();
        }
    }

    @Override
    public Lecteur getByIdLecteur(long idLecteur) throws Exception {
        try {
            cnx = sql.connect();
            Lecteur lecteur = null;
            String query = "SELECT * FROM lecteur WHERE idLecteur = ?";
            statement = cnx.prepareStatement(query);
            statement.setLong(1, idLecteur);
            result = statement.executeQuery();
            if (result.next()) {
                lecteur = createLecteur();
            }
            return lecteur;
        } finally {
            close();
        }
    }

    @Override
    public Lecteur getByIp(String ip) throws Exception {
        try {
            cnx = sql.connect();
            Lecteur lecteur = null;
            String query = "SELECT * FROM lecteur WHERE ip = ?";
            statement = cnx.prepareStatement(query);
            statement.setString(1, ip);
            result = statement.executeQuery();
            if (result.next()) {
                lecteur = createLecteur();
            }
            return lecteur;
        } finally {
            close();
        }
    }

    @Override
    public List<String> getListIp() throws Exception {
        try {
            cnx = sql.connect();
            List<String> ips = new ArrayList<>();
            String query = "SELECT ip FROM lecteur ORDER BY ip ASC";
            statement = cnx.prepareStatement(query);
            result = statement.executeQuery();
            while (result.next()) {
                ips.add(result.getString("ip"));
            }
            return ips;
        } finally {
            close();
        }
    }

}
