package metier;

import java.util.ArrayList;
import java.util.List;
import physique.AccesDataService;
import physique.PhysiqueFactory;

public class AccesServiceImpl implements AccesService {

    private final AccesDataService accesDataSrv;

    AccesServiceImpl() {
        accesDataSrv = PhysiqueFactory.getAccesDataService();
    }

    @Override
    public List<Acces> addAll(List<Acces> acces) {
        List<Acces> newAcces = new ArrayList<>();
        try {
            for (int i=0; i<acces.size(); i++) {
                Acces a = acces.get(i);
                if (a.getBadge().getIdBadge() <= 0 && a.getZone().getIdZone() <= 0) {
                    acces.remove(i);
                    i--;
                }
            }
            if(!acces.isEmpty()){
                newAcces = accesDataSrv.addAll(acces);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return newAcces;
    }

    @Override
    public boolean removeAll(List<Acces> acces) {
        boolean execute = false;
        try {
            for (int i=0; i<acces.size(); i++) {
                Acces a = acces.get(i);
                if (a.getBadge().getIdBadge() <= 0 || a.getZone().getIdZone() <= 0) {
                    acces.remove(i);
                    i--;
                }
            }
            if(!acces.isEmpty()){
                execute = accesDataSrv.removeAll(acces);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public Acces getByIdAcces(long idBadge, long idZone) {
        Acces acces = null;
        try {
            if (idBadge > 0 && idZone > 0) {
                acces = accesDataSrv.getByIdAcces(idBadge, idZone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return acces;
    }

    @Override
    public List<Acces> getByBadge(Badge badge) {
        List<Acces> acces = new ArrayList<>();
        try {
            if (badge.getIdBadge() > 0) {
                acces = accesDataSrv.getByBadge(badge);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return acces;
    }

    @Override
    public List<Acces> getByZone(Zone zone) {
        List<Acces> acces = new ArrayList<>();
        try {
            if (zone.getIdZone() > 0) {
                acces = accesDataSrv.getByZone(zone);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return acces;
    }

}
