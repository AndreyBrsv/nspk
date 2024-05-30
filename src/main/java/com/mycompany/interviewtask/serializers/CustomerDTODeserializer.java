package com.mycompany.interviewtask.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mycompany.interviewtask.exception.CustomInternalException;
import com.mycompany.interviewtask.model.CustomerDTO;
import com.mycompany.interviewtask.model.PrivilegeStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j

public class CustomerDTODeserializer extends StdDeserializer<CustomerDTO> {

    public CustomerDTODeserializer() {
        this(null);
    }

    public CustomerDTODeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CustomerDTO deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        try {
            String firstName = node.get("firstName").asText();
            String lastName = node.get("lastName").asText();
            PrivilegeStatus status = null;

            try {
                status = PrivilegeStatus.valueOf(node.get("status").asText().toUpperCase());
            } catch (Exception e) {
                status = PrivilegeStatus.NO_CARD;
            }

            Integer numberOfPurchases = node.has("numberOfPurchases") ? node.get("numberOfPurchases").asInt() : null;
            Integer numberOfReturns = node.has("numberOfReturns") ? node.get("numberOfReturns").asInt() : null;
            String phoneNumber = node.has("phoneNumber") ? node.get("phoneNumber").asText() : null;

            return new CustomerDTO(firstName, lastName, status, numberOfPurchases, numberOfReturns, phoneNumber);
        } catch (Exception e) {
            log.error("Exception when parse: {}", node, e);
            throw new CustomInternalException("Exception when parse file");
        }
    }
}
