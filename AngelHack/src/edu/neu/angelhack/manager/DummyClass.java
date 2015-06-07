package edu.neu.angelhack.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

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
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.json.*;

public class DummyClass {
	private String apikey = "e92d3e29-a835-4b3c-a6fe-97be1c6dcee0";
	private String scene_photo = "document_scan";
	private String url_ocrdocument = "https://api.idolondemand.com/1/api/sync/ocrdocument/v1";

	// private String filesrc = "D:\\SaturdayMayten08.JPG";

	public String post1(String filesrc) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String output = new String();

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
				StringBuilder out = new StringBuilder();
				if (entity != null) {
					InputStream input = entity.getContent();
					BufferedReader rdr = new BufferedReader(new InputStreamReader(input));
					//StringWriter s =new StringWriter();
					//IOUtils.copy(input, s,"");
					String line = "";
					while((line = rdr.readLine())!= null)
					{
						out.append(line);
					}
					
					String jsonstr = out.toString();
					output = convertStringToJson(jsonstr);
					//entity.writeTo(System.out);
					//return output;
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
		return output;
	}

	public String convertStringToJson(String jsonstr) {
		String str = null;
		System.out.println(jsonstr);
		try {
			JSONObject obj = new JSONObject(jsonstr);
			
			str = obj.getJSONArray("text_block").getJSONObject(0)
					.getString("text");
			//return(str);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
		
	}

	public static HashMap<String,String> fetchWordsFromFile() throws Exception
	{
		HashMap<String,String> items = new HashMap<String,String>();
		File f = new File("D:\\AllWords.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line ="";
		while((line=br.readLine())!=null){
			Stemmer s =new Stemmer();
			char [] arr = line.split("\\s+")[0].trim().toCharArray();
			s.add(arr, arr.length);
			s.stem();
			String[] str = line.split("\\s+");
			String l="";
			for(int i=1;i<str.length;i++)
				l+=str[i]+" ";
			items.put(s.toString(),l);
		}
		return items;
	}

	public static String parse(String keyval, HashMap<String, String> keyvalpair) throws Exception{

		String[] arr = keyval.split("\\s+");
		//System.out.println(arr[0]+" "+arr[1]);
		ArrayList<String> words = removeSingleDoubleWords(arr);
		String value = words.get(words.size()-1).trim();
		//System.out.println(value);
		HashMap<String, String> posswords = fetchWordsFromFile();

		ArrayList<ArrayList<String>> similarchars = fetchSimilarCharacters();
		String key="";
		String type="";
		for(int j=0;j<words.size()-1;j++)
		{
			String w = words.get(j).toLowerCase();
			Stemmer s = new Stemmer();
			s.add(w.toCharArray(),w.length());
			s.stem();
			String word = s.toString();
			if(posswords.containsKey(word)) 
			{
				key+=" "+word;
				type=posswords.get(word);
			}
			else if(word.trim().toLowerCase().equals("lb"));
			else {
				ArrayList<String> allpermuts = modifyWordToClosestMeaning(word,posswords,similarchars);
				String t = getMinimumDictanceWord(word,allpermuts,posswords).trim();
				key += " "+t;
				type=type.equals("") ? (posswords.containsKey(t) ? posswords.get(t) : "") : type;
			}		
		}

		keyvalpair.put(key.trim(), type.equals("") ? "Miscellaneous" : type);	
		return (key.trim()+" "+value);
	}

	private static String getMinimumDictanceWord(String orig,ArrayList<String> allpermuts, HashMap<String, String> posswords) {
		int min=Integer.MAX_VALUE;
		String newword = orig;
		for(String word : allpermuts)
		{
			if(posswords.containsKey(word))
			{
				//System.out.println("in posswords "+word);
				int val = EditDistance(word.toCharArray(),orig.toCharArray(),word.length(),orig.length());
				if(val<min)
				{
					min=val;
					newword = word;
				}
			}
		}
		return newword;
	}

	static int EditDistance( char[] X, char[] Y, int m, int n )
	{
		// Base cases
		if( m == 0 && n == 0 )
			return 0;

		if( m == 0 )
			return n;

		if( n == 0 )
			return m;

		// Recurse
		int left = EditDistance(X, Y, m-1, n) + 1;
		int right = EditDistance(X, Y, m, n-1) + 1;
		int corner = EditDistance(X, Y, m-1, n-1) + ((X[m-1] != Y[n-1]) ? 1 : 0);

		return min(left, right, corner);
	}

	// Returns Minimum among a, b, c
	static int min(int a, int b, int c)
	{
		int min = (a<=b ? a : b);
		return min<=c ? min : c;
	}

	private static void display(ArrayList<String> allpermuts) {
		for(String s : allpermuts)
			System.out.println(s);
	}

	private static ArrayList<String> modifyWordToClosestMeaning(String word,
			HashMap<String, String> posswords, ArrayList<ArrayList<String>> similarchars) throws Exception {
		ArrayList<String> allpermuts = new ArrayList<String>();
		if(word.isEmpty()) {
			allpermuts.add("");
			return allpermuts;
		}
		allpermuts = modifyWordToClosestMeaning(word.substring(1),posswords,similarchars);
		ArrayList<String> arr = new ArrayList<String>();
		for(String s : findSimilarCharsOfChar(""+word.charAt(0),similarchars))
		{
			for(String  str : allpermuts)
				arr.add((s+str).toLowerCase());
		}
		allpermuts=arr;
		return allpermuts;
	}

	private static ArrayList<String> findSimilarCharsOfChar(String c, ArrayList<ArrayList<String>> similarchars) {
		ArrayList<String> arr = new ArrayList<String>();
		for(ArrayList<String> a : similarchars){
			if(a.contains(c)) return a;
		}
		arr.add(c);
		return arr;
	}

	private static ArrayList<ArrayList<String>> fetchSimilarCharacters() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\similarcharacters.txt")));
		ArrayList<ArrayList<String>> similarchars = new ArrayList<ArrayList<String>>();
		String line="";

		while((line=br.readLine())!=null){
			String[] chars = line.split("\\s+");
			ArrayList<String> arr = new ArrayList<String>();
			for(int i=0;i<chars.length;i++)
				arr.add(chars[i]);
			similarchars.add(arr);
		}
		br.close();
		return similarchars;
	}

	private static ArrayList<String> removeSingleDoubleWords(String[] arr) {
		ArrayList<String> words = new ArrayList<String>();
		for(int i=0;i<arr.length;i++){
			if(arr[i].length()>1) words.add(arr[i].replaceAll("\\$", ""));
		}
		return words;
	}
	
	public void getCropImage(String fileName) throws IOException {
		   int offsetFromLeft = 0;
		   int offsetFromTop = 0;
		   String fls[] = fileName.split("\\\\");
		   String dotSplit[] = fls[fls.length-1].split(".");
		   String basePath = "";
		   for(int i=0; i < fls.length-1; i++)
		   {
		      basePath+= fls[i] + "\\";
		   }
		   String newPath_shop = basePath + dotSplit[0] + "_shop."+dotSplit[1];// "C:\\Vardhman-Drive\\Untitled1_shop.JPG";
		   String newPath_data = basePath + dotSplit[0] + "_data."+dotSplit[1];
		   String imageFormat = "jpg";
		   BufferedImage image = ImageIO.read(new File(fileName));
		   int height = image.getHeight();
		   int width = image.getWidth();
		   int widthOfNewImage = width;
		   int heightOfShop = (int)(.20 * height);
		   int heightOfData = (int)(.80 * height);
		   System.out.println("Height : " + height + "\nWidth : " + width);
		   BufferedImage out_shop = image.getSubimage(offsetFromLeft, offsetFromTop,
		         widthOfNewImage, heightOfShop);
		   BufferedImage out_data = image.getSubimage(offsetFromLeft, heightOfShop,
		         widthOfNewImage, heightOfData);
		   ImageIO.write(out_shop, imageFormat, new File(newPath_shop));
		   ImageIO.write(out_data, imageFormat, new File(newPath_data));
		   
	}

	public String textParser(String text) throws Exception
	{
		HashMap<String,String> h = new HashMap<String,String>();
		String textspc = text.replaceAll("\\\\n","|");
		String initclear="[^\\w\\d\\. \\$|]+";
		String regex="([\\w\\d\\s\\.]+[\\s]+[$\\d]+([\\.][\\d]+)?)";

		String newtext = textspc.replaceAll(initclear, " ");

		Pattern p= Pattern.compile(regex);
		Matcher m = p.matcher(newtext);
		HashMap<String,String> keyvalpair = new HashMap<String,String>();
		while(m.find())
		{
			//System.out.println(m.group(1));
			String keyvalue = parse(m.group(1).trim(),keyvalpair);
			String[] arr = keyvalue.split(" ");
			String ans="";
			for(int i=0;i<arr.length-1;i++)
				ans+=arr[i]+" ";
			ans=ans.trim();
			if(ans.matches("\\d+(\\.\\d+)*") || ans.trim().equals("")) continue;
			//System.out.println(ans);
			h.put(ans,(arr[arr.length-1].equals("") || arr[arr.length-1].equals("Lb"))? "1.00" : arr[arr.length-1]);
		}
		Iterator itr = h.keySet().iterator();
		String result="";
		while(itr.hasNext())
		{
			String word=(String)(itr.next());
			result+= word+"**"+h.get(word)+"**"+keyvalpair.get(word)+"\n";
		}
		result=result.substring(0, result.length()-1);
		System.out.println(result);
		return(result);
		/*BufferedReader br = new BufferedReader(new FileReader(new File("words.txt")));
		PrintWriter pw = new PrintWriter("AllWords.txt");
		String line="";
		String type="";
		while((line=br.readLine())!=null)
		{
			System.out.println(line);
			if(line.toLowerCase().contains("type:")) 
				type=line.substring(line.indexOf("Type:")+5).toLowerCase().trim();
			else if(line.trim().equals(""));
			else pw.println(line.toLowerCase()+" "+type);
		}
		pw.close();*/
	}
}
