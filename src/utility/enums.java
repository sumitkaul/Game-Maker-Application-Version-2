package utility;

//TODO this might be completely unused.

public class enums {

	public enum Layouts {
	    FLOW, BOX
    }
	public enum Sounds {
		PaddleHit, BrickBlow, Win
	}
	
	public enum GameState{
		INITIAL,RUNNING,PAUSED,STOPPED,REPLAY,UNDOING
	}
	
	public enum Direction {
		LEFT, RIGHT, TOP, BOTTOM
	}
	
	public enum ImageSource{
		PRIMARY,SECONDARY
	}
	
	public enum QueryType{
		LOGIN,REGISTER, LOADMAKER,LOADPLAYER,SAVEMAKER,SAVEPLAYER,HIGHSCORE,SENDSCORE,GETMAKERGAMES,GETPLAYERGAMES,GETUSERNAME,SETWEBIMAGES,GETWEBIMAGES
	}
	public enum LookAndFeelType{
		METAL,SYSTEM,GTK,MOTIF, TATTOO, QUAQUA, INFO, OYOAHA, NIMRODLF,TINY,SMOOTHMETAL
	}
}

