import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

public class BaseballElimination {

	private int n; // number of teams
	private List<String> teams; // name of the team
	private int[] w, l, r; // number of wins, losses and remaining games of teach team
	private int[][] g; // games left to play of team i against team j

	// As in the second assignment, file will probably be in the src if in
	// command line or in the root if run on Eclipse. Used similar structure
	// on assignment 1 of Algorithms 2 (synsets assignment)
	public BaseballElimination(String filename) { // create a baseball division from given filename in format specified
													// below
		int i = 0;
		In in = new In(filename);
		n = in.readInt();
		teams = new ArrayList<>();
		w = new int[n];
		l = new int[n];
		r = new int[n];
		g = new int[n][n];
		in.readLine(); // skips blank character after reading n
		while (!in.isEmpty()) {
			String line = in.readLine();
			String[] fields = line.split(" +"); // needs to separate with one OR MORE white spaces
			teams.add(fields[0]);
			w[i] = Integer.parseInt(fields[1]);
			l[i] = Integer.parseInt(fields[2]);
			r[i] = Integer.parseInt(fields[3]);
			for (int j = 0; j < n; j++) {
				g[i][j] = Integer.parseInt(fields[j + 4]);
			}
			i++;
		}
	}

	public int numberOfTeams() { // number of teams
		return n;
	}

	public Iterable<String> teams() { // all teams
		return teams;
	}

	public int wins(String team) { // number of wins for given team
		int i = 0;
		for (String teamName : teams) {
			if (teamName.equalsIgnoreCase(team)) {
				break;
			}
			i++;
		}
		return w[i];
	}

	public int losses(String team) { // number of losses for given team
		int i = 0;
		for (String teamName : teams) {
			if (teamName.equalsIgnoreCase(team)) {
				break;
			}
			i++;
		}
		return l[i];
	}

	public int remaining(String team) { // number of remaining games for given team
		int i = 0;
		for (String teamName : teams) {
			if (teamName.equalsIgnoreCase(team)) {
				break;
			}
			i++;
		}
		return r[i];
	}

	public int against(String team1, String team2) { // number of remaining games between team1 and team2
		int i = 0, j = 0, k = 0;
		boolean foundTeam1 = false;
		boolean foundTeam2 = false;
		for (String teamName : teams) {
			if (teamName.equalsIgnoreCase(team1)) {
				i = k;
				foundTeam1 = true;
			} else if (teamName.equalsIgnoreCase(team2)) {
				j = k;
				foundTeam2 = true;
			}
			if (foundTeam1 && foundTeam2) {
				break;
			}
			k++;
		}
		return g[i][j];
	}

	// if not trivially eliminated, builds a flownetwork to check whether there will be a non-trivial elimination
	public boolean isEliminated(String team) { // is given team eliminated?
		boolean isEliminated = isTrivialElimination(team);
		if (isEliminated) {
			return true;
		}
		// number of vertices is the source vertex + possible combinations of remaining teams +
		// + remaining teams + sink vertex
		int numberOfVertices = 1 + (n - 1) * (n - 2) / 2 + (n - 1) + 1;
		int teamIndex = teamIndex(team);
		FlowNetwork flowNetwork = new FlowNetwork(numberOfVertices);
		// to preserve a one to one mapping of the team vertices with the actual
		// team index, the source vertex will be the team in question to be checked
		addEdgesFromSourceVertex(flowNetwork, teamIndex);
		addEdgesFromGameVertices(flowNetwork, teamIndex);
		addEdgesToSinkVertex(flowNetwork, teamIndex, numberOfVertices-1); //sink vertext is the last index by the program convention
        
		FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, teamIndex, numberOfVertices-1);
		return false;
	}
	// public Iterable<String> certificateOfElimination(String team) // subset R of teams that eliminates given team;
	// null if not eliminated

	private int teamIndex(String team) {
		int i = 0;
		for (String teamName : teams) {
			if (teamName.equalsIgnoreCase(team)) {
				break;
			}
			i++;
		}
		return i;
	}

	private boolean isTrivialElimination(String team) {
		boolean isTrivialElimination = false;
		int i = teamIndex(team);
		int thresholdWins = w[i] + r[i]; // maximum of wins the team can have in the league
		for (int j = 0; j < n; j++) {
			if (w[j] > thresholdWins) {
				isTrivialElimination = true;
				break;
			}
		}
		return isTrivialElimination;
	}
	
	private void addEdgesFromSourceVertex(FlowNetwork flowNetwork, int teamIndex) {
		int targetVertex = n; //starts from n is is updated within the method
		for (int i = 0; i < n; i++) {
			if (i == teamIndex) continue;
			for (int j=(i+1); j < n; j++) {
				if (j == teamIndex) continue;
				FlowEdge flowEdge = new FlowEdge(teamIndex, targetVertex, g[i][j] , 0);
				flowNetwork.addEdge(flowEdge);
				targetVertex++;
			}
		}
	}
	
	private void addEdgesFromGameVertices(FlowNetwork flowNetwork, int teamIndex) {
		int sourceVertex = n; //starts from n and is updated within the method
		for (int i = 0; i < n; i++) {
			if (i == teamIndex) continue;
			for (int j=(i+1); j < n; j++) {
				if (j == teamIndex) continue;
				FlowEdge flowEdge1 = new FlowEdge(sourceVertex, i, Double.POSITIVE_INFINITY , 0);
				FlowEdge flowEdge2 = new FlowEdge(sourceVertex, j, Double.POSITIVE_INFINITY , 0);
				flowNetwork.addEdge(flowEdge1);
				flowNetwork.addEdge(flowEdge2);
				sourceVertex++;
			}
		}
	}

	private void addEdgesToSinkVertex(FlowNetwork flowNetwork, int teamIndex, int sinkVertex) {
		for (int i = 0; i < n; i++) {
			if (i == teamIndex) {
				continue; // the very team to be analyzed should not be here
			}
			FlowEdge flowEdge = new FlowEdge(teamIndex, sinkVertex, w[teamIndex]+r[teamIndex]-w[i] , 0);
			flowNetwork.addEdge(flowEdge);
		}
	}

	public static void main(String[] args) {
		BaseballElimination baseballTest = new BaseballElimination(
				"C:/Users/ffonseca/workspace/Algorithms_2_Princeton_Assignment_3/teams5.txt");
	}

}
