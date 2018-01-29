import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class BaseballElimination {
	
	private int n; //number of teams
	private List<String> teams; //name  of the team
	private int[] w, l, r; //number of wins, losses and remaining games of teach team
	private int[][] g; // games left to play of team i against team j
	
	// As in the second assignment, file will probably be in the src if in 
	// command line or in the root if run on Eclipse. Used similar structure 
	// on assignment 1 of Algorithms 2 (synsets assignment)
	public BaseballElimination(String filename) {	// create a baseball division from given filename in format specified below
		int i = 0;
		In in = new In(filename);
		n = in.readInt();
		teams = new ArrayList<>();
		w = new int[n];
		l = new int[n];
		r = new int[n];
		g = new int[n][n];
		in.readLine(); //skips blank character after reading n
		while (!in.isEmpty()) {
			String line = in.readLine();
			String [] fields = line.split(" +"); //needs to separate with one OR MORE white spaces
			teams.add(fields[0]);
			w[i] = Integer.parseInt(fields[1]);
			l[i] = Integer.parseInt(fields[2]);
			r[i] = Integer.parseInt(fields[3]);
			for(int j = 0;j < n;j++) {
				g[i][j] =  Integer.parseInt(fields[j+4]);
			}				
			i++;
		}
	}
	
	public int numberOfTeams() {  // number of teams
		return n;
	}
	
	public Iterable<String> teams()   {                             // all teams
		return teams;
	}
//	public              int wins(String team)                      // number of wins for given team
//	public              int losses(String team)                    // number of losses for given team
//	public              int remaining(String team)                 // number of remaining games for given team
//	public              int against(String team1, String team2)    // number of remaining games between team1 and team2
//	public          boolean isEliminated(String team)              // is given team eliminated?
//	public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated

	public static void main(String[] args) {
		
		BaseballElimination baseballTest = new BaseballElimination("C:/Users/ffonseca/workspace/Algorithms_2_Princeton_Assignment_3/teams5.txt");

	}

}
