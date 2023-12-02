package air.found.payproandroidbackend.data_access.manual;

import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Status;
import air.found.payproandroidbackend.core.models.Terminal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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


    public static List<Terminal> findByMerchantId(Integer merchantId) {
        List<Terminal> terminals = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM terminals WHERE merchant_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, merchantId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Terminal terminal = new Terminal();
                        terminal.setTerminalId(resultSet.getInt("terminal_id"));
                        terminal.setTerminalKey(resultSet.getString("terminal_key"));
                        terminal.setType(resultSet.getInt("type"));
                        terminal.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                        Status status = StatusRepository.getStatus(resultSet.getInt("status_id"));
                        terminal.setStatus(status);

                        terminals.add(terminal);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return terminals;
    }
}
