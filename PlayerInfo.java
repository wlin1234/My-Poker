import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PlayerInfo {
	private String name;
	private int numwin;
	private int numlost;
	private int winSameSuit;
	private int winfirst;
	private int winsecond;
	private int winthird;
	private int winmixed;
	private int score;
	private int losefirst;
	private int losesecond;
	private int losethird;
	public int[] allStats = new int[7];
			
	public PlayerInfo(String name, int numwin, int numlost, int winSameSuit, int winfirst, int winsecond, int winthird, int winmixed, int score
			,int losefirst, int losesecond, int losethird) {
		this.name = name;
		this.numwin = numwin;
		this.numlost = numlost;
		this.winSameSuit = winSameSuit;
		this.winfirst = winfirst;
		this.winsecond = winsecond;
		this.winthird = winthird;
		this.winmixed = winmixed;
		this.score = score;
		this.losefirst = losefirst;
		this.losesecond = losesecond;
		this.losethird = losethird;
				
	}
	public String getName() {
		return name;
	}
	public int getWin() {
		return numwin;
	}
	public int getlost() {
		return numlost;
	}
	public int getsamesuit() {
		return winSameSuit;
	}
	public int getwinfirst() {
		return winfirst;
	}
	public int getwinsecond() {
		return winsecond;
	}
	public int getwinthird() {
		return winthird;
	}
	public int getwinmixed() {
		return winmixed;
	}
	public int getscore() {
		return score;
	}
	public int getlosefirst() {
		return losefirst;
	}
	public int getlosesecond() {
		return losesecond;
	}
	public int getlosethird() {
		return losethird;
	}
	//method to increment wins, category == 1 when win with 60%+ (1-5)
	public void increwin(int category, boolean isSameSuit) {
		if(isSameSuit) {
			this.winSameSuit++;
		}
		this.numwin++;
		if(category == 1) {
			this.winfirst++;
		} else if(category == 2) {
			this.winsecond++;
		} else if (category == 3) {
			this.winthird++;
		} else if(category == 4) {
			this.winmixed++;
		}
	}
	public void increlost(int category, boolean isSameSuit) {
		
		this.numlost++;
		System.out.println(category);
		if(category == 1) {
			this.losefirst++;
		} else if(category == 2) {
			this.losesecond++;
		} else if (category == 3) {
			this.losethird++;
		} else if(category == 4) {
			
		}
	}
	public void setScore(int score) {
		this.score = score;
	}
	//update everything on database with the in game name
	public void updateEverything() {
		//update accts set numwin = 2, numlost = 3 where playername = "wei";
		Connection conn = DBConnection.getDBConnection();
		try {
		String query = "update accts set numwin ="+this.getWin()+", numlost ="+this.getlost()+", winSameSuit ="+this.getsamesuit()+", winFirst ="+this.getwinfirst()+", winSecond ="+this.getwinsecond()+", winThird ="+this.getwinthird()+", winMixed ="+this.getwinmixed()+", score ="+this.getscore()+
				", loseFirst ="+this.getlosefirst()+", loseSecond ="+this.getlosesecond()+", loseThird ="+this.getlosethird()+" where playername =\""+this.getName()+"\"";
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getLargest() {
		allStats[0] = this.getwinfirst();
		allStats[1] = this.getlosefirst();
		allStats[2] = this.getwinsecond();
		allStats[3] = this.getlosesecond();
		allStats[4] = this.getwinthird();
		allStats[5] = this.getlosethird();
		allStats[6] = this.getwinmixed();
		int ret = allStats[0];
		for(int i = 1; i < 7; i++) {
			if(ret <= allStats[i]) {
				ret = allStats[i];
			}
		}
		return ret;
		
	}
}
