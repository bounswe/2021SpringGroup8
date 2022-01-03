import axios from "axios";
import AuthService from "./auth.service"
import authService from "./auth.service";

const API_URL = "http://3.145.120.66:8080/";

class SearchService {

    search(community_id, data_type_name, filters) {
        console.log(data_type_name)
        console.log(filters)
        let filter_array = []
        filters.map((item, index) => {
            let new_filter_object = []
            if (item.fieldType === 'str' && item.fieldValue !== "") {
                new_filter_object = ['search text', item.fieldName, [item.fieldValue]]
                filter_array.push(new_filter_object)
            } else if (item.fieldType === 'int' && item.fieldValue !== "") {
                new_filter_object = [item.fieldOperator, item.fieldName, [parseInt(item.fieldValue)]]
                filter_array.push(new_filter_object)
            } else if (item.fieldType === 'bool' && item.fieldValue !== "") {

                new_filter_object = [item.fieldValue === "1" ? "checked" : "unchecked", item.fieldName]
                filter_array.push(new_filter_object)
            }
            console.log(JSON.stringify(filter_array))
        });
        let paramStr = 'communityId=' + community_id + '&datatypename=' + data_type_name + '&filters=' + JSON.stringify(filter_array);
        return axios.post('http://3.145.120.66:8080/searchpost', paramStr,
            {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }
        );
    }


}

export default new SearchService();