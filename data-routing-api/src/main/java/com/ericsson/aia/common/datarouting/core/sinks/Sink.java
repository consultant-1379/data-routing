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

import com.ericsson.aia.common.datarouting.api.DataSink;
import com.ericsson.aia.common.datarouting.api.StreamingSink;

/**
 * abstract parent for all single unthreaded sinks which are used internally for efficiency.
 *
 * @param <V>
 *            The type of the record
 */
public abstract class Sink<V> implements DataSink<V> {

    final StreamingSink<?, V> sink;
    final String topicName;
    private final int threads;

    /**
     * Constructor for basic sink.
     *
     * @param topicName
     *            The name of the topic to send records to.
     * @param sink
     *            The transport sink to be wrapped.
     * @param threads
     *            The number of threads use to process incoming requests.
     */
    public Sink(final String topicName, final StreamingSink<?, V> sink, final int threads) {
        this.sink = sink;
        this.topicName = topicName;

        final int numberOfPartitions = sink.getNoPartitions(topicName);
        this.threads = threads < numberOfPartitions ? numberOfPartitions : threads;
    }

    /**
     * Gets the number of threads which should be used for processing. if the specified number of threads is less than the number of partitions then
     * the number of partitions will be used instead of the specified number of threads.
     *
     * @return the number of threads which should be used for processing.
     */
    public int getThreads() {
        return threads;
    }

}
