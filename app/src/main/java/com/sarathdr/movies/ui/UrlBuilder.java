package com.sarathdr.movies.ui;

import com.sarathdr.movies.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * @author sdevadas on 12/06/16.
 */
public class UrlBuilder {

    /**
     * Base URL of the backend API
     */
    private static String sBaseUrl = MoviesApplication.getInstance().getString(R.string.api_base_url);

    private static String DEFAULT_RESPONSE = "json";

    public static String getSearch(String keyword, int page) {
        return sBaseUrl
                + "?r=" + DEFAULT_RESPONSE
                + "&s=" + encodePathSegment(keyword)
                + "&page=" + page;
    }

    public static String getMovieById(String id) {
        return sBaseUrl
                + "?r=" + DEFAULT_RESPONSE
                + "&i=" + encodePathSegment(id);
    }


    /**
     * URL-encodes a String using UTF-8 suitable for the query string, i.e., spaces are encoded as a plus sign.
     *
     * @param toEncode input to be encoded
     */
    private static String encode(String toEncode) {
        try {
            return URLEncoder.encode(toEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * URL-encodes a String using UTF-8 suitable for use as a path segment, i.e., spaces are encoded as {@code "%20"}.
     *
     * @param pathSegment input to be encoded
     */
    private static String encodePathSegment(String pathSegment) {
        // First encode as if for the query string, then replace "+" by "%20".
        // We could also map "%2B" (the query-string URL-encoded plus sign) back to "+", but it's not necessary, because
        // "%2B" must also be handled correctly by the server.
        return encode(pathSegment).replaceAll(Pattern.quote("+"), "%20");
    }

}
