import authService from "./auth.service";
import axios from "axios";
import querystring from "querystring";

const API_URL =  "http://3.145.120.66:8080/";
class ProfileService {



    getUserToken() {
        let token = localStorage.getItem('usertoken')
        token = token.substring(1, token.length - 1)
        return token
    }



    updateEmail(email) {
        console.log("email is ")
        console.log(email)
        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'email='+email+'&@usertoken='+token ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)
            console.log(querystring.stringify({
                "@usertoken": this.getUserToken(),
                email:email,


            }))
            return axios.post(`http://3.145.120.66:8080/updateprofile`,
                searchParams,

                // querystring.
                // stringify({"@usertoken":token, communityTitle:communityTitle, description:description}),

                // paramStr,

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

export default new ProfileService();
