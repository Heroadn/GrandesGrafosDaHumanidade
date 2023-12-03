import { defineStore } from 'pinia'
import instance from "@/stores/axious"
const HOST = 'localhost:8080'
const PATH = 'graph'

export class Trajeto {
    public shortestDistance: string;
    public shortestPath: string[];

    constructor(
        shortestDistance: string,
        shortestPath: string[]) 
    {
        this.shortestDistance = shortestDistance
        this.shortestPath = shortestPath
    }
}

export class TravelCost
{
    public travelTimeHours: any; 
    public numberOfDrivers: any; 
    public foodExpenses: any;
    public fuelConsumption: any;

    constructor(
        travelTimeHours: any, 
        numberOfDrivers: any, 
        foodExpenses: any, 
        fuelConsumption: any)
    {
      this.travelTimeHours = travelTimeHours;
      this.numberOfDrivers = numberOfDrivers;
      this.foodExpenses    = foodExpenses;
      this.fuelConsumption = fuelConsumption;
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
        async getExpense(distance: string, vehicle: string)
        {
            //expenses?distance=10&vehicle_type=Car
            const response = await instance.get(`http://${HOST}/expenses?distance=${distance}&vehicle_type=${vehicle}`);
            return response;
        },
        async getCoordinates()
        {
            const response = await instance.get(`http://${HOST}/${PATH}/coordinates`);
            return response;
        },
        printValues() {
            console.log("ID: " + this.getShortestDistance)
        },
    }
});
  