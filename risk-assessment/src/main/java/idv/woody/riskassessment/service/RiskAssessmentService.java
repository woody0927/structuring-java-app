package idv.woody.riskassessment.service;

import idv.woody.onboarding.api.ClientApi;
import idv.woody.onboarding.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentService {
    private final ClientApi clientApi;

    @Autowired
    public RiskAssessmentService(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    public List<Client> listClientsForRiskAssessment() {
        return clientApi.listRealClients();
    }

}
