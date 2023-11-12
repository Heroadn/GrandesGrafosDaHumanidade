import os

from flask import Flask, current_app

from src.controller import main_bp
from src.utils import csv_to_graph

main_graph = csv_to_graph(os.path.join("resources", "paths.csv"))

app = Flask(__name__)

app.main_graph = main_graph

app.register_blueprint(main_bp)
