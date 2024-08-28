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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.aia.common.datarouting.api.SourceTechnologyProvider;
import com.ericsson.aia.common.datarouting.api.StreamingSource;
import com.ericsson.aia.common.datarouting.api.exception.UnavailableTechnologyException;
import com.ericsson.aia.common.sdkutil.uri.Uri;

/**
 * This class is used to build a data Source for an input router.
 *
 * @param <K>
 *            The type of the record key.
 * @param <V>
 *            The type of the record.
 */
public class DataRouterSourceBuilder<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataRouterSourceBuilder.class);
    private final Uri uri;
    private final SourceTechnologyProvider<K, V> sourceTechnologyProvider;
    private Properties SourceProperties;

    /**
     * Adds a new Source to the InputDataRouter.
     *
     * @param uri
     *            The URI of the Source.
     */
    DataRouterSourceBuilder(final String uri) {
        this.uri = new Uri(uri);
        sourceTechnologyProvider = getTechnologyProvider(this.uri.getTechnology());
    }

    /**
     * Set the Properties to build this Source.
     *
     * @param SourceProperties
     *            The Properties to use for this Source.
     *
     * @return DataRouterSourceBuilder<V>
     */
    public DataRouterSourceBuilder<K, V> setProperties(final Properties SourceProperties) {
        this.SourceProperties = SourceProperties;
        return this;
    }

    /**
     * Builds a new Source.
     *
     * @return builds the specified Source<String, V>
     */
    StreamingSource<K, V> build() {
        final String topicName = uri.getTopicName();
        LOGGER.info("DataRouting:: Creating source for topic name:{}, for technology:{}", topicName, sourceTechnologyProvider.getProviderName());
        return sourceTechnologyProvider.getSource(topicName, SourceProperties);
    }

    private SourceTechnologyProvider<K, V> getTechnologyProvider(final String technology) {
        for (final SourceTechnologyProvider<K, V> recordInterpreter : ServiceLoader.load(SourceTechnologyProvider.class)) {
            if (recordInterpreter.getProviderName().equalsIgnoreCase(technology)) {
                return recordInterpreter;
            }
        }
        throw new UnavailableTechnologyException("Unable to load technology provider for technology::" + technology);
    }

}