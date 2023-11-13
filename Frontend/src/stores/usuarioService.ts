import { defineStore } from 'pinia'
import instance from "@/stores/axious"

//renomear para useAuthStore
export const useUsuarioService = defineStore('usuarioService', {
    state: () => ({ nome: ""}),
    getters: {
        getNome: (state) => state.nome
    },
    actions:
    {
        printValues() {
            console.log(this.getNome)
        },
        async sendImage(fileName:string, fileSize:string, fileImage64:string)
        {   
            const response = instance.post("http://localhost:8081/midia/usuario", {
                fileName:  fileName,
                fileSize: fileSize,
                fileImage64: fileImage64
            });
            
            return response;
        },
        async meuUsuario()
        {
            const response = await instance.get("http://localhost:8081/usuario/meu-usuario");
            return response;
        },
        async post(nome:string, email:string, senha:string, descricao:string)
        {
            const response = await fetch("http://localhost:8081/usuario", 
            {
                method: "POST",
                headers: 
                {
                "Content-Type": "application/json",
                },
                body: JSON.stringify(
                {
                nome:  nome,
                email: email,
                senha: senha,
                descricao: descricao
                }),
            });

            return response;
        },
        async delete(id:string)
        {
            let url = "http://localhost:8081/usuario/" + id;
            const response = instance.delete(url, {
            });
            
            return response;
        },
        async update(id:string, nome:string, email:string, senha:string, descricao: string)
        {   
            let url = "http://localhost:8081/usuario/" + id;
            const response = instance.put(url, {
                nome:  nome,
                email: email,
                senha: senha,
                descricao: descricao
            });
            
            return response;
        },
        async get(id:string)
        {
            const response = await instance.get("http://localhost:8081/usuario/" + id);
            return response;
        },
        
        async all(page:number = 0)
        {
            const response = await instance.get("http://localhost:8081/usuario");
            return response;
        }
    }
})
  