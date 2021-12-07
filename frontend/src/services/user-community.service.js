
import axios from "axios";
import querystring from "querystring";


class UserCommunityService {

    getCommunities() {
        return axios.post("http://localhost:8080/getallcommunities", {headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }



    getCommunityById(id) {
        let paramStr = 'communityId='+id;
        let searchParams = new URLSearchParams(paramStr);
        return axios.post(`http://localhost:8080/getcommunity`, searchParams,
            {headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }


}

export default new UserCommunityService();
