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
var projects = [];
function startApp() {
    request(apiRoot + '/api/v1/languages/', function(data){
        projects = data.objects;
        $.mobile.changePage( "#languagesPage");
    }, function() {
        navigator.notification.alert(
            'Kullanici adi veya parola hatali',  // message
            'Dikkat!!',            // title
            'Opps'                  // buttonName
        );});


    var selectedlanguage = '';
    var selectedlanguageIndex = 0;
    var selectedProjectIndex = 0;
    var selectedStoreIndex = 0;

    $(document).on("pageshow", "#languagesPage",function() {
        var markup = '';
        for (var i = 0; i < projects.length; i++) {
            markup += '<li class="language" data-code="'+ i +'"> ' + projects[i].fullname + ' </li>';
        }

        $('#Languages_Content').html(markup);
        $("#Languages_Content").listview( "refresh" );

        $('.language').on("tap", function() {
            selectedlanguageIndex = $(this).attr("data-code");
            console.log(selectedlanguageIndex)
            getSelectedLanguageData(selectedlanguageIndex, function() {
                $.mobile.changePage( "#projectsPage");
            });
        });
    });


    $(document).on("pageshow","#projectsPage",function() {
        var language = projects[selectedlanguageIndex];
        console.log(language)

        var markup = '';
        for (var i = 0; i < language.translation_projects.length; i++) {
            var real_path = language.translation_projects[i].real_path.split("/")[0];
            markup += '<li class="project" data-code="' + i +'"> ' + real_path + ' </li>';
        }

        $('#Projects_Content').html(markup);
        $("#Projects_Content").listview( "refresh" );

        $('.project').on("tap", function() {
            selectedProjectIndex = $(this).attr("data-code");
            getSelectedProjectData(selectedlanguageIndex, selectedProjectIndex, function() {
                $.mobile.changePage( "#filesPage");
            });
        });

    $(document).on("pageshow","#filesPage",function() {
        var stores = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores;
        console.log(stores)
        var markup = '';
        for (var i = 0; i < stores.length; i++) {
            var progress = stores[i]['statistics']['translated']['percentage'];
            markup += '<tr><td><a class="store" data-code="' + i +'">' + stores[i].name + '</a></td>' +
                '<td><progress value="'+ progress +'" max="100"></progress></td></tr>';
        }

        $('#Files_Content').html(markup);
        $("#fileTable").table( "refresh" );


        $('.store').on("tap", function() {
            selectedStoreIndex = $(this).attr("data-code");
            $.mobile.changePage( "#translatePage");
        });
    });

    });

    $(document).on("pageshow","#filesPage",function() {
        var stores = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores;
        console.log(stores)
        var markup = '';
        for (var i = 0; i < stores.length; i++) {
            var progress = stores[i]['statistics']['translated']['percentage'];
            markup += '<tr><td><a class="store" data-code="' + i +'">' + stores[i].name + '</a></td>' +
                '<td><progress value="'+ progress +'" max="100"></progress></td></tr>';
        }

        $('#Files_Content').html(markup);
        $("#fileTable").table( "refresh" );


        $('.store').on("tap", function() {
            selectedStoreIndex = $(this).attr("data-code");
            $.mobile.changePage( "#translatePage");
    });

});


}

function getSelectedLanguageData(languageIndex, callback) {
    var language = projects[languageIndex];
    var translationProjectCount = language.translation_projects.length;
    var completedRequestCount = 0;

    for (var i = 0; i < language.translation_projects.length; i++) {
        updateTranslationProject(languageIndex, i, apiRoot + language.translation_projects[i], isCompleted)
    }

    function isCompleted() {
        completedRequestCount++;
        if (completedRequestCount == translationProjectCount) {
            callback();
        }
    }
}

function updateTranslationProject(i, j, url, callback) {
    request(url, function(data) {
        projects[i]['translation_projects'][j] = data;
        callback()
    });
}

function getSelectedProjectData(languageIndex, projectIndex, callback) {
    var stores = projects[languageIndex]['translation_projects'][projectIndex]['stores'];
    var storesCount = stores.length;
    var completedRequestCount = 0;

    for (var i = 0; i < stores.length; i++) {
        updateStores(languageIndex, projectIndex, i, apiRoot + stores[i] + 'statistics/', isCompleted)
    }

    function isCompleted() {
        completedRequestCount++;
        if (completedRequestCount == storesCount) {
            callback();
        }
    }
}


function updateStores(i, j, k, url, callback) {
    request(url, function(data) {
        projects[i]['translation_projects'][j]['stores'][k] = data;
        callback();
    });
};

function request(url, successCallback, errorCallback) {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
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