package com.github.monaboiste.transactional.event;

public interface PayloadSerializer {

    <T> byte[] serialize(T payload);

    <T> T deserialize(byte[] bytes);
}
