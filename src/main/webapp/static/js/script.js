

// This adds event handlers to buttons
let dom = {
    init: function () {
        this.addEventListenersToButtons();
    },
    addEventListenersToButtons: function () {
        $(".checkout_button").on("click", function () {
            $(`
                <div></div>                      
              `).appendTo(".container");
        })
    }
};

dom.init();