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
import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Sink that partitions and filters records.
 *
 * @param <V>
 *            The type of the record.
 */
public class PartitionedFilteredSink<V> extends Sink<V> {

    private final Predicate<V> acceptedRecords;
    private final RecordPartitioner<V> partitioner;

    /**
     * Instantiates a sink that partitions and filters records.
     *
     * @param acceptedRecords
     *            The filter that should be applied.
     * @param partitioner
     *            The partitioner used to partition records.
     * @param topicName
     *            The name of the topic to send records to.
     * @param sink
     *            The transport sink to be wrapped.
     * @param threads
     *            The number of threads use to process incoming requests.
     */
    PartitionedFilteredSink(final String topicName, final StreamingSink<?, V> sink, final Predicate<V> acceptedRecords,
            final RecordPartitioner<V> partitioner, final int threads) {
        super(topicName, sink, threads);
        this.acceptedRecords = acceptedRecords;
        this.partitioner = partitioner;
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
        for (final V record : Iterables.filter(records, acceptedRecords)) {
            sink.sendMessage(topicName, partitioner.getPartitionKey(record), null, record);
        }
    }

}
