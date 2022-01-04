import React from "react";
import ReactDOM from "react-dom";
import {Provider} from "react-redux";
import store from "./store";
import {BrowserRouter} from "react-router-dom";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import "bootstrap/dist/css/bootstrap.min.css"

ReactDOM.render(
    < Provider store={store}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </Provider>,
    document.getElementById("root")
);
serviceWorker.unregister();
