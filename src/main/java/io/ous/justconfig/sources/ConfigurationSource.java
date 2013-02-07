package io.ous.justconfig.sources;

public interface ConfigurationSource {
	public String getString(String name);
	public Byte getByte(String name);
	public Short getShort(String name);
	public Long getLong(String name);
	public Double getDouble(String name);
	public Integer getInteger(String name);
	public Float getFloat(String name);
	public Boolean getBoolean(String name);
	public Character getCharacter(String name);
}
