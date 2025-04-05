package picpay.picpaySimplificado.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import picpay.picpaySimplificado.entities.Authorization;
import picpay.picpaySimplificado.entities.Transactions;
import picpay.picpaySimplificado.exceptions.UnauthorizedTransactionException;

@Service
public class AuthorizerService {

    private RestClient restClient;

    public AuthorizerService(RestClient.Builder restClient) {
        this.restClient = restClient
                .baseUrl("https://util.devi.tools/api/v2/authorize")
                .build();
    }

    public void authorize(Transactions transaction) {
        var response = restClient.get()
                .retrieve()
                .toEntity(Authorization.class);

        if (response.getStatusCode().isError() || !response.getStatusCode().is2xxSuccessful()) {
            throw new UnauthorizedTransactionException("Transactions not authorized");
        }
    }
}
