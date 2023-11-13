import { defineStore } from 'pinia'

//renomear para useAuthStore
export const useAuthService = defineStore('authService', {
    state: () => ({id: '', email: "", accessToken: '', refreshToken: '' }),
    getters: {
        getAccessToken: (state)  => state.accessToken,
        getRefreshToken: (state)  => state.refreshToken,
        isLogged: (state) => state.refreshToken != ''
    },
    actions:
    {
        revoke()
        {
            this.id = ''
            this.email = ''
            this.accessToken = ''
            this.refreshToken = ''
        },
        async requestRefreshToken(nome:string, senha:string)
        {
            const response = await fetch("http://localhost:8081/usuario/login-refresh", 
            {
                method: "POST",
                headers: 
                {
                "Content-Type": "application/json",
                },
                body: JSON.stringify(
                {
                nome:  nome,
                senha: senha
                }),
            });

            return response;
        },
        async requestAccessToken(refreshToken:string)
        {
            const response = await fetch("http://localhost:8081/usuario/login-access", 
            {
                method: "POST",
                headers: 
                {
                "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    'refreshToken': refreshToken
                }),
            });

            return response;
        },
        async renewToken()
        {
            let response = this.requestAccessToken(this.getRefreshToken)
            response = this.requestAccessToken(this.refreshToken);
            this.accessToken  = (await (await response).text()).toString();

            return response;
        },
        async login(nome:string, senha:string)
        {
            let response = this.requestRefreshToken(nome, senha);
            this.refreshToken = (await (await response).text()).toString();
            //TODO: verificar por erros de autenticação
            
            if((await (await response).status) != 200)
                return response;

            response = this.requestAccessToken(this.refreshToken);
            this.accessToken  = (await (await response).text()).toString();

            return response
        }
    }
})
  