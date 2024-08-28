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
import java.util.List;

/**
 * Interface for source of records.
 *
 * @param <K>
 *            The type of the key.
 *
 * @param <V>
 *            The type of the record.
 */
public interface StreamingSource<K, V> {

    /**
     * Close the consumer and perform cleanup
     */
    void close();

    /**
     * Fetch data for the topics or partitions specified using one of the subscribe/assign APIs.
     *
     * @return stream of messages collected duirng poll methods.
     */
    Collection<V> collectStream();

    /**
     * This method will register event listener with the Subscriber.
     *
     * @param listener
     *            list of events listener interested to subscribe the events.
     */
    void registerEventListener(EventListener<V> listener);

    /**
     * This method will remove the event listener associated the Subscriber.
     *
     * @param listener
     *            remove listeners which were previously registered using {@link #registerEventListener(GenericEventListener)}
     */
    void removeEventListener(EventListener<V> listener);

    /**
     * This method initiate the Subscriber service.
     */
    void start();

    /**
     * This method used to subscribe list of interested topics.
     *
     * @param topics
     *            list of topics for which subscriber is interested.
     */
    void subscribe(List<String> topics);

}
