package air.found.payproandroidbackend.data_access.manual;

import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Terminal;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.logging.LogManager;

public class TerminalRepository extends DBRepository {
    private static final String INSERT_TERMINAL_SQL =
            "INSERT INTO terminals (terminal_key, type, merchant_id, status_id, created_at) VALUES (?, ?, ?, ?, ?)";


    public static boolean addTerminalToMerchant(Integer merchantId, Terminal terminal) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            connection.setAutoCommit(false);

            Merchant merchant = MerchantRepository.getMerchant(merchantId);

            if (merchant != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TERMINAL_SQL)) {

                    preparedStatement.setString(1, terminal.getTerminalKey());
                    preparedStatement.setInt(2, terminal.getType());
                    preparedStatement.setInt(3, merchantId);
                    preparedStatement.setInt(4, terminal.getStatus().getStatusId());
                    preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                    preparedStatement.executeUpdate();
                    connection.commit();

                    return true;
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                    return false;
                }
            } else {
                connection.rollback();
                System.out.println("Merchant with ID " + merchantId + " not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
