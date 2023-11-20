package unidad3.ejercicio01;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class Ejercicio1 {
	
	public static void main(String[] args) throws IOException {
		Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
		URL url = new URL("https://www.educastur.es");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try (Scanner s = new Scanner(con.getInputStream())) {
			while (s.findWithinHorizon("<img .*?src=\"(.*?)\"", 0) != null) {
				String path = s.match().group(1);
				download(urlPattern.matcher(path).matches() ? new URL(path) : new URL(url, path));
			}
		}
	}
	
	static void download(URL url) throws IOException {
		int n = 1;
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		String path = System.getProperty("user.home") + "/Pictures/" + FilenameUtils.getName(url.getPath());
		if (FilenameUtils.getExtension(path).isEmpty())
			path += "." + con.getContentType().split("/")[1];
		File file = new File(path);
		if (file.exists())
			file = new File(FilenameUtils.getFullPath(path) + FilenameUtils.getBaseName(path) + (n++) + "." + FilenameUtils.getExtension(path));
		System.out.printf("Descargando: %s\n", url.toString());
		System.out.printf("         en: %s\n", file.toString());
		System.out.printf("      desde: %s\n", con.getContentType());
		try (
			BufferedInputStream in = new BufferedInputStream(con.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		){
			out.write(in.readAllBytes());
		}
	}	
}