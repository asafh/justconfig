package io.ous.justconfig.values;

import io.ous.justconfig.ConfigurationSource;
import io.ous.justconfig.Primitives;
import io.ous.justconfig.Primitives.PrimitiveType;

public class PrimitiveValueReaderService implements ValueReaderService<Object> {

	public boolean readable(Class<?> type) {
		return Primitives.getType(type)!= null; //Wrapped or unwrapped primitive
	}

	public Object readValue(ClassLoader loader,
			ConfigurationSource config, String name, Class<? extends Object> type) {
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
