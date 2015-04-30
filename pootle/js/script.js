var language;
var selectproject;
var sfile;
var a=0;
var c=0;
var req;
var clength;
var rusername;
var rpassword;
var risim;
var rsunucu;
var rarray= new Array();
var rrarray=0;
var rlength;
var register;
$(document).ready(function() {
  $("#cevir").fadeOut();
    $("#registerresult").fadeOut();
    $("#kayit").fadeOut();
   $("#inputs").fadeOut();
   $("#start").fadeIn();
   
});
function noconnect(){
   results.innerHTML = "";
   $("#cevir").fadeOut();
    $("#registerresult").fadeOut();
    $("#kayit").fadeOut();
   $("#inputs").fadeOut();
   $("#start").fadeIn();
}
$(document).on('click','#rkayit',function(){
  rusername=$('input[name=namekayit]').val();
	 rpassword=$('input[name=passwordkayit]').val();
   risim=$('input[name=isim]').val();
	 rsunucu=$('input[name=sunucu]').val();
		
		if(rusername == '' || rpassword== ''|| risim==''|| rsunucu== ''){
       var insert="Please insert values";
			alert(insert);
      
		}
   else{
   newbutton();
   }
});
var basari="Successfully Saved";

function newbutton(){
   rarray[rrarray]=new Array(risim,rsunucu,rusername,rpassword);
   rrarray=rrarray+1;
   alert(basari);
  $('input[name=passwordkayit]').val("");
	 $('input[name=namekayit]').val("");
   $('input[name=isim]').val("");
	 $('input[name=sunucu]').val("");
  // loginpage();
}
$(document).on('click','#toplam',function(){
    $('#oneri1').html("");
    $('<p>'+""+'</p>').appendTo('#oneri1');
    $('#oneri2').html("");
    $('<p>'+""+'</p>').appendTo('#oneri2');
    $('#oneri3').html("");
    $('<p>'+""+'</p>').appendTo('#oneri3');
    $('#oneri4').html("");
    $('<p>'+""+'</p>').appendTo('#oneri4');
    $('#oneri5').html("");
    $('<p>'+""+'</p>').appendTo('#oneri5');
    sg=0;
    c=0;
    sgarray[0]=undefined;
    sgarray[1]=undefined;
    sgarray[2]=undefined;
    sgarray[3]=undefined;
    sgarray[4]=undefined;
   ceviributton=0;
   ceviri();
});
$(document).on('click','#cevirigerekli',function(){
    $('#oneri1').html("");
    $('<p>'+""+'</p>').appendTo('#oneri1');
    $('#oneri2').html("");
    $('<p>'+""+'</p>').appendTo('#oneri2');
    $('#oneri3').html("");
    $('<p>'+""+'</p>').appendTo('#oneri3');
    $('#oneri4').html("");
    $('<p>'+""+'</p>').appendTo('#oneri4');
    $('#oneri5').html("");
    $('<p>'+""+'</p>').appendTo('#oneri5');
    sg=0;
    sgarray[0]=undefined;
    sgarray[1]=undefined;
    sgarray[2]=undefined;
    sgarray[3]=undefined;
    sgarray[4]=undefined;
    c=0;
   ceviributton=1;
   cevirigerekli();
});
$(document).on('click','#logp',function(){
  $('input[name=passwordkayit]').val("");
	 $('input[name=namekayit]').val("");
   $('input[name=isim]').val("");
	 $('input[name=sunucu]').val("");
   $("#kayit").fadeOut();
   $("#registerresult").fadeOut();
  // $("#rk").fadeOut();
   $("#start").fadeIn();
   results.innerHTML = "";

});
function listsil(){
    results.innerHTML = "";
   rlength=rarray.length;
   results.innerHTML +="<button id='listback' data-l10n-id='back' class='btn-success im buttonBlue'>"+"BACK"+"</button>";
   results.innerHTML +="<p id='siltext' data-l10n-id='siltext'>"+"Tap the list you want to delete element"+'<p/>';
   
    ss=$("#siltext")
   ss.css("color","blue");
   for(i=0;i<rlength;i++){
      while(rarray[i][0]==undefined){
         i=i+1;
         }
      results.innerHTML +='<p>'+rarray[i][1]+'</p>' 
      results.innerHTML +='<header class="listheaderBlue borderBlue">'+"<li><a href='#' id='rmm' i=" + i + "><button id='rid' class='btn-success startlist buttonBlue'>"+rarray[i][0]+"</button></a></li>";

   }
}
$(document).on('click','#sil',function(){
  listsil();
});
$(document).on('click','#listback',function(){
   loginpage();
});
var silme;
$(document).on('click',"#rmm", function(){
     silme= $(this).attr("i");
   rarray[silme]="";
    //loginpage();
   listsil();
});
function loginpage(){
   rlength=rarray.length;
   results.innerHTML = "";

   results.innerHTML +="<tr><td><button id='logp' data-l10n-id='back' class='btn-success im buttonBlue'>"+"BACK"+"</button></td><td><button id='sil' data-l10n-id='sil' class='btn-success im buttonBlue'>"+"DELETE"+"</button></td></tr>";
   for(i=0;i<rlength;i++){
      while(rarray[i][0]==undefined){
         i=i+1;
         }
       //results.innerHTML +='<p>'+rarray[i][1]+'</p>'  
      results.innerHTML +='<header class="listheaderBlue borderBlue">'+"<li><a href='#' id='rk' i=" + i + "><button id='rid' class='btn-success startlist buttonBlue'>"+rarray[i][0]+"</button></a></li>";
       
      //results.innerHTML +='<header class="listheaderBlue borderBlue">'+"<ul class='stlist' style='list-style-type:none'><li><a href='#' id='rk' i=" + i + "><button id='rid' class='btn-success input buttonBlue'>"+rarray[i][0]+"</button></a></li></ul>";
     
   }
}
var url;
function islet(){
   url=rarray[register][1];
   username=rarray[register][2];
   password=rarray[register][3];
   sendRequest(username,password);
}
$(document).on('click',"#rk", function(){
     register = $(this).attr("i");
     islet();
});	
$(document).on('click','#giris',function(){
   $("#kayit").fadeOut();
  // $("#inputs").fadeIn();
   $("#start").fadeOut();
   $("#registerresult").fadeIn();
 //   results.innerHTML = "";
    //results.innerHTML +=" <tr><td><button id='fileback' data-l10n-id='back' class='btn-success buttonBlue'>"+"BACK"+"</button></td><td><button id='logout' class='btn-success buttonBlue'>"+"LOGOUT"+"</button></td></tr>";
    //results.innerHTML += '<table><tr><td><p id="t" data-l10n-id="t" >' + "t" + '</p></td><td><p id="cg" data-l10n-id="cg" >'+"cg"+'</p></td></tr><tr><td><button id="toplam" class="btn-success input buttonBlue" ><p id="t1" >'+"topp"+'</p></button></td><td><button id="cevirigerekli" class="btn-success input buttonBlue"><p id="t2" >'+"need"+'</p></button></td></tr></table>';
    
  loginpage();
   
   
});
$(document).on('click','#kaydol',function(){
   s1=$("#issim")
   s1.css("color","blue");
    s2=$("#suucu")
   s2.css("color","blue");
    s3=$("#ukyt")
   s3.css("color","blue");
    s4=$("#pkyt")
   s4.css("color","blue");
  $("#inputs").fadeOut();
   $("#start").fadeOut();
   $("#kayit").fadeIn();
   });
/*$(document).on('click','#oner',function(){
  var data = JSON.parse(crequest.response);
   stringdata=JSON.stringify(data);
   var obj = JSON.parse(stringdata);
   alert(obj);
   obj.target_f="melek";
   
   
  var cevir=$("textarea").val();
   var desc=cevir.split(" "); 
   alert(desc);
//  var melek=cevir.length;
  // alert(melek);
   ffrequest=new XMLHttpRequest({mozSystem:true});
    ffrequest.open('POST','http://'+url+'/api/v1/units/66251/',true,username,password);
   //osrequest.open('POST','http://api.ozdincer.com/api/v1/suggestions/',true,username,password);
   //prequest.open('GET','api.ozdincer.com/api/v1/suggestions/8/',true,username,password);
 // ffrequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
   ffrequest.setRequestHeader("Content-Type", "application/json");

ffrequest.timeout = 5750;
	ffrequest.addEventListener('timeout', function() {
		alert('No connection..');
	});
 //  ffrequest.send('target_f=lkşö&translator_comment_f=deneme&unit=/api/v1/units/66251/');
 ffrequest.send(JSON.stringify({
//'suggestions':'["/api/v1/suggestions/8/", "/api/v1/suggestions/12/,"/api/v1/suggestions/13/"]'
    "resource_uri": "",
    "target_f": "hjbnkj",
    "translator_comment_f": "",
    "unit": ""
})); 
   
   //ffrequest.send();
   ffrequest.onreadystatechange=function(){
  alert(ffrequest.status);
   }
});*/
$(document).on('click','#gonder',function(){
 
    gonderr();
  
});
$(document).on('click','#logout',function(){
   
   $("#cevir").fadeOut();
   //$("#results").fadeOut()
   $("#inputs").fadeOut();
   $("#registerresult").fadeOut();
   $("#results").fadeIn();
   results.innerHTML = "";
   $("#start").fadeIn();
  // loginpage();
       //results.innerHTML +="<button id='logp' class='btn-success buttonBlue'>"+"BACK"+"</button>";                   
      
   
   
});

$(document).on('click','#languageback',function(){
   languages();
   
});
$(document).on('click','#projeback',function(){
   projects();
   
});
$(document).on('click','#fileback',function(){
  
   files();
  // results.innerHTML = "";
   //loginpage();
   
});
var topp;
var need;
$(document).on('click','#tableback',function(){
    $("#cevir").fadeOut();
    $("#filepage").fadeIn();
    $("#tablepage").fadeIn();
    $("#registerresult").fadeIn();
    $("#kayit").fadeOut();
   $("#inputs").fadeOut();
   $("#start").fadeOut();
   $("#results").fadeIn();
    $("#field").fadeOut();
    $("#project").fadeOut();
   results.innerHTML = "";
   //$("#filepage").fadeIn();
   tablee();
  
	
  
});
 function languages(){
        var data = JSON.parse(request.response);
        stringdata=JSON.stringify(data);
        var obj = JSON.parse(stringdata);
        results.innerHTML = "";
    $("#registerresult").fadeOut();
         $("#results").fadeIn();
    results.innerHTML = "";
   results.innerHTML +="<button id='logout' data-l10n-id='logout' class='btn-success im buttonBlue'>"+"LOGOUT"+"</button>";

                             
                // array[i]= obj.objects[i].fullname+"<hr/>"; 
//$('#movie-list').append('<header class="listheaderBlue borderBlue">'+'<p class="language">'+obj.objects[i].fullname+'</p>');
                             
                     
         for (var i in stringdata) {
                          //results.innerHTML +='<ul class="a" data-code='+obj.objects[i].translation_projects+'><header class="listheaderBlue borderBlue">'+'</header>'+'<li><a href="#">' + obj.objects[i].fullname +'</a></li>'+ '</p></ul>';
									        //results.innerHTML +='<ul class="a"'+'><header class="listheaderBlue borderBlue">'+'data-code='+obj.objects[i].translation_projects+'</header>'+'<li><a href="#">' + obj.objects[i].fullname +'</a></li>'+ '</p></ul>';
                          
             //results.innerHTML +='<ul class="a"'+'><header class="listheaderBlue borderBlue ">'+'< i=' + i + 'a href="#" id="foo" <li>' + obj.objects[i].fullname +'</li></a>'+'</header>'+ '</ul>'; 
          //2015 results.innerHTML +='<ul class="a"'+'><header class="listheaderBlue borderBlue ">'+'<li><a i=' + i + ' href="#">' + obj.objects[i].fullname +'</a></li>'+ '</p></ul>'+'</header>'; 
        
                 var languagecss='<p id=css>'+obj.objects[i].fullname+'</p>'          //results.innerHTML +='<header class="listheaderBlue borderBlue "></header>'+'<a  id="foo" i=' + i  +' href="#"'+ obj.objects[i].fullname +'></a>'; 
      // results.innerHTML +='<header class="listheaderBlue borderBlue">'+'<ul id="menn" data-role="listview" data-inset="true" class="listview"><li><a href="#" id="foo"  i=' + i + '>'+languagecss+'</a></li>'+ '</ul>'+'</header>'; 
            results.innerHTML +='<header class="listheaderBlue borderBlue">'+'<ul data-role="listview" data-inset="true" class="listview"><li><a href="#" id="foo"  i=' + i + '>'+'<h5>'+'<img src="http://www.dijitalteknoloji.net/wp-content/uploads/2012/04/foldercolorizer.jpg"' + languagecss +'</h5>'+'</a></li>'+ '</ul>'+'</header>';           
//results.innerHTML +='<header class="listheaderBlue borderBlue">'+'<ul style="list-style-image:http://www.dijitalteknoloji.net/wp-content/uploads/2012/04/foldercolorizer.jpg" data-role="listview" data-inset="true" class="listview"><li><a href="#" id="foo"  i=' + i + '>'+'<h5>' + languagecss +'</h5>'+'</a></li>'+ '</ul>'+'</header>';           
  
  //results.innerHTML +='<header class="listheaderBlue borderBlue">'+'<ul data-role="listview" data-inset="true" class="listview"><li><a href="#" id="foo"  i=' + i + '>'+'<h5>'+'<img src="http://www.dijitalteknoloji.net/wp-content/uploads/2012/04/foldercolorizer.jpg"/>' + languagecss +'</h5>'+'</a></li>'+ '</ul>'+'</header>';           
          
            //results.innerHTML +='<header class="listheaderBlue borderBlue"><table id=ps><tr><td id=ss></td></tr></table><ul data-role="listview" data-inset="true" class="listview"><li><img src="klasor.image><a href="#" id="foo"  i=' + i + '>' + languagecss +'</a></li>'+ '</ul>'+'</header>'; 
                      //  ps.addClass("mine");
                            //results.innerHTML +='<header class="listheaderBlue borderBlue ">'+'<ul data-role="listview" data-inset="true" class="listview"><li><a i=' + i + ' href="#">' + obj.objects[i].fullname +'</a></li>'+ '</ul>'+'</header>'; 
                           // results.innerHTML +='<ul class="b">'+'<li> i=' + i + obj.objects[i].fullname +'</li></ul>'; 
                            //'<img src="http://www.h-online.com/imgs/43/9/8/6/3/0/9/jQueryMobile_80-310cdbb54c204225.png" />
                              // results.innerHTML +='<header class="listheaderBlue borderBlue">'+'</header><a href="#"" id="foo"' + obj.objects[i].fullname +'</a>'; 
                       //results.innerHTML +='<li> <a class="language" data-code="'+ obj.objects[0].translation_projects +'"'+' >' + obj.objects[i].fullname + '</a> </li>';
												//results.innerHTML += '<li> <a class="language" data-code="'+ projects[i].translation_projects +'"'+' >' + (i + 1) + ' - ' + projects[i].fullname + '</a> </li>';
                      
           }
        
     }


$(document).on('click','#submit',function(){
		
		
		
		
		if(username == '' || password== ''){
			alert('Please insert values');
		}else{
			sendRequest(username,password);
		}
	});
/* $(document).on('click', '.listview', function () {
          // var melek = this.getAttribute("i");
           var melek = $(this).attr("i");
            alert(melek);
           alert(obj.objects[i].translation_projects);
        });
        */
$(document).on('click',"#foo", function(){
     language = $(this).attr("i");
   projects();
});	

$(document).on('click',"#project", function(){
		
     selectproject= $(this).attr("i");
     files();
});	
$(document).on('click',"#file", function(){
		
     sfile= $(this).attr("i");
   tablee();
});	
 /*for (var j in stringdata) {
                            results.innerHTML += '<header class="listheaderBlue borderBlue">'+'<p class="project">'+obj.real_path+'</p>'; 
                        }
  */
var reqid;
$(document).on('click',"#request", function(){
		
     reqid= $(this).attr("i");
     $('textarea').val(sgarray[reqid]);
     
});


var suggestions;
var deger;
var value;
function ceviri(){
      var data = JSON.parse(newrequest.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
   var store=obj.stores;
   store="'"+store+"'";
   store = store.split(","); 
   slength=store.length;
   store[sfile] = store[sfile].replace("'", "");
   store[sfile] = store[sfile].replace("'", "");
   req=store[sfile];
   ceviris();
   
   
   
}
var map;
function ceviris(){

//$(document).ready(function () {
        //$("#oner").click(function () {
    nrequest=new XMLHttpRequest({mozSystem:true});

	
	/*paste the URL to get*/
	
	nrequest.open('GET', 'http://'+url+req,true,username,password);
	
	/*set the timeout to detect connection issues*/
	nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
      noconnect();
	});
	
	/*send the request*/
	nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){

          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       selected[c] = selected[c].replace("'", "");
                       clength=selected.length; 
             
                       crequest=new XMLHttpRequest({mozSystem:true});
              crequest.open('GET', 'http://'+url+selected[c],true,username,password);
              crequest.send();
              crequest.onreadystatechange=function(){
                 map=selected[c];

                 if(crequest.status==200 && crequest.readyState==4){
                    var data = JSON.parse(crequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    deger=obj.source_f;
                    value=obj.target_f;
                    suggestions=obj.suggestions;
                    if(suggestions==""){
                     // dil();
                       ssonuc();
                    }
                    else{
                       //dil();
                       suggest();
                    }
                    
                   /*  $("#results").fadeOut();
                     $("#cevir").fadeIn();
                     $('#kelime').html("");
                     $("<p>" + deger+ "</p>").appendTo('#kelime');
                     $('textarea').val(value);
                    */
                    
                 }
             }      
              
}
}
}
function ssonuc(){
   results.innerHTML = "";
   $('#kelime').html("");
   $("<p>" + deger+ "</p>").appendTo('#kelime');
   $('textarea').val(value);
   $('#koneri').html("");
   $("<p>" +""+ "</p>").appendTo('#koneri');
   /* $('#ggonder').html("");
  $("<p>" +put+ "</p>").appendTo('#ggonder');*/
  /* $('#gonder').html("");
   $("<p>" +put+ "</p>").appendTo('#gonder');
   $('#oner').html("");
   $("<p>" +post+ "</p>").appendTo('#oner');
   $('#onceki').html("");
   $("<p>" +on+ "</p>").appendTo('#onceki');
    $('#sonraki').html("");
   $("<p>" +son+ "</p>").appendTo('#sonraki');*/
   $("#registerresult").fadeOut();
   $("#results").fadeOut();
   $("#cevir").fadeIn();
   //results.innerHTML +=" <tr><td><button id='tableback' class='btn-success buttonBlue'>"+"BACK"+"</button></td><td><button id='logout' class='btn-success buttonBlue'>"+"LOGOUT"+"</button></td></tr>";
   p = $("#kelime");
   p.css("border", "1px solid blue");
   p.css("color", "blue");
}
var text;
var put;
var post;
var on;
var son;
var Turkish;

function sonuc(){
   results.innerHTML = "";
   text="User Suggestions"
   $('#kelime').html("");
   $("<p>" + deger+ "</p>").appendTo('#kelime');
   $('textarea').val(value);
   $('#koneri').html("");
   $("<p id='text' data-l10n-id='text'>" +text+ "</p>").appendTo('#koneri');
   if(sgarray[0]!=undefined){
      $('#oneri1').html("");
      $('<p><li><a href="#" id="request" i=' + 0 + '>' + sgarray[0]+ '</a></li></p>').appendTo('#oneri1');
   }
   if(sgarray[1]!=undefined){
      $('#oneri2').html("");
      $('<p><li><a href="#" id="request" i=' + 1 + '>' + sgarray[1]+ '</a></li></p>').appendTo('#oneri2');
   }
   if(sgarray[2]!=undefined){
      $('#oneri3').html("");
      $('<p><li><a href="#" id="request" i=' + 2 + '>' + sgarray[2]+ '</a></li></p>').appendTo('#oneri3');
   }
   if(sgarray[3]!=undefined){
      $('#oneri4').html("");
      $('<p><li><a href="#" id="request" i=' + 3 + '>' + sgarray[3]+ '</a></li></p>').appendTo('#oneri4');
     
   }
   if(sgarray[4]!=undefined){
      $('#oneri5').html("");
      $('<p><li><a href="#" id="request" i=' + 4 + '>' + sgarray[4]+ '</a></li></p>').appendTo('#oneri5');
   }
   t = $("#textarea");
   t.css("border", "blue");
   t.css("color", "blue");
   p = $("#kelime");
  p.css("border", "1px solid blue");
  p.css("color", "blue");
  o1 = $("#oneri1");
  o1.css("color", "blue");
   o2 = $("#oneri2");
  o2.css("color", "blue");
   o3 = $("#oneri3");
  o3.css("color", "blue");
   o4 = $("#oneri4");
  o4.css("color", "blue");
   o5 = $("#oneri5");
  o5.css("color", "blue");
   o6=$("#koneri")
   o6.css("color","blue");
   $("#results").fadeOut();
   $("#cevir").fadeIn();
  
}


var sgarray = new Array(10);
var sg=0;
var oneri;
function suggest(){
   suggestions="'"+suggestions+"'";
   suggestions = suggestions.split(","); 
   var sglength=suggestions.length;
   if(sg<sglength){
                      
   suggestions[sg] = suggestions[sg].replace("'", "");
   suggestions[sg] = suggestions[sg].replace("'", "");
   oneri=suggestions[sg];
   sgrequest();
                     
   }
   else{
     sonuc(); 
   }
   
}
function sgrequest(){
   orequest=new XMLHttpRequest({mozSystem:true});
  orequest.open('GET', 'http://'+url+oneri,true,username,password);  
  orequest.timeout = 5750;
	orequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
    
   orequest.send();
	
	/*when request change status*/
   
	orequest.onreadystatechange=function(){

          if(orequest.status==200 && orequest.readyState==4){
                       var data = JSON.parse(orequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       sgarray[sg]=obj.target_f ;
                       sg=sg+1;
                       suggest();
}
  }
}
/*
function sgrequest(){
   
  mrequest=new XMLHttpRequest({mozSystem:true});
  mrequest.open('GET', 'http://api.ozdincer.com'+oneri,true,username,password);  
  mrequest.timeout = 5750;
	mrequest.addEventListener('timeout', function() {
		alert('No connection..');
	});
    
   mrequest.send();
	
	
	mrequest.onreadystatechange=function(){

          if(mrequest.status==200 && mrequest.readyState==4){
                       var data = JSON.parse(mrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var sgarray[sg]=obj.target_f ;
                       alert(target_f);
                       sg=sg+1;
                       suggest();
                       
          } 
   
}
}
*/


$(document).on('click','#onceki',function(){
    if(ceviributton==1){
       c=c-1;
       cevirionceki();
      
     
   }
   else{
      c=c-1;
	/*paste the URL to get*/
	nrequest=new XMLHttpRequest({mozSystem:true});
  nrequest.open('GET', 'http://'+url+req,true,username,password);  
  nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
    
   nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){

          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       length=selected.length; 
                       drequest=new XMLHttpRequest({mozSystem:true});
                       map=selected[c];
              drequest.open('GET', 'http://'+url+selected[c],true,username,password);
              drequest.send();
             drequest.onreadystatechange=function(){
                 

                 if(drequest.status==200 && drequest.readyState==4){
                    var data = JSON.parse(drequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    deger=obj.source_f;
                    value=obj.target_f;
                 suggestions=obj.suggestions;
                    $('#oneri1').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri1');
                    $('#oneri2').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri2');
                    $('#oneri3').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri3');
                    $('#oneri4').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri4');
                    $('#oneri5').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri5');
                    sg=0;
                    sgarray[0]=undefined;
                    sgarray[1]=undefined;
                    sgarray[2]=undefined;
                    sgarray[3]=undefined;
                    sgarray[4]=undefined;
                    if(suggestions==""){
                       ssonuc();
                    }
                    else{
                       suggest();
                    }
                 }
             }       
          }
  }
    
    
   } 
    
	}); 
function cevirionceki(){
	/*paste the URL to get*/
	nrequest=new XMLHttpRequest({mozSystem:true});
  nrequest.open('GET', 'http://'+url+req,true,username,password);  
  nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
    
   nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){

          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       length=selected.length; 
                       wrequest=new XMLHttpRequest({mozSystem:true});
                       map=selected[c];
              wrequest.open('GET', 'http://'+url+selected[c],true,username,password);
              wrequest.send();
              wrequest.onreadystatechange=function(){
                 

                 if(wrequest.status==200 && wrequest.readyState==4){
                    var data = JSON.parse(wrequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    deger=obj.source_f;
                    value=obj.target_f;
                 suggestions=obj.suggestions;
                    $('#oneri1').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri1');
                    $('#oneri2').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri2');
                    $('#oneri3').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri3');
                    $('#oneri4').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri4');
                    $('#oneri5').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri5');
                    sg=0;
                    sgarray[0]=undefined;
                    sgarray[1]=undefined;
                    sgarray[2]=undefined;
                    sgarray[3]=undefined;
                    sgarray[4]=undefined;
                    if(obj.state==50){
                    if(suggestions==""){
                      // dil();
                       ssonuc();
                    }
                    else{
                     //  dil();
                       suggest();
                    }
                    }
                    }
                    if(obj.state!=50){
                       c=c-1;
                       cevirionceki();
                    }
                 }
             }       
          }
}
    
function cevirisonraki(){
	/*paste the URL to get*/
	nrequest=new XMLHttpRequest({mozSystem:true});
  nrequest.open('GET', 'http://'+url+req,true,username,password);  
  nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
    
   nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){

          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       length=selected.length; 
                       zrequest=new XMLHttpRequest({mozSystem:true});
                       map=selected[c];
              zrequest.open('GET', 'http://'+url+selected[c],true,username,password);
              zrequest.send();
              zrequest.onreadystatechange=function(){
                 

                 if(zrequest.status==200 && zrequest.readyState==4){
                    var data = JSON.parse(zrequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    deger=obj.source_f;
                    value=obj.target_f;
                 suggestions=obj.suggestions;
                    $('#oneri1').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri1');
                    $('#oneri2').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri2');
                    $('#oneri3').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri3');
                    $('#oneri4').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri4');
                    $('#oneri5').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri5');
                    sg=0;
                    sgarray[0]=undefined;
                    sgarray[1]=undefined;
                    sgarray[2]=undefined;
                    sgarray[3]=undefined;
                    sgarray[4]=undefined;
                    if(obj.state==50){
                    if(suggestions==""){
                      // dil();
                       ssonuc();
                    }
                    else{
                     // dil();
                       suggest();
                    }
                    }
                    }
                    if(obj.state!=50){
                       c=c+1;
                       cevirisonraki();
                    }
                 }
             }       
          }
  }
    


$(document).on('click','#sonraki',function(){
		
		  

   if(ceviributton==1){
      c=c+1;
       cevirisonraki();
     
   }
      
   else{
      c=c+1;
   
	/*paste the URL to get*/
	nrequest=new XMLHttpRequest({mozSystem:true});
  nrequest.open('GET', 'http://'+url+req,true,username,password);  
  nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
    
   nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){

          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       length=selected.length;
                       map=selected[c];
                       yrequest=new XMLHttpRequest({mozSystem:true});
              yrequest.open('GET', 'http://'+url+selected[c],true,username,password);
              yrequest.send();
              yrequest.onreadystatechange=function(){
                 

                 if(yrequest.status==200 && yrequest.readyState==4){
                    var data = JSON.parse(yrequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    deger=obj.source_f;
                    value=obj.target_f;
                   suggestions=obj.suggestions;
                    $('#oneri1').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri1');
                    $('#oneri2').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri2');
                    $('#oneri3').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri3');
                    $('#oneri4').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri4');
                    $('#oneri5').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri5');
                    sg=0;
                    sgarray[0]=undefined;
                    sgarray[1]=undefined;
                    sgarray[2]=undefined;
                    sgarray[3]=undefined;
                    sgarray[4]=undefined;
                   if(suggestions==""){
                       ssonuc();
                    }
                    else{
                       suggest();
                    }
                 }
             }       
          }
  }
   } 
	});  


var farray = new Array(10);
var flength;
var x=0;
var requestfile;
function files(){
   $("#cevir").fadeOut();
   $("#registerresult").fadeOut();
   var data = JSON.parse(request.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
   var selectfile=obj.objects[language].translation_projects[selectproject];
              newrequest=new XMLHttpRequest({mozSystem:true});
              newrequest.open('GET', 'http://'+url+selectfile,true,username,password);
              newrequest.send();
      newrequest.onreadystatechange=function(){

          if(newrequest.status==200 && newrequest.readyState==4){
                  
                    var data = JSON.parse(newrequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    var file=obj.stores;
                    file="'"+file+"'";
                    file = file.split(","); 
                    flength=file.length;
                    
              if(x<flength) {
              file[x] = file[x].replace("'", "");
              file[x] = file[x].replace("'", "");
              requestfile=file[x]; 
              pro();
          
         
      }
             else {
                 list();
              }
            
            
   
             
}
      }
      
   
   function pro(){
              frequest=new XMLHttpRequest({mozSystem:true});
              frequest.open('GET', 'http://'+url+requestfile,true,username,password);
              frequest.send();
      frequest.onreadystatechange=function(){

          if(frequest.status==200 && frequest.readyState==4){
                  
                    var data = JSON.parse(frequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    var goster=obj.name;
                    farray[x]=goster;
                    x=x+1;
             files();
          }
          }
      
   }
   function list(){
    results.innerHTML = "";
   results.innerHTML +=" <tr><td><button id='projeback' data-l10n-id='back' class='btn-success im buttonBlue'>"+"BACK"+"</button></td><td><button id='logout' data-l10n-id='logout' class='btn-success im buttonBlue'>"+"LOGOUT"+"</button></td></tr>";

      
  for (i=0;i<flength;i++) {
     var filecss='<p id=css>'+farray[i]+'</p>'   
        results.innerHTML +='<header class="listheaderBlue borderBlue">'+'<ul data-role="listview" data-inset="true" class="listview"><li><a href="#" id="file"  i=' + i + '>'+'<h5>'+'<img src="https://m1.behance.net/rendition/modules/76250619/disp/c260fc99b46349f97246fa61701926b3.png"' + filecss +'</h5>'+'</a></li>'+ '</ul>'+'</header>';           
          
                        // results.innerHTML +='<header class="listheaderBlue borderBlue ">'+'<ul data-role="listview" data-inset="true" class="list"><li><a href="#" id="file" i=' + i + '>' + farray[i] +'</a></li>'+ '</ul>'+'</header>'; 
                            
                        }
      x=0;
      
    
  }
  
  /* function requestfile(){
      frequest=new XMLHttpRequest({mozSystem:true});
              frequest.open('GET', 'http://api.ozdincer.com'+file[x],true,username,password);
              frequest.send();
             frequest.onreadystatechange=function(){

          if(frequest.status==200 && frequest.readyState==4){
                  
                    var data = JSON.parse(frequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    var farray[x]=obj.name;
             alert(farray[x]);
}
      }
      
   }
   */
  /* function control(){
      if (x<flength){
             x=x+1;
         requestfile();
         
             
          }
                else{
                   filename();
                }
   }
   function filename(){
      results.innerHTML = "";
      for (i=0;i<flength;i++) {
                          
                         results.innerHTML +='<header class="listheaderBlue borderBlue ">'+'<ul data-role="listview" data-inset="true" class="list"><li><a href="#" id="project" i=' + i + '>' + array[i] +'</a></li>'+ '</ul>'+'</header>'; 
                            
                        }
   }
   */
   
}

var array = new Array(10);
var length;
function projects(){
      $("#registerresult").fadeOut();
      $("#cevir").fadeOut();
      var data = JSON.parse(request.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
      var selected=obj.objects[language].translation_projects;
      selected="'"+selected+"'";
      selected = selected.split(","); 
      length=selected.length;
      if(a<length) {
              selected[a] = selected[a].replace("'", "");
              selected[a] = selected[a].replace("'", "");
              lang();
          
         
      }
   else {
      liste();
   }
   function lang(){
              newrequest=new XMLHttpRequest({mozSystem:true});
              newrequest.open('GET', 'http://'+url+selected[a],true,username,password);
              newrequest.send();
      newrequest.onreadystatechange=function(){

          if(newrequest.status==200 && newrequest.readyState==4){
                  
                    var data = JSON.parse(newrequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    var list=obj.real_path;
                    list="'"+list+"'";
                    list = list.split("/");  
                    list[0] = list[0].replace("'", "");
                    array[a]=list[0];
                    a=a+1;
             projects();
          }
          }
   }
 
 function liste(){
     results.innerHTML = "";
   results.innerHTML +=" <tr><td><button id='languageback' data-l10n-id='back' class='btn-success im buttonBlue'>"+"BACK"+"</button></td><td><button id='logout' data-l10n-id='logout' class='btn-success im buttonBlue'>"+"LOGOUT"+"</button></td></tr>";

  
  for (i=0;i<length;i++) {
            
                         var projectcss='<p id=css>'+array[i]+'</p>'   
                         results.innerHTML +='<header class="listheaderBlue borderBlue">'+'<ul data-role="listview" data-inset="true" class="listview"><li><a href="#" id="project"  i=' + i + '>'+'<h5>'+'<img src="http://www.dijitalteknoloji.net/wp-content/uploads/2012/04/foldercolorizer.jpg"' + projectcss+'</h5>'+'</a></li>'+ '</ul>'+'</header>';           
            
                        // results.innerHTML +='<header class="listheaderBlue borderBlue ">'+'<ul data-role="listview" data-inset="true" class="list"><li><a href="#" id="project" i=' + i + '>' + array[i] +'</a></li>'+ '</ul>'+'</header>'; 
                            
                        }
    a=0;
    //files();
  }
}
var ceviributton=0;
function cevirigerekli(){
   $("#registerresult").fadeOut();
   results.innerHTML = "";
   results.innerHTML +=" <tr><td><button id='tableback' data-l10n-id='back' class='btn-success im buttonBlue'>"+"BACK"+"</button></td><td><button id='logout' data-l10n-id='logout' class='btn-success im buttonBlue'>"+"LOGOUT"+"</button></td></tr>";

   var data = JSON.parse(newrequest.response);
   stringdata=JSON.stringify(data);
   var obj = JSON.parse(stringdata);
   var store=obj.stores;
   store="'"+store+"'";
   store = store.split(","); 
   slength=store.length;
   store[sfile] = store[sfile].replace("'", "");
   store[sfile] = store[sfile].replace("'", "");
   req=store[sfile];
   nrequest=new XMLHttpRequest({mozSystem:true});

	
	/*paste the URL to get*/
	
	nrequest.open('GET', 'http://'+url+req,true,username,password);
	
	/*set the timeout to detect connection issues*/
	nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
	
	/*send the request*/
	nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){

          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       selected[c] = selected[c].replace("'", "");
                       clength=selected.length; 
             
                       crequest=new XMLHttpRequest({mozSystem:true});
              crequest.open('GET', 'http://'+url+selected[c],true,username,password);
              crequest.send();
              crequest.onreadystatechange=function(){
                 map=selected[c];

                 if(crequest.status==200 && crequest.readyState==4){
                    var data = JSON.parse(crequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    if (obj.state==50){
                    deger=obj.source_f;
                    value=obj.target_f;
                    suggestions=obj.suggestions;
                    if(suggestions==""){
                      // dil();
                       ssonuc();
                    }
                    else{
                      //dil();
                       suggest();
                    }
                    }
                    if(obj.state!=50){
                       c=c+1;
                       cevirigerekli();
                    }
                    
                   /*  $("#results").fadeOut();
                     $("#cevir").fadeIn();
                     $('#kelime').html("");
                     $("<p>" + deger+ "</p>").appendTo('#kelime');
                     $('textarea').val(value);
                    */
                    
                 }
             }      
              
}
}
}
var t;
var cg;
function tablee(){
   
   $("#registerresult").fadeOut();
   results.innerHTML = "";
     cg="NEED TRANSLATION";
     t="TOTAL";
    
   var data = JSON.parse(newrequest.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
   var store=obj.stores;
   store="'"+store+"'";
   store = store.split(","); 
   slength=store.length;
   store[sfile] = store[sfile].replace("'", "");
   store[sfile] = store[sfile].replace("'", "");
   req=store[sfile];
    
      
   strequest=new XMLHttpRequest({mozSystem:true});

	
	/*paste the URL to get*/
	
	strequest.open('GET', 'http://'+url+req+'statistics/',true,username,password);
	
	/*set the timeout to detect connection issues*/
	strequest.timeout = 5750;
	strequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
	
	/*send the request*/
	strequest.send();
	
	/*when request change status*/
	strequest.onreadystatechange=function(){
          if(strequest.status==200 && strequest.readyState==4){
                       
                       var data = JSON.parse(strequest.response);
                       stringdata=JSON.stringify(data);
                        var obj = JSON.parse(stringdata);
                        topp=obj.statistics.total.words;
                       need=obj.statistics.untranslated.words;
             results.innerHTML = "";
              results.innerHTML +=" <tr><td><button id='fileback' data-l10n-id='back' class='btn-success im buttonBlue'>"+"BACK"+"</button></td><td><button id='logout' data-l10n-id='logout' class='btn-success im buttonBlue'>"+"LOGOUT"+"</button></td></tr>";

             results.innerHTML += '<table><tr><td><p id="t" data-l10n-id="t" >' + t + '</p></td><td><p id="cg" data-l10n-id="cg" >'+cg+'</p></td></tr><tr><td><button id="toplam" class="btn-success input buttonBlue" ><p id="t1" >'+topp+'</p></button></td><td><button id="cevirigerekli" class="btn-success input buttonBlue"><p id="t2" >'+need+'</p></button></td></tr></table>';
    
             
   t1 = $("#cg");
   t1.css("border", "2px solid blue");
   t1.css("color", "blue");       
   t2= $("#t");
   t2.css("border", "2px solid blue");
   t2.css("color", "blue");    
              }
  }
  
    
 //  results.innerHTML += '<table><tr><td><p id="cg" >' + cg + '</p></td><td><p id="t" >'+t+'</p></td></tr><tr><td><button id="toplam" class="btn-success input buttonBlue" ><p id="top1" >'+top+'</p></button></td><td><button id="cevirigerekli" class="btn-success input buttonBlue"><p id="top2" >'+need+'</p></button></td></tr></table>';
         
   
                                                                                                                 
  

   /*var table = $("#field");
var	rowCnt=2;
var	colCnt=3;

	for (var i=0; i<rowCnt; i++) {
      var newRow = $("<tr></tr>");
      for (var j=0; j<colCnt; j++) {
         var newCell = $("<td></td>");      
         // newCell.html(field[i][j]==null ? "&nbsp;" : field[i][j]); 
         newCell.html("&nbsp;")
                .data({ "row": i, "col": j})                
                .addClass("closed")         
                .appendTo(newRow);
      }
      table.append(newRow);      
    }    
*/
	
//$('<table id="field"><thead><tr><th>'+"Çevrilicek"+'</th><th>'+"Çevrilmiş"+'</th></tr></thead><tbody><tr><td>'+"123"+'</td><td>'+""112+'</td></tr></tbody></table>');

}
/*
function make_base_auth(username, password) {
    var tok = username + ':' + password;
    var hash = btoa(tok);
    return 'Basic ' + hash;
}*/
function gonderr(){
   var cevir=$("textarea").val();
   var desc=cevir.split(" "); 
//  var melek=cevir.length;
  // alert(melek);
   
   prequest=new XMLHttpRequest({mozSystem:true});
  prequest.open('PUT','http://'+url+map,true,username,password);
   //prequest.open('GET','api.ozdincer.com/api/v1/suggestions/8/',true,username,password);
   prequest.setRequestHeader("Content-Type", "application/json");

prequest.timeout = 5750;
	prequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
   
  // prequest.send( );
    if (ceviributton==1){
  prequest.send(JSON.stringify({
"target_f": ''+cevir+'',
"target_length": ''+cevir.length+'',
"target_wordcount":''+desc.length+'',
"translator_comment":"",
"suggestions": "",
     "state":"200"

}));
    } 
   else{
      prequest.send(JSON.stringify({
"target_f": ''+cevir+'',
"target_length": ''+cevir.length+'',
"target_wordcount":''+desc.length+'',
"translator_comment":"",
"suggestions": ""

}));
   }
  // prequest.send("deneme");
 // prequest.send('{target_f:"denemeee"}');
   
 /* prequest.onreadystatechange=function(){
      alert(prequest.status);
      var data = JSON.parse(prequest.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
      alert(stringdata);
   }*/
      /*
      username=$('input[name=username]').val();
	password=$('input[name=password]').val();
   
   prequest=new XMLHttpRequest({mozSystem:true});
  prequest.open('PUT','http://api.ozdincer.com/api/v1/units/66251/',true,username,password);
   //prequest.open('GET','api.ozdincer.com/api/v1/suggestions/8/',true,username,password);
   prequest.setRequestHeader("Content-Type", "application/json");

prequest.timeout = 5750;
	prequest.addEventListener('timeout', function() {
		alert('No connection..');
	});
   //prequest.send( );
  prequest.send(JSON.stringify({
"target_f": "deneme",
"target_length": "52",
"target_wordcount":"45",
"translator_comment": "denemeee",
"suggestions": ""

}));
   
  // prequest.send("deneme");
 // prequest.send('{target_f:"denemeee"}');
   
   prequest.onreadystatechange=function(){
      alert(prequest.status);
      var data = JSON.parse(prequest.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
      alert(stringdata);
      */
 c=c+1;
	/*paste the URL to get*/
	nrequest=new XMLHttpRequest({mozSystem:true});
  nrequest.open('GET', 'http://'+url+req,true,username,password);  
  nrequest.timeout = 5750;
	nrequest.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
    
   nrequest.send();
	
	/*when request change status*/
	nrequest.onreadystatechange=function(){
   
          if(nrequest.status==200 && nrequest.readyState==4){
                       var data = JSON.parse(nrequest.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       var selected=obj.units;
                       selected="'"+selected+"'";
                       selected = selected.split(","); 
                       selected[c] = selected[c].replace("'", "");
                       length=selected.length; 
                       map=selected[c];
                       newrequest=new XMLHttpRequest({mozSystem:true});
              newrequest.open('GET', 'http://'+url+selected[c],true,username,password);
              newrequest.send();
              newrequest.onreadystatechange=function(){
                 

                 if(newrequest.status==200 && newrequest.readyState==4){
                    var data = JSON.parse(newrequest.response);
                    stringdata=JSON.stringify(data);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    deger=obj.source_f;
                    value=obj.target_f;
                   suggestions=obj.suggestions;
                    $('#oneri1').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri1');
                    $('#oneri2').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri2');
                    $('#oneri3').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri3');
                    $('#oneri4').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri4');
                    $('#oneri5').html("");
                    $('<p>'+""+'</p>').appendTo('#oneri5');
                    sg=0;
                    sgarray[0]=undefined;
                    sgarray[1]=undefined;
                    sgarray[2]=undefined;
                    sgarray[3]=undefined;
                    sgarray[4]=undefined;
                   if(suggestions==""){
                       ssonuc();
                    }
                    else{
                       suggest();
                    }
                 }
             }       
          }
  }
    



 }
/*
function tr(){
   trequest=new XMLHttpRequest({mozSystem:true});
  trequest.open('GET','https://www.googleapis.com/language/translate/v2',true);
 // trequest.send("key": "API-KEY","source": "en","target": "tr","q": "angel");
   /*trequest.onreadystatechange=function(){
   alert(trequest.status);
}
}
*/


var connection="No connection..";
/*send the xmlHTTPRequest*/
function sendRequest(username,password){
        
   request=new XMLHttpRequest({mozSystem:true});

	
	/*paste the URL to get*/
	
	request.open('GET', 'http://'+url+'/api/v1/languages/',true,username,password);
	
	/*set the timeout to detect connection issues*/
	request.timeout = 5750;
	request.addEventListener('timeout', function() {
		alert(connection);
     noconnect();
	});
	
	/*send the request*/
	request.send();
	
	/*when request change status*/
	request.onreadystatechange=function(){

          if(request.status==200 && request.readyState==4){
             $("#cevir").fadeOut();
             //var giris="Login Successful.."
			        // alert(giris);
		      // alert('Giriş Başarılı..');
                       var data = JSON.parse(request.response);
                       stringdata=JSON.stringify(data);
                        var obj = JSON.parse(stringdata);
                       results.innerHTML = "";
                       $("#inputs").fadeOut();
            // gonderr();
						         languages();
                     // tr();
                     //  kullanici();
      

      }  
     

		/*if it's ready but hasn't find any page*/
		if(request.status == 401 && request.readyState==4){
      var incorrect="Password incorrect.."
			alert(incorrect);
			$('input[name=password]').val("");
			$('input[name=username]').val("");
			return;
		}
     
     
		
		/*if it's ready and has found the URL*/
		
};
	}


