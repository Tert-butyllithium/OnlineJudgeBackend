package edu.sustech.oj_server.moss;

import edu.sustech.oj_server.entity.SourceCode;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.*;

/**
 * Client for communicating with the MOSS server socket. Handles the
 * configuration, sending of files and receiving results.
 */
public class SocketClient {
	private static final String MESSAGE_UNKNOWN_LANGUAGE = "MOSS Server does not recognize this programming language";
	private static final String DEFAULT_LANGUAGE = "java";
	private static final int STARTING_SETID = 1;

	private Socket socket;
	private Stage currentStage = Stage.DISCONNECTED;

	private String server;
	private int port;
	private String userID;
	private String language;
	private int setID = STARTING_SETID;
	private long optM = 10000;
	private int optD = 0;
	private int optX = 0;
	private long optN = 5000;
	private String optC = "";
	private URL resultURL;
	private List<String> supportedLanguages = Arrays.asList("c", "cc", "java",
			"ml", "pascal", "ada", "lisp", "schema", "haskell", "fortran",
			"ascii", "vhdl", "perl", "matlab", "python", "mips", "prolog",
			"spice", "vb", "csharp", "modula2", "a8086", "javascript", "plsql");

	private OutputStream out;
	private BufferedReader in;

	private ArrayList<SourceCode> sourceCodeBuffer;
	private ArrayList<File> fileBuffer;

	public void setLanguage(String language) throws MossException {
		if (!supportedLanguages.contains(language)) {
			throw new MossException(MESSAGE_UNKNOWN_LANGUAGE);
		}
		this.language = language;
	}

	public void setOptC(String optC) {
		this.optC = optC;
	}

	public void setOptM(long optM) {
		this.optM = optM;
	}

	public void setOptN(long optN) {
		this.optN = optN;
	}

	public void setOptX(int optX) {
		this.optX = optX;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	@Deprecated
	public int getIncSetID() {
		return setID++;
	}

	public String getLanguage() {
		return language;
	}

	public String getOptC() {
		return optC;
	}

	public int getOptD() {
		return optD;
	}

	public long getOptM() {
		return optM;
	}

	public long getOptN() {
		return optN;
	}

	public int getOptX() {
		return optX;
	}

	public int getPort() {
		return port;
	}

	public URL getResultURL() {
		return resultURL;
	}

	public String getServer() {
		return server;
	}

	public int getSetID() {
		return setID;
	}

	public Socket getSocket() {
		return socket;
	}

	public List<String> getSupportedLanguages() {
		return supportedLanguages;
	}

	public String getUserID() {
		return userID;
	}

	public SocketClient() {
		this.server = "moss.stanford.edu";
		this.port = 7690;
		this.language = DEFAULT_LANGUAGE;
		this.userID="552410302";
		this.sourceCodeBuffer=new ArrayList<>();
		this.fileBuffer=new ArrayList<>();
	}

	public SocketClient(String server, int port) {
		this();
		this.server = server;
		this.port = port;
	}

	public SocketClient(String server, int port, String language) {
		this(server, port);
		this.language = language;
	}

	private void close() {
		/*
		 * do not check stage here so clients can close a connection in any
		 * situation.
		 */
		try {
			sendCommand("end\n");
			out.close();
			in.close();
			socket.close();
		} catch (MossException e) {
		} catch (IOException e2) {
		} finally {
			currentStage = Stage.DISCONNECTED;
		}

	}

	private void connect() throws UnknownHostException, IOException, SecurityException {
		if (currentStage != Stage.DISCONNECTED) {
//			throw new RuntimeException("Client is already connected");
			currentStage=Stage.AWAITING_INITIALIZATION;
			return;
		}
		socket = new Socket(this.server, this.port);
		socket.setKeepAlive(true);
		out = socket.getOutputStream();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream(),
				Charsets.US_ASCII));
		currentStage = Stage.AWAITING_INITIALIZATION;
	}

	private String readFromServer() throws IOException {
		return in.readLine();
	}
	private void run() throws MossException, IOException, UnknownHostException {
		connect();
		sendInitialization();
		sendLanguage();
	}
	private String sendCommand(Object... objects) throws MossException {
		// TODO test
		Vector<String> commandStrings = new Vector<String>();
		String[] commandArray = new String[commandStrings.size()];
		for (Object o : objects) {
			String s;
			s = o.toString();

			commandStrings.add(s);
		}
		return sendCommandStrings(commandStrings.toArray(commandArray));
	}
	private String sendCommandStrings(String... strings) throws MossException {
		if (strings == null || strings.length == 0) {
			throw new MossException(
					"Failed to send command because it was empty.");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			String s = strings[i];
			sb.append(s);
			if (i != strings.length - 1) {
				sb.append(" ");
			}
		}
		sb.append('\n');
		try {
			byte[] bytes = (sb.toString()).getBytes(Charsets.US_ASCII);
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			throw new MossException("Failed to send command: " + e.getMessage());
		}

		return sb.toString();
	}
	private void sendInitialization() throws MossException {
		if (currentStage != Stage.AWAITING_INITIALIZATION) {
			throw new RuntimeException(
					"Cannot send initialization. Client is either already initialized or not connected yet.");
		}
		sendCommand("moss", userID);
		sendCommand("directory", optD);
		sendCommand("X", optX);
		sendCommand("maxmatches", optM);
		sendCommand("show", optN);
		currentStage = Stage.AWAITING_LANGUAGE;
	}

	private void sendLanguage() throws MossException, IOException {
		if (currentStage != Stage.AWAITING_LANGUAGE) {
			throw new RuntimeException(
					"Language already sent or client is not initialized yet.");
		}
		sendCommand("language", language);
		System.out.printf("Language sent: %s\n", language);
		// confirm valid language server-side
		String serverString;
		serverString = readFromServer();
		if (serverString == null
				|| !serverString.trim().toLowerCase(Locale.ENGLISH)
						.equals("yes")) {
			throw new MossException(MESSAGE_UNKNOWN_LANGUAGE);
		}
		currentStage = Stage.AWAITING_FILES;
	}

	private void sendQuery() throws MossException, IOException {
		if (currentStage != Stage.AWAITING_QUERY) {
			throw new RuntimeException(
					"Cannot send query at this time. Connection is either not initialized or already closed");
		}
		if (setID == 1) {
			throw new MossException("You did not upload any files yet");
		}
		sendCommand(String.format(Locale.ENGLISH, "%s %d %s", "query", 0, optC));
		currentStage = Stage.AWAITING_RESULTS;
		System.out.println("Query Submited.");
		// Query submitted, waiting for server's response
		String result = readFromServer();
		System.out.println(result);
		if (null != result
				&& result.toLowerCase(Locale.ENGLISH).startsWith("http")) {
			try {
				this.resultURL = new URL(result.trim());
			} catch (MalformedURLException e) {
				throw new MossException(
						"MOSS submission failed. The server did not return a valid URL with detection results.",
						e);
			}
			currentStage = Stage.AWAITING_END;
		} else {
			throw new MossException(
					"MOSS submission failed. The server did not return a valid URL with detection results.");
		}
	}

	private void uploadFile(File file) throws IOException {
		uploadFile(file, false);
	}

	private void uploadBaseFile(File file) throws IOException {
		uploadFile(file, true);
	}

	private void uploadFile(File file, boolean isBaseFile) throws IOException {
		if (currentStage != Stage.AWAITING_FILES
				&& currentStage != Stage.AWAITING_QUERY) {
			throw new RuntimeException(
					"Cannot upload file. Client is either not initialized properly or the connection is already closed");
		}
		byte[] fileBytes = FileUtils.readFileToByteArray(file);
		String filename = normalizeFilename(file.getAbsolutePath());
		String uploadString = String.format(Locale.ENGLISH,
				"file %d %s %d %s\n", // format:
				isBaseFile ? 0 : getIncSetID(), // 1. setID
				language, // 2. language
				fileBytes.length, // 3. size
				/*
				 * Use Unix-style path to remain consistent. TODO test this with
				 * non-local files, e.g. on network shares
				 */
				filename); // 4. file path
		System.out.println("uploading file: " + filename);
		out.write(uploadString.getBytes(Charsets.UTF_8));
		out.write(fileBytes);
		currentStage = Stage.AWAITING_QUERY;

	}
	private void uploadContent(String identifier, String code) throws IOException {
		if (currentStage != Stage.AWAITING_FILES
				&& currentStage != Stage.AWAITING_QUERY) {
			throw new RuntimeException(
					"Cannot upload file. Client is either not initialized properly or the connection is already closed");
		}

		byte[] codeBytes = code.getBytes();
		String uploadString = String.format(Locale.ENGLISH,
				"file %d %s %d %s\n",
				getIncSetID(),
				language,
				codeBytes.length,
				identifier);
		System.out.println("Uploading file: " + identifier);
		out.write(uploadString.getBytes(Charsets.UTF_8));
		out.write(codeBytes);
		currentStage=Stage.AWAITING_QUERY;
	}

	private String normalizeFilename(String filename) {
		String result = Normalizer.normalize(filename, Normalizer.Form.NFD);
		result = FilenameUtils.normalizeNoEndSeparator(result, true)
				.replaceAll("[^\\p{ASCII}]", "");
		return result;
	}

	public void addContent(SourceCode sourceCode){
		sourceCodeBuffer.add(sourceCode);
	}

	public void addFile(File file){
		fileBuffer.add(file);
	}

	public URL request() throws IOException, MossException {
		run();
		File baseDir=new File(this.getClass().getClassLoader().getResource("basefile").getPath());
		for (File file: baseDir.listFiles()){
			uploadBaseFile(file);
		}
		for (SourceCode sourceCode: sourceCodeBuffer){
			uploadContent(sourceCode.getId()+"/"+sourceCode.getUser_id(),sourceCode.getCode());
		}
		sendQuery();
		close();
		return getResultURL();
	}

	public void init(){
		sourceCodeBuffer.clear();
		fileBuffer.clear();
		currentStage=Stage.DISCONNECTED;
	}

	public static void main(String[] args) throws IOException, MossException {
		SocketClient socketClient = new SocketClient();
		socketClient.init();
//		socketClient.run();
//		String path="/home/bigbang/IDEA/GoGoTianXiaDiYi/src/main/java/edu/sustech/oj_server/moss/";
//		socketClient.uploadBaseFile(new File(path+"input"));
//		socketClient.uploadBaseFile(new File(path+"reader"));
//		socketClient.uploadBaseFile(new File(path+"printer"));
//		socketClient.uploadFile(new File(path+"input"));
//		socketClient.uploadFile(new File(path+"input"));
//		socketClient.sendQuery();
//		System.out.println(socketClient.getResultURL());
//		socketClient.close();
	}

}
