package io.ous.justconfig.strategies.impl;

import java.lang.reflect.Method;

public class BeanMethodPropetyStrategy extends MethodPropetyStrategy {
	public static final BeanMethodPropetyStrategy BEAN_METHOD_PROPERTY_STRATEGY_INSTANCE = new BeanMethodPropetyStrategy();
	protected static final String GET_PREFIX = "get";
	protected static final String IS_PREFIX = "is";
	protected static final String[] PREFIXES = {GET_PREFIX, IS_PREFIX};
	
	/**
	 * The BeanMethodPropetyStrategy will strip a prefixed "get" or "is" before any method name if exists
	 * @param method
	 */
	@Override
	public String getPropertyName(Method method) {
		String ret = super.getPropertyName(method);
		for(String prefix : PREFIXES) {
			int preLength = prefix.length();
			if(ret.length() <= preLength) { //For the third check
				continue;
			}
			if(ret.startsWith(prefix)) {
				char firstChar = ret.charAt(preLength);
				if(Character.isUpperCase(firstChar)) { //not gettysburgVila() only getTysburgVila
					return Character.toLowerCase(firstChar) + ret.substring(preLength+1); //Plus one for the lowercased char
				}
			}
		}
		return ret;
	}
}
