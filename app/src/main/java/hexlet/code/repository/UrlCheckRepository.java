package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code, h1, title, "
                     + "description) VALUES (?, ?, ?, ?, ?)";

        try (var conn = connPool.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, urlCheck.getUrlId());
            stmt.setInt(2, urlCheck.getStatusCode());
            stmt.setString(3, urlCheck.getH1());
            stmt.setString(4, urlCheck.getTitle());
            stmt.setString(5, urlCheck.getDescription());

            stmt.executeUpdate();

            try (var keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    urlCheck.setId(keys.getLong(1));
                } else {
                    throw new SQLException("No generated ID returned");
                }
            }
        }
    }

    public static List<UrlCheck> findAllByUrlId(Long id) throws SQLException {
        var urlChecks = new ArrayList<UrlCheck>();
        String sql = "SELECT * FROM url_checks WHERE url_id = (?) ORDER BY created_at DESC";
        try (var conn = connPool.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var urlId = resultSet.getLong("url_id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var urlCheck = new UrlCheck(urlId, statusCode, title, h1, description);
                urlCheck.setId(resultSet.getLong("id"));
                urlCheck.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                urlChecks.add(urlCheck);
            }
        }
        return urlChecks;
    }
}
