define([ 'jquery', 'underscore', 'backbone', 'vm', 'utility' ], 
function($, _, Backbone, Vm, Utility) {

    var AppRouter = Backbone.Router.extend({
        routes : {
            // Default - catch all
            '*actions' : 'defaultAction'
        }
    });

    var initialize = function(options) {
        var appView = options.appView;
        var router = new AppRouter(options);
        router.on('route:defaultAction', function(actions) {
            require([ 'views/page' ], function(DashboardPage) {
                var dashboardPage = Vm.create(appView, 'DashboardPage', DashboardPage);
                dashboardPage.render();
            });
        });
        Backbone.history.start();
        Utility.hidePageLoader();
    };

    return {
        initialize : initialize
    };
});