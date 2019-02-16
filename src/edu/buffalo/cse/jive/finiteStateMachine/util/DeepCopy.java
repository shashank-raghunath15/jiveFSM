package edu.buffalo.cse.jive.finiteStateMachine.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepCopy {

	public static <T extends Serializable> T deepCopy(T object) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream stream = new ObjectOutputStream(outputStream);
			stream.writeObject(object);
			stream.flush();
			stream.close();
			outputStream.close();
			byte[] byteArray = outputStream.toByteArray();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			@SuppressWarnings("unchecked")
			T object2 = (T) objectInputStream.readObject();
			inputStream.close();
			objectInputStream.close();
			return object2;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
