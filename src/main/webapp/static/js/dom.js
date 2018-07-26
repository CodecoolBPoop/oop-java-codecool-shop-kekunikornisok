

// This adds event handlers to buttons
let dom = {
    shoppingCartItemNum: 0,
    init: function () {
        dom.addEventListenersToButtons();
        dataHandler.getShoppingCartInfo(dom.setShoppingCartVisual);
    },
    
    addEventListenersToButtons: function () {
        let productsToDecrease = document.getElementsByClassName("fa-minus");
        for (let product of productsToDecrease) {
            product.addEventListener("click", function () {
                dom.decreaseProductQuantity(product.id);
                document.getElementById("item-count").innerText = --dom.shoppingCartItemNum;
            })
        }

        let productsToIncrease = document.getElementsByClassName("fa-plus");
        for (let product of productsToIncrease) {
            product.addEventListener("click", function () {
                dom.increaseProductQuantity(product.id);
                document.getElementById("item-count").innerText = ++dom.shoppingCartItemNum;
            })
        }

        let addToCartButtons = document.getElementsByClassName("add_to_cart");
        for (let button of addToCartButtons) {
            button.addEventListener("click", function () {
                document.getElementById("item-count").innerText = ++dom.shoppingCartItemNum;
            });
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

        if (newValues["newTotalItems"] === "0") {
            document.getElementById("item-count").hidden = true;
        }

        document.getElementById("total_items").innerText = "Total items: " + newValues["newTotalItems"];
        document.getElementById("total_price").innerText = "Total price: " + newValues["newTotalPrice"] + " USD";
    },

    setShoppingCartVisual: function (shoppingCartInfo) {
        console.log("pina");
        let isVisible;
        if (shoppingCartInfo["isVisible"] === "true") {
            isVisible = true;
        } else if (shoppingCartInfo["isVisible"] === "false") {
            isVisible = false;
        }
        dom.shoppingCartItemNum = parseInt(shoppingCartInfo["totalItemsInCart"]);
        document.getElementById("item-count").hidden = isVisible;
        document.getElementById("item-count").innerText = shoppingCartInfo["totalItemsInCart"];
    }
};

dom.init();