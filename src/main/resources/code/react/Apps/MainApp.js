'use strict'
var jQuery = require('jquery');
var React = require('react');
var ReactDOM = require('react-dom');
var Reflux = require('reflux');
var Route = require('react-router');
var createReactClass = require('create-react-class');

var HomePageApp = createReactClass({
    mixins: [
    ],

    getInitialState: function() {
        return {
        };
    },

    componentDidMount: function () {
        // Add event listeners in componentDidMount
    },

    render: function(){
        return (
            <div>
                <div>
                </div>
                <div>
                </div>
            </div>
        );
    }
});

ReactDOM.render(<MainApp />, document.getElementById("mainAppContent"));

module.exports = MainApp;