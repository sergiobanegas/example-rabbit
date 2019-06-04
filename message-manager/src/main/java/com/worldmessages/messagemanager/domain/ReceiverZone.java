package com.worldmessages.messagemanager.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum ReceiverZone {

    WORLD("WORLD"),
    SPAIN("ES"),
    ITALY("IT"),
    JAPAN("JP"),
    EUROPEAN_UNION("EU");

    private static final String INVALID_RECEIVER_MESSAGE = acceptedValuesString();
    private static final String COMMA_DELIMITER = ", ";

    @Setter
    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean hasValue(final ReceiverZone receiverZone) {
        return value.equals(receiverZone.getValue());
    }

    @SuppressWarnings("unused")
    @JsonCreator
    public static ReceiverZone fromJson(final String jsonValue) {
        return Arrays.stream(values())
                .filter(type -> type.value.equals(jsonValue))
                .findAny()
                .orElseThrow(() -> new ValidationException(INVALID_RECEIVER_MESSAGE));
    }

    public boolean isUnion() {
        return Union.hasValue(value);
    }

    public Optional<Union> getUnion() {
        return Union.getCountryUnion(this);
    }

    private static String acceptedValuesString() {
        return Arrays.stream(values()).map(ReceiverZone::getValue).collect(Collectors.joining(COMMA_DELIMITER));
    }

}
