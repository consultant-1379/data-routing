/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.aia.common.datarouting.source.mocks;

import java.util.Properties;

import org.apache.avro.generic.GenericRecord;

import com.ericsson.aia.common.datarouting.api.StreamingSource;
import com.ericsson.aia.common.datarouting.api.SourceTechnologyProvider;

/**
 *
 * Provider for KAFKA subscribers
 *
 * @param <K>
 *            The type of the record key.
 *
 * @param <V>
 *            The record being consumed.
 */
public class SourceTechnologyProviderStub implements SourceTechnologyProvider<String, GenericRecord> {

    @Override
    public String getProviderName() {
        return "kafka";
    }

    @Override
    public StreamingSource<String, GenericRecord> getSource(final String topicName, final Properties sourceProperties) {
        return new SourceStub();
    }

}
