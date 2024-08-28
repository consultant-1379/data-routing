/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.aia.common.sdkutil.uri;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for DataRoutingUtil
 *
 */
public class DataRoutingUtilTest {

    @Test
    public void shouldReturnTrueForARegex() {
        assertTrue(DataRoutingUtil.isRegex(".*Kafka"));
        assertTrue(DataRoutingUtil.isRegex(".?Kafka"));
        assertTrue(DataRoutingUtil.isRegex("\\dKafka"));
        assertTrue(DataRoutingUtil.isRegex("[Kafka JMS]"));
        assertTrue(DataRoutingUtil.isRegex("Kafka|JMS"));
        assertTrue(DataRoutingUtil.isRegex(".*Kafka"));
        assertTrue(DataRoutingUtil.isRegex("[Kafka.*|\\dJMS.?]"));
    }

    @Test
    public void shouldReturnFalseForANonRegex() {
        assertFalse(DataRoutingUtil.isRegex("Kafka"));
        assertFalse(DataRoutingUtil.isRegex("Kaf  ka"));
    }
}
