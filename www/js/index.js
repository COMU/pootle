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
    initialize: function () {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function () {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function () {
        app.receivedEvent('deviceready');
    },
    // Update DOM on a Received Event
    receivedEvent: function (id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }

};

function pageLoaded() {
    $('#divList').empty();

    var users = JSON.parse(localStorage.getItem('users'));

    for (var i = 0; i < users.length; i++) {
        var row = users[i];
        $('#divList').append('<a id=' + row['nick'] + '><h3 class="ui-li-heading">' + row['nick'] + '</h3></a>');
        console.log(divList);
        //console.log(row['serverAddress'])
        //apix ='http://api.ozdincer.com'
        $('#' + row['nick']).on("tap", function () {
            apix = row['serverAddress'];
            userName = row['userName'];
            passWord = row['password'];
            $.mobile.changePage("#loginPage");
        });
    }

    $('#divList').listview();
}



var apix;




var i = 0;
var j = 0;
var k = 0;
arr = [];
var apiRoot = '';
//var apiRoot = apix;
console.log(apix)

$(document).on("pagebeforeshow", "#loginPage", function () {
    apiRoot = 'http://' + apix;
    console.log(apiRoot);
    console.log(userName);
    console.log(passWord);

    if (window.localStorage.getItem("username") && window.localStorage.getItem("password")) {
        startApp();
    }
});

var username = window.localStorage.getItem("username") || userName;
var password = window.localStorage.getItem("password") || passWord;

//console.log(apix);

var projects = [];
function startApp() {
    //apiRoot = apix;
    //console.log(apix)
    if ($('#remember').is(':checked')) {
        // save username and password

        window.localStorage.setItem("username", $('#username').val());
        window.localStorage.setItem("password", $('#password').val());
    }

    $('#aboutButton').on("tap", function () {

        $.mobile.changePage("#about");

    });

    $('#cancel').on("tap", function () {

        $.mobile.changePage("#welcomepage");

    });

    $('#aboutButton2').on("tap", function () {

        $.mobile.changePage("#about");

    });

    $('#cancel2').on("tap", function () {

        $.mobile.changePage("#welcomepage");

    });

    $('#aboutButton3').on("tap", function () {

        $.mobile.changePage("#about");

    });

    $('#cancel3').on("tap", function () {

        $.mobile.changePage("#welcomepage");

    });

    $('#aboutButton4').on("tap", function () {

        $.mobile.changePage("#about");

    });

    $('#cancel4').on("tap", function () {

        $.mobile.changePage("#welcomepage");

    });


    $('#aboutButton5').on("tap", function () {

        $.mobile.changePage("#about");

    });

    $('#cancel5').on("tap", function () {

        $.mobile.changePage("#welcomepage");

    });

    request(apiRoot + '/api/v1/languages/', function (data) {
        projects = data.objects;
        $.mobile.changePage("#languagesPage");
    }, function () {
        navigator.notification.alert(
            'Kullanici adi veya parola hatali',  // message
            'Dikkat!!',            // title
            'Opps'                  // buttonName
        );
    });

    var selectedlanguage = '';
    var selectedlanguageIndex = 0;
    var selectedProjectIndex = 0;
    var selectedStoreIndex = 0;
    var selectedUnitIndex = 0;
    var selectedSugIndex = 0;

    $(document).on("pageshow", "#languagesPage", function () {
        var markup = '';
        for (var i = 0; i < projects.length; i++) {
            markup += '<li class="language" data-code="' + i + '"> ' + projects[i].fullname + ' </li>';
        }

        $('#Languages_Content').html(markup);
        $("#Languages_Content").listview("refresh");

        $('.language').on("tap", function () {
            selectedlanguageIndex = $(this).attr("data-code");
            console.log(selectedlanguageIndex)
            getSelectedLanguageData(selectedlanguageIndex, function () {
                $.mobile.changePage("#projectsPage");
            });
        });
    });


    $(document).on("pageshow", "#projectsPage", function () {
        var language = projects[selectedlanguageIndex];
        console.log(language)

        var markup = '';
        for (var i = 0; i < language.translation_projects.length; i++) {
            var real_path = language.translation_projects[i].real_path.split("/")[0];
            markup += '<li class="project" data-code="' + i + '"> ' + real_path + ' </li>';
        }

        $('#Projects_Content').html(markup);
        $("#Projects_Content").listview("refresh");

        $('.project').on("tap", function () {
            selectedProjectIndex = $(this).attr("data-code");
            getSelectedProjectData(selectedlanguageIndex, selectedProjectIndex, function () {
                $.mobile.changePage("#filesPage");
            });
        });
    });

    $(document).on("pageshow", "#filesPage", function () {
        var stores = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores;
        console.log(stores)
        var markup = '';
        for (var i = 0; i < stores.length; i++) {
            var progress = stores[i]['statistics']['translated']['percentage'];
            markup += '<tr><td><a class="store" data-code="' + i + '">' + stores[i].name + '</a></td>' +
                '<td><progress value="' + progress + '" max="100"></progress></td></tr>';
        }

        $('#Files_Content').html(markup);
        $("#fileTable").table("refresh");


        $('.store').on("tap", function () {
            selectedStoreIndex = $(this).attr("data-code");
            $.mobile.changePage("#aboutFilePage");
        });
    });

    $(document).on("pageshow", "#aboutFilePage", function () {
        var store = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex];
        console.log(store)
        var markup = '';
        var markup2 = '';

        var total = store.statistics.total.words;
        var fuzzy = store.statistics.fuzzy.words;
        console.log(total);
        var needtranslate = store.statistics.untranslated.words;
        var suggestions = store.statistics.suggestions;
        markup2 += store.name;
        markup += '<tr><td><a class="total" >' + total + '</a></td>' +
            '<td><a class="untranslated" >' + needtranslate + '</a></td>' +
            '<td><a class="fuzzy" >' + fuzzy + '</a></td>';

        $('#FileName').html(markup2);
        $('#AboutFiles_Content').html(markup);
        $("#AboutFileTable").table("refresh");


        $('.total').on("tap", function () {
            getSelectedUnitData(selectedlanguageIndex, selectedProjectIndex, selectedStoreIndex, selectedUnitIndex, function () {
                $.mobile.changePage("#TranslatePage");
            });
        });

        $('.untranslated').on("tap", function () {
            getSelectedStateData(selectedlanguageIndex, selectedProjectIndex, selectedStoreIndex, selectedUnitIndex, function () {
                $.mobile.changePage("#TranslatePage");
            });
        });

        $('.fuzzy').on("tap", function () {
            getSelectedFuzzyData(selectedlanguageIndex, selectedProjectIndex, selectedStoreIndex, selectedUnitIndex, function () {
                $.mobile.changePage("#TranslatePage");
            });
        });


    });


    $(document).on("pageshow", "#TranslatePage", function () {

        var units = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units;
        console.log(units);
        var store = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex];

        console.log(units[i].suggestions);
        var markup = '<textarea id="textarea" class="TranslateTurkish" > ' + units[i].target_f + ' </textarea>';
        var markup2 = '<p class="TranslateEnglish">' + units[i].source_f + '</p>'
        var markup3 = store.name;

        $('#oneri').on("tap", function () {

            suggest = units[i].suggestions;

            getSelectedUnitData(selectedlanguageIndex, selectedProjectIndex, selectedStoreIndex, selectedUnitIndex, selectedSugIndex, function () {
                console.log(projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units[selectedUnitIndex].suggestions[selectedSugIndex]);
            });


        });

        $('#TranslateTurkish').html(markup);
        $('#TranslateEnglish').html(markup2);
        $('#file_name').html(markup3);

        function sonraki() {
            i = i + 1;


            getSelectedUnitData(selectedlanguageIndex, selectedProjectIndex, selectedStoreIndex, i, function () {
                var unit = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units[i];
                console.log(arr)
                markup = '<textarea class="TranslateTurkish" > ' + unit.target_f + ' </textarea>';
                markup2 = '<p class="TranslateEnglish">' + unit.source_f + '</p>';

                $('#TranslateTurkish').html(markup);
                $('#TranslateEnglish').html(markup2);
            });

        };


        $('#onceki').on("tap", function () {
            /*if(i>0) {
             i = i - 1;
             var unit = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units[i];
             markup = '<textarea class="TranslateTurkish" > ' + unit.target_f + ' </textarea>';
             markup2 = '<p class="TranslateEnglish">' + unit.source_f + '</p>';

             $('#TranslateTurkish').html(markup);
             $('#TranslateEnglish').html(markup2);
             console.log(projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units[i]);
             }*/

            if (arr.length > 0) {
                i = arr[arr.length - 1];
                var unit = projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units[i];
                markup = '<textarea class="TranslateTurkish" > ' + unit.target_f + ' </textarea>';
                markup2 = '<p class="TranslateEnglish">' + unit.source_f + '</p>';

                $('#TranslateTurkish').html(markup);
                $('#TranslateEnglish').html(markup2);
                arr.pop(arr.length - 1);
                console.log(arr);
                console.log(projects[selectedlanguageIndex].translation_projects[selectedProjectIndex].stores[selectedStoreIndex].units[i]);
            }
            ;


        });

        $('#sonraki').on("tap", function () {
            sonraki();
        });


        $('#gonder').on("tap", function () {
            console.log(units[i].resource_uri);
            units[i].target_f = document.getElementById('textarea').value;

            $.ajax({
                type: 'PUT',
                url: apiRoot + units[i].resource_uri,
                contentType: 'application/json',

                headers: {
                    "Authorization": "Basic " + btoa(username + ":" + password)
                },
                data: JSON.stringify({
                    "target_f": units[i].target_f,
                    "target_length": units[i].target_f.split('').length,
                    "target_wordcount": units[i].target_f.split(' ').length,
                    "translator_comment": units[i].translator_comment,
                    "suggestions": ""}),

                success: function (data) {
                    alert("gonderildi");
                    sonraki();

                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                },
                dataType: 'json'
            });


        });

        $('#oner').on("tap", function () {

            console.log(units[i].suggestions);
            units[i].target_f = document.getElementById('textarea').value;

            $.ajax({
                type: 'POST',
                url: apiRoot + '/api/v1/suggestions/',
                contentType: 'application/json',


                headers: {
                    "Authorization": "Basic " + btoa(username + ":" + password)
                },
                data: JSON.stringify({
                    "target_f": units[i].target_f,
                    "translator_comment_f": units[i].translator_comment,
                    "unit": units[i].resource_uri
                }),

                success: function (data) {
                    alert("tamam");
                    sonraki();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                },
                dataType: 'json'
            });


        });


    });



}



function getSelectedStateData(languageindex, projectindex, storeindex, unitindex, callback) {
    var unitsUrl = projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex];


    console.log(unitsUrl);
    request(apiRoot + unitsUrl, function (data) {
        units = data;
        console.log(units);
        var state = units.state;
        var suggest = units.suggestions;

        if (state != 0) {
            unitindex = unitindex + 1;
            getSelectedStateData(languageindex, projectindex, storeindex, unitindex, callback);
            console.log(projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex]);
            console.log(unitindex);

        } else {
            projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex] = units;
            i = unitindex;
            arr.push(i);

            callback();

        }
        projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex] = data;

    });
}


function getSelectedFuzzyData(languageindex, projectindex, storeindex, unitindex, callback) {
    var unitsUrl = projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex];

    console.log(unitsUrl);
    request(apiRoot + unitsUrl, function (data) {
        units = data;
        console.log(units);
        var state = units.state;
        console.log(state)

        if (state == 50) {
            projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex] = units;
            i = unitindex;
            callback();

        } else {
            unitindex = unitindex + 1;
            getSelectedStateData(languageindex, projectindex, storeindex, unitindex, callback);
            console.log(projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex]);
            console.log(unitindex);


        }
        projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex] = data;

    });
}


function getSelectedUnitData(languageindex, projectindex, storeindex, unitindex, callback) {
    var units = projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex];
    console.log(units);

    request(apiRoot + units, function (data) {
        projects[languageindex]['translation_projects'][projectindex]['stores'][storeindex]['units'][unitindex] = data;
        console.log(data);
        callback();
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
    request(url, function (data) {
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
    request(url, function (data) {
        projects[i]['translation_projects'][j]['stores'][k] = data;
        callback();
    });
};


function request(url, successCallback, errorCallback) {
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

}

function make_base_auth(user, password) {
    var tok = user + ':' + password;
    var hash = btoa(tok);
    return "Basic " + hash;
};