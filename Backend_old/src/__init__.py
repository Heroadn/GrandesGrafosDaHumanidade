import os

from flask import Flask, current_app
from flask_cors import CORS

from src.controller import main_bp
from src.utils import csv_to_graph, json_file_to_dict

main_graph = csv_to_graph(os.path.join("resources", "paths.csv"))
vehicles = json_file_to_dict(os.path.join("resources", "vehicles.json"))

app = Flask(__name__)
CORS(app)

app.main_graph = main_graph
app.vehicles = vehicles

app.register_blueprint(main_bp)
