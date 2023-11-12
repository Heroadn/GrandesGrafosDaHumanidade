from src.controller import main_bp

from flask import current_app, jsonify

@main_bp.route('/graph/shortest_path/<string:point_a>/<string:point_b>', methods=['GET'])
def get_shortest_path(point_a, point_b):
    shortest_path_obj = current_app.main_graph.dijkstra(point_a, point_b)

    if shortest_path_obj:
        return jsonify({
            "shortest_path":shortest_path_obj.paths,
            "shortest_distance":shortest_path_obj.distance
            })
    else:
        return jsonify({"msg":"Couldn't find a path from {point_a} to {point_b}"}), 404


@main_bp.route('/graph/print', methods=['GET'])
def print_graph():
    graph_described = current_app.main_graph.print_graph()
    return jsonify({"msg":graph_described})