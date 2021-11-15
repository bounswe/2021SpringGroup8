import React from "react";
import {Formik} from "formik";
import * as Yup from "yup";
import "../styles.css";
import {register} from "../../actions/register";
import axios from "axios";


const ValidatedSignUpForm = () => (
    <Formik initialValues={{email: "", password: "", username: ""}}
            onSubmit={(values, actions) => {
                setTimeout(() => {
                    alert(JSON.stringify(values, null, 2));
                    const username = values.username;
                    const email = values.email;
                    const password = values.password;
                    /*TODO Send Request */
                    actions.setSubmitting(false);
                }, 1000);
            }}
            validationSchema={Yup.object().shape({
                email: Yup.string()
                    .email()
                    .required("Required"),
                password: Yup.string()
                    .required("No password provided.")
                    .min(8, "Password is too short - should be 8 chars minimum.")
                    .matches(/(?=.*[0-9])/, "Password must contain a number.")
            })}
    >
        {
            props => {
                const {values, touched, errors, isSubmitting, handleChange, handleBlur, handleSubmit} = props;
                return (
                    <div className="col-md-12">

                        <div className="card card-container">
                            <form onSubmit={handleSubmit}>
                                <label htmlFor="email">Username</label>
                                <input
                                    name="username"
                                    type="text"
                                    placeholder="Enter your username"
                                    value={values.username}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
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

                        </div>
                    </div>

                );
            }

        }

    </Formik>


);

export default ValidatedSignUpForm


