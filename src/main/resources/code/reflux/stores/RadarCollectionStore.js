'use strict';

import Reflux from 'reflux';
import React from 'react';
import jQuery from 'jquery';
var _ = require('lodash');

// Actions
import radarCollectionActions from "../actions/RadarCollectionActions";

var RadarCollectionStore = Reflux.createStore({
    listenables: [radarCollectionActions],

    radarCollection: [],

    init: function() {
        this.radarCollection = [];
    },

    onGetByUserId: function(userId) {
        var returnValue = new Array();

        jQuery.ajax({
            url: '/api/User/' + userId + '/Radars',
            async: true,
            dataType: 'json',
            success: function (requestData) {
                console.log(requestData);
                this.radarCollection = requestData;
                this.trigger({radarCollection:requestData});
                returnValue = requestData;
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(url, status, err.toString());
            }.bind(this)
        });

        return returnValue;
    },

    onGetPublicByUserId: function(userId) {
        var returnValue = [];

        jQuery.ajax({
            url: '/api/public/User/' + userId + '/Radars',
            async: false,
            dataType: 'json',
            success: function (requestData) {
                console.log(requestData);
                returnValue = requestData;
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(url, status, err.toString());
            }.bind(this)
        });

        this.radarCollection = returnValue;
        this.trigger({radarCollection:returnValue});
        return returnValue;
    },

     onCreateRadarInstance: function (userId, radarInstanceName) {
         var radarInstanceData = {};
         radarInstanceData.name = radarInstanceName;

         jQuery.ajax({
             method: "POST",
             url: "/api/User/" + userId + '/Radar',
             data: JSON.stringify(radarInstanceData),
             contentType: "application/json; charset=utf-8",
             success: function (restData) {
                 console.log(restData);
                 this.onGetByUserId(userId);
             }.bind(this),
             error: function (xhr, status, err) {
                 console.error(url, status, err.toString());
             }.bind(this)
         });
     },

     onUpdateChart: function (chartId, chartName, pointEarnerId, tasks) {
         var chartData = {
             Name: chartName,
             PointEarnerId: pointEarnerId,
             Tasks: tasks
         };

         jQuery.ajax({
             method: "PUT",
             url: "/api/Chart/" + chartId,
             data: JSON.stringify(chartData),
             contentType: "application/json; charset=utf-8",
             success: function (restData) {
                 console.log(restData);
                 this.onGetAllTasks();
             }.bind(this),
             error: function (xhr, status, err) {
                 console.error(url, status, err.toString());
             }.bind(this)
         });
     },

     onDeleteRadarInstance: function (userId, radarInstanceId) {
         jQuery.ajax({
             method: "DELETE",
             url: "/api/User/" + userId + "/Radar/" + radarInstanceId,
             contentType: "application/json; charset=utf-8",
             success: function (restData) {
                 console.log(restData);
                 this.onGetByUserId(userId);
             }.bind(this),
             error: function (xhr, status, err) {
                 console.error(url, status, err.toString());
             }.bind(this)
         });
     },

      onPublishRadarInstance: function (userId, radarInstanceId, isPublished) {
          var parameters = {};
          parameters.isPublished = isPublished;

          jQuery.ajax({
              method: "PUT",
              url:  '/api/User/' + userId + '/Radar/' + radarInstanceId + '/Publish',
              data: JSON.stringify(parameters),
              contentType: "application/json; charset=utf-8",
              success: function (restData) {
                  console.log(restData);
                  this.onGetByUserId(userId);
              }.bind(this),
              error: function (xhr, status, err) {
                  console.error(url, status, err.toString());
              }.bind(this)
          });
      },

     onLockRadarInstance: function (userId, radarInstanceId, isLocked) {
          var parameters = {};
          parameters.isLocked = isLocked;

         jQuery.ajax({
             method: "PUT",
             url:  '/api/User/' + userId + '/Radar/' + radarInstanceId + '/Lock',
             data: JSON.stringify(parameters),
             contentType: "application/json; charset=utf-8",
             success: function (restData) {
                 console.log(restData);
                 this.onGetByUserId(userId);
             }.bind(this),
             error: function (xhr, status, err) {
                 console.error(url, status, err.toString());
             }.bind(this)
         });
     }
});

module.exports = RadarCollectionStore;
