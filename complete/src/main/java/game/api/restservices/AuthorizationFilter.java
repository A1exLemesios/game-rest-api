package game.api.restservices;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import game.api.Dao.TokenDao;
import game.api.Dao.UserDao;
import game.api.Model.LoginRequest;
import game.api.Model.User;

@Component
public class AuthorizationFilter {
	
	@Autowired
	private TokenDao tokenDao;
	
	@Autowired
	private UserDao userDao;

	public String createToken (LoginRequest loginRequest) throws Exception {
		System.out.println("Create token called for player : " + loginRequest.getUserName());
		ApplicationConstants applicationConstants = new ApplicationConstants();
		
		User user = userDao.getUserByUsernameAndPassword(loginRequest.getUserName(), loginRequest.getPassWord());

		if (user == null) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "User doesn't exist . Please use register API first");			
		}
		String signingKey = applicationConstants.SIGNING_KEY;
		String token = null;
		String tokenInput = signingKey + loginRequest.getPassWord() + loginRequest.getUserName();
		
		token = Base64.getEncoder().encodeToString(tokenInput.getBytes());
		
		List <String> tokensList = tokenDao.retrieveAllTokens();
		
		for (String string : tokensList) {
			if (string.equals(token)) {
				 throw new ResponseStatusException(
				           HttpStatus.UNAUTHORIZED, "This user has already a valid token .");			
			}
		}
		try {
			tokenDao.saveToken(token);
		} catch (Exception e) {
			throw e;
		}
		
		return token;
	}
	
	public boolean validateToken (String username, String authorizationToken) throws Exception {
		System.out.println("Validate token called for player : " + username);

		ApplicationConstants applicationConstants = new ApplicationConstants();

		List <String> tokensList = tokenDao.retrieveAllTokens();
		boolean valid = false;
		User user = userDao.getUser(username);
		if (user == null) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "User not found");	
		}
		for(String token : tokensList) {
			String temp = token;
			
			byte[] decodedBytes = Base64.getDecoder().decode(temp);
			String decodedString = new String(decodedBytes);
			decodedString = decodedString.replace(applicationConstants.SIGNING_KEY, "");
			if ((user.getPassword() + user.getUsername()).equals(decodedString)) {
			valid = true;
			
			return valid;	
			}

		}
		if (!valid) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "Token is not valid");		
		}

		return valid;
	}
	
	public void registerUser(LoginRequest request) throws Exception {
		System.out.println("Register user called for player : " + request.getUserName());

		User user = userDao.getUserByUsernameAndPassword(request.getUserName(), request.getPassWord());
		if (user != null) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "User already registered. Please aquire a token");	
		}
		
		userDao.register(request);
	}
	
	
	
}
