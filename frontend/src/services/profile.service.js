import authService from "./auth.service";
import axios from "axios";
import querystring from "querystring";

class ProfileService {



    getUserToken() {
        let token = localStorage.getItem('usertoken')
        token = token.substring(1, token.length - 1)
        return token
    }

    getMyProfile(){

        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  '&@usertoken='+token ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)
            console.log(querystring.stringify({
                "@usertoken": this.getUserToken(),
            }))
            return axios.post(`http://3.145.120.66:8080/getmyprofile`,
                searchParams,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
        } else {
            return {"response": false, "message": "Needs to be login to use this functionality"};
        }

    }
    getProfilePreview(id){

        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'userid='+id ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)

            return axios.post(`http://3.145.120.66:8080/getuserpreview`,
                querystring.stringify({
                    userid: id,
                }),
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
        } else {
            return {"response": false, "message": "Needs to be login to use this functionality"};
        }

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
    updateName(name) {
        console.log("name is ")
        console.log(name)
        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'name='+name+'&@usertoken='+token ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)
            // console.log(querystring.stringify({
            //     "@usertoken": this.getUserToken(),
            //     email:email,
            //
            //
            // }))
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
    updateSurname(surname) {
        // console.log("name is ")
        // console.log(surname)
        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'surname='+surname+'&@usertoken='+token ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)
            // console.log(querystring.stringify({
            //     "@usertoken": this.getUserToken(),
            //     email:email,
            //
            //
            // }))
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

    updateCity(city) {
        // console.log("name is ")
        // console.log(surname)
        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'city='+city+'&@usertoken='+token ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)
            // console.log(querystring.stringify({
            //     "@usertoken": this.getUserToken(),
            //     email:email,
            // }))
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
    updateBirthdate(birthdate) {
        // console.log("name is ")
        // console.log(surname)
        const user = authService.getCurrentUser()
        const token = this.getUserToken()
        if (user) {
            let paramStr =  'birthdate='+birthdate+'&@usertoken='+token ;
            let searchParams = new URLSearchParams(paramStr);
            console.log("param str is")
            console.log(paramStr)
            // console.log(querystring.stringify({
            //     "@usertoken": this.getUserToken(),
            //     email:email,
            // }))
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
