package air.found.payproandroidbackend.data_access.manual;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserMerchantsRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserMerchantsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUserMerchantRelationship(Integer userId, Integer merchantId) {
        String sql = "INSERT INTO user_merchants (user_id, merchant_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, userId, merchantId);
    }
}