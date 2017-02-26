package metier;

import java.util.ArrayList;
import java.util.List;
import physique.PhysiqueFactory;
import physique.ZoneDataService;

public class ZoneServiceImpl implements ZoneService {

    private final ZoneDataService zoneDataSrv;
    private final LecteurService lecteurSrv;

    ZoneServiceImpl() {
        zoneDataSrv = PhysiqueFactory.getZoneDataService();
        lecteurSrv = MetierFactory.getLecteurService();
    }

    @Override
    public Zone add(Zone zone, long idBatiment) {
        Zone newZone = null;
        try {
            if (!zone.getNomZone().isEmpty() && !zone.getHoraireOuverture().isEmpty() && !zone.getHoraireFermeture().isEmpty() && idBatiment > 0) {
                newZone = zoneDataSrv.add(zone, idBatiment);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return newZone;
    }

    @Override
    public boolean remove(Zone zone) {
        boolean execute = false;
        try {
            if (zone.getIdZone() > 0) {
                execute = zoneDataSrv.remove(zone);
                lecteurSrv.removeByZone(zone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }
    
    @Override
    public boolean removeByBatiment(Batiment batiment) {
        boolean execute = false;
        try {
            if (batiment.getIdBatiment() > 0) {
                execute = zoneDataSrv.removeByBatiment(batiment);
                for (Zone zone : batiment.getZones()) {
                    lecteurSrv.removeByZone(zone);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public boolean update(Zone zone) {
        boolean execute = false;
        try {
            if (zone.getIdZone() > 0 && !zone.getNomZone().isEmpty() && !zone.getHoraireOuverture().isEmpty() && !zone.getHoraireFermeture().isEmpty()) {
                execute = zoneDataSrv.update(zone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public Zone getByIdZone(long idZone) {
        Zone zone = null;
        try {
            if (idZone > 0) {
                zone = zoneDataSrv.getByIdZone(idZone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return zone;
    }

    @Override
    public Zone getByNomZone(String nomZone) {
        Zone zone = null;
        try {
            if (!nomZone.isEmpty()) {
                zone = zoneDataSrv.getByNomZone(nomZone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return zone;
    }

    @Override
    public List<String> getListNomZone() {
        List<String> nomZones = new ArrayList<>();
        try {
            nomZones = zoneDataSrv.getListNomZone();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return nomZones;
    }

}
