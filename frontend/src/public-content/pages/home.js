import React,{Component} from "react";


import Footer from "../Components/footer/footer";
import HomeStyles from "./homepage.module.css";
import Navbar from "../Components/navbar";
import Header from "../Components/header/header";

import {FcAdvance, FcIdea} from "react-icons/fc";
import {Gi3DStairs} from "react-icons/gi";
import logo from "../images/logo_t.png";
import logo2 from "../images/logo.png";




class Home extends Component{
    render(){
        return (
            <>
                <Header/>
                <Navbar/>
                <View/>
                <Footer/>
            </>
        );
    }
};

const View = () => {
    const solutions = [{
        keyID: 1,
        icon: <Gi3DStairs/>,
        header: "First ",
        content: "I'm a paragraph. Click here to add your own text and edit me. Let your users get to know you.",
        link: "",
        backgroundColor: "#097747E6"
    },
        {
            keyID: 2,
            icon: <FcIdea />,
            header: "Second",
            content: "I'm a paragraph. Click here to add your own text and edit me. Let your users get to know you.",
            link: "",
            backgroundColor: "#0DB26BE6"
        },
        {
            keyID: 3,
            icon: <FcAdvance />,
            header: "Third",
            content: "I'm a paragraph. Click here to add your own text and edit me. Let your users get to know you.",
            link: "/aboutus",
            backgroundColor: "#6DCCA3E6"
        }
    ];

    return (

        <div className={HomeStyles.carouselContainer}>
            <h4>
                Join FaceSpace
            </h4>
            <img src={logo2} width="200"
                 height="120" alt="alternative txt" />
            <div className={HomeStyles.solutionsCarouselContainer}>
                {solutions.map(solution => SolutionInfoBox(solution))}
            </div>

        </div>





    );
}
const SolutionInfoBox = (solution) =>
    <div key={solution.keyID} className={HomeStyles.solutionsInfoContainer}
         style={{"backgroundColor": solution.backgroundColor}}>
        <h2>
            {solution.icon}
        </h2>
        <h3>{solution.header}</h3>
        <p>{solution.content}</p>

    </div>


export default Home;
