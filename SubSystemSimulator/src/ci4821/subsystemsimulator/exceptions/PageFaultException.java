package ci4821.subsystemsimulator.exceptions;

import ci4821.subsystemsimulator.software.SymProcess;

public class PageFaultException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SymProcess process;
	private int pageID;
	
	
	
	public PageFaultException() {
		super();
	}

	public PageFaultException(int pageID, SymProcess process) {
		super();
		this.process = process;
		this.pageID = pageID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SymProcess getProcess() {
		return process;
	}

	public int getPageID() {
		return pageID;
	}
	
	
}
