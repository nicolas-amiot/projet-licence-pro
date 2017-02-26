package physique;

import java.util.List;
import metier.Batiment;

public interface BatimentDataService {
    Batiment add(Batiment batiment) throws Exception;
    boolean remove(Batiment batiment) throws Exception;
    boolean update(Batiment batiment) throws Exception;
    Batiment getByIdBatiment(long idBatiment) throws Exception;
    Batiment getByNomBatiment(String nomBatiment) throws Exception;
    List<String> getListNomBatiment() throws Exception;
}
