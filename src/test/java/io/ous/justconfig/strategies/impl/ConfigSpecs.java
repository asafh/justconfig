package io.ous.justconfig.strategies.impl;

@interface ConfigSpecs {
	public static final String DEF_STRING_VAL = "def";
	public static final int DEF_INT_VAL = 332;
	int primError();
	int primDef() default DEF_INT_VAL;
	String nullString();
	String defString() default DEF_STRING_VAL;
	String sysPropStr();
	int sysPropInt();
	String getObject();
	String isThing();
}
