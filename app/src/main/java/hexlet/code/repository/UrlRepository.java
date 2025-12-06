package hexlet.code.repository;

import hexlet.code.dto.UrlItem;
import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (var conn = connPool.getConnection();
            var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            try (var keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    url.setId(keys.getLong(1));
                } else {
                    throw new SQLException("No generated ID returned for URL: " + url.getName());
                }
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";

        try (var conn = connPool.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var urls = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(id, name, createdAt);
                urls.add(url);
            }
            return urls;
        }
    }

    public static List<UrlItem> getItems() throws SQLException {
        String sql = "SELECT u.*, c.created_at AS check_created_at, c.status_code "
                + "FROM urls AS u "
                + "LEFT JOIN url_checks AS c "
                + "ON c.id = ( SELECT id FROM url_checks "
                + "WHERE url_id = u.id "
                + "ORDER BY created_at DESC LIMIT 1 )";

        try (var conn = connPool.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var urlItems = new ArrayList<UrlItem>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                Timestamp ts = resultSet.getTimestamp("check_created_at");
                LocalDateTime checkCreatedAt = (ts != null) ? ts.toLocalDateTime() : null;
                var statusCode = resultSet.getInt("status_code");
                var urlItem = new UrlItem(id, name, checkCreatedAt, statusCode);
                urlItems.add(urlItem);
            }
            return urlItems;
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = (?)";

        try (var conn = connPool.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var id = resultSet.getLong("id");
                var nameUrl = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(id, nameUrl, createdAt);
                return Optional.of(url);
            } else {
                return Optional.empty();
            }
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = (?)";
        try (var conn = connPool.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var nameUrl = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(id, nameUrl, createdAt);
                return Optional.of(url);
            } else {
                return Optional.empty();
            }
        }
    }

    public static void removeAll() throws SQLException {
        try (var conn = connPool.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM urls");
        }
    }
}
