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
		        alert('giriş başarılı');
			/*xml parsing the answer*/
			request.open('GET', 'http://api.ozdincer.com/',true);
			response=request.responseText;
			responseDoc=$.parseXML(response);
			$response=$(responseDoc);
		
			/*scraping the answer to find useful infos, then clear current list*/
			titolo= $response.find('Languages');
			$('#reslist').empty();
			
			/*iterate all the values*/
			for(i=0;i<=titolo.length; i++){
					linkst=$response.find('entry link')[i].getAttribute("href");
					/*show results*/
					$('#reslist').append('<header class="listheaderBlue borderBlue">'+''+'</header><a href="#" id="foo" title="'+linkst+'">'+''+'<p class="Languages">'+titolo[i].textContent+'</p></a>');

                         
                      }
                        goToCard(1);
			
			/*add this datas to storage*/
			searchData={
				'languages':Languages,
				
			};
			
		  }
	

		/*if it's ready but hasn't find any page*/
		if(request.status == 401 && request.readyState==4){
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
function goToCard(cardNum){
	document.querySelector('x-deck').showCard(cardNum);
	
	/*reset inputs and clear lists*/
	$('input[name=Languages]').val("");
	$('#list_his').empty();
	$('#star_list').empty();	
	
}

