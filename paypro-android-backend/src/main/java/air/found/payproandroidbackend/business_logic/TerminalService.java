package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.data_access.persistence.MerchantRepository;
import air.found.payproandroidbackend.data_access.persistence.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerminalService {
    private final TerminalRepository terminalRepository;
    private final MerchantRepository merchantRepository;
    @Autowired
    public TerminalService(TerminalRepository terminalRepository, MerchantRepository merchantRepository) {
        this.terminalRepository = terminalRepository;
        this.merchantRepository = merchantRepository;
    }

    public ServiceResult<Boolean> addTerminalToMerchant(Integer merchantId, Terminal terminal) {
        if (!isValidTerminalKey(terminal.getTerminalKey())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_TERMINAL_KEY);
        }

        if (terminalRepository.existsByTerminalKey(terminal.getTerminalKey())) {
            return ServiceResult.failure(ApiError.ERR_TERMINAL_ALREADY_EXISTS);
        }

        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);

        if (optionalMerchant.isEmpty()) {
            return ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND);
        }

        Merchant merchant = optionalMerchant.get();
        terminal.setMerchant(merchant);
        terminalRepository.save(terminal);

        return ServiceResult.success();
    }


    public ServiceResult<List<Terminal>> getTerminalsForMerchant(Integer merchantId) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);

        if (optionalMerchant.isEmpty()) {
            return ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND);
        }

        List<Terminal> terminals = air.found.payproandroidbackend.data_access.manual.TerminalRepository.findByMerchantId(merchantId);
        return ServiceResult.success(terminals);
    }

    private boolean isValidTerminalKey(String terminalKey) {
        if (terminalKey.length() < 2 || terminalKey.length() > 20) {
            return false;
        }

        terminalKey = terminalKey.trim();

        if (!terminalKey.matches("[a-zA-Z0-9\\s$&'m@_-]+")) {
            return false;
        }

        return true;
    }
}
