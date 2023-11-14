from src.controller import main_bp

from flask import current_app, jsonify


@main_bp.route('/info/vehicles', methods=['GET'])
def get_vehicles_info():
    vehicles_info = current_app.vehicles

    return jsonify({"vehicles":vehicles_info})