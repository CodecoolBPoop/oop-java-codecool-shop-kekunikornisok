

// This adds event handlers to buttons
let dom = {
    init: function () {
        dom.addEventListenersToButtons();
    },
    
    addEventListenersToButtons: function () {
        let productsToDecrease = document.getElementsByClassName("fa-minus");
        for (let product of productsToDecrease) {
            product.addEventListener("click", function () {
                dom.decreaseProductQuantity(product.id);
            })
        }

        let productsToIncrease = document.getElementsByClassName("fa-plus");
        for (let product of productsToIncrease) {
            product.addEventListener("click", function () {
                dom.increaseProductQuantity(product.id);
            })
        }
    },
    
    decreaseProductQuantity: function (productId) {
        dataHandler.decreaseProductQuantity(productId, dom.displayNewValues);
    },

    increaseProductQuantity: function (productId) {
        dataHandler.increaseProductQuantity(productId, dom.displayNewValues);
    },
    
    displayNewValues: function (newValues) {
        if (newValues["newQuantity"] !== "0") {
            document.getElementById("quantity_" + newValues["productId"]).innerText = newValues["newQuantity"];
        } else {
            document.getElementById("table_row_" + newValues["productId"]).remove();
        }

        document.getElementById("total_items").innerText = "Total items: " + newValues["newTotalItems"];
        document.getElementById("total_price").innerText = "Total price: " + newValues["newTotalPrice"] + " USD";
    }
};

dom.init();