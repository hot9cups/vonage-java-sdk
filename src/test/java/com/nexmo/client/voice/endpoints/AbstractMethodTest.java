package com.nexmo.client.voice.endpoints;


import com.nexmo.client.HttpWrapper;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthCollection;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.JWTAuthMethod;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class AbstractMethodTest {
    private static class ConcreteMethod extends AbstractMethod<String, String> {
        public ConcreteMethod(HttpWrapper httpWrapper) {
            super(httpWrapper);
        }

        @Override
        protected Class[] getAcceptableAuthMethods() {
            return new Class[]{ JWTAuthMethod.class };
        }

        @Override
        public HttpUriRequest makeRequest(String request) throws NexmoClientException, UnsupportedEncodingException {
            return new HttpGet(request);
        }

        @Override
        public String parseResponse(HttpResponse response) throws IOException {
            return "response";
        }
    }

    private HttpWrapper mockWrapper;
    private HttpClient mockHttpClient;
    private AuthCollection mockAuthMethods;
    private AuthMethod mockAuthMethod;

    @Before
    public void setUp() throws Exception {
        mockWrapper = mock(HttpWrapper.class);
        mockAuthMethods = mock(AuthCollection.class);
        mockAuthMethod = mock(AuthMethod.class);
        mockHttpClient = mock(HttpClient.class);
        when(mockAuthMethods.getAcceptableAuthMethod(any(Set.class))).thenReturn(mockAuthMethod);

        when(mockWrapper.getHttpClient()).thenReturn(mockHttpClient);
        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(new BasicHttpResponse(
                new BasicStatusLine(
                        new ProtocolVersion("1.1", 1, 1), 200, "OK")));
        when(mockWrapper.getAuthMethods()).thenReturn(mockAuthMethods);
    }

    @Test
    public void testExecute() throws Exception {
        ConcreteMethod method = new ConcreteMethod(mockWrapper);

        String result = method.execute("url");
        assertEquals("response", result);
    }

    @Test
    public void testGetAuthMethod() throws Exception {
        ConcreteMethod method = new ConcreteMethod(mockWrapper);

        AuthMethod auth = method.getAuthMethod(method.getAcceptableAuthMethods());
        assertEquals(auth, mockAuthMethod);
    }

    @Test
    public void testApplyAuth() throws Exception {
        ConcreteMethod method = new ConcreteMethod(mockWrapper);

        HttpUriRequest request = new HttpGet("url");
        method.applyAuth(request);
        verify(mockAuthMethod).apply(request);
    }
}

