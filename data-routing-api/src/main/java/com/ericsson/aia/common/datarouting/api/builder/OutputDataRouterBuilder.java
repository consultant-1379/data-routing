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

package com.ericsson.aia.common.datarouting.api.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.aia.common.datarouting.api.DataSink;
import com.ericsson.aia.common.datarouting.core.DataRouterSink;
import com.ericsson.aia.common.datarouting.core.sinks.Sink;

/**
 * This class is used to build an output which is a collection of sinks which are wrapped to appear as a single sink. Each individual sink is within
 * the output optionally has a filter to determine which records to send and a partitioner to determine how to partition the messages its sending.
 *
 * @param <V>
 *            The type of the record being produced.
 */
public class OutputDataRouterBuilder<V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutputDataRouterBuilder.class);
    private final List<DataRouterSinkBuilder<V>> sinkBuilders = new LinkedList<>();

    /**
     * Adds a sink to the output.
     *
     * @param uri
     *            The URI of the sink.
     * @return DataRouterSinkBuilder<V>
     */
    public DataRouterSinkBuilder<V> addSink(final String uri) {
        final DataRouterSinkBuilder<V> sink = new DataRouterSinkBuilder<>(uri);
        sinkBuilders.add(sink);
        return sink;
    }

    /**
     * Builds a new output which represents all the sinks added to the builder.
     *
     * @return DataSink<V>
     */
    public DataSink<V> build() {

        final Collection<Sink<V>> dataSinks = new ArrayList<>();
        for (final DataRouterSinkBuilder<V> sinkBuilder : sinkBuilders) {
            final Sink<V> datatSink = sinkBuilder.build();
            dataSinks.add(datatSink);
        }

        LOGGER.info("DataRouting:: Creating output with multiple sinks::{} ", dataSinks);
        return new DataRouterSink<>(dataSinks);

    }

}
