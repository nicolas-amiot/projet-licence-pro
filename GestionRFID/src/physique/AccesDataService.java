package physique;

import java.util.List;
import metier.Acces;
import metier.Badge;
import metier.Zone;

public interface AccesDataService {
    List<Acces> addAll(List<Acces> acces) throws Exception;
    boolean removeAll(List<Acces> acces) throws Exception;
    Acces getByIdAcces(long idBadge, long idZone) throws Exception;
    List<Acces> getByBadge(Badge badge) throws Exception;
    List<Acces> getByZone(Zone zone) throws Exception;
}
