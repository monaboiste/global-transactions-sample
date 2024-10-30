package com.github.monaboiste.transactional.domain.event;

interface PayloadSerializer {

    <T> byte[] serialize(T payload);

    <T> T deserialize(byte[] bytes);
}
