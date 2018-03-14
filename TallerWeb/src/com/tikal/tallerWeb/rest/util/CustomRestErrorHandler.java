/**
 *   Copyright 2015 TIKAL-TECHNOLOGY
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.tikal.tallerWeb.rest.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.tikal.tallerWeb.control.LoginController;
import com.tikal.tallerWeb.control.StartupController;

/**
 *
 * @author Nekorp
 */
@Component
public class CustomRestErrorHandler extends DefaultResponseErrorHandler implements StartupController {

    @Autowired
    private LoginController loginController;
    private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
            HttpStatus statusCode;
            try {
                    statusCode = response.getStatusCode();
            }
            catch (IllegalArgumentException ex) {
                    throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
                                    response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
            }
            return statusCode;
    }
    private byte[] getResponseBody(ClientHttpResponse response) {
            try {
                    InputStream responseBody = response.getBody();
                    if (responseBody != null) {
                            return FileCopyUtils.copyToByteArray(responseBody);
                    }
            }
            catch (IOException ex) {
                    // ignore
            }
            return new byte[0];
    }
    private Charset getCharset(ClientHttpResponse response) {
            HttpHeaders headers = response.getHeaders();
            MediaType contentType = headers.getContentType();
            return contentType != null ? contentType.getCharSet() : null;
    }
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = getHttpStatusCode(response);
        if (statusCode == HttpStatus.UNAUTHORIZED) {
            loginController.start(this);
        } else {
            super.handleError(response);
        }
    }

    @Override
    public void startup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void afterLogin() {
        loginController.finish();
    }
}
