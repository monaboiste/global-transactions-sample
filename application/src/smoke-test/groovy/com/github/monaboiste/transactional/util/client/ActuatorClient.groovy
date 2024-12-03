package com.github.monaboiste.transactional.util.client

import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ActuatorClient extends Client {

    ActuatorClient(String host) {
        super(host)
    }

    HttpResponse<Map<String, ?>> health() {
        def request = HttpRequest.newBuilder()
                .GET()
                .header("User-Agent", USER_AGENT)
                .uri(URI.create("${host}/actuator/health"))
                .build()
        return withErrorHandling(request)
    }
}
