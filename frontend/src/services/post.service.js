import axios from "axios";

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
}

export default new PostService();
