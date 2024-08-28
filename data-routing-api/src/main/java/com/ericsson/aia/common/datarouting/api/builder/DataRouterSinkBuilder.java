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

import java.util.Properties;
import java.util.ServiceLoader;

import com.ericsson.aia.common.datarouting.api.SinkTechnologyProvider;
import com.ericsson.aia.common.datarouting.api.StreamingSink;
import com.ericsson.aia.common.datarouting.api.exception.UnavailableFormatException;
import com.ericsson.aia.common.datarouting.api.exception.UnavailableTechnologyException;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;
import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;
import com.ericsson.aia.common.datarouting.core.sinks.Sink;
import com.ericsson.aia.common.datarouting.core.sinks.SinkFactory;
import com.ericsson.aia.common.sdkutil.uri.Uri;
import com.google.common.base.Predicate;

/**
 * This class is used to build a data sink for an output router.
 *
 * @param <V>
 *            The type of the record.
 */
public class DataRouterSinkBuilder<V> {

    private final Uri uri;
    private final RecordInterpreter<V> recordInterpreter;
    private final SinkTechnologyProvider<V> sinkTechnologyProvider;
    private final FilterBuilder<V> filterBuilder;
    private final PartitionBuilder<V> partitionBuilder;
    private int threads;
    private Properties sinkProperties;

    /**
     * Instantiates a sink builder.
     *
     * @param uri
     *            The URI of the sink to build.
     */
    DataRouterSinkBuilder(final String uri) {
        this.uri = new Uri(uri);
        recordInterpreter = getDataInterpreter(this.uri.getDataFormat());
        sinkTechnologyProvider = getTechnologyProvider(this.uri.getTechnology());
        filterBuilder = new FilterBuilder<>(recordInterpreter);

        partitionBuilder = sinkTechnologyProvider.getPartitionBuilder();
        partitionBuilder.setRecordInterpreter(recordInterpreter);
        partitionBuilder.setDataRouterSinkBuilder(this);
    }

    /**
     * Set the Properties to build this sink.
     *
     * @param sinkProperties
     *            The Properties to use for this sink.
     *
     * @return DataRouterSinkBuilder<V>
     */
    public DataRouterSinkBuilder<V> setProperties(final Properties sinkProperties) {
        this.sinkProperties = sinkProperties;
        return this;
    }

    /**
     * Sets the number of threads to filter messages. Defaults to number of sinks
     *
     * @param threads
     *            The number of threads used for filtering.
     * @return DataRouterSinkBuilder<V>
     */
    public DataRouterSinkBuilder<V> setThreadPoolSize(final int threads) {
        this.threads = threads;
        return this;
    }

    /**
     * Creates a builder that defines which records should be accepted be this sink.
     *
     * @return FilterBuilder<V>
     */
    public FilterBuilder<V> filter() {
        return filterBuilder;
    }

    /**
     * Creates a builder that defines the partitioning strategy for records.
     *
     * @return PartitionBuilder<V>
     */
    public PartitionBuilder<V> partitionStrategy() {
        return partitionBuilder;
    }

    /**
     * Builds a new sink using the technology provider interpreted from the specified URI for the sink. It then optionally wraps the sink retrieved
     * from mediation with a filter and partitioner.
     *
     * @return DataSink<V>
     */
    Sink<V> build() {
        final StreamingSink<?, V> sink = sinkTechnologyProvider.getSink(sinkProperties);
        final Predicate<V> filter = filterBuilder.build();
        final RecordPartitioner<V> partitioner = partitionBuilder.build();
        return SinkFactory.getSink(filter, partitioner, uri.getTopicName(), sink, threads);
    }

    @SuppressWarnings("unchecked") // warning present because of using service loader with an interface containing a generic type.
    private RecordInterpreter<V> getDataInterpreter(final String dataFormat) {
        for (final RecordInterpreter<V> recordInterpreter : ServiceLoader.load(RecordInterpreter.class)) {
            if (recordInterpreter.getInterpretableDataFormat().equalsIgnoreCase(dataFormat)) {
                return recordInterpreter;
            }
        }
        throw new UnavailableFormatException();
    }

    @SuppressWarnings("unchecked") // warning present because of using service loader with an interface containing a generic type.
    private SinkTechnologyProvider<V> getTechnologyProvider(final String technology) {
        for (final SinkTechnologyProvider<V> recordInterpreter : ServiceLoader.load(SinkTechnologyProvider.class)) {
            if (recordInterpreter.getProviderName().equalsIgnoreCase(technology)) {
                return recordInterpreter;
            }
        }
        throw new UnavailableTechnologyException();
    }

}