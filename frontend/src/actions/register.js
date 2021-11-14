import {
    REGISTER_SUCCESS,
    REGISTER_FAIL,
    LOGIN_SUCCESS,
    LOGIN_FAIL,
    LOGOUT,

} from "./types";

import RegisterService from "../services/register.service";

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

export const login = (username, password) => (dispatch) => {
    return RegisterService.login(username, password).then(
        (data) => {
            dispatch({
                type: LOGIN_SUCCESS,
                payload: { user: data },
            });

            return Promise.resolve();
        },
        (error) => {


            dispatch({
                type: LOGIN_FAIL,
            });


            return Promise.reject();
        }
    );
};

export const logout = () => (dispatch) => {
    RegisterService.logout();

    dispatch({
        type: LOGOUT,
    });
};






