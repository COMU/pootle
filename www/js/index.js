/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

var apiRoot = 'http://api.ozdincer.com';
function startApp() {
    request(apiRoot + '/api/v1/languages/', function(data){
        if (JSON.parse(window.localStorage.getItem('db')))
            allData = JSON.parse(window.localStorage.getItem('db'));
        else
            createData(data);

        window.location.hash = '#languagesPage';
        console.log(data)
        projects = data.objects;

    }, function() {
        navigator.notification.alert(
            'Kullanici adi veya parola hatali',  // message
            'Dikkat!!',            // title
            'Opps'                  // buttonName
        );});


    var selectedlanguage = '';
    var selectedlanguageIndex = 0;
    var selectedProjectIndex = 0;

    $(document).on("pageshow","#languagesPage",function() {
        var markup = '<ul data-role="listview" data-theme="b">';
        for (var i = 0; i < projects.length; i++) {
            markup += '<li> <a class="language" data-code="'+ i +'"'+' >' + (i + 1) + ' - ' + projects[i].fullname + '</a> </li>';
        }

        markup += '</ul>';
        $('#Languages_Content').html(markup);
        $('a.language').on("tap", function() {
            selectedlanguageIndex = $(this).attr("data-code");
            console.log(selectedlanguageIndex)
            $.mobile.changePage( "#projectsPage");
        });
    });


    $(document).on("pageshow","#projectsPage",function() {
        var language = allData[selectedlanguageIndex];
        var markup = '<ul data-role="listview" data-theme="b">';
        for (var i = 0; i < language.translation_projects.length; i++) {
            real_path = language.translation_projects[i].real_path.split("/")[0];
            markup += '<li> <a class="project" data-code="' + i +'"'+' >' + real_path + '</a> </li>';
        }
        markup += '</ul>';

        $('#Projects_Content').html(markup);

        $('a.project').on("tap", function() {
            selectedProjectIndex = $(this).attr("data-code");
            $.mobile.changePage( "#filesPage");
        });

    });

    $(document).on("pageshow","#filesPage",function() {
        var stores = allData[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores;
        var markup = '<ul data-role="listview" data-theme="b">';
        for (var i = 0; i < stores.length; i++) {
            markup += '<li> <a href="#"' + ' >' + stores[i].name + '</a> </li>';
        }
        markup += '</ul>';
        $('#Files_Content').html(markup);
    });


}

var allData = [];
var translationProjectCount = 0;
var storesCount = 0;
function createData(data) {
    allData = data.objects;

    for (var i = 0; i < allData.length; i++) {
        var translationProjects = allData[i]['translation_projects'];
        for (var j = 0; j < translationProjects.length; j++) {
            updateTranslationProject(i, j, apiRoot + translationProjects[j]);
        }
    }
}

function updateTranslationProject(i, j, url) {
    request(url, function(data) {
        allData[i]['translation_projects'][j] = data;
        translationProjectCount++;
        console.log(translationProjectCount)
        if (translationProjectCount == 65) {
            startStores();
        }
    });
}

function startStores() {
    for (var i = 0; i < allData.length; i++) {
        var translationProjects = allData[i]['translation_projects'];
        for (var j = 0; j < translationProjects.length; j++) {
            var stores = translationProjects[j]['stores'];
            for (var k = 0; k < stores.length; k++) {
                updateStores(i, j, k, apiRoot + stores[k]);
            }
        }
    }
}

function updateStores(i, j, k, url) {
    request(url, function(data) {
        allData[i]['translation_projects'][j]['stores'][k] = data;
        storesCount++;
        console.log(storesCount)
        if (storesCount == 74) {
            window.localStorage.setItem('db', JSON.stringify(allData));
        }
    });
};

function request(url, successCallback, errorCallback) {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'jsonp',
        async: false,
        data: '{}',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', make_base_auth(username, password));
        },
        success: successCallback,
        error: errorCallback
    });

    function make_base_auth(user, password) {
        var tok = user + ':' + password;
        var hash = btoa(tok);
        return "Basic " + hash;
    };
}
 