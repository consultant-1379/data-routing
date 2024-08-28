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

import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;
import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;
import com.ericsson.aia.common.datarouting.core.partition.KeyRecordPartitioner;
import com.ericsson.aia.common.datarouting.core.partition.MvelRecordPartitioner;
import com.ericsson.aia.common.datarouting.core.partition.RoundRobinRecordPartitioner;

/**
 * This class is responsible for determining how records will be partitioned.
 *
 * @param <V>
 *            The type of the record.
 */
public abstract class PartitionBuilder<V> {
    private RecordInterpreter<V> recordInterpreter;
    private DataRouterSinkBuilder<V> dataRouterSinkBuilder;
    private RecordPartitioner<V> partitioner;

    /**
     * Set the partitioning of data to use the result of the passed in message as a partition key.
     *
     * @param mvelFunction
     *            The function the should be evaluated to obtain the partition key.
     *
     * @return DataRouterSinkBuilder<V>
     */
    public DataRouterSinkBuilder<V> applyFunction(final String mvelFunction) {
        partitioner = new MvelRecordPartitioner<>(mvelFunction, recordInterpreter);
        return dataRouterSinkBuilder;
    }

    /**
     * Partitions the records using round robin.
     *
     * @param attribute
     *            The name of the attribute from the record to use as a partition key.
     *
     * @return DataRouterSinkBuilder<V>
     */
    public DataRouterSinkBuilder<V> applyKey(final String attribute) {
        partitioner = new KeyRecordPartitioner<>(attribute, recordInterpreter);
        return dataRouterSinkBuilder;
    }

    /**
     * Partitions the records using round robin. understanding the number of partitions is technology specific.
     *
     * @return DataRouterSinkBuilder<V>
     */
    public abstract DataRouterSinkBuilder<V> applyRoundRobin();

    /**
     * Partitions the records using round robin
     *
     * @param numberOfPartitions
     *            The number of partitions to split the data into.
     *
     * @return DataRouterSinkBuilder<V>
     */
    public DataRouterSinkBuilder<V> applyRoundRobin(final Integer numberOfPartitions) {
        partitioner = new RoundRobinRecordPartitioner<>(numberOfPartitions);
        return dataRouterSinkBuilder;
    }

    /**
     * Builds and returns the RecordPartitioner.
     *
     * @return The object which should be used to partition records.
     */
    protected RecordPartitioner<V> build() {
        return partitioner;
    }

    public void setRecordInterpreter(final RecordInterpreter<V> recordInterpreter) {
        this.recordInterpreter = recordInterpreter;
    }

    public void setDataRouterSinkBuilder(final DataRouterSinkBuilder<V> dataRouterSinkBuilder) {
        this.dataRouterSinkBuilder = dataRouterSinkBuilder;
    }

    protected DataRouterSinkBuilder<V> getDataRouterSinkBuilder() {
        return dataRouterSinkBuilder;
    }
}
