import { defineStore } from 'pinia'
import instance from "@/stores/axious"
const HOST = 'localhost:5000'
const PATH = 'graph/content'

export class Trajeto {
    id: string;
    shortestDistance: string;
    shortestPath: string[];

    constructor(
        id: string, 
        shortestDistance: string,
        shortestPath: string[]) 
    {
        this.id = id
        this.shortestDistance = shortestDistance
        this.shortestPath = shortestPath
    }
}

//renomear para useAuthStore
export const useTrajetoService = defineStore('trajetoService', {
    state: () => (Trajeto),
    getters: {
        getId: (state) => state.prototype.id,
        getShortestDistance: (state) => state.prototype.shortestDistance,
        getShortestPath: (state) => state.prototype.shortestPath
    },
    actions:
    {
        async allNodes(page:number = 0)
        {
            const response = await instance.get(`http://${HOST}/${PATH}`);
            return response;
        },
        async shortestPath(source: string, destination: string)
        {
            const response = await instance.get(`http://${HOST}/${PATH}/${source}/${destination}`);
            return response;
        },
        printValues() {
            console.log("ID: " + this.getId)
        },
    }
});
  