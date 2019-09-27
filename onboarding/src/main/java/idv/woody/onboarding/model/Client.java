package idv.woody.onboarding.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Client {
    private String id;
    private String firstName;
    private String lastName;
    private ClientType type;

    private Client(final String firstName, final String lastName, final ClientType type) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public static Client ofProspect(final String firstName, final String lastName) {
        return new Client(firstName, lastName, ClientType.PROSPECT);
    }

    public void toRealClient() {
        this.type = ClientType.REAL;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", type=" + type +
                '}';
    }
}
