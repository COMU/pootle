$(document).on('click','#submit',function(){
		
		
		username=$('input[name=username]').val();
		password=$('input[name=password]').val();
		
		
		if(username == '' || password== ''){
			alert('Please insert values');
		}else{
			sendRequest(username,password);
		}
	});
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
                         alert(request.status);
		         alert('giriş başarılı');
			/*xml parsing the answer*/
			
			
		
		}


		/*if it's ready but hasn't find any page*/
		if(request.status == 404 && request.readyState==4){
			alert(request.status);
			$('input[name=password]').val("");
			$('input[name=username]').val("");
			return;
		}
		
		/*if it's ready and has found the URL*/
		else{
                 alert(request.status); 
              }
	};
}


