package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import exceptions.ArgException;
import exceptions.InvalidChoiceException;
import partie.Partie;

public class BattleMain {

	private final static String URL_ID_EQUIPE = "/player/getIdEquipe";

	private static String serverUrl;
	private static String name;
	private static String password;
	private static int niveau;
	private static String format;

	private static String idEquipe;

	public static void main(String[] args) throws IOException, ArgException, InvalidChoiceException, InterruptedException {

		String idEquipe;
		// Init properties //
		initProperties("config/config.properties");
		niveau = 0;

		if (args.length > 0) {

			switch (args[0]) {
///epic-ws/epic/player/getIdEquipe/{nomEquipe}/{motDePasse}
// epic-ws/epic/player/getIdEquipe/test/test			
			case "-p":
				System.out.println(get(serverUrl + "/ping"));
				break;

			case "-config":
				showProperties();
				break;

			case "-e":
				if (args.length > 1) {
					int niveau = Integer.parseInt(args[1]);
					System.out.println("On va joueur contre un  IA de niveau: " + niveau);
					idEquipe = get(serverUrl + URL_ID_EQUIPE + "/" + name + "/" + password);
					System.out.println("Id equipe :" + idEquipe);
					
					Partie p = new Partie( serverUrl, idEquipe, format, niveau);
					
				} else {
					throw new ArgException("Niveau difficulté IA attendu après l'option -e");
				}

				break;

			case "-m":
				niveau = -1;
				System.out.println("On va joueur contre un autre joueur");
				idEquipe = get(URL_ID_EQUIPE + "/" + name + "/" + "password");
				System.out.println("Id equipe :" + idEquipe);

				Partie p = new Partie( serverUrl, idEquipe, format, niveau);
				
				break;

			default:
				throw new ArgException("Mauvaise extension : " + args[0]);
			}

		} else {
			throw new ArgException("Argument attendu");
		}

	}

	public static void showProperties() {
		System.out.println("Server url :'" + serverUrl + "'");
		System.out.println("Team name : '" + name + "'");
		System.out.println("Team password '" + password + "'");
		System.out.println("Format '" + format + "'");
	}

	public static void initProperties(String file) {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(file);

			// load a properties file
			prop.load(input);

			serverUrl = prop.getProperty("rest.base.url");
			name = prop.getProperty("team.name");
			password = prop.getProperty("team.password");
			format = prop.getProperty("format");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String get(String url) throws IOException {

		String res = "";

		URL oracle = null;
		try {
			oracle = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			res = inputLine;
		in.close();

		return res;
	}

}
