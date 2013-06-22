package structures;

public class DataDelivered {

	private int id;
	private int DDRDID;
	private String data;
	private String description;
	
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
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
