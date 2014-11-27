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

function startApp() {


    request('http://api.ozdincer.com/api/v1/languages/', function(data){
        alert('Başarıyla giriş yaptınız.');
        window.location.hash = '#languagesPage';
        console.log(data)
        projects = data.objects;

    }, function() {
        alert('Kullanıcı adı veya parola hatalı.')});


    var selectedlanguage = '';
    var selectedproject = '';

    $(document).on("pageshow","#languagesPage",function() {
        var markup = '<ul data-role="listview" data-theme="b">';
        for (var i = 0; i < projects.length; i++) {
            markup += '<li> <a class="language" data-code="'+ projects[i].translation_projects +'"'+' >' + (i + 1) + ' - ' + projects[i].fullname + '</a> </li>';
        }

        markup += '</ul>';
        $('#Languages_Content').html(markup);

        $('a.language').on("tap", function() {
            selectedlanguage = $(this).attr("data-code");
            $.mobile.changePage( "#projectsPage");
        });
    });


    $(document).on("pageshow","#projectsPage",function() {
        selectedlanguage = selectedlanguage.split(",");
        var project = [];

        for(var j=0; j<selectedlanguage.length;j++) {
            url="http://api.ozdincer.com" + selectedlanguage[j];
            request(url, function (data) {

                project= data ;
                var markup = '<ul data-role="listview" data-theme="b">';
                pootle_path = (project.pootle_path).split("/")[2];
                markup += '<li> <a class="project" data-code="' + project.stores +'"'+' >' + pootle_path + '</a> </li></ul>';

                $('#Projects_Content').html(markup);

                $('a.project').on("tap", function() {
                    selectedproject = $(this).attr("data-code");
                    $.mobile.changePage( "#filesPage");
                });

            }, function () {
                alert('olmadi')
            });

        }

    });

   $(document).on("pageshow","#filesPage",function() {
        selectedproject = selectedproject.split(",");
        console.log(selectedproject);
        var files = [];

        for(var j=0; j<selectedproject.length;j++) {

            url="http://api.ozdincer.com" + selectedproject[j];
            request(url, function (data) {

                files=data;
                console.log(files);
                var markup = '<ul data-role="listview" data-theme="b">';

                markup += '<li> <a href="#"' + ' >' + files.name + '</a> </li>';


                markup += '</ul>';
                $('#Files_Content').html(markup);

            }, function () {
                alert('olmadi')
            });

            console.log(selectedlanguage);
        }

    });


}


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


