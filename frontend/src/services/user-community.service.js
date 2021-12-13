import authService from "./auth.service";
import axios from "axios";
import querystring from "querystring";


class UserCommunityService {

    getCommunities() {
        return axios.post("http://3.145.120.66:8080/getallcommunities", {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        });
    }

    getUserToken() {
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
        const token = this.getUserToken()
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

    createDataTypeForCommunity(community_id, fields, name) {
        const token = this.getUserToken()
        const user = authService.getCurrentUser()
        if (user) {
            const something = JSON.stringify(fields).toString()
            console.log(querystring.stringify({
                "@usertoken": this.getUserToken(),
                communityId: community_id,
                datatypename: name,
                datatypefields: something
            }))
            let paramStr = 'communityId=' + community_id + '&@usertoken=' + token + "&datatypename=" + name + "&datatypefields=" + something;
            let searchParams = new URLSearchParams(paramStr);
            console.log(paramStr)
            return axios.post(`http://3.145.120.66:8080/createdatatype`, paramStr,
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
