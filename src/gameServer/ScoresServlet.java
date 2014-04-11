package gameServer;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class ScoresServlet extends HttpServlet {
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(ScoresServlet.class);
	
	private DatabaseDriver dbDriver;
	
	public ScoresServlet(){
		dbDriver = new DatabaseDriver();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.debug("doGet");
		response.setContentType("text/xml");
		response.setStatus(HttpServletResponse.SC_OK);
		// get games from db
		String scoresXml = dbDriver.getScores();
		//String gameXml = new String("<Games><GameMaker></GameMaker></Games>");
		response.getWriter().println(scoresXml);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		LOG.debug("doPost");
		// insert game into db
		InputStream requestInput = null;
		try {
			requestInput = request.getInputStream();
		} catch (IOException e) {
			LOG.error(e);
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOG.error(e);
		}
		Document requestDoc = null;
		try {
			requestDoc = dBuilder.parse(requestInput);
		} catch (SAXException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		String user = requestDoc.getDocumentElement().getAttribute("userName");
		String gameName = requestDoc.getDocumentElement().getAttribute("gameName");
		int score = Integer.parseInt(requestDoc.getDocumentElement().getAttribute("score"));
		dbDriver.insertScore(user, score, gameName);
		response.setStatus(HttpServletResponse.SC_OK);
	}	

}
