package air.found.payproandroidbackend.data_access.manual;

import air.found.payproandroidbackend.core.models.Status;

import java.sql.*;

public class StatusRepository extends DBRepository{

    public static Status getStatus(Integer statusId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM statuses WHERE status_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, statusId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Status status = new Status();
                        status.setStatusId(resultSet.getInt("status_id"));
                        status.setStatusName(resultSet.getString("status_name"));

                        return status;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
