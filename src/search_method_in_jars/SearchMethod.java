package search_method_in_jars;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SearchMethod {

	// Informe o caminho onde estão os jars alvo de busca
	private static final String PATH_TO_JARS = "C:\\Users\\indra\\Downloads\\jars\\";

	// Informe o nome da classe que deseja fazer a busca
	private static final String SEARCH_METHOD_NAME = "RetryingMetaStoreClient";

	public static void main(String[] args) {

		List<String> fileNameFromPath = getFileNameFromPath(PATH_TO_JARS);

		try {
			System.out.println("CLASSE: " + SEARCH_METHOD_NAME + "\n");
			for (String fileName : fileNameFromPath) {
				
				Set<String> classNamesFromJarFile = getClassNamesFromJarFile(new File(PATH_TO_JARS + fileName), SEARCH_METHOD_NAME);
				
				if (classNamesFromJarFile != null && !classNamesFromJarFile.isEmpty()) {
					System.out.println("ARQUIVO: " + fileName);
				} 
			} 
			System.out.println("*****************************************************************************");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Set<String> getClassNamesFromJarFile(File givenFile, String nameToCompare) throws IOException {
		Set<String> classNames = new HashSet<>();

		try (JarFile jarFile = new JarFile(givenFile)) {
			Enumeration<JarEntry> e = jarFile.entries();
			while (e.hasMoreElements()) {
				JarEntry jarEntry = e.nextElement();
				if (jarEntry.getName().endsWith(".class") && jarEntry.getName().contains(nameToCompare)) {
					String className = jarEntry.getName().replace("/", ".").replace(".class", "");
					classNames.add(className);
				}
			}
			return classNames;
		}
	}

	private static List<String> getFileNameFromPath(String path) {
		List<String> results = new ArrayList<String>();
		File[] files = new File(path).listFiles();

		for (File file : files) {
			if (file.isFile() && file.getName().contains(".jar")) {
				results.add(file.getName());
			}
		}

		return results;
	}
}
