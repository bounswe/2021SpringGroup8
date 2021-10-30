import { createStore, applyMiddleware } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import thunk from "redux-thunk";

const middleware = [thunk];
const store = createStore(

    composeWithDevTools(applyMiddleware(...middleware))
);

export default store;
