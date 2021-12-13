import authService from "./auth.service";
import axios from "axios";
import querystring from "querystring";


const API_URL = "http://localhost:8080/";

class UserCommunityService {

    getCommunities() {
        return axios.post("http://localhost:8080/getallcommunities", {
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
            let paramStr = 'communityId=' + community_id + '&@usertoken='+ user['@usertoken'];
            let searchParams = new URLSearchParams(paramStr);
            return axios.post(`http://3.144.184.237:8080/subscribetocommunity`, searchParams,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
        } else {
            return {"response": false, "message": "Needs to be login to use this functionality"};
        }

    }

    createCommunity(communityTitle, description) {
        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'communityTitle='+communityTitle+'&description='+description + '@usertoken='+ token ;
            let searchParams = new URLSearchParams(paramStr);
            return axios.post(API_URL+`/createcommunity`, querystring.stringify({"@usertoken":token, communityTitle:communityTitle, description:description}),
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
