import axios from "axios";
import authService from "./auth.service";

const API_URL = "http://localhost:8080/";

class UserService {

    async getUsersCommunities() {
        const user = authService.getCurrentUser();
        if (user) {
            let paramStr = '@usertoken=' + user['id'];
            let searchParams = new URLSearchParams(paramStr);
            return axios.post(API_URL + "getmyprofile", searchParams, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });

        }


    }
}

export default new UserService();
