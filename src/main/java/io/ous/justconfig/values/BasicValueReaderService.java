package io.ous.justconfig.values;

import io.ous.justconfig.sources.ConfigurationSource;
import io.ous.justconfig.util.Primitives;
import io.ous.justconfig.util.Primitives.PrimitiveType;

/**
 * This ValueReader will read any primitive type or a String
 * @author Asafh
 *
 */
public class BasicValueReaderService implements ValueReaderService {

	public boolean readable(Class<?> type) {
		return String.class.equals(type) || Primitives.getType(type)!= null; //Wrapped or unwrapped primitive
	}

	public Object readValue(ClassLoader loader,
			ConfigurationSource config, String name, Class<?> type) {
		if(String.class.equals(type)) {
			return config.getString(name);
		}
		PrimitiveType pType = Primitives.getType(type);
		switch(pType) {
		case INTEGER:
			return config.getInteger(name);
		case LONG:
			return config.getLong(name);
		case DOUBLE:
			return config.getDouble(name);
		case FLOAT:
			return config.getFloat(name);
		case BOOLEAN:
			return config.getBoolean(name);
		case CHARACTER:
			return config.getCharacter(name);
		case BYTE:
			return config.getByte(name);
		case SHORT:
			return config.getShort(name);
		default: //Will never be default... just for the compiler
		case VOID:
			throw new IllegalArgumentException("Cannot retreive void value: "+name);
		}
	}

	

}
