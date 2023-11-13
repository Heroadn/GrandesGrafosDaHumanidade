import { defineStore } from 'pinia'
import instance from "@/stores/axious"

//renomear para useAuthStore
export const usePokemonService = defineStore('pokemonService', {
    state: () => ({
        id: '', nome:'', descricao:'', sexo: '', tipos: [''], geracao: '', 
        ataqueMinimo:'', ataqueMaximo: '', defesaMinimo:'', defesaMaximo: ''}),
    getters: {
        getNome: (state) => state.nome,
        getId: (state) => state.id
    },
    actions:
    {
        async post(nome: string, descricao:string, sexo: string, tipos: string[], geracao: string, 
                    ataqueMinimo:string, ataqueMaximo: string, defesaMinimo:string, defesaMaximo: string)
        {   
            let tiposEncoded = '';
            tipos.forEach((tipo) =>{
                tiposEncoded += (tipo + "|")
            });

            const response = instance.post("http://localhost:8081/pokemon", {
                nome: nome, 
                descricao: descricao, 
                pokemonAtributo:{
                    sexo: sexo, 
                    tipo: tiposEncoded, 
                    geracao: geracao, 
                    ataqueMinimo: ataqueMinimo, 
                    ataqueMaximo: ataqueMaximo, 
                    defesaMinimo: defesaMinimo, 
                    defesaMaximo: defesaMaximo
                }
            });
            
            return response;
        },
        async delete(id:string)
        {
            let url = "http://localhost:8081/pokemon/" + id;
            const response = instance.delete(url, {
            });
            
            return response;
        },
        async update(id: String, nome: string, descricao:string, sexo: string, tipos: string[], geracao: string, 
                        ataqueMinimo:string, ataqueMaximo: string, defesaMinimo:string, defesaMaximo: string)
        {   
            let tiposEncoded = '';
            tipos.forEach((tipo) =>{
                tiposEncoded += (tipo + "|")
            });

            let url = "http://localhost:8081/pokemon/" + id;
            const response = instance.put(url, {
                nome: nome, 
                descricao: descricao, 
                pokemonAtributo:{
                    sexo: sexo, 
                    tipo: tiposEncoded, 
                    geracao: geracao, 
                    ataqueMinimo: ataqueMinimo, 
                    ataqueMaximo: ataqueMaximo, 
                    defesaMinimo: defesaMinimo, 
                    defesaMaximo: defesaMaximo
                }
            });

            return response;
        },
        async get(id:string)
        {
            const response = await instance.get("http://localhost:8081/pokemon/" + id);
            return response;
        },
        
        async all(page:number = 0)
        {
            const response = await instance.get("http://localhost:8081/pokemon");
            return response;
        }
    }
})
  