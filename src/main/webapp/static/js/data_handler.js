

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
            }
        })
    },

    getShoppingCartInfo: function (callback) {
        $.ajax({
            type: "GET",
            url: "/change-quantity",
            success: function (newValues) {
                callback(newValues);
            }
        })
    }

};