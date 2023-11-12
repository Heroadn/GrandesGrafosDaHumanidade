import os
import csv

from flask import Flask, current_app

from src.controller import main_bp
from src.edge import Edge
from src.graph import Graph

def csv_to_graph(csv_file_path: str) -> Graph:
    graph = Graph()

    with open(csv_file_path, newline='') as csvfile:
        csv_content = csv.reader(csvfile, delimiter=',', quotechar='"')
        for row in csv_content:
            if len(row) >= 3:
                if row[2].isdigit(): #Verifying if distance field is a valid digit to be converted to integer
                    graph.add_edge(Edge(row[0], row[1], int(row[2])))

    return graph

main_graph = csv_to_graph(os.path.join("resources", "paths.csv"))

app = Flask(__name__)

app.main_graph = main_graph

app.register_blueprint(main_bp)


