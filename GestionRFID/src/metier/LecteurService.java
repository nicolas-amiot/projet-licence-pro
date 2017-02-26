package metier;

import java.util.List;

public interface LecteurService {
    Lecteur add(Lecteur lecteur, long idZone);
    boolean remove(Lecteur lecteur);
    boolean removeByZone(Zone zone);
    boolean update(Lecteur lecteur);
    Lecteur getByIdLecteur(long idLecteur);
    Lecteur getByIp(String ip);
    List<String> getListIp();
}
