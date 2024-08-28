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

package com.ericsson.aia.common.datarouting.api;

import java.util.Properties;

import com.ericsson.aia.common.datarouting.api.builder.PartitionBuilder;

/**
 * Interface for SinkTechnologyProvider used to create technology specific implementations of sinks.
 *
 * @param <V>
 *            Type of the records being sent.
 */
public interface SinkTechnologyProvider<V> {

    /**
     * Creates a new sink using the properties specified
     *
     * @param sinkProperties
     *            The properties for the sink
     *
     * @return Transport service publisher
     */
    StreamingSink<?, V> getSink(Properties sinkProperties);

    /**
     * Used to determine the technology implemented by the SinkTechnologyProvider.
     *
     * @return String of technology name.
     */
    String getProviderName();

    /**
     * Get a partition builder for the specified technology.
     *
     * @return Technology specific PartitionBuilder
     */
    PartitionBuilder<V> getPartitionBuilder();
}
