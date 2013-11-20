package com.kt.naas.domain;

import java.util.ArrayList;
import java.util.List;

public class SnList extends SnObject {
	private static final long serialVersionUID = 2631112056479849009L;

	private int size;
	private String type;
	private String name;
	private List<SnEntry> entries;
	
	public SnList()
	{
		entries = new ArrayList<SnEntry>();
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SnEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<SnEntry> entries) {
		this.entries = entries;
	}
	public void addEntry(SnEntry entry)
	{
		entries.add(entry);
	}

	@Override
	public String toString() {
		return String.format("SnList [size=%s, type=%s, name=%s]", size, type,
				name);
	}
	
	
}
