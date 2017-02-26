package physique;

import java.util.List;
import metier.Batiment;
import metier.Zone;

public interface ZoneDataService {
    Zone add(Zone zone, long idBatiment) throws Exception;
    boolean remove(Zone zone) throws Exception;
    boolean removeByBatiment(Batiment batiment) throws Exception;
    boolean update(Zone zone) throws Exception;
    Zone getByIdZone(long idZone) throws Exception;
    Zone getByNomZone(String nomZone) throws Exception;
    List<String> getListNomZone() throws Exception;
}
