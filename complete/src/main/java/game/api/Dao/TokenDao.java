
package game.api.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import game.api.Model.LeaderBoardRecord;


@Repository
public class TokenDao {

	
	
	public void saveToken(String token) throws Exception {
		try
		{
			Path pathToFile = Paths.get("src/main/java/game/api/Dao/Token.txt");
			String filePath = pathToFile.toAbsolutePath().toString();
			
		    FileWriter fw = new FileWriter(filePath,true);
		    fw.write("\n");
		    fw.write(token);

		    fw.close();
		}
		catch(IOException e) {
		    System.err.println("Error while saving token in db");
		    throw e;
		}	
	}
	
	public List<String> retrieveAllTokens() throws Exception {
		List <String> tokensList = new ArrayList<String>();
		
		 try {
			 Path pathToFile = Paths.get("src\\main\\java\\game\\api\\Dao\\Token.txt");
			 String file = pathToFile.toAbsolutePath().toString();		
		     BufferedReader reader = new BufferedReader(new FileReader(file));
		     
		     String token ;
		     while ((token = reader.readLine()) != null) {
		    	tokensList.add(token);
		     }
		     reader.close();
		} catch (Exception e) {
		    System.err.println("Error while retrieving tokens in db");
			throw e;
		}
		
		return tokensList;
	}
}