from src.controller import main_bp

from flask import current_app, jsonify


@main_bp.route('/info/vehicles', methods=['GET'])
def get_vehicles_info():
    vehicles_info = current_app.vehicles

    vehicles_list = []
    for k in vehicles_info.keys():
        vehicles_info[k]["name"] = k
        vehicles_list.append(vehicles_info[k])

    return jsonify({"vehicles":vehicles_list})