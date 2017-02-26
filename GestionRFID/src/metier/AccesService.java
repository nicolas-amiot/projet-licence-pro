package metier;

import java.util.List;

public interface AccesService {
    List<Acces> addAll(List<Acces> acces);
    boolean removeAll(List<Acces> acces);
    Acces getByIdAcces(long idBadge, long idZone);
    List<Acces> getByBadge(Badge badge);
    List<Acces> getByZone(Zone zone);
}
