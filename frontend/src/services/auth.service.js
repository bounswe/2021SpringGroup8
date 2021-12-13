import axios from "axios";
import querystring from "querystring";

const API_URL = "http://localhost:8080/";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "login", querystring.stringify({username:username, password:password}), {
                headers:{
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });
            // .then((response) => {
            //     if (response.data) {
            //         //assumes that the response will be a json object
            //         console.log(JSON.stringify(response.data['@return']));
            //         localStorage.setItem("user", JSON.stringify(response.data['@return']));
            //     }
            //     return response.data;
            // });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username, email, password, name, surname, birthdate) {
        return axios.post(API_URL + "signup",
            querystring.stringify({
                username: username,
                password: password,
                email: email,
                name: name,
                surname: surname,
                birthdate: birthdate
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

export default new AuthService();
