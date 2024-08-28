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

package com.ericsson.aia.common.sdkutil.uri;

/**
 * This class is used to easily parse a URI for its useful information i.e "kafka://topic?fomat=avro" technology is "kafka", fomat is "avro",
 * topicName is "topic"
 *
 */
public class Uri {

    private static final String URI_FORMAT_QUERY_PARAM = "format";
    private final String technology;
    private final String topicName;
    private final String dataFormat;

    /**
     * URI used for parsing string into usable format.
     *
     * @param uri
     *            The URI in string form.
     */
    public Uri(final String uri) {
        try {
            final String[] technologySplit = uri.split("://");
            technology = technologySplit[0].trim();

            final String[] topicNameSplit = technologySplit[1].split("\\?");
            topicName = topicNameSplit[0].trim();

            dataFormat = getDataFormatFromQueryParams(topicNameSplit[1]);
        } catch (final ArrayIndexOutOfBoundsException exception) {
            throw new InvalidUriException(exception);
        }
    }

    private String getDataFormatFromQueryParams(final String queryParams) {
        for (final String queryParam : queryParams.split("&")) {
            final String[] splitQueryParam = queryParam.split("=");

            final String param = splitQueryParam[0].trim();
            final String value = splitQueryParam[1].trim();

            if (param.equalsIgnoreCase(URI_FORMAT_QUERY_PARAM)) {
                return value;
            }
        }
        return null;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTechnology() {
        return technology;
    }

}
