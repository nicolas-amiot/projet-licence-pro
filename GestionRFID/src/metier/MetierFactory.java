package metier;


public class MetierFactory {
    
    private static BadgeService badgeSrv = null;
    private static BatimentService batimentSrv = null;
    private static AccesService accesSrv = null;
    private static LecteurService lecteurSrv = null;
    private static PersonneService personneSrv = null;
    private static ZoneService zoneSrv = null;
    
    public static BadgeService getBadgeService() {
        if (badgeSrv == null) {
            badgeSrv = new BadgeServiceImpl();
        }
        return badgeSrv;
    }

    public static BatimentService getBatimentService() {
        if(batimentSrv == null){
            batimentSrv = new BatimentServiceImpl();
        }
        return batimentSrv;
    }
    
    public static AccesService getAccesService(){
        if(accesSrv == null){
            accesSrv = new AccesServiceImpl();
        }
        return accesSrv;
    }
    
    public static LecteurService getLecteurService(){
        if(lecteurSrv == null){
            lecteurSrv = new LecteurServiceImpl();
        }
        return lecteurSrv;
    }
    
    public static PersonneService getPersonneService(){
        if(personneSrv == null){
            personneSrv = new PersonneServiceImpl();
        }
        return personneSrv;
    }
    
    public static ZoneService getZoneService(){
        if(zoneSrv == null){
            zoneSrv = new ZoneServiceImpl();
        }
        return zoneSrv;
    }
}
