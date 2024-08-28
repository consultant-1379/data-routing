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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.aia.common.datarouting.api.StreamingSink;
import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Factory used to create specific sink implementations based on functionality.
 *
 */
public class SinkFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SinkFactory.class);

    private SinkFactory() {

    }

    /**
     * This method will create a Sink that may filters and/or partition records. The specific Implementation is based on the functionality required.
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
     * @param <V>
     *            The type of the record.
     *
     * @return DataSink<V> a partitioning and filtering data sink.
     */
    public static final <V> Sink<V> getSink(final Predicate<V> acceptedRecords, final RecordPartitioner<V> partitioner, final String topicName,
            final StreamingSink<?, V> sink, final int threads) {

        LOGGER.info("DataRouting:: Creating new sink for topicName={}, wrapping sink={} with filter={} and partitioner={},", acceptedRecords,
                partitioner, topicName, sink);

        if (hasValidFilter(acceptedRecords)) {
            LOGGER.info("DataRouting:: Sink has a valid filter");
            return getFilteredSink(acceptedRecords, partitioner, topicName, sink, threads);
        } else {
            LOGGER.info("DataRouting:: Sink has an invalid filter");
            return getUnFilteredSink(partitioner, topicName, sink, threads);
        }
    }

    private static <V> Sink<V> getFilteredSink(final Predicate<V> acceptedRecords, final RecordPartitioner<V> partitioner, final String topicName,
            final StreamingSink<?, V> sink, final int threads) {

        if (hasValidPartitioner(partitioner)) {
            LOGGER.info("DataRouting:: Sink has a valid partitioner");
            return new PartitionedFilteredSink<>(topicName, sink, acceptedRecords, partitioner, threads);
        } else {
            LOGGER.info("DataRouting:: Sink has an invalid partitioner");
            return new FilteredSink<>(acceptedRecords, topicName, sink, threads);
        }
    }

    private static <V> Sink<V> getUnFilteredSink(final RecordPartitioner<V> partitioner, final String topicName, final StreamingSink<?, V> sink,
            final int threads) {

        if (hasValidPartitioner(partitioner)) {
            LOGGER.info("DataRouting:: Sink has a valid partitioner");
            return new PartitionedUnFilteredSink<>(topicName, sink, partitioner, threads);
        } else {
            LOGGER.info("DataRouting:: Sink has an invalid partitioner");
            return new UnfilteredSink<>(topicName, sink, threads);
        }
    }

    private static <V> boolean hasValidFilter(final Predicate<V> acceptedRecords) {
        return (acceptedRecords != null) && !acceptedRecords.equals(Predicates.alwaysTrue());
    }

    private static <V> boolean hasValidPartitioner(final RecordPartitioner<V> partitioner) {
        return (partitioner != null);
    }
}
