package com.worldmessages.messagemanager.util;

import com.worldmessages.messagemanager.domain.ReceiverZone;

public class QueueUtil {

    private static final String DOT = ".";
    private static final String UNION_PREFIX = DOT + Constants.UNION_SUFFIX;
    private static final String WITHOUT_UNION_PREFIX = Constants.NO_UNION_PREFIX + DOT;

    public static String getQueueName(final ReceiverZone receiverZone) {
        final String receiverLowerCaseValue = receiverZone.getValue().toLowerCase();
        return receiverZone.isUnion()
                ? receiverLowerCaseValue + UNION_PREFIX
                : receiverZone.getUnion()
                .map(countryUnion ->
                        countryUnion.getValue().toLowerCase() + DOT + receiverLowerCaseValue
                ).orElse(WITHOUT_UNION_PREFIX + receiverLowerCaseValue);
    }

}
