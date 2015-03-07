package http.request;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Да се направи Java програма, която чете HTTP заявки от текстов файл Requests.txt,
 *  израща ги на HTTP сървър, слушащ на зададен хост и порт, прочита отговора от сървъра,
 *  записва отговора във файл Response.txt и ако кодът на отговора не е 200 ОК,
 *  отговорът и заявката, която го е предизвикала,
 *  се записват в отделен текстов файл - Errors.txt
 * 
 *
 */
public class HttpRequestsExample {
	 
	// GET POST HEAD OPTIONS PUT DELETE TRACE
	private static final String FILENAME="Request.txt"; 
	private static final String FILENAME_OK="Response.txt"; 
	private static final String FILENAME_ERROR ="Errors.txt"; 
	private static final String URL_NAME="http://localhost:8080"; 
	
	public static void main(String[] args)  {
 
		HttpRequestsExample http = new HttpRequestsExample();
 		List<String> requestsFromFile= http.getRequestFromFile(FILENAME);
		List<Respond> responses = new ArrayList<Respond>();
 		for (String request:requestsFromFile) {
			System.out.println("Testing 1 - Send Http "+request+" request");
			Respond response=null;
			try {
				response = http.getRespond(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responses.add(response);
		}
 		http.writeResponsesToFiles(responses);
	}
	
	/**
	 * @param File to read requests from
	 * @return ArrayList with requests as a String
	 */
	List<String> getRequestFromFile(String fileName){ //package for testing with JUnit
		File textFile = new File(fileName);
		String request=null;
		List<String> requestList= new ArrayList<String>();
		Scanner sc= null;
		try {
			sc = new Scanner(textFile);
			while (sc.hasNextLine()) {
				request=sc.nextLine(); 
				requestList.add(request);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File 'Request.txt' not found");
			e.printStackTrace();
		} finally {
			if (sc!=null) sc.close();
		}
		return requestList;
	}
	
	/**
	 * @param request to execute to server
	 * @return the response from the server as an object(Respond) that contains number,message,and the request itself
	 * @throws IOException
	 */
	Respond getRespond(String request) throws IOException { //package for testing with JUnit
	 
		Respond response = new Respond(0,null,request);
		URL obj = new URL(URL_NAME);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(request);
	 	int responseCode = con.getResponseCode();
		response.setNumber(responseCode);
	 	System.out.println("\nSending "+request+" request to URL : " + URL_NAME);
		System.out.println("Response Code : " + responseCode);
		String respondMessage = con.getResponseMessage();
		response.setText(respondMessage);
		System.out.println("Response Message is :"+respondMessage);
		return response;
	}
	
	/** sends two ArrayList of responses to two text files which names are constants for the class
	 * call another sub-method writeListToFile which performs the writing 
	 * @param Arraylist with responses got from the main method
	 */ 
	void writeResponsesToFiles(List<Respond> respondList){ //package for testing with JUnit
		List<Respond> allList = new ArrayList<Respond>();
		List<Respond> errorList = new ArrayList<Respond>();
		for (Respond response:respondList){ 
			if (response.getNumber() ==200){
				allList.add(response);
			} else {
				Respond trimmedResponse = new Respond(response.getNumber(),response.getText(),"");
				allList.add(trimmedResponse); //without the request as in the task definition.
				errorList.add(response);
			}
		}
		if (!allList.isEmpty()) {
			writeListToFile(allList,FILENAME_OK);
		}
		if (!errorList.isEmpty()) {
			writeListToFile(errorList,FILENAME_ERROR);
		}
	}
	
	/**
	 * @param ArrayList of responses sent from the method writeResponsesToFiles
	 * @param fileName to write responses to sent from the parent method
	 */
	static void writeListToFile(List<Respond> list,String fileName) { //package for testing with JUnit
		if (list.isEmpty()) return; //we do not want empty files 
		File fileToWrite = new File(fileName);
		BufferedWriter writeBuffer=null;
		try {
			writeBuffer = new BufferedWriter(new FileWriter(fileToWrite));
			for (Respond response:list){
				writeBuffer.write(response.getNumber()+" ");
				writeBuffer.write(response.getText());
				if (response.getNumber()!=200) {
					writeBuffer.write(" "+response.getRequest());
				}
				writeBuffer.newLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if (writeBuffer!=null){
				try {
					writeBuffer.flush();
					writeBuffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
		 
}
	 