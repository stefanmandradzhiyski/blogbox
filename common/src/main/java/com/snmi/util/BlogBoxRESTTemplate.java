package com.snmi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.snmi.enums.ResponseType;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * BlogBoxRESTTemplate is a blog box rest template util class
 * which can be used to by every client to send request to internal resources
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class BlogBoxRESTTemplate {

    private static final RestTemplate restTemplate = new RestTemplate();

    static {
        HttpClient client = HttpClients.createDefault();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
    }

    private BlogBoxRESTTemplate() {
        throw new IllegalArgumentException(BlogBoxGlobalConstant.UTIL_CLASS_EXCEPTION_MESSAGE);
    }

    /**
     * Build the request which will be send to the internal resources
     * @param token take the token from the header
     * @param url take the internal resource url
     * @param httpMethod take the http method
     * @param bodyObject take the body object of the request
     * @param pathSegment take the path segments (variable)
     * @param params take other possible url params
     * @param responseClass take the response class
     * @param responseType take the response type
     * @return the result returned from the internal resource
     */
    public static ResponseEntity<?> buildRequest(
            String token,
            String url,
            HttpMethod httpMethod,
            Object bodyObject,
            String pathSegment,
            Map<String, Object> params,
            Class<?> responseClass,
            ResponseType responseType
    ) {
        HttpHeaders httpHeaders = prepareHeaders(token);
        UriComponentsBuilder builder = prepareUri(url, pathSegment, params);
        HttpEntity<?> httpEntity = prepareHttpEntity(httpHeaders, bodyObject);

        return exchange(builder.toUriString(), httpMethod, httpEntity, responseClass, responseType);
    }

    private static HttpHeaders prepareHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        return headers;
    }

    private static UriComponentsBuilder prepareUri(String url, String pathSegment, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (pathSegment != null) {
            builder.pathSegment(pathSegment);
        }

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return builder;
    }

    private static HttpEntity<?> prepareHttpEntity(HttpHeaders httpHeaders, Object bodyObject) {
        if (bodyObject == null) {
            return new HttpEntity<>(httpHeaders);
        }

        return new HttpEntity<>(bodyObject, httpHeaders);
    }

    private static ResponseEntity<?> exchange(
            String finalUrl,
            HttpMethod httpMethod,
            HttpEntity<?> httpEntity,
            Class<?> responseClass,
            ResponseType responseType
    ) {
        try {
            if (responseType == ResponseType.UNIT) {
                return restTemplate.exchange(
                        finalUrl,
                        httpMethod,
                        httpEntity,
                        responseClass);
            } else {
                CollectionType type;
                ObjectMapper mapper = new ObjectMapper();
                type = mapper.getTypeFactory().constructCollectionType(List.class, responseClass);
                return restTemplate.exchange(
                        finalUrl,
                        httpMethod,
                        httpEntity,
                        ParameterizedTypeReference.forType(type));
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (HttpServerErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
