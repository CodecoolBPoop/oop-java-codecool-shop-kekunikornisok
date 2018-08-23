

// This adds event handlers to buttons
let dom = {
    shoppingCartItemNum: 0,
    init: function () {
        dom.addEventListenersToButtons();
        dataHandler.getShoppingCartInfo(dom.setShoppingCartVisual);
    },

    addEventListenersToButtons: function () {
        $(".fa-minus").on("click", function () {
            dom.decreaseProductQuantity(this.id);
            dom.showAlert("Item removed from cart!", "success");
            $("#item-count").text(--dom.shoppingCartItemNum);
        });

        $(".fa-plus").on("click", function () {
            dom.increaseProductQuantity(this.id);
            dom.showAlert("Item added to cart!", "success");
            $("#item-count").text(++dom.shoppingCartItemNum);
        });

        $(".add_to_cart").on("click", function () {
            dom.showAlert("Item added to cart!", "success");
            $("#item-count").text(++dom.shoppingCartItemNum);
        });

        $(document).on("show_alert", ".alert", function(){
            window.setTimeout($.proxy(function() {
                $(this).fadeTo(500, 0).slideUp(500, function(){
                    $(this).remove();
                });
            }, this), 2000);
        });

        $("#checkout_button").on("click", function () {
            $("html, body").animate({
                // This is not working for some reason
                // scrollTop: $("#payment").offset().top
                scrollTop: 180
            }, 1000);
        });

        $("#payment_back_button").on("click", function () {
            $("html, body").animate({ scrollTop: 100 }, 1000);
        });

        $("#payment_next_button").on("click", function () {
            $("html, body").animate({ scrollTop: 270 }, 1000);
            dataHandler.billingAddress($("#first_name_billing").val(), $("#last_name_billing").val(), $("#country_billing").val(),
                $("#state_billing").val(), $("#address_billing").val(), $("#zip_billing").val(), dom.billingAddressInfo)
        });

        $("#confirmation_button").on("click", function () {
            $("html, body").animate({ scrollTop: 0 }, 1000);
        });

        $("#register-button").on("click", function () {
            dataHandler.registerUser($("#email_register").val(),
                $("#password_register").val(),
                $("#confirm_password").val(),
                dom.resetRegisterInfo);
        });

        $("#login-button").on("click", function () {
            dataHandler.loginUser($("#email_login").val(), $("#password_login").val(), dom.loginUser);
        });

        $("#logout-button").on("click", function () {
            dataHandler.logoutUser(dom.logoutUser);
        });
    },

    decreaseProductQuantity: function (productId) {
        dataHandler.decreaseProductQuantity(productId, dom.displayNewValues);
    },

    increaseProductQuantity: function (productId) {
        dataHandler.increaseProductQuantity(productId, dom.displayNewValues);
    },

    displayNewValues: function (newValues) {
        if (newValues["newQuantity"] !== 0) {
            $("#quantity_" + newValues["productId"]).text(newValues["newQuantity"]);
        } else {
            $("#table_row_" + newValues["productId"]).remove();
        }

        if (newValues["newTotalItems"] === 0) {
            $("#item-count").toggleClass("d-none");
            $(".shopping-cart-not-empty").toggleClass("d-none");
            $(".shopping-cart-empty").toggleClass("d-none");
        }

        $("#total_items").text("Total items: " + newValues["newTotalItems"]);
        $("#total_price").text("Total price: " + newValues["newTotalPrice"] + " USD");
    },

    setShoppingCartVisual: function (shoppingCartInfo) {
        if (parseInt(shoppingCartInfo["totalItemsInCart"]) !== 0) {
            $("#item-count").toggleClass("d-none");
            $(".shopping-cart-empty").toggleClass("d-none");
        }

        dom.shoppingCartItemNum = parseInt(shoppingCartInfo["totalItemsInCart"]);
        $("#item-count").text(shoppingCartInfo["totalItemsInCart"]);
    },

    showAlert: function (message, color) {
        $(`
            <div class="alert alert-${color}">${message}</div>
        `).appendTo("#alert-messages").trigger("show_alert");
    },

    handleError: function (errorInfo) {
        if (!$("#item-count").hasClass("d-none")) {
            $("#item-count").toggleClass("d-none");
        }

        if (!$(".shopping-cart-empty").hasClass("d-none")) {
            $(".shopping-cart-empty").toggleClass("d-none");
        }

        if (!$(".shopping-cart-not-empty").hasClass("d-none")) {
            $(".shopping-cart-not-empty").toggleClass("d-none");
        }

        $(`
            <div class="row h1 text-center">
                404 Internal Server Error!<br>
                <div class="row h2 text-center">${errorInfo["errorMessage"]}</div>
            </div>
        `).appendTo(".container")
    },

    resetRegisterInfo: function (alertInfo) {
        dom.showAlert(alertInfo["alertMessage"], alertInfo["alertColor"]);
        if (alertInfo["alertColor"] === "success") {
            $("#email_register").val("");
        }
        $("#password_register").val("");
        $("#confirm_password").val("");
    },

    loginUser: function (alertInfo) {
        dom.showAlert(alertInfo["alertMessage"], alertInfo["alertColor"]);
        $("#email_login").val("");
        $("#password_login").val("");
        if (alertInfo["alertColor"] === "success") {
            $(".logged-out").toggleClass("d-none");
            $(".logged-in").toggleClass("d-none");
        }
    },

    logoutUser: function (alertInfo) {
        dom.showAlert(alertInfo["alertMessage"], alertInfo["alertColor"]);
        if (alertInfo["alertColor"] === "success") {
            $(".logged-out").toggleClass("d-none");
            $(".logged-in").toggleClass("d-none");
        }
    },

    billingAddressInfo: function (alertInfo) {
        dom.showAlert(alertInfo["alertMessage"], alertInfo["alertColor"]);
    }
};

dom.init();