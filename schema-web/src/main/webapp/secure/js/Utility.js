Backbone.View.prototype.close = function(){
	console.log('Backbone.View.close');
	// Cleanly closes this view and all of it's rendered subviews
    this.$el.empty();
    this.undelegateEvents();
    if (this._renderedSubViews) {
      for (var i = 0; i < this._renderedSubViews.length; i++) this._renderedSubViews[i].close();
    }
    if (this.onClose) this.onClose();
};

$.xhrPool = [];
$.xhrPool.abortAll = function() {
	console.log('xhrPool.abortAll');
	try {
	    $(this).each(function(idx, jqXHR) {
	        jqXHR.abort();
	    });
	    while ($(this).length > 0) {
	    	$(this).pop();
	    }
	} catch(err) {
		console.log(err);
	}
};

$.ajaxSetup({
    beforeSend: function(jqXHR) {
        $.xhrPool.push(jqXHR);
    },
    complete: function(jqXHR) {
        var index = $.xhrPool.indexOf(jqXHR);
        if (index > -1) {
            $.xhrPool.splice(index, 1);
        }
    }
});

String.prototype.replaceAll = function(character,replaceChar){
    var word = this.valueOf();

    while(word.indexOf(character) != -1)
        word = word.replace(character,replaceChar);

    return word;
};

function isNumber(n) {
	  return !isNaN(parseFloat(n)) && isFinite(n);
};

if (!window.console) {
	window.console = {log: function() {}};
}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

$.noty.defaults = {
	  layout: 'center',
	  theme: 'defaultTheme',
	  type: 'information',
	  text: '',
	  dismissQueue: true, // If you want to use queue feature set this true
	  template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
	  animation: {
	    open: {height: 'show'},
	    close: {height: 'toggle'},
	    easing: 'swing',
	    speed: 1 // opening & closing animation speed
	  },
	  timeout: false, // delay for closing event. Set false for sticky notifications
	  force: false, // adds notification to the beginning of queue when set to true
	  modal: false,
	  closeWith: ['click'], // ['click', 'button', 'hover']
	  callback: {
	    onShow: function() {},
	    afterShow: function() {},
	    onClose: function() {},
	    afterClose: function() {}
	  },
	  buttons: false // an array of buttons
};

function showPageLoader() {
	$('.modal-loader').show();
};

function hidePageLoader() {
	$('.modal-loader').hide();
}
