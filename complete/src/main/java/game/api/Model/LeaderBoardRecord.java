package game.api.Model;


public class LeaderBoardRecord {
	
	private String palindromeText;
	private String score;
	private String userId;
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPalindromeText() {
		return palindromeText;
	}
	public void setPalindromeText(String palindromeText) {
		this.palindromeText = palindromeText;
	}
	

}
