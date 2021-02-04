# defining the graph object
class Graph:
    def __init__(self, vertex_amount):
        self.V = vertex_amount # amount of vertices in the graph object
        self.graph = [] # a list to store the graph's edges and their weights

    # function to add weighted edges
    def add_edge(self, s, e, w):
        self.graph.append([s, e, w])

    # helper function for kruskal's algorithm function, finds the parent of a vertex
    def find(self, parent, i):
        if parent[i] == i:
            return i
        return self.find(parent, parent[i])

    # helper function for kruskal's algorithm function, for union
    def union(self, parent, rank, v1, v2):
        root1 = self.find(parent, v1)
        root2 = self.find(parent, v2)

        if rank[root1] < rank[root2]:
            parent[root1] = root2
        elif rank[root1] > rank[root2]:
            parent[root2] = root1
        else:
            parent[root2] = root1
            rank[root1] += 1

    # function to apply kruskal's algorithm to the graph to find the minimum search tree
    def kruskal(self):
        mst = []
        i, e = 0, 0
        self.graph = sorted(self.graph, key=lambda item: item[2])
        parent = []
        rank = []

        for n in range(self.V):
            parent.append(n)
            rank.append(0)

        while e < self.V - 1:
            u, v, w = self.graph[i]
            i += 1
            x = self.find(parent, u)
            y = self.find(parent, v)
            if x != y:
                e += 1
                mst.append([u, v, w])
                self.union(parent, rank, x, y)
        
        mst = sorted(mst, key=lambda item: item[0])
        return mst
            

# function for reading graph files
def read_file(filename):
    # reading the file, line by line into a list
    rfile = open(filename, 'r')
    buffer = rfile.readlines()

    # removing newline-characters from end of the strings read from the file and splitting them into readable data
    listlength = len(buffer)
    for n in range(listlength):
        buffer[n] = buffer[n].removesuffix("\n")
        buffer[n] = buffer[n].split()

    # converting the strings into integers
    for i in range(listlength):
        for j in range(len(buffer[i])):
            buffer[i][j] = int(buffer[i][j])

    return buffer


# function for applying breadth search to a minimum search tree
def breadth_first_search(graph, startpoint, goal):
    visited = {} # dictionary to store the visited nodes(in the keys) and the nodes that came previous to them(in the values)
    queue = [(startpoint, None)] # a queue that contains the starting point of the graph and the value of the previous node

    while queue:   
        node, prev = queue.pop()
        if node not in visited: # only taking into account nodes we haven't visited yet
            visited[node] = prev

            # if the goal node is found, backtrack the shortest path with the help of the visited dictionary  
            if node == goal: 
                result = [] 
                while node != None:
                    result.append(node)
                    node = visited[node]
                return result[::-1]
            
            # else we add neighbouring nodes to the queue and keep searching
            else:
                neighbours = graph[node]
                for neighbour in neighbours:
                    queue.append((neighbour, node))

    return result
        

# main program
if __name__ == "__main__":

    # reading the filename from user input and setting up the needed variables
    filename = input("filename: ")
    content = read_file(filename)
    cityamount = content[0][0]
    roadamount = content[0][1]
    goal_city = content[-1][0] - 1
    graf = Graph(cityamount)

    # adding the edges given in the file to the graph
    for n in range(1,  roadamount+1):
        graf.add_edge(content[n][0]-1, content[n][1]-1, content[n][2])

    mst_list = graf.kruskal() # getting the minimum search tree of the graph by applying kruskal's algorithm
    
    # making the minimum search tree into dictionary form for the breadth search function
    min_search_tree = {} 
    for n in range(len(mst_list)):
        if mst_list[n][0] not in min_search_tree:
            min_search_tree[mst_list[n][0]] = []

        if mst_list[n][1] not in min_search_tree:
            min_search_tree[mst_list[n][1]] = []

    for n in range(len(mst_list)):
        min_search_tree[mst_list[n][0]].append(mst_list[n][1])
        min_search_tree[mst_list[n][1]].append(mst_list[n][0])

    result = breadth_first_search(min_search_tree, 0, goal_city) # applying breadth search to the minimum search tree
                                                                 # to get the path with the lowest max weight

    # finding the maximum height of the found path
    maxheight = 0
    for n in range(1, len(result)):
        for m in range(len(mst_list)):
            if (result[n] == mst_list[m][0] and result[n-1] == mst_list[m][1]):
                if mst_list[m][2] > maxheight:
                    maxheight = mst_list[m][2]
                    break
            elif  (result[n] == mst_list[m][1] and result[n-1] == mst_list[m][0]):
                if mst_list[m][2] > maxheight:
                    maxheight = mst_list[m][2]
                    break

    # getting all the info into a printable form and then printing it
    resultprint = ""

    if len(result) == 0:
        print("No route found")
    else :
        for n in range(len(result)):
            result[n] += 1
            resultprint += "{} -> ".format(result[n])

        resultprint = resultprint.removesuffix(" -> ")

        print("\nFile: {}".format(filename))
        print("Best route: {}".format(resultprint))
        print("Maximum height of route: {}\n".format(maxheight))
