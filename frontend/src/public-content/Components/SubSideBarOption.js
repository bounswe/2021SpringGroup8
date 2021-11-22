import React from "react";
import "./styles/SubSidebarOption.css"


function SubSidebarOption({active, text, Icon, onClick}) {
    return (
        <div className={`subSidebarOption ${active && "sidebarOption--active"}`} onClick={onClick}>
            <Icon/>
            <h4>{text}</h4>
        </div>
    );
}

export default SubSidebarOption;
