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

import java.util.Properties;

/**
 *
 * This interface is used to create a subscriber.
 *
 * @param <V>
 *            The type of the record.
 * @param <K>
 *            The type of the record key.
 */
public interface SourceTechnologyProvider<K, V> {

    /**
     * Get a source using a properties file and the name of the topic.
     *
     * @param topicName
     *            The name of the topic being subscribed.
     *
     * @param sourceProperties
     *            The properties to create the source.
     *
     * @return a source
     */
    StreamingSource<K, V> getSource(String topicName, Properties sourceProperties);

    /**
     * The name of the technology that is provided.
     *
     * @return the technology name.
     */
    String getProviderName();

}
