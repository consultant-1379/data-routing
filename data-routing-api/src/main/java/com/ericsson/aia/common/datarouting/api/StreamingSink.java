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

/**
 * Interface of a data routing sink which is a wrapper around a collection of sinks
 *
 * @param <K>
 *            The type of the record key.
 * @param <V>
 *            The type of the record value.
 */
public interface StreamingSink<K, V> {

    /**
     * Close will be invoked on all of the sinks represented by this sink.
     */
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
     * Gets the number of partitions currently in use by the sink.
     *
     * @param topicName
     *            The name of the topic.
     *
     * @return the number of partitions in use.
     */
    int getNoPartitions(String topicName);

    /**
     * Send the given record asynchronously and return a future which will eventually contain the response information.
     *
     * @param topic
     *            The topic the record will be appended to
     * @param value
     *            The record contents
     */
    void sendMessage(String topic, V value);

    /**
     * Send the given record asynchronously and return a future which will eventually contain the response information.
     *
     * @param topic
     *            The topic the record will be appended to
     * @param key
     *            The key that will be included in the record
     * @param value
     *            The record contents
     */
    void sendMessage(String topic, K key, V value);

    /**
     * Send the given record.
     *
     * @param topic
     *            The topic the record will be sent to.
     *
     * @param pKey
     *            The partition to which the record should be sent.
     *
     * @param key
     *            The key that will be included in the record.
     *
     * @param value
     *            The record contents.
     */
    void sendMessage(String topic, Integer pKey, K key, V value);

}
