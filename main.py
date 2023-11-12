from src import app

if __name__ == "__main__":
    '''
    graph = csv_to_graph("paths.csv")

    graph.print_graph()

    graph.dijkstra("cidade_a", "cidade_d")
    graph.dijkstra("cidade_a", "cidade_f")
    value = graph.dijkstra("cidade_f", "cidade_a")

    if value:
        print(value.paths)
        print(value.distance)
    '''
    app.run()