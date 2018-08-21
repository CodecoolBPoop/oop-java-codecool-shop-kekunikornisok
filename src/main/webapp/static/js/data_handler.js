

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
    }

};