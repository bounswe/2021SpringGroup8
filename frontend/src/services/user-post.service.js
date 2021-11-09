
import axios from "axios";


class UserPostService {
    getPosts() {
        return axios.get("http://localhost:8080/api/postController/getAllPosts");
    }



    getPostById(id) {
        return axios.get(`http://localhost:8080/api/postController/getAllPosts/${id}`);
    }

    getPostByTitle(topic){

        return axios.get(`http://localhost:8080/api/postController/getAllPosts?topic=${topic}`);
    }


}

export default new UserPostService();