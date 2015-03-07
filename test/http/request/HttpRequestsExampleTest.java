package http.request;
import static org.junit.Assert.*;
import http.request.HttpRequestsExample;
import http.request.Respond;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class HttpRequestsExampleTest {

	static HttpRequestsExample testClass;
	
	@Before
	public void setUp(){
		testClass = new HttpRequestsExample();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testMain() {	//if it works
		testClass.main(new String[] {"arg1"});
	}
	
	@Test
	public void testgetRequestFromFileReturnsNotNull(){
		String fileName="thisIsWrongFilename.txt";
		assertNotNull("check if ArrayList exists",testClass.getRequestFromFile(fileName));	
	}
	@Test
	public void testgetRequestFromFilereturnsEmtyArray(){
		String fileName="thisIsWrong.txt";
		assertTrue("check if ArrayList is empty",testClass.getRequestFromFile(fileName).isEmpty());	
	}
	@Test(expected=IOException.class)
	public void testgetRespondForExpeption() throws IOException{
		String request="thisIsWrongRequest";
		testClass.getRespond(request);	
	}
	@Test
	public void	testwriteResponsesToFilesForEmtpyList(){ //if it works with empty list
		List<Respond> testList = new ArrayList<Respond>();
		testClass.writeResponsesToFiles(testList);
	}
	@Test
	public void testwriteListToFileForEmptyList(){
		List<Respond> testList = new ArrayList<Respond>();
		String fileName="JunitTempFile.txt";
		HttpRequestsExample.writeListToFile(testList, fileName);
		File testFile = new File(fileName);
		assertFalse("Emtpy file exists!", testFile.exists());
	}
	
	
}
