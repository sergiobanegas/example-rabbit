package com.worldmessages.messagemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public enum Union {

    EUROPEAN_UNION("EU");

    @Getter
    @Setter
    private String value;

    private static Map<Union, List<ReceiverZone>> unionsCountries;

    static {
        unionsCountries = new HashMap<>();
        unionsCountries.put(Union.EUROPEAN_UNION, Arrays.asList(ReceiverZone.SPAIN, ReceiverZone.ITALY));
    }

    public static boolean hasValue(final String zone) {
        return Arrays.stream(Union.values()).anyMatch(union -> union.getValue().equals(zone));
    }

    public static Optional<Union> getCountryUnion(final ReceiverZone receiverZone) {
        return unionsCountries.keySet().stream().filter(c -> unionsCountries.get(c).contains(receiverZone)).findFirst();
    }

    public static boolean countryBelongsToAnUnion(final ReceiverZone receiverZone) {
        return getCountryUnion(receiverZone).isPresent();
    }

}
