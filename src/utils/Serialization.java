package utils;

import java.io.*;

/**
 * Class for serialization
 * @param <T>
 */
public class Serialization<T> {

    public T readObject(byte[] data) throws IOException, ClassNotFoundException, ClassCastException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(byteStream);
        return (T) ois.readObject();
    }

    public byte[] writeObject(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(object);
        return outputStream.toByteArray();
    }

}




