package android.topdown.game;

public class HighScore {
	private int score;
	private String name,town;
	public HighScore(int score, String name, String town){
		this.score = score;
		this.name = name;
		this.town = town;
	}
	public HighScore(String savedata){
		String[] stage = savedata.split(".");
		name = stage[0];
		town = stage[1];
		score = Integer.parseInt(stage[2]);
	}
	/**
	 * @return all the data of this formated for showing it to the user
	 */
	public String getShow(){
		return name +" "+town+" "+score;
	}
}
