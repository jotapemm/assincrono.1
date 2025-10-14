package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public final class ConnectionFactory {

    private static final String PROPERTIES_RESOURCE = "/db.properties";

    private static String jdbcUrl;
    private static String jdbcUser;
    private static String jdbcPassword;

    static {
        loadConfiguration();
    }

    private ConnectionFactory() {
    }

    private static void loadConfiguration() {
        Properties properties = new Properties();
        try (InputStream in = ConnectionFactory.class.getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (in != null) {
                properties.load(in);
            }
        } catch (Exception ignored) {
        }

        String envUrl = System.getenv("DB_URL");
        String envUser = System.getenv("DB_USER");
        String envPass = System.getenv("DB_PASSWORD");

        jdbcUrl = firstNonBlank(envUrl, properties.getProperty("db.url"),
                "jdbc:postgresql://localhost:5432/consultasdb");
        jdbcUser = firstNonBlank(envUser, properties.getProperty("db.user"), "postgres");
        jdbcPassword = Objects.toString(firstNonBlank(envPass, properties.getProperty("db.password"), "root"));
    }

    private static String firstNonBlank(String a, String b, String fallback) {
        if (a != null && !a.isBlank()) return a;
        if (b != null && !b.isBlank()) return b;
        return fallback;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
    }
}
