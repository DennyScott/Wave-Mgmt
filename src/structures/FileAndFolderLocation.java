package structures;

import java.util.ArrayList;

public class FileAndFolderLocation {

	private int id;
	private int DDRDID;
	private String os;
	private String workingLocation;
	private ArrayList<FFLocations> data;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the dDRDID
	 */
	public int getDDRDID() {
		return DDRDID;
	}
	/**
	 * @param dDRDID the dDRDID to set
	 */
	public void setDDRDID(int dDRDID) {
		DDRDID = dDRDID;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}
	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}
	/**
	 * @return the workingLocation
	 */
	public String getWorkingLocation() {
		return workingLocation;
	}
	/**
	 * @param workingLocation the workingLocation to set
	 */
	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}
	/**
	 * @return the data
	 */
	public ArrayList<FFLocations> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<FFLocations> data) {
		this.data = data;
	}
	
	public Object[][] getObject(){
		Object[][] obj = new Object[data.size()][3];
		
		for(int i =0; i<obj.length; i++){
			obj[i][0] = data.get(i).getLocation();
			obj[i][1] = data.get(i).getDescription();
			obj[i][2] = data.get(i).getFfID();
		}
		
		return obj;
	}
	
	
}
