package idv.woody.onboarding.dao;

import idv.woody.onboarding.model.Client;
import idv.woody.onboarding.model.ClientType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ClientDao {
    private Map<String, Client> persistedClient = new HashMap<>();
    public void saveOrUpdate(final Client client) {
        persistedClient.put(client.getId(), client);
    }

    public List<Client> findByType(ClientType clientType) {
        return persistedClient.values().stream().filter(client -> client.getType() == clientType).collect(Collectors.toList());
    }
}
