package metier;

import java.util.List;

public interface BadgeService {
    public Badge add(Badge badge, long idPersonne);
    public boolean remove(Badge badge);
    public boolean update(Badge badge);
    Badge getByIdBadge(long idBadge);
    Badge getByNumero(String numero);
    List<String> getListNumero();
}
