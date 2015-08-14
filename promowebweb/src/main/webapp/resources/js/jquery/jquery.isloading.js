/**
* Loading plugin for jQuery
* 
* Small helper to give the user a visual feedback that something is happening 
* when fetching/posting data
* 
* USAGE:
* - global overlay:                     $.isLoading();
* - use javascript:                     $( selector ).isLoading();
* - On non-form elements:               $("div").isLoading({ text: "Loading", position:'inside'});
* - remove the loading element:         $( selector ).isLoading( "hide" );
*
* @author Linus Yan <linus.yan@hotmail.com>
* ---
* Copyright 2014, Linus Yan ( https://github.com/linus87/isLoading )
* Released under the MIT, BSD, and GPL Licenses.
*/
;(function ( $, window, document, undefined ) {

    // Create the defaults once
    var pluginName = "isLoading",
        defaults = {
            'position': "right",        // right | inside | overlay
            'text': "",                 // Text to display next to the loader
            'class': "icon-refresh",    // loader CSS class
            'tpl': '<span class="isloading-wrapper %wrapper%">%text%<i class="%class% icon-spin"></i></span>',    // loader base Tag
            'disableSource': true,      // true | false
            'disableOthers': []
        };

    // The actual plugin constructor
    function Plugin( element, options ) {
        this.element = element;
        this.$element = $(this.element);
        
        // Merge user options with default ones
        this.options = $.extend( {}, defaults, options );

        this._defaults     = defaults;
        this._name         = pluginName;
        this._loader       = null;                // Contain the loading tag element

        this.init();
    }

    // Contructor function for the plugin (only once on page load)
    function contruct() {

        if ( !$[pluginName] ) {
            $.isLoading = function( opts ) {
                $( "body" ).isLoading( opts );
            };
        }
    }
    
    Plugin.prototype = {
        
        init: function() {
            
            if( this.$element.is( "body") ) {
                this.options.position = "overlay";
            }
            this.show();
        },

        show: function() {

            var tpl = this.options.tpl.replace( '%wrapper%', ' isloading-show ' + ' isloading-' + this.options.position );
            tpl = tpl.replace( '%class%', this.options['class'] );
            tpl = tpl.replace( '%text%', ( this.options.text !== "" ) ? this.options.text + ' ' : '' );
            this._loader = $( tpl );
            
            // Disable the element
            if( this.$element.is( "input, textarea" ) && true === this.options.disableSource ) {

            	this.$element.attr( "disabled", "disabled" );

            }
            else if( true === this.options.disableSource ) {

            	this.$element.addClass( "disabled" );

            }

            // Set position
            switch( this.options.position ) {

            	case "replace":
            		this.$element.html( this._loader );
            		break;
            	
                case "inside":
                	var _inside = $('<div class="isloading-inside-wrapper"></div>');
                	
                	_inside.css({
                		width: this.$element.width(),
                		height: this.$element.height(),
                		position: 'absolute',
                		color: '#fff',
                		background: 'rgba(0,0,0,0.75)',
                		"z-index": 10
                	});
                	
                	this.$element.prepend(_inside);
                	this.$element.find('.isloading-inside-wrapper').html( this._loader );
                	
                	this._loader.css({
                		left: (this.$element.width() - this._loader.innerWidth()) / 2,
                		top: (this.$element.height() - this._loader.innerHeight()) / 2,
                		position: 'absolute'
                	});
                    break;
                    
                case "overlay":
                    if( this.$element.is( "body") ) {
						var max_height = this.element.scrollHeight > document.documentElement.scrollHeight ? this.element.scrollHeight : document.documentElement.scrollHeight;
                        $( "body" ).prepend( '<div class="isloading-overlay" style="position:fixed; left:0; top:0; z-index: 10000; color: #fff; background: rgba(0,0,0,0.75); width: 100%; height: ' + max_height + 'px;" />' );
                        
                        this.$element.find(".isloading-overlay").html( this._loader );
                        this._loader.css({
                    		left: ($(window).width() - this._loader.innerWidth()) / 2 + document.documentElement.scrollLeft,
                    		top: ($(window).height() - this._loader.innerHeight()) / 2  + document.documentElement.scrollTop,
                    		position: 'absolute',
                    		'z-index': 90000
                    	});
                    }
                    else {
                        var pos = this.$element.position();
                        $( "body" ).prepend( '<div class="isloading-overlay" style="position:absolute; top: ' + pos.top + 'px; left: ' + pos.left + 'px; z-index: 10000; color: #fff;  background: rgba(0,0,0,0.75); width: ' + this.$element.outerWidth() + 'px; height: ' + this.$element.outerHeight() + 'px;" />' );
                        this.$element.find(".isloading-overlay").html( this._loader );
                        this._loader.css({
                    		left: (this.$element.width() - this._loader.innerWidth()) / 2,
                    		top: (this.$element.height() - this._loader.innerHeight()) / 2,
                    		position: 'absolute',
                    		'z-index': 90000
                    	});
                    }
                    
                    break;

                default:
                    this.$element.after( this._loader );
                    break;
            }

            this.disableOthers();
        },

        hide: function() {

            if( "overlay" === this.options.position ) {

                $( ".isloading-overlay" ).remove();

            } else if ("inside" === this.options.position) {
            	
            	this.$element.find( ".isloading-inside-wrapper" ).remove();
            	
            } else {

                $( this._loader ).remove();
                this.$element.text( this.$element.attr( "data-isloading-label" ) );

            }

            this.$element.removeAttr("disabled").removeClass("disabled");

            this.enableOthers();
        },

        disableOthers: function() {
            $.each(this.options.disableOthers, function( i, e ) {
                var elt = $( e );
                if( elt.is( "button, input, textarea" ) ) {
                    elt.attr( "disabled", "disabled" );
                }
                else {
                    elt.addClass( "disabled" );
                }
            });
        },

        enableOthers: function() {
            $.each(this.options.disableOthers, function( i, e ) {
                var elt = $( e );
                if( elt.is( "button, input, textarea" ) ) {
                    elt.removeAttr( "disabled" );
                }
                else {
                    elt.removeClass( "disabled" );
                }
            });
        }
    };
    
    // Constructor
    $.fn[pluginName] = function ( options ) {
        return this.each(function () {
            if ( !$.data( this, "plugin_" + pluginName ) ) {
                $.data( this, "plugin_" + pluginName, new Plugin( this, options ) );
            } else {
                var elt = $.data( this, "plugin_" + pluginName );

                if( "hide" === options )    { elt.hide(); }
                else                        { elt.show(); }
            }
        });
    };
    
    contruct();

})( jQuery, window, document );