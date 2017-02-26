package physique;

import java.util.List;
import metier.Badge;

public interface BadgeDataService {
    Badge add(Badge badge, long idPersonne) throws Exception;
    boolean remove(Badge badge) throws Exception;
    boolean update(Badge badge) throws Exception;
    Badge getByIdBadge(long idBadge) throws Exception;
    Badge getByNumero(String numero) throws Exception;
    List<String> getListNumero() throws Exception;
}