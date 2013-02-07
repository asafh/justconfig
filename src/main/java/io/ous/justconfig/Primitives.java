package io.ous.justconfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A utility class for primitive types and their wrappings object types
 * @author Asafh
 *
 */
public class Primitives {
	public static enum PrimitiveType {
		INTEGER(Integer.TYPE,		Integer.class),
		LONG(Long.TYPE,				Long.class),
		DOUBLE(Double.TYPE,			Double.class),
		FLOAT(Float.TYPE,			Float.class),
		BOOLEAN(Boolean.TYPE,		Boolean.class),
		CHARACTER(Character.TYPE,	Character.class),
		BYTE(Byte.TYPE,				Byte.class),
		VOID(Void.TYPE,				Void.class),
		SHORT(Short.TYPE,			Short.class);
		
		private final Class<?> primitive;
		private final Class<?> wrapping;
		PrimitiveType(Class<?> primitive, Class<?> wrapping) {
			this.primitive = primitive;
			this.wrapping = wrapping;
		}
		
		/**
		 * Returns the primitive class for this type
		 * @return
		 */
		public Class<?> getPrimitive() {
			return primitive;
		}
		/**
		 * Returns the wrapping class for this type
		 * @return
		 */
		public Class<?> getWrapping() {
			return wrapping;
		}
		/**
		 * Returns the name for the wrapper (e.g. Integer, Character)
		 * @return
		 */
		public String getWrappingName() {
			return wrapping.getSimpleName();
		}
		/**
		 * Returns the name for the primitive (e.g. int, char)
		 * @return
		 */
		public String getPrimitiveName() {
			return primitive.getSimpleName();
		}
		
	}
	//No need for lazy loading this...
	private static final Primitives INSTANCE = new Primitives();
	
	private final Map<Class<?>,PrimitiveType> primitiveToType; 
	private final Map<Class<?>,PrimitiveType> wrappingToType;
	private Primitives() {
		primitiveToType = new HashMap<Class<?>,PrimitiveType>();
		wrappingToType = new HashMap<Class<?>,PrimitiveType>();
		
		for(PrimitiveType type : PrimitiveType.values()) {
			primitiveToType.put(type.getPrimitive(), type);
			wrappingToType.put(type.getWrapping(), type);
		}
	}
	
	/**
	 * Returns true iff the given class represents a primitive
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitive(Class<?> clz) {
		return INSTANCE.primitiveToType.containsKey(clz);
	}
	
	/**
	 * Returns the PrimitiveType corresponding to the given primitive or wrapped type (e.g. Integer.TYPE or Integer.class will return INTEGER) <br/>
	 * Will return null if the given type is not a wrapped or unwrapped primitive type.
	 * @param primitiveOrWrapping
	 * @return
	 */
	public static PrimitiveType getType(Class<?> primitiveOrWrapping) {
		PrimitiveType ret = getTypeFromPrimitive(primitiveOrWrapping);
		return ret == null ? getTypeFromWrapping(primitiveOrWrapping) : ret;
	}
	
	/**
	 * Returns the PrimitiveType corresponding to the given primitive class (e.g. Integer.TYPE will return PrimitiveType.INTEGER)
	 * @param primitive
	 * @return
	 */
	public static PrimitiveType getTypeFromPrimitive(Class<?> primitive) {
		return INSTANCE.primitiveToType.get(primitive);
	}
	/**
	 * Returns the PrimitiveType corresponding to the given wrapping class (e.g. Integer.class will return PrimitiveType.INTEGER)
	 * @param primitive
	 * @return
	 */
	public static PrimitiveType getTypeFromWrapping(Class<?> wrapping) {
		return INSTANCE.wrappingToType.get(wrapping);
	}
	/**
	 * Returns the wrapping class for the primitive class given (or null if that class is not a primitive)
	 * @param primitive
	 * @return
	 */
	public static Class<?> getWrapping(Class<?> primitive) {
		return getTypeFromPrimitive(primitive).getWrapping();
	}
	/**
	 * Returns the primitive class for the wrapping class given (or null if that class is not a wrapping)
	 * @param wrapping
	 * @return
	 */
	public static Class<?> getPrimitve(Class<?> wrapping) {
		return getTypeFromWrapping(wrapping).getPrimitive();
	}
	/**
	 * Returns the set of all primitive classes
	 * @return
	 */
	public static Set<Class<?>> getPrimitives() {
		return Collections.unmodifiableSet(INSTANCE.primitiveToType.keySet());
	}
	/**
	 * Returns the set of all wrapping classes
	 * @return
	 */
	public static Set<Class<?>> getWrappings() {
		return Collections.unmodifiableSet(INSTANCE.wrappingToType.keySet());
	}
}
