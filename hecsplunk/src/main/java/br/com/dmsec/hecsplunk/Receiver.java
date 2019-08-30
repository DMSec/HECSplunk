/*
 * Copyright 2012 Splunk, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"): you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package br.com.dmsec.hecsplunk;

import java.io.IOException;


import com.google.gson.Gson;


import br.com.dmsec.hecsplunk.model.Event;

/**
 * The {@code Receiver} class represents a named index and unnamed index
 * receivers.
 */
public class Receiver {

    Service service = null;

    /**
     * Class constructor.
     *
     * @param service The connected {@code Service} instance.
     */
    public Receiver(Service service) {
        this.service = service;
    }

   
    /**
     * Submits an event to this index through HTTP POST.
     *
     * @param data A string containing event data.
     */
    public void submit(String data) {
        submit(null, null, data);
    }
    
    public void submit(Event data) {
    	Gson gson = new Gson();
    	submit(null,null,gson.toJson(data));
    }

    /**
     * Submits an event to this index through HTTP POST.
     *
     * @param indexName The index to write to.
     * @param data A string containing event data.
     */
    public void submit(String indexName, String data) {
        submit(indexName, null, data);
    }

    /**
     * Submits an event to this index through HTTP POST.
     *
     * @param data A string containing event data.
     * @param args Optional arguments for this stream. Valid parameters are:
     * "host", "host_regex", "source", and "sourcetype".
     */
    public void submit(Args args, String data) {
        submit(null, args, data);
    }

    /**
     * Logs an event to this index through HTTP POST.
     *
     * @param indexName The index to write to.
     * @param data A string containing event data.
     * @param args Optional arguments for this stream. Valid parameters are:
     * "host", "host_regex", "source", and "sourcetype".
     */
    public void submit(String indexName, Args args, String data) {
        
        RequestMessage request = new RequestMessage("POST");
        
        request.setContent(data);
                
        ResponseMessage response = service.send(service.hecEndPoint, request);
        try {
            response.getContent().close();
        } catch (IOException e) {
            // noop
        }
    }

    /**
     * Submits an event to this index through HTTP POST. This method is an alias
     * for {@code submit()}.
     *
     * @param data A string containing event data.
     */
    public void log(String data) {
        submit(data);
    }

    /**
     * Submits an event to this index through HTTP POST. This method is an alias
     * for {@code submit()}.
     *
     * @param indexName The index to write to.
     * @param data A string containing event data.
     */
    public void log(String indexName, String data) {
        submit(indexName, data);
    }

    /**
     * Submits an event to this index through HTTP POST. This method is an alias
     * for {@code submit()}.
     *
     * @param args Optional arguments for this stream. Valid parameters are:
     * "host", "host_regex", "source", and "sourcetype".
     * @param data A string containing event data.
     */
    public void log(Args args, String data) {
        submit(args, data);
    }

    /**
     * Logs an event to this index through HTTP POST. This method is an alias
     * for {@code submit()}.
     *
     * @param indexName The index to write to.
     * @param args Optional arguments for this stream. Valid parameters are:
     * "host", "host_regex", "source", and "sourcetype".
     * @param data A string containing event data.
     */
    public void log(String indexName, Args args, String data) {
        submit(indexName, args, data);
    }
}
