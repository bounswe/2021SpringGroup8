import axios from "axios";
import AuthService from "./auth.service"
const API_URL = "http://3.145.120.66:8080/";

class PostService {

    getPost(id) {
        let paramStr = 'postId=' + id;
        let searchParams = new URLSearchParams(paramStr);
        return axios.post(API_URL + "viewpost", searchParams, {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        });
    }

    submitPost(communityId, title, datatypename, datatypevalues){
        const token = AuthService.getUserToken()
        const user = AuthService.getCurrentUser()
        console.log(communityId, title, datatypename, datatypevalues)
        if (user) {
            let paramStr = 'title=' + title + '&communityId=' + communityId + '&@usertoken=' + token + "&datatypename=" + datatypename + "&datatypevalues=" + JSON.stringify(datatypevalues);
            return axios.post(`http://3.145.120.66:8080/submitpost`, paramStr,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
        }
    }
}

export default new PostService();
