import axios from "axios";
import authService from "./auth.service";

const API_URL = "http://3.145.120.66:8080/";

class UserService {

    async getUsersCommunities() {
        const user = authService.getCurrentUser();
        if (user) {
            const token = authService.getUserToken();
            let paramStr = '@usertoken=' + token;
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
