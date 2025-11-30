package Service;

public class DuckSocialServiceFactory {
    private static DuckSocialService instance;

    public static DuckSocialService getInstance() {
        if (instance == null) {
            instance = new DuckSocialService();
        }
        return instance;
    }

    public static void reloadInstance() {
        instance = new DuckSocialService();
    }
}