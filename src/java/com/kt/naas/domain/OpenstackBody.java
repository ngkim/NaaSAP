package com.kt.naas.domain;

public class OpenstackBody {

	// Get Tokens
	private String	tenantName = "http";
	private String	userName = "";
	private String	password = "";
	
	// VM Create
	private String	flavorRef = "";
	private String	imageRef = "";
	private String	vmName = "";
	
	// Flavor Create
	private String	flavorName = "";
	private String	ram = "";
	private String	vcpus = "";
	private String	disk = "";
	private String	flavorId = "";
	
	// Floating IP Create/Delete/Add/Remove
	private String	pool = "";
	private String	address = "";
	
	// Tenant Create
	//private String	tenantName = "";
	private String	description = "";

	@Override
	public String toString() {
		return String
				.format("OpenstackBody [tenantName=%s, userName=%s, password=%s, flavorRef=%s, imageRef=%s, vmName=%s, flavorName=%s, ram=%s, vcpus=%s, disk=%s, flavorId=%s, pool=%s, address=%s, description=%s]",
						tenantName, userName, password, flavorRef, imageRef,
						vmName, flavorName, ram, vcpus, disk, flavorId, pool,
						address, description);
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFlavorRef() {
		return flavorRef;
	}

	public void setFlavorRef(String flavorRef) {
		this.flavorRef = flavorRef;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getFlavorName() {
		return flavorName;
	}

	public void setFlavorName(String flavorName) {
		this.flavorName = flavorName;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getVcpus() {
		return vcpus;
	}

	public void setVcpus(String vcpus) {
		this.vcpus = vcpus;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((disk == null) ? 0 : disk.hashCode());
		result = prime * result
				+ ((flavorId == null) ? 0 : flavorId.hashCode());
		result = prime * result
				+ ((flavorName == null) ? 0 : flavorName.hashCode());
		result = prime * result
				+ ((flavorRef == null) ? 0 : flavorRef.hashCode());
		result = prime * result
				+ ((imageRef == null) ? 0 : imageRef.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((pool == null) ? 0 : pool.hashCode());
		result = prime * result + ((ram == null) ? 0 : ram.hashCode());
		result = prime * result
				+ ((tenantName == null) ? 0 : tenantName.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((vcpus == null) ? 0 : vcpus.hashCode());
		result = prime * result + ((vmName == null) ? 0 : vmName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenstackBody other = (OpenstackBody) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (disk == null) {
			if (other.disk != null)
				return false;
		} else if (!disk.equals(other.disk))
			return false;
		if (flavorId == null) {
			if (other.flavorId != null)
				return false;
		} else if (!flavorId.equals(other.flavorId))
			return false;
		if (flavorName == null) {
			if (other.flavorName != null)
				return false;
		} else if (!flavorName.equals(other.flavorName))
			return false;
		if (flavorRef == null) {
			if (other.flavorRef != null)
				return false;
		} else if (!flavorRef.equals(other.flavorRef))
			return false;
		if (imageRef == null) {
			if (other.imageRef != null)
				return false;
		} else if (!imageRef.equals(other.imageRef))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pool == null) {
			if (other.pool != null)
				return false;
		} else if (!pool.equals(other.pool))
			return false;
		if (ram == null) {
			if (other.ram != null)
				return false;
		} else if (!ram.equals(other.ram))
			return false;
		if (tenantName == null) {
			if (other.tenantName != null)
				return false;
		} else if (!tenantName.equals(other.tenantName))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (vcpus == null) {
			if (other.vcpus != null)
				return false;
		} else if (!vcpus.equals(other.vcpus))
			return false;
		if (vmName == null) {
			if (other.vmName != null)
				return false;
		} else if (!vmName.equals(other.vmName))
			return false;
		return true;
	}
	
}
