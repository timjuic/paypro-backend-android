package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.data_access.persistence.MerchantRepository;
import air.found.payproandroidbackend.data_access.persistence.TerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TerminalService {
    private static final Pattern TERMINAL_KEY_PATTERN = Pattern.compile("[a-zA-Z0-9\\s$&'m@_-]{2,20}");
    private final TerminalRepository terminalRepository;
    private final MerchantRepository merchantRepository;

    public ServiceResult<Void> addTerminalToMerchant(Integer merchantId, Terminal terminal) {
        if (!isValidTerminalKey(terminal.getTerminalKey())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_TERMINAL_KEY);
        }

        if (terminalRepository.existsByTerminalKey(terminal.getTerminalKey())) {
            return ServiceResult.failure(ApiError.ERR_TERMINAL_ALREADY_EXISTS);
        }

         return merchantRepository.findById(merchantId)
                 .map(merchant -> {
                     terminal.setMerchant(merchant);
                     terminalRepository.save(terminal);
                     return ServiceResult.<Void>success();
                 })
                 .orElse(ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND));
    }


    public ServiceResult<List<Terminal>> getTerminalsForMerchant(Integer merchantId) {
        return merchantRepository.findById(merchantId)
                .map(merchant -> ServiceResult.success(air.found.payproandroidbackend.data_access.manual.TerminalRepository.findByMerchantId(merchantId)))
                .orElse(ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND));
    }


    public ServiceResult<Void> deleteTerminal(Integer terminalId) {
        return terminalRepository.findById(terminalId)
                .map(terminal -> {
                    terminalRepository.delete(terminal);
                    return ServiceResult.<Void>success();
                })
                .orElse(ServiceResult.failure(ApiError.ERR_TERMINAL_NOT_FOUND));
    }

    private boolean isValidTerminalKey(String terminalKey) {
        return TERMINAL_KEY_PATTERN.matcher(terminalKey.trim()).matches();
    }
}
