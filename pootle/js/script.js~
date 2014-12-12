$(document).on('click','#submit',function(){
		
		
		username=$('input[name=username]').val();
		password=$('input[name=password]').val();
		
		
		if(username == '' || password== ''){
			alert('Please insert values');
		}else{
			sendRequest(username,password);
		}
	});

function projects(){
      var data = JSON.parse(request.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
      var selected=obj.objects[0].translation_projects;
      selected="'"+selected+"'";
      selected = selected.split(","); 
      length=selected.length;

      for(var a=0; a<length; a++) {
              selected[a] = selected[a].replace("'", "");
              alert(selected[a]);
              newrequest=new XMLHttpRequest({mozSystem:true});
              newrequest.open('GET', 'http://api.ozdincer.com'+selected[a],true,username,password);
              newrequest.send();
              newrequest.onreadystatechange=function(){
                 

                 if(newrequest.status==200 && newrequest.readyState==4){
                    var data = JSON.parse(newrequest.response);
                    stringdata=JSON.stringify(data);
                    alert(stringdata);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    files();
                    alert(obj.real_path);
                    for (var j in stringdata) {
                            results.innerHTML += '<header class="listheaderBlue borderBlue">'+'<p class="project">'+obj.real_path+'</p>'; 
                        }
                 }
      }
     }

}
function files(){
      var data = JSON.parse(newrequest.response);
      stringdata=JSON.stringify(data);
      var obj = JSON.parse(stringdata);
      var selected=obj.stores;
      selected="'"+selected+"'";
      selected = selected.split(","); 
      length=selected.length;

            for(var y=0; y<length; y++) {
              selected[y] = selected[y].replace("'", "");
              alert(selected[y]);
              filerequest=new XMLHttpRequest({mozSystem:true});
              filerequest.open('GET', 'http://api.ozdincer.com'+selected[y],true,username,password);
              filerequest.send();
              filerequest.onreadystatechange=function(){
               if(filerequest.status==200 && filerequest.readyState==4){
                    var data = JSON.parse(filerequest.response);
                    stringdata=JSON.stringify(data);
                    alert(stringdata);
                    var obj = JSON.parse(stringdata);
                    results.innerHTML = "";
                    alert(obj.name);
                    for (var j in stringdata) {
                            results.innerHTML += '<header class="listheaderBlue borderBlue">'+'<p class="project">'+obj.name+'</p>'; 
                        }
                 }
                 
           }


}

/*send the xmlHTTPRequest*/
function sendRequest(username,password){
        

        request=new XMLHttpRequest({mozSystem:true});

	
	/*paste the URL to get*/
	
	request.open('GET', 'http://api.ozdincer.com/api/v1/languages/',true,username,password);
	
	/*set the timeout to detect connection issues*/
	request.timeout = 5750;
	request.addEventListener('timeout', function() {
		alert('No connection..');
	});
	
	/*send the request*/
	request.send();
	
	/*when request change status*/
	request.onreadystatechange=function(){

          if(request.status==200 && request.readyState==4){
		       alert('Giriş Başarılı..');
                       var data = JSON.parse(request.response);
                       stringdata=JSON.stringify(data);
                       var obj = JSON.parse(stringdata);
                       results.innerHTML = "";
                      
                       
                             
                // array[i]= obj.objects[i].fullname+"<hr/>"; 
//$('#movie-list').append('<header class="listheaderBlue borderBlue">'+'<p class="language">'+obj.objects[i].fullname+'</p>');
                             
                   
                        for (var i in stringdata) {
                            results.innerHTML += '<header class="listheaderBlue borderBlue">'+'<p class="language">'+obj.objects[i].fullname+'</p>'; 
                        }

          }

          

		/*if it's ready but hasn't find any page*/
		if(request.status == 401 && request.readyState==4){
			alert('Parola yanlış..');
			$('input[name=password]').val("");
			$('input[name=username]').val("");
			return;
		}
		
		/*if it's ready and has found the URL*/
		
};
	}

