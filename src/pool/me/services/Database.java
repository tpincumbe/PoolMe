package pool.me.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pool.me.domain.Carpool;
import pool.me.domain.User;
import pool.me.domain.User.CarAudio;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * This class provides methods to connect to the database
 * for information on the users, car pools, etc
 * @author Vegeta9000
 *
 */
public class Database {
	private String userURL, carpoolURL;
	private Context c;
	
	public Database(){
		this(null);
	}
	public Database(Context con){
		userURL = "http://m.cip.gatech.edu/developer/vegeta9000/widget/api/pool_me";
		carpoolURL = "http://m.cip.gatech.edu/developer/vegeta9000/widget/api/pool_me";
		c = con;
	}
	
	public User getUser(String email){
		User u = null;
		
		JSONObject jd = null;
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		
		nvp.add(new BasicNameValuePair("email", email));
		jd = connect(userURL + "/getUser", nvp);
		
		try{			
			u = new User(jd.getString("Email"), jd.getString("Password"));
			u.setAboutMe(jd.getString("AboutMe"));
			u.setContactNumber(jd.getInt("PhoneNumber"));
			u.setDepartureTime(jd.getString("StartTime"));
			u.setFirstName(jd.getString("FirstName"));
			u.setLastName(jd.getString("LastName"));
			u.setFacebookID(jd.getString("FacebookUsername"));
			u.setReturnTime(jd.getString("ReturnTime"));
			u.setSourceLocation(jd.getString("StartLocation"));
			u.setDestLocation(jd.getString("DestLocation"));
			u.setWillingToDrive(jd.getBoolean("Driver"));
			
			//Check what radio pref enum to use based on info from database.
			String radioPrefs = "";
			String rp = jd.getString("RadioPrefs");
			if (rp.compareToIgnoreCase("SILENCE") == 0 ){
				radioPrefs = "Silence";
			}else if (rp.compareToIgnoreCase("ROCK") == 0){
				radioPrefs = "ROCK";
			}else if (rp.compareToIgnoreCase("CLASSICAL") == 0){
				radioPrefs = "CLASSICAL";
			}else if (rp.compareToIgnoreCase("POP") == 0){
				radioPrefs = "POP";
			}else if (rp.compareToIgnoreCase("RAP") == 0){
				radioPrefs = "RAP";
			}else if (rp.compareToIgnoreCase("NEWS") == 0){
				radioPrefs = "NEWS";
			}else if (rp.compareToIgnoreCase("SPORTS") == 0){
				radioPrefs = "SPORTS";
			}else if (rp.compareToIgnoreCase("CHATTING") == 0){
				radioPrefs = "CHATTING";
			}
			u.setRadioPrefs(radioPrefs);
			
		}catch(JSONException e1){
			Log.e("JSON Exception", e1.toString());
		}
		
		return u;
	}
	
	public boolean addUser(User u){
		String url = userURL + "/addUser";
		
		JSONObject jd = null;
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		
		nvp.add(new BasicNameValuePair("fname", u.getFirstName()));
		nvp.add(new BasicNameValuePair("lname", u.getLastName()));
		nvp.add(new BasicNameValuePair("pnum", new Long(u.getContactNumber()).toString()));
		nvp.add(new BasicNameValuePair("about", u.getAboutMe()));
		nvp.add(new BasicNameValuePair("driver", new Boolean(u.isWillingToDrive()).toString()));
		nvp.add(new BasicNameValuePair("radiopref", u.getRadioPrefs()));
		nvp.add(new BasicNameValuePair("email", u.getEmailAddress()));
		nvp.add(new BasicNameValuePair("fbuname", u.getFacebookID()));
		nvp.add(new BasicNameValuePair("startloc", u.getSourceLocation()));
		nvp.add(new BasicNameValuePair("destloc", u.getDestLocation()));
		nvp.add(new BasicNameValuePair("starttime", u.getDepartureTime()));
		nvp.add(new BasicNameValuePair("returntime", u.getReturnTime()));
		nvp.add(new BasicNameValuePair("pwd", u.getPass()));
		
		jd = connect(url, nvp);
		Toast t;
		if (jd != null){
			t = Toast.makeText(c, "Created new user in database", Toast.LENGTH_SHORT);
			t.show();
			return true;
		}else {
			t = Toast.makeText(c, "Error creating user in databse", Toast.LENGTH_SHORT);
			t.show();
			return false;
		}
	}
	
	public void updateFName(String fname, String email){
		String url = userURL + "/updateFName";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		
		nvp.add(new BasicNameValuePair("email", email));
		nvp.add(new BasicNameValuePair("fname", fname));
		
		
		JSONObject jd = null;
		
		jd = connect(url, nvp);
	}
	
	public void updateLName(String lname, String email){
		String url = userURL + "/updateLName";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("lname", lname));
		
		
		JSONObject jd = null;
		
		jd = connect(url, nvp);
	}
	
	public void updatePNum(Long pnum, String email){
		String url = userURL + "/updatePNum";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("pnum", pnum.toString()));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateAboutMe(String about, String email){
		String url = userURL + "/updateAboutMe";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("about", about));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateDriver(Boolean drive, String email){
		String url = userURL + "/updateDriver";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("driver", drive.toString()));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateRadioPref(String radiopref, String email){
		String url = userURL + "/updateRadioPref";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("radiopref", radiopref));
		
		
		JSONObject jd = null;
		
		jd = connect(url, nvp);
	}
	
	public void updateEmail(String email, String oldemail){
		String url = userURL + "/updateEmail";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("oldemail", oldemail));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateFBName(String name, String email){
		String url = userURL + "/updateFBName";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("fbuname", name));
		
		
		JSONObject jd = null;
		
		jd = connect(url, nvp);
	}
	
	public void updateStartLoc(String startloc, String email){
		String url = userURL + "/updateStartLoc";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("startloc", startloc));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateDestLoc(String destloc, String email){
		String url = userURL + "/updateDestLoc";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("destloc", destloc));
		
		
		JSONObject jd = null;
		
		jd = connect(url, nvp);
	}
	
	public void updateStartTime(String starttime, String email){
		String url = userURL + "/updateStartTime";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("starttime", starttime));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateReturnTime(String returntime, String email){
		String url = userURL + "/updateReturnTime";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("returntime", returntime));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updatePwd(String pwd, String email){
		String url = userURL + "/updatePWD";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("pwd", pwd));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public boolean authenticate(String email, String pwd){
		boolean auth = false;
		String url = userURL + "/authenticate";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("email", email));
		
		nvp.add(new BasicNameValuePair("pwd", pwd));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
		
		try{
			auth = jd.getBoolean("auth");
			Log.e("AUTH:", new Boolean(auth).toString());
		}catch(JSONException e1){
			Log.e("Json error", e1.toString());
		}
			
		return auth;
	}
	
	public Carpool getPool(Integer id){
		Carpool cp = new Carpool();
		
		JSONObject jd = null;

		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", id.toString()));
		
		
		jd = connect(carpoolURL + "/getPool", nvp);
		
		try{			
			cp.setCapacity(jd.getInt("capacity"));
			cp.setDriverEmail(jd.getString("owneremail"));
			
			ArrayList<String> memEmail = new ArrayList<String>();
			memEmail.add(jd.getString("memberemail"));
			cp.setMembersEmail(memEmail);
			
			cp.setDeptTime(jd.getString("depttime"));
			cp.setRetTime(jd.getString("returntime"));
			
//			cp.setRoute(new Route());
		}catch(JSONException e1){}
		
		return cp;
	}
	
	public ArrayList<Carpool> getAllPools(){
		ArrayList<Carpool> cps = new ArrayList<Carpool>();
		JSONArray ja = null;
		String url = carpoolURL + "/getAllPools";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		
		ja = connectArr(url, nvp);
		Log.i("names", ja.toString());
		try{
			for (int i = 0; i < ja.length(); i++){
				Carpool c = new Carpool();
				c.setId(ja.getJSONObject(i).getInt("Carpool_Id"));
				c.setCapacity(ja.getJSONObject(i).getInt("Capacity"));
				c.setDriverEmail(ja.getJSONObject(i).getString("Owner_Email"));
				
				ArrayList<String> memEmail = new ArrayList<String>();
				memEmail.add(ja.getJSONObject(i).getString("Member_Email"));
				c.setMembersEmail(memEmail);
				
				c.setDeptTime(ja.getJSONObject(i).getString("DepartTime"));
				c.setRetTime(ja.getJSONObject(i).getString("ReturnTime"));
				cps.add(c);
			}
		}catch (JSONException e){
			Log.e("JSON Error", e.toString());
		}
		
		for (int i = 0; i < cps.size(); i++){
			Log.v("cps[" + i + "]", cps.get(i).toString());
		}
		return cps;
	}
	
	public ArrayList<Carpool> getMyPools(String email){
		ArrayList<Carpool> cps = new ArrayList<Carpool>();
		JSONArray ja = null;
		String url = carpoolURL + "/getAllPools";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		
		nvp.add(new BasicNameValuePair("email", email.toString()));
		ja = connectArr(url, nvp);
		
		try{
			for (int i = 0; i < ja.length(); i++){
				Carpool c = new Carpool();
				c.setId(ja.getJSONObject(i).getInt("Carpool_Id"));
				c.setCapacity(ja.getJSONObject(i).getInt("Capacity"));
				c.setDriverEmail(ja.getJSONObject(i).getString("Owner_Email"));
				
				ArrayList<String> memEmail = new ArrayList<String>();
				memEmail.add(ja.getJSONObject(i).getString("Member_Email"));
				c.setMembersEmail(memEmail);
				
				c.setDeptTime(ja.getJSONObject(i).getString("DepartTime"));
				c.setRetTime(ja.getJSONObject(i).getString("ReturnTime"));
				cps.add(c);
			}
		}catch (JSONException e){
			Log.e("JSON Exception", e.toString());
		}
		
		return cps;
	}
	
	public void addPool(Carpool cp){
		
		JSONObject jd = null;
		
		String url = carpoolURL + "/getNumPools";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		
		jd = connect(url, nvp);
		
		try{
			cp.setId(jd.getInt("NumPools") + 1);
			Log.v("num", "got num pools");
		}catch(JSONException e1){
			Log.e("JSON Exception", e1.toString());
		}
		
		url = carpoolURL + "/addPool";
		nvp.add(new BasicNameValuePair("id", new Integer(cp.getId()).toString()));
		nvp.add(new BasicNameValuePair("owneremail", cp.getDriverEmail()));
		nvp.add(new BasicNameValuePair("memberemail", cp.getMembersEmail().get(0)));
		nvp.add(new BasicNameValuePair("capcaity", new Integer(cp.getCapacity()).toString()));
		nvp.add(new BasicNameValuePair("depttime", cp.getDeptTime()));
		nvp.add(new BasicNameValuePair("returntime", cp.getRetTime()));
		
		
		jd = connect(url, nvp);
	}
	
	public void updateID(Integer newid, Integer oldid){
		String url = carpoolURL + "/updateID"; 
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", newid.toString()));
		nvp.add(new BasicNameValuePair("oldid", oldid.toString()));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateOwnerEmail(String email, Integer id){
		String url = carpoolURL + "/updateOwnerEmail";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", id.toString()));
		nvp.add(new BasicNameValuePair("email", email));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateMemberEmail(String email, Integer id){
		String url = carpoolURL + "/updateMemberEmail";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", id.toString()));
		nvp.add(new BasicNameValuePair("email", email));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateCapacity(Integer capacity, Integer id){
		String url = carpoolURL + "/updateCapacity";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", id.toString()));
		nvp.add(new BasicNameValuePair("capacity", capacity.toString()));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateDeptTime(String dept, Integer id){
		String url = carpoolURL + "updateDeptTime";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", id.toString()));
		nvp.add(new BasicNameValuePair("depttime", dept));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	public void updateReturnTime(String ret, Integer id){
		String url = carpoolURL + "/updateDeptTime";
		ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("id", id.toString()));
		nvp.add(new BasicNameValuePair("returntime", ret));
		
		
		JSONObject jd = null;
		
		jd = connect(url,nvp);
	}
	
	private JSONObject connect(String url, ArrayList<NameValuePair> nvp){
		JSONArray jArray = null;
		JSONObject jo = null;
		String result = null;
		InputStream is = null;
		StringBuilder sb=null;
		
		
		//http post
		try{
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(url);
		     httppost.setEntity(new UrlEncodedFormEntity(nvp));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		}catch(Exception e){
		     Log.e("log_tag", "Error in http connection"+e.toString());
		}
		
		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		    sb = new StringBuilder();
		    sb.append(reader.readLine() + "\n");

		    String line="0";
		    while ((line = reader.readLine()) != null) {
		    	sb.append(line + "\n");
		    }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		
		Log.e("DBRESULTS", result);
		
		try{
		jo = new JSONObject(result);
		}catch(JSONException e1){
			Log.e("Json error", e1.toString());
		}
				
		return jo;
	}
	
	private JSONArray connectArr(String url, ArrayList<NameValuePair> nvp){
		JSONArray jArray = null;
		JSONObject jo = null;
		String result = null;
		InputStream is = null;
		StringBuilder sb=null;
		
		
		//http post
		try{
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(url);
		     httppost.setEntity(new UrlEncodedFormEntity(nvp));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		}catch(Exception e){
		     Log.e("log_tag", "Error in http connection"+e.toString());
		}
		
		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		    sb = new StringBuilder();
		    sb.append(reader.readLine() + "\n");

		    String line="0";
		    while ((line = reader.readLine()) != null) {
		    	sb.append(line + "\n");
		    }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		
		Log.e("DBRESULTS", result);
		
		try{
		jArray = new JSONArray(result);
		}catch(JSONException e1){
			Log.e("Json error", e1.toString());
		}
				
		return jArray;
	}

}
