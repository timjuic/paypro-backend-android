package air.found.payproandroidbackend.data_access.manual;

import air.found.payproandroidbackend.core.models.Merchant;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static air.found.payproandroidbackend.data_access.manual.DBRepository.*;

public class MerchantRepository {

    private static final String SELECT_MERCHANTS_BY_USER_ID =
            "SELECT * FROM merchants m " +
                    "JOIN user_merchants um ON m.merchant_id = um.merchant_id " +
                    "WHERE um.user_id = ?";

    public static List<Merchant> getMerchantsByUser(Integer userId) {
        List<Merchant> merchants = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MERCHANTS_BY_USER_ID)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Merchant merchant = new Merchant();
                    merchant.setId(resultSet.getInt("merchant_id"));
                    merchant.setMerchantName(resultSet.getString("full_name"));
                    merchant.setStatus(resultSet.getInt("status_id"));
                    Merchant.Address address = new Merchant.Address();
                    address.setStreetName(resultSet.getString("street_name"));
                    address.setCity(resultSet.getString("city_name"));
                    address.setPostalCode(resultSet.getInt("postal_code"));
                    address.setStreetNumber(resultSet.getString("street_number"));
                    merchant.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                    merchant.setAddress(address);
                    merchants.add(merchant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        } catch (RuntimeException e) {
            throw e;
        }

        return merchants;
    }

}
