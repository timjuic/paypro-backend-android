package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.enums.StatusType;
import air.found.payproandroidbackend.core.models.Status;
import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.data_access.persistence.MerchantRepository;
import air.found.payproandroidbackend.data_access.persistence.TerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        if (!isValidStatus(terminal.getStatus())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_STATUS);
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


    public ServiceResult<Void> deleteTerminal(Integer terminalId, Integer merchantId) {
        Optional<Terminal> optionalTerminal = terminalRepository.findById(terminalId);

        if (optionalTerminal.isPresent()) {
            Terminal terminal = optionalTerminal.get();

            if (terminal.getMerchant() != null && terminal.getMerchant().getId().equals(merchantId)) {
                terminalRepository.delete(terminal);
                return ServiceResult.<Void>success();
            } else {
                return ServiceResult.failure(ApiError.ERR_TERMINAL_DOES_NOT_BELONG_TO_THE_MERCHANT);
            }
        } else {
            return ServiceResult.failure(ApiError.ERR_TERMINAL_NOT_FOUND);
        }
    }

    private boolean isValidTerminalKey(String terminalKey) {
        return TERMINAL_KEY_PATTERN.matcher(terminalKey.trim()).matches();
    }

    private boolean isValidStatus(Status status) {
        Set<String> validNames = Arrays.stream(StatusType.values())
                .map(StatusType::getName)
                .collect(Collectors.toSet());

        return validNames.contains(status.getStatusName());
    }
}
