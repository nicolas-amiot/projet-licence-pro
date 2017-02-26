package metier;

import java.util.ArrayList;
import java.util.List;
import physique.PhysiqueFactory;
import physique.BadgeDataService;

public class BadgeServiceImpl implements BadgeService {

    private final BadgeDataService badgeDataSrv;

    BadgeServiceImpl() {
        badgeDataSrv = PhysiqueFactory.getBadgeDataService();
    }

    @Override
    public Badge add(Badge badge, long idPersonne) {
        Badge newBadge = null;
        try {
            if (!badge.getNumero().isEmpty() && !badge.getPassword().isEmpty() && badge.getDateCreation() != null && idPersonne > 0) {
                newBadge = badgeDataSrv.add(badge, idPersonne);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return newBadge;
    }

    @Override
    public boolean remove(Badge badge) {
        boolean execute = false;
        try {
            if (badge.getIdBadge() > 0) {
                execute = badgeDataSrv.remove(badge);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public boolean update(Badge badge) {
        boolean execute = false;
        try {
            if (badge.getIdBadge() > 0 && !badge.getNumero().isEmpty() && !badge.getPassword().isEmpty()) {
                execute = badgeDataSrv.update(badge);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public Badge getByIdBadge(long idBadge) {
        Badge badge = null;
        try {
            if (idBadge > 0) {
                badge = badgeDataSrv.getByIdBadge(idBadge);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return badge;
    }

    @Override
    public Badge getByNumero(String numero) {
        Badge badge = null;
        try {
            if (!numero.isEmpty()) {
                badge = badgeDataSrv.getByNumero(numero);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return badge;
    }

    @Override
    public List<String> getListNumero() {
        List<String> numeros = new ArrayList<>();
        try {
            numeros = badgeDataSrv.getListNumero();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return numeros;
    }
}
