package com.kt.naas.domain;

public class VmFlavor {
	private String	serverid;
	private String	flavorid;
	private String	flavorname;
	private String	href;
	private int	ram;
	private int	vcpus;
	private int	disk;
	@Override
	public String toString() {
		return String
				.format("VmFlavor [serverid=%s, flavorid=%s, flavorname=%s, href=%s, ram=%s, vcpus=%s, disk=%s]",
						serverid, flavorid, flavorname, href, ram, vcpus, disk);
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getFlavorid() {
		return flavorid;
	}
	public void setFlavorid(String flavorid) {
		this.flavorid = flavorid;
	}
	public String getFlavorname() {
		return flavorname;
	}
	public void setFlavorname(String flavorname) {
		this.flavorname = flavorname;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public int getRam() {
		return ram;
	}
	public void setRam(int ram) {
		this.ram = ram;
	}
	public int getVcpus() {
		return vcpus;
	}
	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}
	public int getDisk() {
		return disk;
	}
	public void setDisk(int disk) {
		this.disk = disk;
	}
	
}
