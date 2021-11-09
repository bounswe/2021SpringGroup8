import React from "react";
import Sidebar from "../Components/Sidebar";
import "../styles.css"
import Feed from "../Components/Feed";


function HomePage() {
    return (
        <div className="home_page">
            <Sidebar/>
            <Feed/>

        </div>
    );
}

export default HomePage;
