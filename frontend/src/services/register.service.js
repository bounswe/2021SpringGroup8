import axios from "axios";
import querystring from "querystring";

const API_URL = "http://localhost:8080/";

class RegisterService {
    login(username, password) {
        return axios
            .post(API_URL + "login", querystring.stringify({username:username, password:password}), {
                headers:{
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            })
            .then((response) => {
                if (response.data) {
                    //assumes that the response will be a json object
                    console.log(JSON.stringify(response.data['@return']));
                    if(JSON.stringify(response.data['@return'])){
                        localStorage.setItem("user", JSON.stringify(response.data['@return']));
                        localStorage.setItem("usertoken", JSON.stringify(response.data['@usertoken']));
                    }else{
                        localStorage.setItem("user", null);
                        alert("Wrong Credentials");
                    }

                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
        localStorage.removeItem("usertoken");
    }

    register(username, email, password) {
        return axios.post(API_URL + "signup",
            querystring.stringify({
                username: username,
                password: password,
                email: email
            }), {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });

    }
    getCurrentUser() {
        const userStr = localStorage.getItem("user");
        if (userStr) return JSON.parse(userStr);

        return null;
    }
}

export default new RegisterService();
