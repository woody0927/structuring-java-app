package idv.woody.riskassessment.demo;

import idv.woody.onboarding.service.ClientService;
import idv.woody.riskassessment.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoIllegalAccess {
    private final ClientService clientService;
    private final RiskAssessmentService riskAssessmentService;

    @Autowired
    public DemoIllegalAccess(ClientService clientService, RiskAssessmentService riskAssessmentService) {
        this.clientService = clientService;
        this.riskAssessmentService = riskAssessmentService;
    }

    public void illegalAccessExample() {
        shouldNotBeInvokedFromOtherModule();
        accessOverApi();
    }

    private void shouldNotBeInvokedFromOtherModule() {
        clientService.register("firstName", "lastName");
        clientService.listProspectClients().stream().forEach(client -> System.out.println("prospect client: " + client));
        clientService.listProspectClients().stream().forEach(clientService::backgroundCheck);
    }

    private void accessOverApi() {
        riskAssessmentService.listClientsForRiskAssessment().stream().forEach(client -> System.out.println("real client: " + client));
    }
}
