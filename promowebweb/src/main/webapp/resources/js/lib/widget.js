;(function() {
var namespace = window.cbt = window.cbt || {};

/**
 * The Message class allows additional information to be provided to
 * subscribers.
 * 
 * @param name
 *            {String} The name of the message
 * @param props
 *            {Object} An object with properties that should be applied to the
 *            newly created message
 */
var Message = namespace.Message = function(name, data) {
	this.name = name;
	this.data = data;
};

Message.prototype = {
	/**
	 * Return the message name.
	 * 
	 * @returns {String} The name of the message
	 */
	getName : function() {
		return this.name;
	},

	/**
	 * Return the message data.
	 * 
	 * @returns {Object} The data for the message
	 */
	getData : function() {
		return this.data;
	}
};

/**
 * Observable observes many topics (different message names) , each topic has a ListenerList object. ListenerList has many Listeners.
 * Each Listener maps a message callback. When specified topic is published, all Listeners attached with this topic will be called.
 */
var ListenerList = namespace.ListenerList = function() {
	this._listeners = [];
	this._onEmpty = function(){};
};

ListenerList.prototype = (function(){
	var nextHandleId = 1;
	
	/**
	 * Each listener is distinguished by its id.
	 */
	var Listener = function(callback, thisObj, autoRemove, removed){
		this.callback = callback;
		this.thisObj = thisObj;
		this.removed = removed;
		this.autoRemove = autoRemove;
		this.id = nextHandleId++;
	};
	
	return {
		/**
		 * Add a new Listener.
		 * @param callback
		 * @param thisObj
		 * @param autoRemove
		 * @returns {Listener} A listener handle
		 */
		add : function(callback, thisObj, autoRemove) {
			var _this = this;
			var listener = new Listener(callback, thisObj, autoRemove);
			
			listener.remove = function() {
				_this.remove(listener);
			};

			this._listeners.push(listener);

			return listener;
		},
		
		/**
		 * Remove a listener from listener list.
		 * 
		 * @param listener
		 *            Listener object
		 */
		remove: function(listener){
			listener.removed = true;
			this._listeners = this._listeners.filter(function(handler){
				return !handler.removed;
			});
			
			if (this._listeners.length == 0) {
				this._onEmpty();
			}
		},

		/**
		 * Publishes a message to the listeners in this list
		 * 
		 * @param args
		 */
		publish : function() {
			var args = arguments, _this = this;

			_this._listeners.forEach(function(listener) {
				if (listener.removed)
					return;

				listener.callback.apply(listener.thisObj, args);
				
				if (listener.autoRemove) {
					_this.remove(listener);
				}
			});
		},

		/**
		 * Set a callback which would be invoked when listener list is empty.
		 * @param callback
		 * @param thisObj
		 */
		onEmpty : function(callback, thisObj) {
			this._onEmpty = function(){
				callback.apply(thisObj, arguments);
			};
		},

		/**
		 * Empty listener list
		 */
		removeAll : function() {
			var listeners = this._listeners;
			for ( var i = 0; i < listeners.length; i++) {
				// flag listener for removal
				listeners[i].removed = true;
			}
			
			this.listeners = [];
			this._onEmpty();
		}
	};
})();

var Observable = namespace.Observable = function() {
	this._topics = {};
};

Observable.prototype = (function() {
	var Message = namespace.Message;
	
	/**
	 * Return a function which would work as either subscribe or publish function depends on its arguments.
	 * If returned function's argument is function, then it will work as subscribe method. Otherwise,
	 * it works as publish method.
	 * 
	 * @param name {String} Message name
	 * @returns {Function}
	 */
	var createMessageFunc = function(name) {
		return function(props) {
			var args = [ name ].concat(Array.prototype.slice.call(arguments));
			this[typeof props == 'function' ? 'subscribe' : 'publish'].apply(
					this, args);
		};
	};
	
	/**
	 * Check if a message is allowed in observable object.
	 * 
	 * @param messageName
	 * @param observable
	 *            Observable object
	 */
	var checkMessage = function(messageName, observable) {
		var allowedMessages = observable._allowed;
		if (allowedMessages && !allowedMessages[messageName]) {
			throw new Error('Invalid message name of "' + messageName
					+ '". Allowed messages: '
					+ Object.keys(allowedMessages).join(', '));
		}
	};
	
	return {
		observable : true,

		/**
		 * Register message callbacks. When this method is used, only these registered messages 
		 * could be subscribed or published.
		 * 
		 * For example: this.registerMessage("click");
		 * 
		 * Then we can use it as below: this.click(args) ==
		 * this.publish("click", args); this.click(function(){}) ==
		 * this.subscribe("click", function(){});
		 * 
		 * @param messages
		 *            {Array.<String>} An array of message names to register
		 * @param createPublishFunctions
		 *            Boolean value
		 */
		registerMessages : function(messages, createPublishFunctions) {
			if (!this._allowed) {
				this._allowed = {};
			}

			for ( var i = 0, len = messages.length; i < len; i++) {
				var message = messages[i];
				this._allowed[message] = true;

				if (createPublishFunctions) {
					// Make a message name as a property which will work as subscribe and publish method in the meantime.
					this[message] = createMessageFunc(message);
				}
			}

		},

		/**
		 * Registers a listener or a set of listeners for the provided event
		 * types.
		 * 
		 * Two signatures are supported:
		 * <ol>
		 * <li> eventHandle subscribe(type, callback, thisObj, autoRemove)</li>
		 * <li> eventHandle subscribe(callbacks, thisObj, autoRemove)</li>
		 * </ol>
		 * 
		 * @param name
		 *            {String} The message name
		 * @param callback
		 *            {Function} The callback function
		 * @param thisObj
		 * @param autoRemove
		 * @returns {Listener} A handle to
		 *          remove the added listeners or select listeners
		 */
		subscribe : function(name, callback, thisObj, autoRemove) {
			var _this = this;

			if (typeof name == 'object') {
				autoRemove = thisObj; // autoRemove is the third argument
				thisObj = callback; // thisObj is the second argument

				for (var key in name) {
					_this.subscribe(key, name[key], thisObj, autoRemove);
				}

				return this;
			}
			
			checkMessage(name, _this);

			var listenersInstance = _this._topics[name];
			if (!listenersInstance) {
				_this._topics[name] = listenersInstance = new ListenerList();

				// Prevent a memory leak by removing empty listener lists
				listenersInstance.onEmpty(function() {
					delete _this._topics[name];
				});
			}
			return listenersInstance.add(callback, thisObj, autoRemove);
		},

		/**
		 * Unsubscribe all message listeners
		 */
		unsubscribeAll : function() {
			for (var name in this._topics) {
				listeners = this._topics[name];
				listeners.removeAll();
			}

			this._topics = {};
		},

		/**
		 * Publishes a message with the specified name.
		 * 
		 * Arguments can be passed to the subscribers by providing zero or more
		 * arguments after the topic name argument
		 * 
		 * Example code: <js> //Simple string as argument
		 * someObj.publish('myMessage', 'Hello World!');
		 * 
		 * //Multiple arguments someObj.publish('myMessage', 'Hello World!',
		 * 'John Doe'); </js>
		 * 
		 * @param name
		 *            {String|Message} The message name or a
		 *            Message object that has the message name and args as
		 *            properties.
		 * @param message
		 * 			  {Message|*} The message data or Message object. 
		 */
		publish : function(name, message) {

			var args;

			if (Array.isArray(message)) {
				args = message;
			} else {
				if (name instanceof Message) {
					message = name;
					name = message.getName();
				} else {
					message = new Message(name, message);
				}
				args = [ message.data, message ];
			}

			checkMessage(name, this);

			var _this = this;

			var _publish = function(name) {
				var listenersInstance = _this._topics[name];
				if (!listenersInstance)
					return;
				
				listenersInstance.publish.apply(listenersInstance, args);
			};

			_publish(name);
			// subscribers who subscribe all topics will be called.
			_publish('*'); 

			var lastDot = name.lastIndexOf('.');
			if (lastDot >= 0) {
				// subscribers who subscribe all topics of certain namespace will be called.
				_publish(name.substring(0, lastDot + 1) + '*');
			}

			return message;
		}
	};
})();

/**
 * depends on Observable
 * @author lyan2
 */
var Widget = namespace.Widget = function() {};

Widget.prototype = (function(){

	return {
		
		/**
		 * Prototype's object property will be shared between all instances. Property topics (in Observable class) can't 
		 * be saved directly in Widget object, because Widget instance will be the prototype of many
		 * UI components. This is also true for observable object. So, we created getObservable() 
		 * method to dynamically add a _observable property in Widget or subclass instance.
		 * 
		 * Remember: _observable is in Widget or subclass instance, not necessarily in Widget direct instance. 
		 * So each Widget or subclass instance has a observable object, which is not shared.
		 */
		getObservable : function() {
			return this._observable = this._observable || new namespace.Observable();
		},
		
		/**
		 * Subscribe a topic and provide a callback function.
		 * 
		 * @param topic String, such as "news"
		 * @param func Function
		 * @param context The context of the callback function.
		 * 
		 * @return token Unique id of new topic subscriber. You'd better save this token for later unsubscribe.
		 */
		subscribe : function(eventName, func, context) {
			var ob = this.getObservable();
			return ob.subscribe.apply(ob, arguments);
		},
		
		/**
		 * Except topic argument, you can also pass other arguments as the parameters of the function for
		 * specified topic.
		 * 
		 * Example
		 *   obj.publish("news", {"title": "Hello world"});
		 * 
		 * @param topic
		 * 
		 * @return this
		 */
		publish : function(eventName) {
			var ob = this.getObservable();
			ob.publish.apply(ob, arguments);
		}			
	};
	
})();
	
})();