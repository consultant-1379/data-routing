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

package com.ericsson.aia.common.datarouting.core.sinks;

import java.util.Collection;

import com.ericsson.aia.common.datarouting.api.StreamingSink;

/**
 * A sink that doesn't filter or partition records.
 *
 * @param <V>
 *            The type of the record.
 */
public class UnfilteredSink<V> extends Sink<V> {

    /**
     * Instantiates a sink that doesn't filter or partition records.
     *
     * @param topicName
     *            The name of the topic to send records to.
     * @param sink
     *            The transport layer publisher which this sink wraps.
     * @param threads
     *            The number of threads use to process incoming requests.
     */
    UnfilteredSink(final String topicName, final StreamingSink<?, V> sink, final int threads) {
        super(topicName, sink, threads);
    }

    @Override
    public void close() {
        sink.flush();
        sink.close();
    }

    @Override
    public void flush() {
        sink.flush();
    }

    @Override
    public boolean isConnected() {
        return sink.isConnected();
    }

    @Override
    public void sendMessage(final Collection<V> records) {
        for (final V record : records) {
            sink.sendMessage(topicName, record);
        }
    }

}
