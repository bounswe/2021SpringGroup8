import axios from "axios";
import querystring from "querystring";

const API_URL = "http://localhost:8080/";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "login", {username, password})
            .then((response) => {
                if (response.data) {
                    //assumes that the response will be a json object
                    localStorage.setItem("user", JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
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

export default new AuthService();
