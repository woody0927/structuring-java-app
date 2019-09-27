package idv.woody.onboarding.service;

import idv.woody.onboarding.api.ClientApi;
import idv.woody.onboarding.dao.ClientDao;
import idv.woody.onboarding.model.Client;
import idv.woody.onboarding.model.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements ClientApi {

    private final ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client register(final String firstName, final String lastName) {
        final Client prospect = Client.ofProspect(firstName, lastName);
        clientDao.saveOrUpdate(prospect);
        return prospect;
    }

    public List<Client> listProspectClients() {
        return clientDao.findByType(ClientType.PROSPECT);
    }

    public void backgroundCheck(final Client client) {
        // assuming the client always passes the check
        client.toRealClient();
        clientDao.saveOrUpdate(client);
    }

    @Override
    public List<Client> listRealClients() {
        return clientDao.findByType(ClientType.REAL);
    }
}
