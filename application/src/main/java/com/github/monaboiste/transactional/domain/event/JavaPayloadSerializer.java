package com.github.monaboiste.transactional.domain.event;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Component
class JavaPayloadSerializer implements PayloadSerializer {
    @Override
    public <T> byte[] serialize(final T payload) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(payload);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot serialize payload", ex);
        }
        return bos.toByteArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(final byte[] bytes) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (T) objectInputStream.readObject();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot deserialize event", ex);
        }
    }
}
