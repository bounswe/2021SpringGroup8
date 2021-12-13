import React from 'react';
import {Formik} from 'formik';
import * as Yup from 'yup';
import AuthService from "../../services/auth.service";
import Navbar from "../Components/navbar";

export default class Register extends React.Component {
    render() {
        const {history} = this.props;

        function redirectLogin() {
            history.push('/login');
        }

        return (
            <>
            <Navbar/>
            <Formik
                initialValues={{
                    username: '',
                    email: '',
                    password: '',
                    name: '',
                    surname: ''
                }}
                validationSchema={Yup.object().shape({
                    username: Yup.string()
                        .required('Username is required'),
                    name: Yup.string()
                        .required('Name is required'),
                    surname: Yup.string()
                        .required('Surname is required'),
                    email: Yup.string()
                        .email('Email is invalid')
                        .required('Email is required'),
                    password: Yup.string()
                        .min(6, 'Password must be at least 6 characters')
                        .required('Password is required')
                })}
                onSubmit={async (values, actions) => {

                    const username = values.username;
                    const email = values.email;
                    const password = values.password;
                    const name = values.name;
                    const surname = values.surname;
                    const birthdate = values.birthdate;
                    console.log(birthdate)

                    AuthService
                        .register(
                            username,
                            email,
                            password,
                            name,
                            surname,
                        )
                        .then(
                            response => {
                                if (response.data['@success'] !== 'False') {
                                    console.log(response.data['@return']);
                                    localStorage.setItem("user", JSON.stringify(response.data['@return']));
                                    history.push('/profile');
                                } else {
                                    console.log(response.data['@error']);
                                    alert(response.data['@error']);
                                }
                            })
                        .catch(
                            error => {
                                const resMessage =
                                    (error.response &&
                                        error.response.data &&
                                        error.response.data.message) ||
                                    error.message ||
                                    error.toString();
                                console.log(resMessage);
                            }
                        );
                }}

                render={({values, touched, errors, isSubmitting, handleChange, handleBlur, handleSubmit}) => (
                    <div className="col-md-12">

                        <div className="card card-container">
                            <form onSubmit={handleSubmit}>
                                <label htmlFor="name">Name</label>
                                <input
                                    name="name"
                                    type="text"
                                    placeholder="Enter your name"
                                    value={values.name}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {errors.name && touched.name && (
                                    <div className="input-feedback" style={{marginTop: 10}}>{errors.name}</div>
                                )}
                                <label htmlFor="surname">Surname</label>
                                <input
                                    name="surname"
                                    type="text"
                                    placeholder="Enter your surname"
                                    value={values.surname}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {errors.surname && touched.surname && (
                                    <div className="input-feedback">{errors.surname}</div>
                                )}
                                {/*<label htmlFor="birthdate">Birth Date</label>
                                <input
                                    name="birthdate"
                                    type="date"
                                    placeholder="Enter your birthdate"
                                    value={values.birthdate}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {errors.surname && touched.surname && (
                                    <div className="input-feedback">{errors.surname}</div>
                                )}*/}
                                <label htmlFor="username">Username</label>
                                <input
                                    name="username"
                                    type="text"
                                    placeholder="Enter your username"
                                    value={values.username}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {errors.username && touched.username && (
                                    <div className="input-feedback">{errors.username}</div>
                                )}
                                <label htmlFor="email">Email</label>
                                <input
                                    name="email"
                                    type="text"
                                    placeholder="Enter your email"
                                    value={values.email}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    className={errors.email && touched.email && "error"}
                                />
                                {errors.email && touched.email && (
                                    <div className="input-feedback">{errors.email}</div>
                                )}
                                <label htmlFor="email">Password</label>
                                <input
                                    name="password"
                                    type="password"
                                    placeholder="Enter your password"
                                    value={values.password}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    className={errors.password && touched.password && "error"}
                                />
                                {errors.password && touched.password && (
                                    <div className="input-feedback">{errors.password}</div>
                                )}
                                <button type="submit" disabled={isSubmitting}>Sign Up</button>
                            </form>
                            <button style={{backgroundColor: "white", color: "blue", marginTop: "5px"}}
                                    onClick={redirectLogin}> Login
                            </button>
                        </div>
                    </div>

                )}
            />
            </>
        )
    }
}
