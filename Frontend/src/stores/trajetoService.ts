import { defineStore } from 'pinia'
import instance from "@/stores/axious"
const HOST = 'localhost:8080'
const PATH = 'graph'

export class Trajeto {
    shortestDistance: string;
    shortestPath: string[];

    constructor(
        shortestDistance: string,
        shortestPath: string[]) 
    {
        this.shortestDistance = shortestDistance
        this.shortestPath = shortestPath
    }
}

//renomear para useAuthStore
export const useTrajetoService = defineStore('trajetoService', {
    state: () => (Trajeto),
    getters: {
        getShortestDistance: (state) => state.prototype.shortestDistance,
        getShortestPath: (state) => state.prototype.shortestPath
    },
    actions:
    {
        async allNodes(page:number = 0)
        {
            const response = await instance.get(`http://${HOST}/${PATH}/content`);
            return response;
        },
        async shortestPath(source: string, destination: string)
        {
            const response = await instance.get(`http://${HOST}/${PATH}/shortest_path/${source}/${destination}`);
            return response;
        },
        printValues() {
            console.log("ID: " + this.getShortestDistance)
        },
    }
});
  