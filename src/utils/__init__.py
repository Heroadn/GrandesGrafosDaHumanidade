import csv

from src.classes.edge import Edge
from src.classes.graph import Graph

def csv_to_graph(csv_file_path: str) -> Graph:
    graph = Graph()

    with open(csv_file_path, newline='') as csvfile:
        csv_content = csv.reader(csvfile, delimiter=',', quotechar='"')
        for row in csv_content:
            if len(row) >= 3:
                if row[2].isdigit(): #Verifying if distance field is a valid digit to be converted to integer
                    graph.add_edge(Edge(row[0], row[1], int(row[2])))

    return graph