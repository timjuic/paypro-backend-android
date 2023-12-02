package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.data_access.persistence.MerchantRepository;
import air.found.payproandroidbackend.data_access.persistence.TerminalRepository;
import air.found.payproandroidbackend.endpoints.controllers.TerminalController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {
    private final TerminalRepository terminalRepository;
    @Autowired
    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    public Boolean addTerminalToMerchant(Integer merchantId, Terminal terminal) {
        return air.found.payproandroidbackend.data_access.manual.TerminalRepository.addTerminalToMerchant(merchantId, terminal);
    }
}
