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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ericsson.aia.common.sdkutil.uri.InvalidUriException;
import com.ericsson.aia.common.sdkutil.uri.Uri;

/**
 * Test For parsing of URI from a String
 *
 */
public class UriTest {

    @Test
    public void shouldParseUri() {
        final Uri uri = new Uri("kafka://topic1?format=avro");

        assertEquals("kafka", uri.getTechnology());
        assertEquals("topic1", uri.getTopicName());
        assertEquals("avro", uri.getDataFormat());
    }

    @Test(expected = InvalidUriException.class)
    public void shouldThrowExceptionForInvalidUri() {
        new Uri("topic1?format=avro");
    }

    @Test(expected = InvalidUriException.class)
    public void shouldThrowExceptionForIncompleteUri() {
        new Uri("kafka://topic1?");
    }

    @Test(expected = InvalidUriException.class)
    public void shouldThrowExceptionForMalformedUri() {
        new Uri("kafka:/topic1%format&avro");
    }
}
