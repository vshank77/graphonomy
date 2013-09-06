require.config({
    paths : {
        jquery : '../../assets/jslibs/jquery/jquery-min',
        underscore : '../../assets/jslibs/underscore/underscore-min',
        lodash : '../../assets/jslibs/lodash/lodash-min',
        backbone : '../../assets/jslibs/backbone/backbone',

        text : '../../assets/jslibs/require/text',

        templates : '../templates'
    },
    shim : {
        underscore : {
            exports : '_'
        },
        backbone : {
            deps : [ "underscore", "jquery" ],
            exports : "Backbone"
        }
    }
});

require([ 'views/app', 'router', 'vm' ], 
function(AppView, Router, Vm) {

    var appView = Vm.create({}, 'AppView', AppView);
    appView.render();
    Router.initialize({
        appView : appView
    }); 
});