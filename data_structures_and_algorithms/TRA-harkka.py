#defining the vertex object
class Vertex:
    def __init__(self, node):
        self.numb = node
        self.adjacent_nodes = {}

    #function for adding neighbouring nodes
    def add_neighbour(self, neighbour, weight=0):
        self.adjacent_nodes[neighbour] = weight

    #function to return the weight of a node
    def return_weight(self, neighbour):
        return self.adjacent_nodes[neighbour]

    #function to return the name of the vertex
    def return_numb(self):
        return self.numb

    #function to return the edges connected to a vertex
    def return_edges(self):
        return self.adjacent_nodes.keys()
    

#defining the graph object
class Graph:
    def __init__(self):
        self.vertices = {}
        self.vertex_amount = 0

    def __iter__(self):
        return iter(self.vertices.values())

    #function to add a vertex to the graph
    def add_vertex(self, node):
        self.vertex_amount += 1
        new_vertex = Vertex(node)
        self.vertices[node] = new_vertex
        return new_vertex

    #function to add an edge to the graph
    def add_edge(self, start, end, height = 0):
        if start not in self.vertices:
            self.add_vertex(start)
        if end not in self.vertices:
            self.add_vertex(end)
        
        self.vertices[start].add_neighbour(self.vertices[end], height)
        self.vertices[end].add_neighbour(self.vertices[start], height)

    #function to return a vertex
    def return_vertex(self, n):
        if n in self.vertices:
            return self.vertices[n]
        else:
            return None



def read_file(filename):
    #reading the file, line by line into a list
    rfile = open(filename, 'r')
    buffer = rfile.readlines()

    #removing newline-characters from end of the strings read from the file and splitting them into readable data
    listlength = len(buffer)
    for n in range(listlength):
        buffer[n] = buffer[n].removesuffix('\n')
        buffer[n] = buffer[n].split()

    #converting the strings into integers
    for i in range(listlength):
        for j in range(len(buffer[i])):
            buffer[i][j] = int(buffer[i][j])

    return buffer



if __name__ == '__main__':
    graf = Graph()
    content = read_file('ass.txt')
    cityamount = content[0][0]
    roadamount = content[0][1]
    goal_city = content[-1]

    for n in range(1, cityamount):
        graf.add_vertex(n)

    for n in range(1, roadamount):
        graf.add_edge(content[n])