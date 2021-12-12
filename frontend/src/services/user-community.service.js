import authService from "./auth.service";
import axios from "axios";


class UserCommunityService {

    getCommunities() {
        return axios.post("http://3.145.120.66:8080/getallcommunities", {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        });
    }

    getUserToken () {
        let token = localStorage.getItem('usertoken')
        token = token.substring(1, token.length - 1)
        return token
    }


    getCommunityById(id) {
        let paramStr = 'communityId=' + id;
        let searchParams = new URLSearchParams(paramStr);
        return axios.post(`http://3.145.120.66:8080/getcommunity`, searchParams,
            {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });
    }

    subscribeCommunity(community_id) {
        const token =  this.getUserToken()
        const user = authService.getCurrentUser()
        if (user) {
            let paramStr = 'communityId=' + community_id + '&@usertoken=' + token;
            let searchParams = new URLSearchParams(paramStr);
            return axios.post(`http://3.145.120.66:8080/subscribetocommunity`, searchParams,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
        } else {
            return {"response": false, "message": "Needs to be login to use this functionality"};
        }

    }

    unsubscribeCommunity(community_id) {
        const token = this.getUserToken()
        const user = authService.getCurrentUser()
        if (user) {
            let paramStr = 'communityId=' + community_id + '&@usertoken=' + token;
            let searchParams = new URLSearchParams(paramStr);
            return axios.post(`http://3.145.120.66:8080/unsubscribecommunity`, searchParams,
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
