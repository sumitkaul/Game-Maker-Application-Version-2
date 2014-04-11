package gameServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;



@SuppressWarnings("serial")
public class GameServlet extends HttpServlet {
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(GameServlet.class);
	private DatabaseDriver dbDriver;
	
	public GameServlet(){
		dbDriver = new DatabaseDriver();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.debug("doGet");
		response.setContentType("text/xml");
		response.setStatus(HttpServletResponse.SC_OK);
		// get games from db
		String gameXml = dbDriver.getGames();
		response.getWriter().println(gameXml);
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
		Node savedGameNode = requestDoc.getDocumentElement().getChildNodes().item(0);
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			LOG.error(e);
		}
		StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		try {
			transformer.transform(new DOMSource(savedGameNode), new StreamResult(buffer));
		} catch (TransformerException e) {
			LOG.error(e);
		}
		String savedGame = buffer.toString();
		LOG.debug("inserting... user=" + user + " gameName=" + gameName);
		LOG.debug("gameXML:\n" + savedGame);
		dbDriver.insertGame(user, gameName, savedGame);
		response.setStatus(HttpServletResponse.SC_OK);
	}	
}

