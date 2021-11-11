$(document).ready(function(){

	$("#get_code").on('click',function(){
		$("#get_code").prop("disabled", true);
		if($("#email").val()==''||$("#email").val()==null){
			window.alert.show("error",$("#email_cannot_null").html(),"2000");
			setTimeout(function(){
				$("#get_code").css("pointer-events", "auto");
			},2000)
		}else{
		 var formData = new FormData();
		 formData.append("username", $("#email").val());
		 $.ajax({

             type: "POST",
             url: "/api/v1/web/user/forgotPassword",
             data: formData,
			 contentType: false,
             processData: false,
             dataType: "json",
             success: function(response) {
				 $("#get_code").prop("disabled", false);
            	 console.log(response)
            	 switch (response.status.code) {
                 case 4:
                	 window.alert.show("error","このアカウントは存在しない。","2000");
                     break;
                 case 1000:
                     window.alert.show("success",$("#check_email_get_code_set_password").html(),"1200");
                     setTimeout(function(){
                		 window.location.href = '/set-password';
                	 },1200)
                     break;    
             }
             }
         })
	}
	})
})