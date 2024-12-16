package major_project.model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private String dbURL;
    public void initDB(String dbName, String URI) {
        
        File dbFile = new File(dbName);
        this.dbURL = URI;
        String createCacheTableSQL =
                """
                CREATE TABLE IF NOT EXISTS currency (
                    name text NOT NULL,
                    symbol text NOT NULL,
                    id int NOT NULL,
                    hasMetadata BOOLEAN NOT NULL,
                    image_url text,
                    description text,
                    dateLaunched text,
                    website text,
                    PRIMARY KEY (id)
                );
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()) {
            statement.execute(createCacheTableSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void cache(Currency c) {
        if (!c.hasMetadata) {
            String cacheDataSQL =
                    """
                    INSERT INTO currency(name, symbol, id, hasMetadata) VALUES
                    (?, ?, ?, FALSE)
                    """;
            try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)
                ) {
                preparedStatement.setString(1, c.getName());
                preparedStatement.setString(2, c.getSymbol());
                preparedStatement.setInt(3, c.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
        else {
            String cacheDataSQL =
                    """
                    INSERT INTO currency(name, symbol, id, hasMetadata, image_url, description, dateLaunched, website) VALUES
                    (?, ?, ?, TRUE, ?, ?, ?, ?)
                    """;
            try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)
                ) {
                preparedStatement.setString(1, c.getName());
                preparedStatement.setString(2, c.getSymbol());
                preparedStatement.setInt(3, c.getId());
                preparedStatement.setString(4, c.getLogoURL());
                preparedStatement.setString(5, c.getDescription());
                preparedStatement.setString(6, c.getDateLaunched());
                preparedStatement.setString(7, c.getWebsite());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }

    public void uncache(Currency c) {
        String cacheDataSQL =
                    """
                    DELETE FROM currency WHERE id=?
                    """;
            try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)
                ) {
                preparedStatement.setInt(1, c.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
    }

    public Currency getCatchedMetaData(Currency c) {
        String addSingleStudentWithParametersSQL =
                """
                SELECT * FROM currency WHERE id=?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addSingleStudentWithParametersSQL)
            ) {
            preparedStatement.setInt(1, c.getId());
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                c.hasMetadata = true;
                c.description = result.getString("description");
                c.dateLaunched = result.getString("dateLaunched");
                c.website = result.getString("website");    
                c.setLogoURL(result.getString("image_url"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return c;
    }

    public void clearCache() {
        String cacheDataSQL =
                    """
                    DELETE FROM currency
                    """;
            try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)) {
                preparedStatement.executeUpdate();
            } 
            catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
    }

    public Boolean isCached(Currency c) {
        String cacheDataSQL =
                """
                SELECT * FROM currency WHERE id=?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)
            ) {
            preparedStatement.setInt(1, c.getId());
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return false;
    }

    public Boolean isMetadataCached(Currency c) {
        String cacheDataSQL =
                """
                SELECT hasMetadata FROM currency WHERE id=?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)
            ) {
            preparedStatement.setInt(1, c.getId());
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getBoolean("hasMetadata");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return false;
    }

    public Boolean hasCache() {
        String cacheDataSQL =
                """
                SELECT * FROM currency
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(cacheDataSQL)
            ) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return false;
    }
    public List<Currency> cachedListing() {
        List<Currency> list = new ArrayList<>();
        String addSingleStudentWithParametersSQL =
                """
                SELECT * FROM currency
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addSingleStudentWithParametersSQL)
            ) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                list.add(new Currency(result.getString("name"), result.getString("symbol"), result.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return list;
    }
}
