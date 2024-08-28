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

package com.ericsson.aia.common.datarouting.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ericsson.aia.common.datarouting.api.EventListener;
import com.ericsson.aia.common.datarouting.api.StreamingSource;

/**
 *
 * This class is used to hold a collection of all the sinks that make up an output. Any action carried out on this sink will be performed on all sinks
 * that make up the output.
 *
 * @param <K>
 *            The type of the record key.
 * @param <V>
 *            The type of the record.
 */
public class DataRouterSource<K, V> implements StreamingSource<K, V> {

    private final Collection<StreamingSource<K, V>> sources;

    /**
     * Constructs an instance of a DataRouterSource which encloses multiple sources
     *
     * @param sources
     *            The sources to wrap.
     */
    public DataRouterSource(final Collection<StreamingSource<K, V>> sources) {
        this.sources = sources;
    }

    @Override
    public void close() {
        for (final StreamingSource<K, V> source : sources) {
            source.close();
        }
    }

    @Override
    public Collection<V> collectStream() {
        final Collection<V> stream = new ArrayList<>();

        for (final StreamingSource<K, V> source : sources) {
            final Collection<V> partialStream = source.collectStream();
            if (partialStream != null) {
                stream.addAll(partialStream);
            }
        }

        return stream;
    }

    @Override
    public void start() {
        for (final StreamingSource<K, V> source : sources) {
            source.start();
        }
    }

    @Override
    public void subscribe(final List<String> paramList) {
        for (final StreamingSource<K, V> source : sources) {
            source.subscribe(paramList);
        }
    }

    @Override
    public void registerEventListener(final EventListener<V> listener) {
        for (final StreamingSource<K, V> source : sources) {
            source.registerEventListener(listener);
        }
    }

    @Override
    public void removeEventListener(final EventListener<V> listener) {
        for (final StreamingSource<K, V> source : sources) {
            source.removeEventListener(listener);
        }
    }

}
