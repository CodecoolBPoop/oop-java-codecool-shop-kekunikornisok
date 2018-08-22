

// This handles ajax requests
let dataHandler = {

    decreaseProductQuantity: function (productId, callback) {
        $.ajax({
            type: "POST",
            url: "/change-quantity",
            data: {"quantity": "decrease",
                "id": productId},
            success: function (newValues) {
                callback(newValues);
            },
            error: function (errorInfo) {
                dom.handleError(errorInfo);
            }
        })
    },

    increaseProductQuantity: function (productId, callback) {
        $.ajax({
            type: "POST",
            url: "/change-quantity",
            data: {"quantity": "increase",
                "id": productId},
            success: function (newValues) {
                callback(newValues);
            },
            error: function (errorInfo) {
                dom.handleError(errorInfo);
            }
        })
    },

    getShoppingCartInfo: function (callback) {
        $.ajax({
            type: "GET",
            url: "/change-quantity",
            success: function (shoppingCartInfo) {
                callback(shoppingCartInfo);
            },
            error: function (errorInfo) {
                dom.handleError(errorInfo);
            }
        })
    },

    registerUser: function (userEmail, userPassword, callback) {
        $.ajax({
            type: "POST",
            url: "/handle-user",
            data: {"event": "register",
                   "userEmail": userEmail,
                   "userPassword": userPassword},
            success: function (alertInfo) {
                callback(alertInfo);
            },
            error: function (errorInfo) {
                dom.handleError(errorInfo);
            }
        })
    },

    loginUser: function (userEmail, userPassword, callback) {
        $.ajax({
            type: "POST",
            url: "/handle-user",
            data: {"event": "login",
                   "userEmail": userEmail,
                   "userPassword": userPassword},
            success: function (alertInfo) {
                callback(alertInfo);
            },
            error: function (errorInfo) {
                dom.handleError(errorInfo);
            }
        })
    },

    logoutUser: function (callback) {
        $.ajax({
            type: "GET",
            url: "/handle-user",
            success: function (alertInfo) {
                callback(alertInfo);
            },
            error: function (errorInfo) {
                dom.handleError(errorInfo);
            }
        })
    }
};