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

import com.ericsson.aia.common.datarouting.api.DataSink;
import com.ericsson.aia.common.datarouting.core.sinks.Sink;

/**
 * This class is used to hold a collection of all the sinks that make up an output. Any action carried out on this sink will be performed on all sinks
 * that make up the output.
 *
 * @param <V>
 *            The type of the record.
 */
public class DataRouterSink<V> implements DataSink<V> {

    private final Collection<ThreadedSink<V>> topicLevelSinks = new ArrayList<>();

    /**
     * Constructs an instance of a DataRouterSink which encloses multiple sinks
     *
     * @param sinks
     *            The sinks to wrap.
     */
    public DataRouterSink(final Collection<Sink<V>> sinks) {
        for (final Sink<V> sink : sinks) {
            topicLevelSinks.add(new ThreadedSink<>(sink));
        }
    }

    @Override
    public void close() {
        for (final ThreadedSink<V> topicLevelSink : topicLevelSinks) {
            topicLevelSink.close();
        }
    }

    @Override
    public void flush() {
        for (final ThreadedSink<V> topicLevelSink : topicLevelSinks) {
            topicLevelSink.flush();
        }
    }

    @Override
    public boolean isConnected() {
        for (final ThreadedSink<V> topicLevelSink : topicLevelSinks) {
            if (!topicLevelSink.isConnected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void sendMessage(final Collection<V> records) {
        for (final ThreadedSink<V> topicLevelSink : topicLevelSinks) {
            topicLevelSink.sendMessage(records);
        }
    }

}
