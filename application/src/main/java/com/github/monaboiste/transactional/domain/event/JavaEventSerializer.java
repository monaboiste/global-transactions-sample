package com.github.monaboiste.transactional.domain.event;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Component
class JavaEventSerializer implements EventSerializer {

    @Override
    public byte[] serialize(final Event event) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(event);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot serialize event", ex);
        }
        return bos.toByteArray();
    }

    @Override
    public Event deserialize(final byte[] bytes) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (Event) objectInputStream.readObject();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot deserialize event", ex);
        }
    }
}
