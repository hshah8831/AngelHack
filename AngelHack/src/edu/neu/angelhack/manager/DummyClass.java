package edu.neu.angelhack.manager;

import java.io.File;
import java.io.IOException;
//import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import org.json.*;

public class DummyClass {
	private String apikey = "e92d3e29-a835-4b3c-a6fe-97be1c6dcee0";
	private String scene_photo = "document_scan";
	private String url_ocrdocument = "https://api.idolondemand.com/1/api/sync/ocrdocument/v1";

	// private String filesrc = "D:\\SaturdayMayten08.JPG";

	public void post1(String filesrc) {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpPost httppost = new HttpPost(url_ocrdocument);

			File f = new File(filesrc);
			FileBody fileBody = new FileBody(f);
			StringBody apikeyStringBody = new StringBody(apikey,
					ContentType.TEXT_PLAIN);
			StringBody SCENE_PHOTO = new StringBody(scene_photo,
					ContentType.TEXT_PLAIN);

			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("file", fileBody)
					.addPart("apikey", apikeyStringBody)
					.addPart("mode", SCENE_PHOTO).build();

			httppost.setEntity(reqEntity);

			CloseableHttpResponse response = null;

			try {
				response = httpclient.execute(httppost);

				System.out.println(response.getStatusLine());
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String jsonstr = entity.toString();
					convertStringToJson(jsonstr);
					entity.writeTo(System.out);
				}

			} catch (ClientProtocolException cpe) {
				cpe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void convertStringToJson(String jsonstr) {

		JSONObject obj;
		try {
			obj = new JSONObject(jsonstr);
			String str;
			str = obj.getJSONArray("text_block").getJSONObject(0)
					.getString("text");
			System.out.println(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DummyClass dc = new DummyClass();
		String jsonstr = "{\"text_block\":[ {\"text\": \"Ea\noK Market\n14001-008 5/9/2008 14:01:38 Maricel\nInvll:00008880 lrsll:008922\n10 • $10.10 eaén\nLine EA $1.00 F\n1.54 lb 0 $0.75/ lb\nTangerine Honey Lb $1.16 F\nCucumber Persian l4 oz 31.29 F\nPeas Snap bag 2 Lb $1.69 F\nOlll Baby EA $0.59 F\n0.60 lb 0 $0.59/ lb\nDaikon Lb $0.35 F\nCoconut dry EA $1.19 F\n3.78 lb 0 $0.29/ lb\nOnion Rod L8 $1.10 F\n2.30 lb 0 $0.49/ lb\nApple Granny Smith Lb $1.13 F\n0.76 lb 0 $0.79/ lb\nPepper Jalapenos L8 $0.60 F\n4 0 $0.69 each\nMango Yellow EA $2.76 F\n2.55 lb 0 $0.59/ lb\nBanana Chlquita Lb $1.50 F\n0.85 lb 0 $0.89/ lb\nPepper Orange bel l Lb $0.76 F\n1.15 lb 0 $0.49/ lb\nBests Lb $0.56 F\n0.58 lb 0 00.99/ lb\nGinger L8 50.57 F\n0.60 lb 0 $2.49/ lb\n0kra Lb $l .49 F\n0.78 lb 0 $0.99/ lb\nAvocado L8 $0.77 F\n0.99 lb 0 $1.39/ lb\nPR00UCE POUMJ OPEN $1.38 F\n1.43 lb 0 $0.99/ lb\nTonato Roma Lb $1.42 F\nSplnach EA $0.79 F\nCilantro EA $0.33 F\nNet Sales $22.43\nTOTAL SALES $22.4l3\nsiliféfii éééféé\nCash $23.00\nChange $0.57\nItem count 33\nThank You\",\"left\": 0,\"top\": 0,\"width\": 300,\"height\": 620}]}";
		dc.convertStringToJson(jsonstr);
	}

	/*
	 * public static HashMap<String,String> fetchWordsFromFile() throws
	 * Exception { HashMap<String,String> items = new HashMap<String,String>();
	 * File f = new File("D:\\AllWords.txt"); BufferedReader br = new
	 * BufferedReader(new FileReader(f)); String line ="";
	 * while((line=br.readLine())!=null){ Stemmer s =new Stemmer(); char [] arr
	 * = line.split("\\s+")[0].trim().toCharArray(); s.add(arr, arr.length);
	 * s.stem(); String[] str = line.split("\\s+"); String l=""; for(int
	 * i=1;i<str.length;i++) l+=str[i]+" "; items.put(s.toString(),l); } return
	 * items; }
	 * 
	 * public static String parse(String keyval, HashMap<String, String>
	 * keyvalpair) throws Exception{
	 * 
	 * String[] arr = keyval.split("\\s+");
	 * //System.out.println(arr[0]+" "+arr[1]); ArrayList<String> words =
	 * removeSingleDoubleWords(arr); String value =
	 * words.get(words.size()-1).trim(); //System.out.println(value);
	 * HashMap<String, String> posswords = fetchWordsFromFile();
	 * 
	 * ArrayList<ArrayList<String>> similarchars = fetchSimilarCharacters();
	 * String key=""; String type=""; for(int j=0;j<words.size()-1;j++) { String
	 * w = words.get(j).toLowerCase(); Stemmer s = new Stemmer();
	 * s.add(w.toCharArray(),w.length()); s.stem(); String word = s.toString();
	 * if(posswords.containsKey(word)) { key+=" "+word;
	 * type=posswords.get(word); } else if(word.toLowerCase().equals("lb"));
	 * else { ArrayList<String> allpermuts =
	 * modifyWordToClosestMeaning(word,posswords,similarchars); String t =
	 * getMinimumDictanceWord(word,allpermuts,posswords).trim(); key += " "+t;
	 * type=type.equals("") ? (posswords.containsKey(t) ? posswords.get(t) : "")
	 * : type; } }
	 * 
	 * keyvalpair.put(key.trim(), type.equals("") ? "Miscellaneous" : type);
	 * return (key.trim()+" "+value); }
	 * 
	 * private static String getMinimumDictanceWord(String
	 * orig,ArrayList<String> allpermuts, HashMap<String, String> posswords) {
	 * int min=Integer.MAX_VALUE; String newword = orig; for(String word :
	 * allpermuts) { if(posswords.containsKey(word)) {
	 * //System.out.println("in posswords "+word); int val =
	 * EditDistance(word.toCharArray
	 * (),orig.toCharArray(),word.length(),orig.length()); if(val<min) {
	 * min=val; newword = word; } } } return newword; }
	 * 
	 * static int EditDistance( char[] X, char[] Y, int m, int n ) { // Base
	 * cases if( m == 0 && n == 0 ) return 0;
	 * 
	 * if( m == 0 ) return n;
	 * 
	 * if( n == 0 ) return m;
	 * 
	 * // Recurse int left = EditDistance(X, Y, m-1, n) + 1; int right =
	 * EditDistance(X, Y, m, n-1) + 1; int corner = EditDistance(X, Y, m-1, n-1)
	 * + ((X[m-1] != Y[n-1]) ? 1 : 0);
	 * 
	 * return min(left, right, corner); }
	 * 
	 * // Returns Minimum among a, b, c static int min(int a, int b, int c) {
	 * int min = (a<=b ? a : b); return min<=c ? min : c; }
	 * 
	 * private static void display(ArrayList<String> allpermuts) { for(String s
	 * : allpermuts) System.out.println(s); }
	 * 
	 * private static ArrayList<String> modifyWordToClosestMeaning(String word,
	 * HashMap<String, String> posswords, ArrayList<ArrayList<String>>
	 * similarchars) throws Exception { ArrayList<String> allpermuts = new
	 * ArrayList<String>(); if(word.isEmpty()) { allpermuts.add(""); return
	 * allpermuts; } allpermuts =
	 * modifyWordToClosestMeaning(word.substring(1),posswords,similarchars);
	 * ArrayList<String> arr = new ArrayList<String>(); for(String s :
	 * findSimilarCharsOfChar(""+word.charAt(0),similarchars)) { for(String str
	 * : allpermuts) arr.add((s+str).toLowerCase()); } allpermuts=arr; return
	 * allpermuts; }
	 * 
	 * private static ArrayList<String> findSimilarCharsOfChar(String c,
	 * ArrayList<ArrayList<String>> similarchars) { ArrayList<String> arr = new
	 * ArrayList<String>(); for(ArrayList<String> a : similarchars){
	 * if(a.contains(c)) return a; } arr.add(c); return arr; }
	 * 
	 * private static ArrayList<ArrayList<String>> fetchSimilarCharacters()
	 * throws Exception{ BufferedReader br = new BufferedReader(new
	 * FileReader(new File("D:\\similarcharacters.txt")));
	 * ArrayList<ArrayList<String>> similarchars = new
	 * ArrayList<ArrayList<String>>(); String line="";
	 * 
	 * while((line=br.readLine())!=null){ String[] chars = line.split("\\s+");
	 * ArrayList<String> arr = new ArrayList<String>(); for(int
	 * i=0;i<chars.length;i++) arr.add(chars[i]); similarchars.add(arr); }
	 * br.close(); return similarchars; }
	 * 
	 * private static ArrayList<String> removeSingleDoubleWords(String[] arr) {
	 * ArrayList<String> words = new ArrayList<String>(); for(int
	 * i=0;i<arr.length;i++){ if(arr[i].length()>1)
	 * words.add(arr[i].replaceAll("\\$", "")); } return words; }
	 * 
	 * public static void main(String args[]) throws Exception {
	 * HashMap<String,String> h = new HashMap<String,String>(); String text=
	 * "BHKERY CUMHERCIRL\n1 @ 2/5.00\nHLSUM 100% UHEHY 2.50 F\n1 @ 2f5.00\nHLSUH 100% WHEHT 2 50 F\nDHIRY\nS8 UH0lE MILK 3.69 F\nGROCERY\nSB CKIE HSIU 322 2 59 F\n;PR0DUCE\nPRE PHCKHGE CORN 3.99 F"
	 * ; String textspc = text.replaceAll("\\\\n","|"); String
	 * initclear="[^\\w\\d\\. \\$|]+"; String
	 * regex="([\\w\\d\\s\\.]+[\\s]+[$\\d]+([\\.][\\d]+)?)";
	 * 
	 * String newtext = textspc.replaceAll(initclear, " ");
	 * 
	 * Pattern p= Pattern.compile(regex); Matcher m = p.matcher(newtext);
	 * HashMap<String,String> keyvalpair = new HashMap<String,String>();
	 * while(m.find()) { //System.out.println(m.group(1)); String keyvalue =
	 * parse(m.group(1).trim(),keyvalpair); String[] arr = keyvalue.split(" ");
	 * String ans=""; for(int i=0;i<arr.length-1;i++) ans+=arr[i]+" ";
	 * ans=ans.trim(); if(ans.matches("\\d+(\\.\\d+)*") ||
	 * ans.trim().equals("")) continue; //System.out.println(ans);
	 * h.put(ans,arr[arr.length-1].equals("") ? "1.00" : arr[arr.length-1]); }
	 * Iterator itr = h.keySet().iterator(); String result="";
	 * while(itr.hasNext()) { String word=(String)(itr.next()); result+=
	 * word+"**"+h.get(word)+"**"+keyvalpair.get(word)+"\n"; }
	 * result=result.substring(0, result.length()-1);
	 * System.out.println(result); /*BufferedReader br = new BufferedReader(new
	 * FileReader(new File("words.txt"))); PrintWriter pw = new
	 * PrintWriter("AllWords.txt"); String line=""; String type="";
	 * while((line=br.readLine())!=null) { System.out.println(line);
	 * if(line.toLowerCase().contains("type:"))
	 * type=line.substring(line.indexOf("Type:")+5).toLowerCase().trim(); else
	 * if(line.trim().equals("")); else pw.println(line.toLowerCase()+" "+type);
	 * } pw.close();
	 */
	// }*/
}
