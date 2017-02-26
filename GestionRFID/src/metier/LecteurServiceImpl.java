package metier;

import java.util.ArrayList;
import java.util.List;
import physique.LecteurDataService;
import physique.PhysiqueFactory;

public class LecteurServiceImpl implements LecteurService {

    private final LecteurDataService lecteurRFIDDataSrv;

    LecteurServiceImpl() {
        lecteurRFIDDataSrv = PhysiqueFactory.getLecteurDataService();
    }

    @Override
    public Lecteur add(Lecteur lecteur, long idZone) {
        Lecteur newLecteur = null;
        try {
            if (!lecteur.getIp().isEmpty() && idZone > 0) {
                newLecteur = lecteurRFIDDataSrv.add(lecteur, idZone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return newLecteur;
    }

    @Override
    public boolean remove(Lecteur lecteur) {
        boolean execute = false;
        try {
            if (lecteur.getIdLecteur() > 0) {
                execute = lecteurRFIDDataSrv.remove(lecteur);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }
    
    @Override
    public boolean removeByZone(Zone zone) {
        boolean execute = false;
        try {
            if (zone.getIdZone() > 0) {
                execute = lecteurRFIDDataSrv.removeByZone(zone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public boolean update(Lecteur lecteur) {
        boolean execute = false;
        try {
            if (lecteur.getIdLecteur() > 0 && !lecteur.getIp().isEmpty()) {
                execute = lecteurRFIDDataSrv.update(lecteur);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public Lecteur getByIdLecteur(long idLecteur) {
        Lecteur lecteur = null;
        try {
            if (idLecteur > 0) {
                lecteur = lecteurRFIDDataSrv.getByIdLecteur(idLecteur);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return lecteur;
    }

    @Override
    public Lecteur getByIp(String ip) {
        Lecteur lecteur = null;
        try {
            if (!ip.isEmpty()) {
                lecteur = lecteurRFIDDataSrv.getByIp(ip);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return lecteur;
    }

    @Override
    public List<String> getListIp() {
        List<String> ips = new ArrayList<>();
        try {
            ips = lecteurRFIDDataSrv.getListIp();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return ips;
    }

}
