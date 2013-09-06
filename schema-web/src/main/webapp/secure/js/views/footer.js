define([ 'jquery', 'lodash', 'backbone', 'text!templates/footer.html' ],
function($, _, Backbone, footerTemplate) {
    
    var FooterView = Backbone.View.extend({
        el : '.footer',
        intialize : function() {
        },
        render : function() {
            $(this.el).html(footerTemplate);
        },
    });

    return FooterView;
});