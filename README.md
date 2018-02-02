# Algorithms_2_Princeton_Assignment_3
Baseball Elimination

To check for non-trivial elimination of a team, the program builds a FlowNetwork
and computes the min-cut using the Ford-Fulkerson algorithm to check if the vertex 
is on the source side. If one of the teamVertices is indeed on the source side, then
the team is eliminated
