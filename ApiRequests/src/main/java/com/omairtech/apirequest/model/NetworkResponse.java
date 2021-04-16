/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omairtech.apirequest.model;

import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.Request;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Data and headers returned from {@link Network#performRequest(Request)}.
 */
public class NetworkResponse {
    public NetworkResponse(com.android.volley.NetworkResponse networkResponse) {
        if (networkResponse != null) {
            this.statusCode = networkResponse.statusCode;
            this.data = networkResponse.data;
            this.headers = networkResponse.headers;
            if (networkResponse.allHeaders == null) {
                this.allHeaders = null;
            } else {
                this.allHeaders = Collections.unmodifiableList(networkResponse.allHeaders);
            }
            this.notModified = networkResponse.notModified;
            this.networkTimeMs = networkResponse.networkTimeMs;
        } else {
            this.statusCode = 200;
            this.data = null;
            this.headers = new Hashtable<>();
            this.allHeaders = new ArrayList<>();
            this.notModified = false;
            this.networkTimeMs = 0;
        }
    }

    /**
     * The HTTP status code.
     */
    public final int statusCode;

    /**
     * Raw data from this response.
     */
    public final byte[] data;

    /**
     * Response headers.
     *
     * <p>This map is case-insensitive. It should not be mutated directly.
     *
     * <p>Note that if the server returns two headers with the same (case-insensitive) name, this
     * map will only contain the last one. Use {@link #allHeaders} to inspect all headers returned
     * by the server.
     */
    @Nullable
    public final Map<String, String> headers;

    /**
     * All response headers. Must not be mutated directly.
     */
    @Nullable
    public final List<Header> allHeaders;

    /**
     * True if the server returned a 304 (Not Modified).
     */
    public final boolean notModified;

    /**
     * Network roundtrip time in milliseconds.
     */
    public final long networkTimeMs;

    @Nullable
    private static Map<String, String> toHeaderMap(@Nullable List<Header> allHeaders) {
        if (allHeaders == null) {
            return null;
        }
        if (allHeaders.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        // Later elements in the list take precedence.
        for (Header header : allHeaders) {
            headers.put(header.getName(), header.getValue());
        }
        return headers;
    }

    @Nullable
    private static List<Header> toAllHeaderList(@Nullable Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        if (headers.isEmpty()) {
            return Collections.emptyList();
        }
        List<Header> allHeaders = new ArrayList<>(headers.size());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            allHeaders.add(new Header(header.getKey(), header.getValue()));
        }
        return allHeaders;
    }
}
