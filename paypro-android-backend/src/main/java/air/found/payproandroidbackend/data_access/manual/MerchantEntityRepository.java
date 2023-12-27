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

    @Autowired
    public MerchantEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Map<String, Object>> findMerchantsAndTerminalsByUserId(Integer userId) {
        String queryString = "SELECT " +
                "m.merchant_id AS merchantId, m.full_name AS merchantName, m.created_at AS merchantCreatedAt, " +
                "m.status_id AS statusId, s.status_name AS statusName, " +
                "m.street_name AS streetName, m.city_name AS city, " +
                "m.street_number AS streetNumber, m.postal_code AS postalCode, " +
                "t.terminal_id AS terminalId, t.type AS terminalType, t.status_id AS terminalStatusId, " +
                "t.created_at AS terminalCreatedAt, cb.card_brand_id AS cardBrandId, cb.name AS cardBrandName " +
                "FROM merchants m " +
                "JOIN user_merchants um ON m.merchant_id = um.merchant_id " +
                "JOIN terminals t ON m.merchant_id = t.merchant_id " +
                "JOIN statuses s ON m.status_id = s.status_id " +
                "LEFT JOIN merchant_card_brands mcb ON m.merchant_id = mcb.merchant_id " +
                "LEFT JOIN card_brands cb ON mcb.card_brand_id = cb.card_brand_id " +
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
                resultMap.put("merchantId", merchantId);
                resultMap.put("merchantName", row[1]);
                resultMap.put("merchantCreatedAt", row[2]);

                Map<String, Object> addressMap = new HashMap<>();
                addressMap.put("streetName", row[5]);
                addressMap.put("city", row[6]);
                addressMap.put("streetNumber", row[7]);
                addressMap.put("postalCode", row[8]);
                resultMap.put("address", addressMap);

                Map<String, Object> statusMap = new HashMap<>();
                statusMap.put("statusId", row[3]);
                statusMap.put("statusName", row[4]);
                resultMap.put("status", statusMap);

                List<Map<String, Object>> terminals = new ArrayList<>();
                resultMap.put("terminals", terminals);

                List<Map<String, Object>> acceptedCards = new ArrayList<>();
                resultMap.put("acceptedCards", acceptedCards);

                merchantMap.put(merchantId, resultMap);
                resultList.add(resultMap);
            }

            Map<String, Object> terminalMap = new HashMap<>();
            terminalMap.put("terminalId", row[9]);
            terminalMap.put("terminalType", row[10]);
            terminalMap.put("terminalStatusId", row[11]);
            terminalMap.put("terminalCreatedAt", row[12]);

            List<Map<String, Object>> terminals = (List<Map<String, Object>>) merchantMap.get(merchantId).get("terminals");

            if (!terminals.stream().anyMatch(t -> t.get("terminalId").equals(row[9]))) {
                terminals.add(terminalMap);
            }

            if (row[13] != null) {
                Map<String, Object> cardMap = new HashMap<>();
                cardMap.put("cardBrandId", row[13]);
                cardMap.put("cardBrandName", row[14]);
                List<Map<String, Object>> acceptedCards = (List<Map<String, Object>>) merchantMap.get(merchantId).get("acceptedCards");

                if (!acceptedCards.stream().anyMatch(c -> c.get("cardBrandId").equals(row[13]))) {
                    acceptedCards.add(cardMap);
                }
            }
        }

        return resultList;
    }

}
