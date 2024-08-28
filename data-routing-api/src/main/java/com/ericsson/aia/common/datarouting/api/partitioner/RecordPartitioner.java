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

package com.ericsson.aia.common.datarouting.api.partitioner;

/**
 *
 * This class is used to partition records by generating a key.
 *
 * @param <V>
 *            The type of the record being partitioned.
 */
public interface RecordPartitioner<V> {

    /**
     * Generates a partition key from the record.
     *
     * @param record
     *            The record being sent.
     *
     * @return The key to use to partition the data.
     */
    Integer getPartitionKey(V record);

}
