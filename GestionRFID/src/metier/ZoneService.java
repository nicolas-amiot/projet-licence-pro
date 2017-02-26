package metier;

import java.util.List;

public interface ZoneService {
    Zone add(Zone zone, long idBatiment);
    boolean remove(Zone zone);
    boolean removeByBatiment(Batiment batiment);
    boolean update(Zone zone);
    Zone getByIdZone(long idZone);
    Zone getByNomZone(String nomZone);
    List<String> getListNomZone();
}
