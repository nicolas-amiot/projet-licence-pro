package metier;

import java.util.ArrayList;
import java.util.List;
import physique.BatimentDataService;
import physique.PhysiqueFactory;

public class BatimentServiceImpl implements BatimentService {

    private final BatimentDataService batimentDataSrv;
    private final ZoneService zoneSrv;

    BatimentServiceImpl() {
        batimentDataSrv = PhysiqueFactory.getBatimentDataService();
        zoneSrv = MetierFactory.getZoneService();
    }

    @Override
    public Batiment add(Batiment batiment) {
        Batiment newBatiment = null;
        try {
            if (!batiment.getNomBatiment().isEmpty()) {
                newBatiment = batimentDataSrv.add(batiment);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return newBatiment;
    }

    @Override
    public boolean remove(Batiment batiment) {
        boolean execute = false;
        try {
            if (batiment.getIdBatiment() > 0) {
                execute = batimentDataSrv.remove(batiment);
                zoneSrv.removeByBatiment(batiment);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public boolean update(Batiment batiment) {
        boolean execute = false;
        try {
            if (batiment.getIdBatiment() > 0 && !batiment.getNomBatiment().isEmpty()) {
                execute = batimentDataSrv.update(batiment);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public Batiment getByIdBatiment(long idBatiment) {
        Batiment batiment = null;
        try {
            if (idBatiment > 0) {
                batiment = batimentDataSrv.getByIdBatiment(idBatiment);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return batiment;
    }

    @Override
    public Batiment getByNomBatiment(String nomBatiment) {
        Batiment batiment = null;
        try {
            if (!nomBatiment.isEmpty()) {
                batiment = batimentDataSrv.getByNomBatiment(nomBatiment);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return batiment;
    }

    @Override
    public List<String> getListNomBatiment() {
        List<String> nomBatiments = new ArrayList<>();
        try {
            nomBatiments = batimentDataSrv.getListNomBatiment();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return nomBatiments;
    }

}
