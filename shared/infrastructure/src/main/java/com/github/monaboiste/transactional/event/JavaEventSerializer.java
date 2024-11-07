package com.github.monaboiste.transactional.event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class JavaEventSerializer implements EventSerializer {

    @Override
    public <T extends Snapshot> byte[] serialize(final Event<T> event) {
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
    @SuppressWarnings("unchecked")
    public <T extends Snapshot> Event<T> deserialize(final byte[] bytes) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (Event<T>) objectInputStream.readObject();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot deserialize event", ex);
        }
    }
}
