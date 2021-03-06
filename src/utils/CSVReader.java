package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/***
 * <p><b>CSVReader</b> aims to read configurations from the csv result generated by SPLOT solver. </p> 
 *
 */
public class CSVReader {

	public static void main(String[] args) {	
		CSVReader reader = new CSVReader("file/test.csv");
		System.out.println(reader.configs);
	}
	
	/** valid configurations */
	List<List<String>> configs;
	
	public CSVReader(String path){
		File file = new File(path);
		
		if(!file.exists()){
			System.out.println("No Such File! " + path);
			return;
		}
		
		try {
			configs = getValidConfigs(file);
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	/***
	 * <p>To obtain configurations from the csv file generated by <b>T-wise</b> algorithms.</p>
	 * @param file csv result
	 * @return configurations list
	 */
	public List<List<String>> getValidConfigs(File file) throws IOException{
		List<List<String>> configurations = new ArrayList<List<String>>();
		
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		
		/** 
		 * deal with the first line of csv file, add N null configurations.
		 */
		if ((line = br.readLine()) != null){
			String[] parts = line.split(";");
			for (int i = 1; i < parts.length; i++){
				List<String> configuration = new ArrayList<String>();
				configurations.add(configuration);
			}
		}
		
		/**
		 * from the second line of csv file, 
		 */
		while ((line = br.readLine()) != null) {
			String[] parts = line.split(";");
			for (int i = 1; i < parts.length; i++){
				List<String> configuration = configurations.get((i-1));
				parts[i] = parts[i].trim();
				if (parts[i].equals("X")){
					configuration.add(parts[0]);
				} else {
					configuration.add("!" + parts[0]);
				}
			}
		}
	 
		br.close();
		
		return configurations;
	}

}
