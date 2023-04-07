M = ones_matrix(QQ, 5, 5)

G = DiGraph([('A','B'), ('B','A'), ('A','C'), ('C','A'), ('B','E'), ('C','E'), ('E','C'), ('E','D'), ('D','A')])
adjacency_matrix = G.adjacency_matrix()
P = matrix(QQ, 5, 5, lambda i,j: 0 if sum(adjacency_matrix[i]) == 0 
           else (0.85 * adjacency_matrix[i,j] / sum(adjacency_matrix[i])) + (0.15/5))
