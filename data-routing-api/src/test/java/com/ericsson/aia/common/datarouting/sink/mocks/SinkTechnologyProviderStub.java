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

package com.ericsson.aia.common.datarouting.sink.mocks;

import java.util.Properties;

import com.ericsson.aia.common.datarouting.api.StreamingSink;
import com.ericsson.aia.common.datarouting.api.SinkTechnologyProvider;
import com.ericsson.aia.common.datarouting.api.builder.DataRouterSinkBuilder;
import com.ericsson.aia.common.datarouting.api.builder.PartitionBuilder;

/**
 *
 * SinkTechnologyProvider KAFKA stub for testing.
 *
 * @param <V>
 *            The type of the record being sent.
 */
public class SinkTechnologyProviderStub<V> implements SinkTechnologyProvider<V> {

    @SuppressWarnings("unchecked")
    @Override
    public StreamingSink<?, V> getSink(final Properties sinkProperties) {
        return (StreamingSink<?, V>) new SinkStub();
    }

    @Override
    public String getProviderName() {
        return "kafka";
    }

    @Override
    public PartitionBuilder<V> getPartitionBuilder() {
        return new PartitionBuilder<V>() {
            @Override
            public DataRouterSinkBuilder<V> applyRoundRobin() {
                return getDataRouterSinkBuilder();
            }
        };
    }

}
