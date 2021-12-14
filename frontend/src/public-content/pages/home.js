import React,{Component} from "react";


import Footer from "../Components/footer/footer";
import HomeStyles from "./homepage.module.css";
import Navbar from "../Components/navbar";
import Header from "../Components/header/header";

import {FcAdvance, FcIdea} from "react-icons/fc";
import {Gi3DStairs} from "react-icons/gi";
import logo from "../images/logo_t.png";
import logo2 from "../images/facespace_2.png";




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
        header: "Join a purposeful community ",
        content: "Join a purposeful community to easily find like-minded people and do the things you enjoy together  ",
        link: "",
        backgroundColor: "#EE000Ebc"
    },
        {
            keyID: 2,
            icon: <FcIdea />,
            header: "Find out new hobbies ",
            content: "Find out new hobbies you don't know you always enjoyed.",
            link: "",
            backgroundColor: "#0DB26BE6"
        },
        {
            keyID: 3,
            icon: <FcAdvance />,
            header: "Follow the updated events ",
            content: "Follow all the new events as soon as they occur with the ease of FaceSpace",
            link: "/aboutus",
            backgroundColor: "#878501bc "
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
