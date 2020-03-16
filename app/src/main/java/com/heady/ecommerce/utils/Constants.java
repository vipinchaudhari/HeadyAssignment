package com.heady.ecommerce.utils;

public interface Constants {
    String BASE_URL = "https://stark-spire-93433.herokuapp.com";
    String DATABASE_NAME = "heady_database_v1";

    //API fetch status
    String SUCCESS = "SUCCESS";
    String LOADING = "LOADING";
    String ERROR = "ERROR";

    //error message
    String API_ERROR = "An unexpected error has been occurred.";

    //event constants
    String NONE = "NONE";
    String CATEGORY_SELECTED = "CATEGORY_SELECTED";
    String PRODUCT_SELECTED = "PRODUCT_SELECTED";

    //key constants
    String CATEGORY_ID = "CATEGORY_ID";
    String CATEGORY_NAME = "CATEGORY_NAME";
    String PRODUCT_ID = "PRODUCT_ID";
}
