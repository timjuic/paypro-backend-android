package air.found.payproandroidbackend.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenInfo {
    private String access_token;
    private RefreshToken refresh_token;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshToken {
        private String token;
        private Validity valid_for;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Validity {
            private String time_unit;
            private long time_amount;
        }
    }
}
