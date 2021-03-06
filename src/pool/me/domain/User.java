package pool.me.domain;

import java.util.ArrayList;

import android.provider.MediaStore.Images;

/**
 * Contains User information and his/her route and carpool information
 * @author Shashank
 *
 */
public class User {
	
	/*------Static Variables-------*/
	public static enum CarAudio {SILENCE, ROCK, CLASSICAL, POP, RAP, NEWS, SPORTS, CHATTING};
	//TODO: Add radio channels here.
	
	/*---End of Static Variables---*/
	
	private String firstName;
	private String lastName;
	
	
	/*Carpool Information*/
	private String sourceLocation; //Pickup and dropoff locations	
	private String destLocation;
	
	private ArrayList<Carpool> pools;
	
	/*Profile Information*/
	private String emailAddress;
	private String pass;
	
	private long contactNumber;
	private String aboutMe;
	private Images picture;	
	private int rewardPoints;
	private String radioPrefs; 

	private boolean willingToDrive;
	
	private String departureTime, returnTime;
	
	
	
	
	/*Social Network Integration (Only Placeholders for now)*/
	private String facebookID;
	
	public User()
	{}
	
	public User(String emailAddress, String pass)
	{
		this.emailAddress = emailAddress;
		this.pass = pass;
		firstName="";
		lastName="";
		sourceLocation="";
		destLocation="";
		pools=new ArrayList<Carpool>();
		contactNumber=-1;
		aboutMe="";
		picture = new Images();
		rewardPoints = -1;
		radioPrefs="";
		willingToDrive=false;
	}
	public User(String emailAddress, String firstName, String lastName)
	{
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		sourceLocation="";
		destLocation="";
		pools=new ArrayList<Carpool>();
		contactNumber=-1;
		aboutMe="";
		picture = new Images();
		rewardPoints = -1;
		radioPrefs="";
		willingToDrive=false;
		pass="";

	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	

	public ArrayList<Carpool> getPools() {
		return pools;
	}

	public void setPools(ArrayList<Carpool> pools) {
		this.pools = pools;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Images getPicture() {
		return picture;
	}

	public void setPicture(Images picture) {
		this.picture = picture;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public String getRadioPrefs() {
		return radioPrefs;
	}

	public void setRadioPrefs(String radioPrefs) {
		this.radioPrefs = radioPrefs;
	}

	public boolean isWillingToDrive() {
		return willingToDrive;
	}

	public void setWillingToDrive(boolean willingToDrive) {
		this.willingToDrive = willingToDrive;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}
	public String getDestLocation() {
		return destLocation;
	}

	public void setDestLocation(String destLocation) {
		this.destLocation = destLocation;
	}
	
	public String toString(){
		String s = firstName + " " + lastName + " " + emailAddress + " " + pass + " " + aboutMe + " " + 
				willingToDrive + " " + sourceLocation + " " + destLocation + " " + contactNumber + " " + radioPrefs
				 + " " + departureTime + " " + returnTime;
		return s;
	}

}
