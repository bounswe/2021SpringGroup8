import axios from "axios";

const API_URL = "http://35.170.202.102:8080/";

class RegisterService {
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
        return axios.post(API_URL + "signup", {
            username,
            email,
            password,
        }).then((response) => {
            if (response.data) {
                //assumes that the response will be a json object
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
    }
}

export default new RegisterService();
