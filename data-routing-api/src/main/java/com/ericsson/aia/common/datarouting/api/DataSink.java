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

package com.ericsson.aia.common.datarouting.api;

import java.util.Collection;

/**
 * Interface of a data routing sink which is a wrapper around a collection of sinks
 *
 * @param <V>
 */
public interface DataSink<V> extends AutoCloseable {

    /**
     * Close will be invoked on all of the sinks represented by this sink.
     */
    @Override
    void close();

    /**
     * Flush will be invoked on all of the sinks represented by this sink.
     */
    void flush();

    /**
     * Checks if all sinks represented by this data routing sink are connected.
     *
     * @return true if all sinks are connected
     */
    boolean isConnected();

    /**
     * This method will send messages to the appropriate sinks based on defined filters
     *
     * @param records
     *            Collection of records being sent.
     */
    void sendMessage(Collection<V> records);
}
