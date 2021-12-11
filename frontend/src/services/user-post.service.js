
import axios from "axios";


class UserPostService {
    getPosts() {
        return axios.get("http://localhost:8080/api/postController/getAllPosts", {headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }



    getPostById(id) {
        return axios.get(`http://localhost:8080/api/postController/getAllPosts/${id}`,{headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }

    getPostByTitle(topic){

        return axios.get(`http://localhost:8080/api/postController/getAllPosts?topic=${topic}`,{headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }});
    }


}

export default new UserPostService();
