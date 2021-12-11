import {
    REGISTER_SUCCESS,
    REGISTER_FAIL,
    LOGIN_SUCCESS,
    LOGIN_FAIL,
    LOGOUT, SET_MESSAGE,

} from "./types";

import RegisterService from "../services/register.service";
import * as querystring from "querystring";

export const register = (username, email, password) => (dispatch) => {
    return RegisterService.register(username, email, password).then(
        (response) => {
            dispatch({
                type: REGISTER_SUCCESS,
            });



            return Promise.resolve();
        },
        (error) => {


            dispatch({
                type: REGISTER_FAIL,
            });


            return Promise.reject();
        }
    );
};

//promise structure is used
export const login = (username, password) => (dispatch) => {
    return RegisterService.login(username, password).then(
        response => {
            if(response.data['@success'] !== 'False'){
                console.log(response.data['@return']);

                localStorage.setItem("user", JSON.stringify(response.data['@return']));
                console.log(JSON.stringify(response.data['@return']));
                dispatch({
                    type: LOGIN_SUCCESS,
                    payload: { user: response.data },
                });
                return Promise.resolve();
            }else{
                console.log(response.data['@error']);
                alert(response.data['@error']);
                dispatch({
                    type: LOGIN_FAIL,
                });
                return Promise.reject();
            }
        }).catch(
        error => {
            const resMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
            console.log(resMessage);
        }
    )
    ;
};

export const login2 = (username, password) => (dispatch) => {
    return RegisterService.login(username, password).then(
        // success
        (data) => {
            console.log("data is ")
            console.log(data)
            dispatch({
                type: LOGIN_SUCCESS,
                payload: {user: data},
            });

            return Promise.resolve();
        },
        //failure
        (error) => {
            const message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();

            dispatch({
                type: LOGIN_FAIL,
            });

            dispatch({
                type: SET_MESSAGE,
                payload: message,
            });

            return Promise.reject();
        }
    );
}
export const logout = () => (dispatch) => {
    RegisterService.logout();

    dispatch({
        type: LOGOUT,
    });
};






