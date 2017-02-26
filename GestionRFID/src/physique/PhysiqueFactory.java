package physique;

public class PhysiqueFactory {

    private static BadgeDataService badgeDataSrv = null;
    private static BatimentDataService batimentDataSrv = null;
    private static AccesDataService accesDataSrv = null;
    private static LecteurDataService lecteurDataSrv = null;
    private static PersonneDataService personneDataSrv = null;
    private static ZoneDataService zoneDataSrv = null;
    
    public static BadgeDataService getBadgeDataService() {
        if (badgeDataSrv == null) {
            badgeDataSrv = new BadgeDataServiceImpl();
        }
        return badgeDataSrv;
    }

    public static BatimentDataService getBatimentDataService() {
        if(batimentDataSrv == null){
            batimentDataSrv = new BatimentDataServiceImpl();
        }
        return batimentDataSrv;
    }
    
    public static AccesDataService getAccesDataService(){
        if(accesDataSrv == null){
            accesDataSrv = new AccesDataServiceImpl();
        }
        return accesDataSrv;
    }
    
    public static LecteurDataService getLecteurDataService(){
        if(lecteurDataSrv == null){
            lecteurDataSrv = new LecteurDataServiceImpl();
        }
        return lecteurDataSrv;
    }
    
    public static PersonneDataService getPersonneDataService(){
        if(personneDataSrv == null){
            personneDataSrv = new PersonneDataServiceImpl();
        }
        return personneDataSrv;
    }
    
    public static ZoneDataService getZoneDataService(){
        if(zoneDataSrv == null){
            zoneDataSrv = new ZoneDataServiceImpl();
        }
        return zoneDataSrv;
    }
}
