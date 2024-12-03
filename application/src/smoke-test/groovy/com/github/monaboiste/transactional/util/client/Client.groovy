package com.github.monaboiste.transactional.util.client

import com.fasterxml.jackson.databind.json.JsonMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration

import static java.net.http.HttpClient.Version.HTTP_1_1

class Client {
    protected static final Logger log = LoggerFactory.getLogger(Client)
    protected static final def USER_AGENT = "Smoke-Test"

    final String host
    final HttpClient http
    private final JsonMapper deserializer

    protected Client(String host) {
        this(host, defaultDeserializer())
    }

    protected Client(String host, JsonMapper deserializer) {
        this.host = host
        this.http = HttpClient.newBuilder()
                .version(HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build()
        this.deserializer = deserializer
    }

    protected HttpResponse<Map<String, ?>> withErrorHandling(HttpRequest request) {
        log.info("[${getClass().simpleName} ${request.uri()}] REQUEST")
        def response
        try {
            response = http.send(request, new JsonBodyHandler<>(Map.class, deserializer))
        } catch (Exception e) {
            log.error("Unexpected error encountered", e)
            response = new NoOpHttpResponse<>()
        }
        log.info("[${getClass().simpleName} ${request.uri()}] RESPONSE: ${response.statusCode()}")
        return response as HttpResponse<Map<String, ?>>
    }

    private static JsonMapper defaultDeserializer() {
        return new JsonMapper()
    }

    private static class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {
        private final Class<T> clazz
        private final JsonMapper deserializer

        JsonBodyHandler(Class<T> clazz, JsonMapper deserializer) {
            this.clazz = clazz
            this.deserializer = deserializer
        }

        @Override
        HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
            return HttpResponse.BodySubscribers.mapping(
                    HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
                    body -> {
                        try {
                            return deserializer.readValue(body, clazz)
                        } catch (Exception e) {
                            log.error("Failed to deserialize response body", e)
                            return null
                        }
                    }
            )
        }
    }
}
