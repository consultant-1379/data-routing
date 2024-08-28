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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.aia.common.datarouting.api.StreamingSource;
import com.ericsson.aia.common.datarouting.core.DataRouterSource;

/**
 * This class is responsible building an input data router which is a collection of sources that are wrapped to appear as a single source.
 *
 * @param <K>
 *            The type of the record key.
 * @param <V>
 *            The type of the record being consumed.
 */
public class InputDataRouterBuilder<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputDataRouterBuilder.class);
    private final Collection<DataRouterSourceBuilder<K, V>> sourceBuilders = new LinkedList<>();

    /**
     * Adds a new source to the InputDataRouter.
     *
     * @param uri
     *            The URI to source records from.
     *
     * @return DataRouterSourceBuilder.
     */
    public DataRouterSourceBuilder<K, V> addSource(final String uri) {
        final DataRouterSourceBuilder<K, V> dataRouterSourceBuilder = new DataRouterSourceBuilder<>(uri);
        sourceBuilders.add(dataRouterSourceBuilder);
        return dataRouterSourceBuilder;
    }

    /**
     * Builds an InputDataRouter.
     *
     * @return DataSource which represents all sources added to the input
     */
    public StreamingSource<K, V> build() {
        final Collection<StreamingSource<K, V>> dataSources = new ArrayList<>();
        for (final DataRouterSourceBuilder<K, V> sinkBuilder : sourceBuilders) {
            dataSources.add(sinkBuilder.build());
        }
        LOGGER.info("DataRouting:: Creating output with sources::{} ", dataSources);
        return new DataRouterSource<>(dataSources);
    }
}
