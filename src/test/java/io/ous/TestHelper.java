package io.ous;

import io.ous.justconfig.util.Primitives;
import io.ous.justconfig.util.Primitives.PrimitiveType;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;

public class TestHelper {
	@SuppressWarnings("unchecked")
	public static<T> T getBasicValue(Random random, Class<T> type) {
		Object ret;
		if(String.class.equals(type)) {
			ret = new BigInteger(64, random).toString(32);			
		}
		else {
			PrimitiveType pType = Primitives.getType(type);
			switch(pType) {
			case INTEGER:
				ret = random.nextInt();
				break;
			case LONG:
				ret = random.nextLong();
				break;
			case DOUBLE:
				ret = random.nextDouble();
				break;
			case FLOAT:
				ret = random.nextFloat();
				break;
			case BOOLEAN:
				ret = random.nextBoolean();
				break;
			case CHARACTER:
				ret = getBasicValue(random, String.class).charAt(0);
				break;
			case BYTE:
				byte[] bytes = new byte[1];
				random.nextBytes(bytes);
				ret = bytes[0];
				break;
			case SHORT:
				byte[] sBytes = new byte[2];
				random.nextBytes(sBytes);
				ret = ByteBuffer.wrap(sBytes).getShort();
			default: //Will never be default... just for the compiler
			case VOID:
				return null;
			}
		}
		return (T) ret;
	}
}
