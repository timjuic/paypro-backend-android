package air.found.payproandroidbackend.data_access.manual;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MerchantEntityRepository {

    private final EntityManager entityManager;

    public MerchantEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Map<String, Object>> getMerchantsByUser(Integer userId) {
        String queryString = "SELECT " +
                "m.merchant_id AS id, m.full_name AS merchantName, m.created_at AS createdAt, " +
                "s.status_id AS statusId, s.status_name AS statusName, " +
                "m.street_name AS streetName, m.city_name AS city, " +
                "m.street_number AS streetNumber, m.postal_code AS postalCode, " +
                "cb.card_brand_id AS cardBrandId, cb.name AS cardBrandName, " +
                "t.terminal_id AS terminalId, t.created_at AS terminalCreatedAt, " +
                "t.terminal_key AS terminalKey, t.type AS terminalType, " +
                "ts.status_id AS terminalStatusId, ts.status_name AS terminalStatusName " +
                "FROM merchants m " +
                "JOIN user_merchants um ON m.merchant_id = um.merchant_id " +
                "JOIN statuses s ON m.status_id = s.status_id " +
                "LEFT JOIN merchant_card_brands mcb ON m.merchant_id = mcb.merchant_id " +
                "LEFT JOIN card_brands cb ON mcb.card_brand_id = cb.card_brand_id " +
                "LEFT JOIN terminals t ON m.merchant_id = t.merchant_id " +
                "LEFT JOIN statuses ts ON t.status_id = ts.status_id " +
                "WHERE um.user_id = :userId";

        Query query = entityManager.createNativeQuery(queryString)
                .setParameter("userId", userId);

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<Integer, Map<String, Object>> merchantMap = new HashMap<>();

        List<Object[]> rows = query.getResultList();

        for (Object[] row : rows) {
            Integer merchantId = (Integer) row[0];

            if (!merchantMap.containsKey(merchantId)) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("id", merchantId);
                resultMap.put("merchantName", row[1]);
                resultMap.put("createdAt", row[2]);

                Map<String, Object> addressMap = new HashMap<>();
                addressMap.put("streetName", row[5]);
                addressMap.put("city", row[6]);
                addressMap.put("streetNumber", row[7]);
                addressMap.put("postalCode", row[8]);
                resultMap.put("address", addressMap);

                List<Map<String, Object>> acceptedCards = new ArrayList<>();
                resultMap.put("acceptedCards", acceptedCards);

                Map<String, Object> statusMap = new HashMap<>();
                statusMap.put("statusId", row[3]);
                statusMap.put("statusName", row[4]);
                resultMap.put("status", statusMap);

                List<Map<String, Object>> terminals = new ArrayList<>();
                resultMap.put("terminals", terminals);

                merchantMap.put(merchantId, resultMap);
                resultList.add(resultMap);
            }

            if (row[9] != null) {
                Map<String, Object> cardMap = new HashMap<>();
                cardMap.put("cardBrandId", row[9]);
                cardMap.put("cardBrandName", row[10]);
                List<Map<String, Object>> acceptedCards = (List<Map<String, Object>>) merchantMap.get(merchantId).get("acceptedCards");
                acceptedCards.add(cardMap);
            }

            if (row[11] != null) {
                Map<String, Object> terminalMap = new HashMap<>();
                terminalMap.put("terminalId", row[11]);
                terminalMap.put("terminalCreatedAt", row[12]);
                terminalMap.put("terminalKey", row[13]);
                terminalMap.put("terminalType", row[14]);

                Map<String, Object> terminalStatusMap = new HashMap<>();
                terminalStatusMap.put("statusId", row[15]);
                terminalStatusMap.put("statusName", row[16]);
                terminalMap.put("status", terminalStatusMap);

                List<Map<String, Object>> terminals = (List<Map<String, Object>>) merchantMap.get(merchantId).get("terminals");
                terminals.add(terminalMap);
            }
        }

        return resultList;
    }

}