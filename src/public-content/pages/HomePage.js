import React from "react";
import Sidebar from "../Components/Sidebar";
import "../styles.css"
import Feed from "../Components/Feed";
import Widgets from "../Components/Widgets";

function HomePage() {
    return (
        <div className="home_page">
            <Sidebar/>
            <Feed/>
            <Widgets/>
        </div>
    );
}

export default HomePage;
