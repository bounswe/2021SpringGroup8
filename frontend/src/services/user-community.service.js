import authService from "./auth.service";
import axios from "axios";


class UserCommunityService {

    getCommunities() {
        return axios.post("http://localhost:8080/getallcommunities", {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        });
    }


    getCommunityById(id) {
        let paramStr = 'communityId=' + id;
        let searchParams = new URLSearchParams(paramStr);
        return axios.post(`http://localhost:8080/getcommunity`, searchParams,
            {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });
    }

    subscribeCommunity(community_id) {
        const user = authService.getCurrentUser()
        if (user) {
            let paramStr = 'communityId=' + community_id + '&@usertoken='+ user['id'];
            let searchParams = new URLSearchParams(paramStr);
            return axios.post(`http://localhost:8080/subscribetocommunity`, searchParams,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
        } else {
            return {"response": false, "message": "Needs to be login to use this functionality"};
        }

    }


}

export default new UserCommunityService();
