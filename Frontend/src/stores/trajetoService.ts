import { defineStore } from 'pinia'
import instance from "@/stores/axious"
const HOST = 'localhost:8080'
const PATH = 'graph'

export class Trajeto {
    public shortestDistance: string;
    public shortestPath: string[];

    constructor(
        shortestDistance: string = "0",
        shortestPath: string[] = []) 
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
    public totalFuelConsumption: any;
    public fuelConsumptionPrice: any;
    public roadFees: any;
    public totalRoadFeesCost:any;

    constructor(
        travelTimeHours: any = 0, 
        numberOfDrivers: any = 0, 
        foodExpenses: any = 0, 
        totalFuelConsumption: any = 0,
        fuelConsumptionPrice: any = 0,
        roadFees: any = 0,
        totalRoadFeesCost: any = 0)
    {
      this.travelTimeHours = travelTimeHours;
      this.numberOfDrivers = numberOfDrivers;
      this.foodExpenses    = foodExpenses;
      this.totalFuelConsumption = totalFuelConsumption;
      this.fuelConsumptionPrice = fuelConsumptionPrice;
      this.roadFees = roadFees;
      this.totalRoadFeesCost = totalRoadFeesCost;
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
        async getExpense(trajeto: Trajeto, vehicle: string)
        {
            //expenses?distance=10&vehicle_type=Car 
            const paths = trajeto.shortestPath.reduce(
              (a:any, b:any) => (a + ',' + b));

            const distance = trajeto.shortestDistance;
            const response = await instance.get(`http://${HOST}/expenses?distance=${distance}&vehicle_type=${vehicle}&path=${paths}`);
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
  