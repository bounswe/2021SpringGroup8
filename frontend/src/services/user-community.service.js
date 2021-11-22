
import axios from "axios";


class UserCommunityService {

    getCommunities() {
        return axios.post("http://localhost:8080/getallcommunities", {headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }



    getCommunityById(id) {
        return axios.post(`http://localhost:8080//getcommunity`,{communityId: id},
            {headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }


}

export default new UserCommunityService();
