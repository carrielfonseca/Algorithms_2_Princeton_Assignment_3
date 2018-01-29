import edu.princeton.cs.algs4.In;

public class BaseballElimination {
	
	private int n; //number of teams
	
	// As in the second assignment, file will probably be in the src if in 
	// command line or in the root if run on Eclipse. Used similar structure 
	// on assignment 1 of Algorithms 2 (synsets assignment)
	public BaseballElimination(String filename) {	// create a baseball division from given filename in format specified below
		In in = new In(filename);
		n = in.readInt();
		in.readLine(); //skips blank character after reading n
		while (!in.isEmpty()) {
			String line = in.readLine();
			String [] fields = line.split(" ");
			//TODO put here each field in the correspondent structure
			
			
		}
	}
	
	public int numberOfTeams() {  // number of teams
		return n;
	}
	
//	public Iterable<String> teams()                                // all teams
//	public              int wins(String team)                      // number of wins for given team
//	public              int losses(String team)                    // number of losses for given team
//	public              int remaining(String team)                 // number of remaining games for given team
//	public              int against(String team1, String team2)    // number of remaining games between team1 and team2
//	public          boolean isEliminated(String team)              // is given team eliminated?
//	public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BaseballElimination baseballTest = new BaseballElimination("C:/Users/ffonseca/workspace/Algorithms_2_Princeton_Assignment_3/teams5.txt");

	}

}
